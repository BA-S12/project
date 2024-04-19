package com.example.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Investment extends Application {
//    Declare a buttons
    private Button btn_calculate, btn_export;

// Declare a Labels
    private Label lbl_initialBalanceTitle,
                lbl_interestRateTitle,
                lbl_years,
                lbl_yearHint,
                lbl_cye,
                lbl_cycHint,
                lbl_totalBalance,
                lbl_totalBalanceHint,
                lbl_message;

//    Declare a Text fields
    private TextField tf_initialBalance,tf_interestRate;

//    Declare a Text Area
    private TextArea ta_history;

//    The variable to change the value in yearsPane && cyePane && totalBalancePane
    private double balance;
    private int years;

//    Create object for what we declared and invoked in start method
        private void initialize(){
            btn_calculate =new Button("Calculate");
            btn_export =new Button("Export");

            lbl_initialBalanceTitle = new Label("Initial Balance");
            lbl_interestRateTitle = new Label("Interest Rate");
            lbl_years = new Label("0");
            lbl_yearHint = new Label("Years");
            lbl_cye = new Label("0");
            lbl_cycHint = new Label("Current year earnings");
            lbl_totalBalance = new Label("0");
            lbl_totalBalanceHint = new Label("Total Balance");
            lbl_message = new Label("");
            tf_initialBalance =new TextField();
            tf_interestRate =new TextField();

            ta_history = new TextArea();

        }

//        Function to set padding for component
        private void setPadding(double padding, Control... controls){
            for (Control control:controls)
                control.setPadding(new Insets(padding));

        }

        //  Function to set font for component
        private void setFont(double size, Control... controls){
            for (Control control:controls){
                if (control instanceof  TextInputControl){
                    ((TextInputControl) control).setFont(new Font(size));
                }
                else if (control instanceof Labeled){
                    ((Labeled) control).setFont(new Font(size));
                }
            }

        }

//        Function to style text
        private void styleText (String color,String weight,Control... controls){
            for (Control control:controls){
                control.setStyle("-fx-font-weight:"+weight+';'
                        +"-fx-text-fill:"+color+';');
            }
        }
        private void styleText (String color,Control... controls){
        for (Control control:controls){
            control.setStyle("-fx-text-fill:"+color+';');
        }
    }



    @Override
    public void start(Stage stage) throws IOException {
            initialize(); // method to create object

        //Line 1
        HBox inputPane = new HBox(
                lbl_initialBalanceTitle,
                tf_initialBalance,
                lbl_interestRateTitle,
                tf_interestRate
        );
        inputPane.setAlignment(Pos.CENTER);


//        Label for to what the user entered in text field
        HBox messagePane = new HBox(
                lbl_message
        );
        messagePane.setAlignment(Pos.CENTER);

//      make button width take full width of their parent
        btn_calculate.setMaxWidth(Double.MAX_VALUE);


        btn_calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Get the text from input fields
                String initialBalanceText = tf_initialBalance.getText().trim();
                String interestRateText = tf_interestRate.getText().trim();

                // Check if fields are empty
                if (initialBalanceText.isEmpty() || interestRateText.isEmpty()) {
                    lbl_message.setText("You must enter numerical value.");
                }

                // Check if values are non-numeric
                else if (!initialBalanceText.matches("\\d+(\\.\\d+)?") || !interestRateText.matches("\\d+(\\.\\d+)?")) {
                    lbl_message.setText("You must enter numerical value.");
                }

                // Parse the input values
                else {
                    double initBalance = Double.parseDouble(initialBalanceText);
                    double interestRate = Double.parseDouble(interestRateText);

                    // Check if values are less than or equal to zero
                    if (interestRate <= 0 || initBalance <= 0) {
                        lbl_message.setText("You must enter numbers greater than 0.");
                        return; // Exit the method
                    }

                    // Calculate and update the balance
                    if (years == 0) {
                        balance = initBalance;
                    }

                    years++;

                    double cyi = balance * interestRate / 100;
                    balance += cyi;

                    lbl_years.setText(String.valueOf(years));
                    lbl_cye.setText(String.format("$%.2f", cyi));
                    lbl_totalBalance.setText(String.format("$%.2f", balance));

                    ta_history.appendText("Year #: " + years +
                            " you earned: " + String.format("$%.2f", cyi) +
                            " and your total balance is: " + String.format("$%.2f", balance) + "\n");

                    // Clear the message label
                    lbl_message.setText("");
                }
            }
        });

// Create UI panes for displaying output
        VBox yearsPane = new VBox(lbl_years, lbl_yearHint);
        yearsPane.setAlignment(Pos.CENTER);

        VBox cyePane = new VBox(lbl_cye, lbl_cycHint);
        cyePane.setAlignment(Pos.CENTER);

        VBox totalBalancePane = new VBox(lbl_totalBalance, lbl_totalBalanceHint);
        totalBalancePane.setAlignment(Pos.CENTER_RIGHT);

        HBox outputPane = new HBox(yearsPane, cyePane, totalBalancePane);
        outputPane.setAlignment(Pos.CENTER);

// Setup export button
        btn_export.setMaxWidth(Double.MAX_VALUE);
        btn_export.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Create file chooser dialog
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Investment file ");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

                // Show save dialog and get selected file
                File file = fileChooser.showSaveDialog(stage);

                try (PrintWriter printWriter = new PrintWriter(file)) {
                    // Write history text to the selected file
                    printWriter.println(ta_history.getText());
                } catch (FileNotFoundException e) {
                    // Handle file not found exception
                }
            }
        });

// Create root pane and arrange UI components
        VBox rootPane = new VBox(
                inputPane,
                messagePane,
                btn_calculate,
                outputPane,
                ta_history,
                btn_export);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setMinSize(500, 500);

// Set padding for UI components
        setPadding(8,
                lbl_initialBalanceTitle,
                lbl_interestRateTitle,
                tf_interestRate,
                tf_initialBalance,
                lbl_years,
                lbl_yearHint,
                lbl_cye,
                lbl_cycHint,
                lbl_totalBalance,
                lbl_totalBalanceHint,
                ta_history,
                btn_calculate,
                btn_export
        );

// Set font size for UI components
        setFont(16,
                lbl_initialBalanceTitle,
                lbl_interestRateTitle,
                tf_interestRate,
                tf_initialBalance,
                lbl_years,
                lbl_yearHint,
                lbl_cye,
                lbl_cycHint,
                lbl_totalBalance,
                lbl_totalBalanceHint,
                ta_history,
                btn_calculate,
                btn_export
        );
        setFont(40,
                lbl_years,
                lbl_cye,
                lbl_totalBalance);

// Apply text styling to UI components
        styleText("red", "bold", lbl_years, lbl_cye, lbl_totalBalance, lbl_message);
        styleText("red", lbl_yearHint, lbl_cycHint, lbl_totalBalanceHint);

// Create and show the scene
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("Investment app");
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
