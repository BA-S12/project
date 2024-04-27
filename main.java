import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

public class Project extends Application {

    Button btn_calculate = new Button("Calculate");
    Button btn_clear = new Button("Clear");
    Button btn_export = new Button("Export");

    Label lbl_initialBalanceTitle = new Label("Initial Balance");
    Label lbl_interestRateTitle = new Label("Interest Rate");
    Label lbl_years = new Label("0");
    Label lbl_yearHint = new Label("Years");
    Label lbl_cye = new Label("0");
    Label lbl_cycHint = new Label("Current year earnings");
    Label lbl_totalBalance = new Label("0");
    Label lbl_totalBalanceHint = new Label("Total Balance");
    Label lbl_message = new Label("");
    TextField tf_initialBalance = new TextField();
    TextField tf_interestRate = new TextField();

    TextArea ta_history = new TextArea();

    private Design a = new Design(btn_calculate, btn_export, btn_clear, lbl_initialBalanceTitle,
            lbl_interestRateTitle, lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance,
            lbl_totalBalanceHint, lbl_message, tf_initialBalance, tf_interestRate, ta_history);

    @Override
    public void start(Stage stage) throws IOException {

        Image image = new Image("icon.jpg");
        //Line 1
        HBox inputPane = new HBox(lbl_initialBalanceTitle, tf_initialBalance, lbl_interestRateTitle,
                tf_interestRate);
        inputPane.setAlignment(Pos.CENTER);

//        Label for to what the user entered in text field
        HBox messagePane = new HBox(
                lbl_message
        );
        messagePane.setAlignment(Pos.CENTER);

//      make button width take full width of their parent
        btn_calculate.setMaxWidth(Double.MAX_VALUE);
        btn_calculate.setOnAction(new CalculateHandler());

        btn_clear.setMaxWidth(Double.MAX_VALUE);
        btn_clear.setOnAction(new ClearHandler());

// Setup export button
        btn_export.setMaxWidth(Double.MAX_VALUE);
        btn_export.setOnAction(new ExportHandler());

// Create UI panes for displaying output
        VBox yearsPane = new VBox(lbl_years, lbl_yearHint);
        yearsPane.setAlignment(Pos.CENTER);

        VBox cyePane = new VBox(lbl_cye, lbl_cycHint);
        cyePane.setAlignment(Pos.CENTER);

        VBox totalBalancePane = new VBox(lbl_totalBalance, lbl_totalBalanceHint);
        totalBalancePane.setAlignment(Pos.CENTER);

        HBox outputPane = new HBox(yearsPane, cyePane, totalBalancePane);
        outputPane.setAlignment(Pos.CENTER);

// Create root pane and arrange UI components
        BorderPane rootPane = new BorderPane();

        VBox top = new VBox(inputPane, messagePane, btn_calculate);
        VBox bottom = new VBox(btn_clear, ta_history, btn_export);
        ta_history.setDisable(true);
        //a.ta_history.addEventFilter(KeyEvent.KEY_TYPED, e -> e.consume());

        rootPane.setTop(top);
        rootPane.setCenter(outputPane);
        rootPane.setBottom(bottom);
        BorderPane.setAlignment(outputPane, Pos.CENTER);

        //////////////////////////////////////////////
        tf_initialBalance.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                tf_interestRate.requestFocus();
            }
        });

        tf_interestRate.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                a.calculate();
            } else if (e.getCode() == KeyCode.SPACE) {
                if (!ta_history.getText().equalsIgnoreCase("")) {
                    a.export();
                }
            }
        });

// Set padding for UI components
        Style.setPadding(8, lbl_initialBalanceTitle, lbl_interestRateTitle, tf_interestRate, tf_initialBalance,
                lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance, lbl_totalBalanceHint,
                ta_history, btn_calculate, btn_clear, btn_export);

// Set font size for UI components
        Style.setFont(16, lbl_initialBalanceTitle, lbl_interestRateTitle, tf_interestRate, tf_initialBalance,
                lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance, lbl_totalBalanceHint,
                ta_history, btn_calculate, btn_clear, btn_export);

        Style.setFont(40, lbl_years, lbl_cye, lbl_totalBalance);

// Apply text styling to UI components
        Style.styleText("red", "bold", lbl_years, lbl_cye, lbl_totalBalance, lbl_message);
        Style.styleText("red", lbl_yearHint, lbl_cycHint, lbl_totalBalanceHint);

// Create and show the scene
        Scene scene = new Scene(rootPane, 750, 750);
        stage.setScene(scene);
        stage.setTitle("Investment app");
        stage.setMinWidth(650);
        stage.setMinHeight(580);
        stage.getIcons().add(image);
        stage.show();

    }

    public class CalculateHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            a.calculate();
        }
    }

    public class ClearHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            a.clear();
        }
    }

    public class ExportHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            a.export();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
