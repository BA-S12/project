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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class Project extends Application {

    Button btn_calculate = new Button("Calculate");
    Button btn_clear = new Button("Clear");
    Button btn_export = new Button("Export");
    Button btn_back = new Button("Back");

    Tooltip enter = new Tooltip("Shortcut: Enter ");
    Tooltip ctrlSpace = new Tooltip("Shortcut: Space");
    Tooltip ctrlBackSpace = new Tooltip("Shortcut: Backspace");
    Tooltip ctrlZ = new Tooltip("Shortcut: Ctrl+ Z");

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

    private Design design = new Design(btn_calculate, btn_export, btn_back, btn_clear, lbl_initialBalanceTitle,
            lbl_interestRateTitle, lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance,
            lbl_totalBalanceHint, lbl_message, tf_initialBalance, tf_interestRate, ta_history);

    @Override
    public void start(Stage stage) throws IOException {

        Image image = new Image("icon.jpg");

        HBox inputPane = new HBox(lbl_initialBalanceTitle, tf_initialBalance, lbl_interestRateTitle,
                tf_interestRate);
        inputPane.setAlignment(Pos.CENTER);

        HBox messagePane = new HBox(
                lbl_message
        );
        messagePane.setAlignment(Pos.CENTER);

        btn_calculate.setMaxWidth(Double.MAX_VALUE);
        btn_calculate.setOnAction(new CalculateHandler());
        btn_calculate.setTooltip(enter);

        btn_clear.setMaxWidth(Double.MAX_VALUE);
        btn_clear.setOnAction(new ClearHandler());
        btn_clear.setTooltip(ctrlBackSpace);

        btn_back.setMaxWidth(Double.MAX_VALUE);
        btn_back.setOnAction(new BackHandler());
        btn_back.setTooltip(ctrlZ);

        btn_export.setMaxWidth(Double.MAX_VALUE);
        btn_export.setOnAction(new ExportHandler());
        btn_export.setTooltip(ctrlSpace);

        VBox yearsPane = new VBox(lbl_years, lbl_yearHint);
        yearsPane.setAlignment(Pos.CENTER);

        VBox cyePane = new VBox(lbl_cye, lbl_cycHint);
        cyePane.setAlignment(Pos.CENTER);

        VBox totalBalancePane = new VBox(lbl_totalBalance, lbl_totalBalanceHint);
        totalBalancePane.setAlignment(Pos.CENTER);

        HBox outputPane = new HBox(yearsPane, cyePane, totalBalancePane);
        outputPane.setAlignment(Pos.CENTER);

        VBox top = new VBox(inputPane, messagePane, btn_calculate);
        VBox bottom = new VBox(btn_back, btn_clear, ta_history, btn_export);

        BorderPane rootPane = new BorderPane();
        rootPane.setTop(top);
        rootPane.setCenter(outputPane);
        rootPane.setBottom(bottom);
        BorderPane.setAlignment(outputPane, Pos.CENTER);

        tf_initialBalance.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.RIGHT) {
                tf_interestRate.requestFocus();
            }
        });

        tf_interestRate.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.LEFT) {
                tf_initialBalance.requestFocus();
            }
        });
        
        ta_history.setEditable(false);
        ta_history.setOnMouseClicked((e) ->{
            lbl_years.requestFocus();
        });
        
        Style.setPadding(8, lbl_initialBalanceTitle, lbl_interestRateTitle, tf_interestRate, tf_initialBalance,
                lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance, lbl_totalBalanceHint,
                ta_history, btn_calculate, btn_back, btn_clear, btn_export);
        Style.setFont(16, lbl_initialBalanceTitle, lbl_interestRateTitle, tf_interestRate, tf_initialBalance,
                lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance, lbl_totalBalanceHint,
                ta_history, btn_calculate, btn_back, btn_clear, btn_export);
        Style.setFont(40, lbl_years, lbl_cye, lbl_totalBalance);
        Style.styleText("red", "bold", lbl_years, lbl_cye, lbl_totalBalance, lbl_message);
        Style.styleText("red", lbl_yearHint, lbl_cycHint, lbl_totalBalanceHint);

        Scene scene = new Scene(rootPane, 750, 750);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new SceneHandler());

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
            design.calculate();
        }
    }

    public class BackHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            design.back();
        }
    }

    public class ClearHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            design.clear();
        }
    }

    public class ExportHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {
            design.export();
        }
    }

    public class SceneHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent e) {
            if (e.getCode() == KeyCode.ENTER) {
                design.calculate();
            } else if (e.getCode() == KeyCode.SPACE) {
                if (!ta_history.getText().equalsIgnoreCase("")) {
                    design.export();
                }
            } else if (e.getCode() == KeyCode.BACK_SPACE) {
                design.clear();

            } else if (e.isControlDown() && e.getCode() == KeyCode.Z) {
                if (ta_history.getText().equalsIgnoreCase("")) {
                    design.back();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
