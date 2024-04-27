import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Design {

    protected Button btn_calculate, btn_export, btn_clear;

// Declare a Labels
    protected Label lbl_initialBalanceTitle,
            lbl_interestRateTitle,
            lbl_years,
            lbl_yearHint,
            lbl_cye,
            lbl_cycHint,
            lbl_totalBalance,
            lbl_totalBalanceHint,
            lbl_message;

//    Declare a Text fields
    protected TextField tf_initialBalance, tf_interestRate;

//    Declare a Text Area
    protected TextArea ta_history;

    protected double balance;
    protected int years;
//    Create object for what we declared and invoked in start method

    public Design() {
        btn_calculate = new Button("Calculate");
        btn_clear = new Button("Clear");
        btn_export = new Button("Export");
        btn_export.setDisable(true);

        lbl_initialBalanceTitle = new Label("Initial Balance");
        lbl_interestRateTitle = new Label("Interest Rate");
        lbl_years = new Label("0");
        lbl_yearHint = new Label("Years");
        lbl_cye = new Label("0");
        lbl_cycHint = new Label("Current year earnings");
        lbl_totalBalance = new Label("0");
        lbl_totalBalanceHint = new Label("Total Balance");
        lbl_message = new Label("");
        tf_initialBalance = new TextField();
        tf_interestRate = new TextField();

        ta_history = new TextArea();

    }

    public void calculate() {

        // Get the text from input fields
        String initialBalanceText = tf_initialBalance.getText().trim();
        String interestRateText = tf_interestRate.getText().trim();

        // Check if fields are empty
        if (initialBalanceText.isEmpty() || interestRateText.isEmpty()) {
            lbl_message.setText("You must enter numerical value.");

        } // Check if values are non-numeric
        else if (!initialBalanceText.matches("\\d+(\\.\\d+)?") || !interestRateText.matches("\\d+(\\.\\d+)?")) {
            lbl_message.setText("You must enter numerical value.");

        } // Parse the input values
        else {

            double initBalance = Double.parseDouble(initialBalanceText);
            double interestRate = Double.parseDouble(interestRateText);

            // Check if values are less than or equal to zero
            if (interestRate <= 0 || initBalance <= 0) {
                //lbl_message.setText("You must enter numbers greater than 0.");
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

            ta_history.appendText("Year #" + years
                    + ": you earned: " + String.format("$%.2f", cyi)
                    + " and your total balance is: " + String.format("$%.2f", balance) + "\n");

            // clear the message label
            lbl_message.setText("");

            if (!ta_history.getText().equalsIgnoreCase("")) {
                btn_export.setDisable(false);
            }

        }
    }

    public void export(Stage stage) {
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

    public void clear() {

        years = 0;
        balance = 0;
        tf_initialBalance.setText("");
        tf_interestRate.setText("");
        lbl_years.setText("0");
        lbl_cye.setText("0");
        lbl_totalBalance.setText("0");
        ta_history.setText("");
        lbl_message.setText("");
        btn_export.setDisable(false);

    }
}
