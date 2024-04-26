/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Investment extends Application {

    private Design a = new Design();

    @Override
    public void start(Stage stage) throws IOException {
        a.stage = stage;
        
        Image image = new Image("icon.jpg");
        //Line 1
        HBox inputPane = new HBox(a.lbl_initialBalanceTitle, a.tf_initialBalance, a.lbl_interestRateTitle,
                a.tf_interestRate);
        inputPane.setAlignment(Pos.CENTER);

//        Label for to what the user entered in text field
        HBox messagePane = new HBox(
                a.lbl_message
        );
        messagePane.setAlignment(Pos.CENTER);

//      make button width take full width of their parent
        a.btn_calculate.setMaxWidth(Double.MAX_VALUE);

        a.btn_calculate.setOnAction(new CalculateHandler());

// Create UI panes for displaying output
        VBox yearsPane = new VBox(a.lbl_years, a.lbl_yearHint);
        yearsPane.setAlignment(Pos.CENTER);

        VBox cyePane = new VBox(a.lbl_cye, a.lbl_cycHint);
        cyePane.setAlignment(Pos.CENTER);

        VBox totalBalancePane = new VBox(a.lbl_totalBalance, a.lbl_totalBalanceHint);
        totalBalancePane.setAlignment(Pos.CENTER);

        HBox outputPane = new HBox(yearsPane, cyePane, totalBalancePane);
        outputPane.setAlignment(Pos.CENTER);

        
// Setup export button
        a.btn_export.setMaxWidth(Double.MAX_VALUE);
        a.btn_export.setOnAction(new ExportHandler());

// Create root pane and arrange UI components
        BorderPane rootPane = new BorderPane();
        
        VBox top = new VBox(inputPane,messagePane, a.btn_calculate);
        VBox bottom = new VBox(a.ta_history,a.btn_export);
        a.ta_history.setDisable(true);
        //a.ta_history.addEventFilter(KeyEvent.KEY_TYPED, e -> e.consume());
        
        rootPane.setTop(top);
        rootPane.setCenter(outputPane);
        rootPane.setBottom(bottom);
        BorderPane.setAlignment(outputPane, Pos.CENTER);
        
// Set padding for UI components
        Style.setPadding(8, a.lbl_initialBalanceTitle, a.lbl_interestRateTitle, a.tf_interestRate, a.tf_initialBalance,
                a.lbl_years, a.lbl_yearHint, a.lbl_cye, a.lbl_cycHint, a.lbl_totalBalance, a.lbl_totalBalanceHint,
                a.ta_history, a.btn_calculate, a.btn_export);

// Set font size for UI components
        Style.setFont(16, a.lbl_initialBalanceTitle, a.lbl_interestRateTitle, a.tf_interestRate, a.tf_initialBalance,
                a.lbl_years, a.lbl_yearHint, a.lbl_cye, a.lbl_cycHint, a.lbl_totalBalance, a.lbl_totalBalanceHint,
                a.ta_history, a.btn_calculate, a.btn_export);

        Style.setFont(40, a.lbl_years, a.lbl_cye, a.lbl_totalBalance);

// Apply text styling to UI components
        Style.styleText("red", "bold", a.lbl_years, a.lbl_cye, a.lbl_totalBalance, a.lbl_message);
        Style.styleText("red", a.lbl_yearHint, a.lbl_cycHint, a.lbl_totalBalanceHint);
        
// Create and show the scene
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("Investment app");
        stage.setMinWidth(650);
        stage.setMinHeight(650);
        stage.getIcons().add(image);
        stage.show();

    }

    public class CalculateHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            a.calculate();
        }
    }

    public class ExportHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {
            a.export();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
