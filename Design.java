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

    private final Button btn_calculate;
    private final Button btn_export;

    private final Button btn_back;
    private final Button btn_clear;
    private final Label lbl_initialBalanceTitle;
    private final Label lbl_interestRateTitle;

    private final Label lbl_years, lbl_yearHint, lbl_cye, lbl_cycHint, lbl_totalBalance, lbl_totalBalanceHint, lbl_message;

    private final TextField tf_initialBalance, tf_interestRate;

    private final TextArea ta_history;

    private double balance;
    private double initBalance;
    private double interestRate;
    private double oldBalance;
    private int years;
    private String history = "";
    private int oldYears;

    public Design(Button btn_calculate, Button btn_export, Button btn_back, Button btn_clear, Label lbl_initialBalanceTitle, Label lbl_interestRateTitle, Label lbl_years, Label lbl_yearHint, Label lbl_cye, Label lbl_cycHint, Label lbl_totalBalance, Label lbl_totalBalanceHint, Label lbl_message, TextField tf_initialBalance, TextField tf_interestRate, TextArea ta_history) {
        this.btn_calculate = btn_calculate;
        this.btn_export = btn_export;
        this.btn_clear = btn_clear;
        this.btn_back = btn_back;
        this.lbl_initialBalanceTitle = lbl_initialBalanceTitle;
        this.lbl_interestRateTitle = lbl_interestRateTitle;
        this.lbl_years = lbl_years;
        this.lbl_yearHint = lbl_yearHint;
        this.lbl_cye = lbl_cye;
        this.lbl_cycHint = lbl_cycHint;
        this.lbl_totalBalance = lbl_totalBalance;
        this.lbl_totalBalanceHint = lbl_totalBalanceHint;
        this.lbl_message = lbl_message;
        this.tf_initialBalance = tf_initialBalance;
        this.tf_interestRate = tf_interestRate;
        this.ta_history = ta_history;

        btn_clear.setDisable(true);
        btn_export.setDisable(true);
        btn_back.setDisable(true);
    }

    public boolean isNumerical() {
        String initialBalanceText = tf_initialBalance.getText().trim();
        String interestRateText = tf_interestRate.getText().trim();

        if (initialBalanceText.isEmpty() || interestRateText.isEmpty()) {
            lbl_message.setText("You must enter numerical value.");
            return false;
        } else if (!initialBalanceText.matches("\\d+(\\.\\d+)?") || !interestRateText.matches("\\d+(\\.\\d+)?")) {
            lbl_message.setText("You must enter numerical value.");
            return false;
        }

        lbl_message.setText("");
        return true;
    }

    public void calculate() {

        if (isNumerical()) {
            initBalance = Double.parseDouble(tf_initialBalance.getText().trim());
            interestRate = Double.parseDouble(tf_interestRate.getText().trim());

            oldYears = years;
            oldBalance = balance;
            history = ta_history.getText();

            if (interestRate <= 0 || initBalance <= 0) {
                lbl_message.setText("You must enter numbers greater than 0.");
                return; // Exit the method
            }

            if (years == 0) {
                balance = initBalance;
            }

            years++;
            double cyi = balance * interestRate / 100;
            balance += cyi;

            lbl_years.setText(String.valueOf(years));
            lbl_cye.setText(String.format("SR%.2f", cyi));
            lbl_totalBalance.setText(String.format("SR%.2f", balance));
            ta_history.appendText("Year #" + years
                    + ": you earned: " + String.format("SR%.2f", cyi)
                    + " and your total balance is: " + String.format("SR%.2f", balance) + "\n");

            btn_export.setDisable(false);
            btn_clear.setDisable(false);
            btn_back.setDisable(true);
            lbl_years.requestFocus();
        }
    }

    public void export() {
        lbl_years.requestFocus();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Investment file ");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text files", "*.txt"));

        File file = fileChooser.showSaveDialog((Stage) btn_export.getScene().getWindow());

        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println(ta_history.getText());
        } catch (FileNotFoundException e) {

        }
    }

    public void back() {
        years = oldYears;
        oldYears = 0;
        balance = oldBalance;
        oldBalance = 0;

        ta_history.setText(history);
        tf_initialBalance.setText(String.valueOf(initBalance));
        tf_interestRate.setText(String.valueOf(interestRate));
        calculate();
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

        btn_export.setDisable(true);
        btn_clear.setDisable(true);
        btn_back.setDisable(false);
        lbl_years.requestFocus();
    }
}
