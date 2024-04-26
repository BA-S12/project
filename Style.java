
import javafx.geometry.Insets;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import javafx.scene.text.Font;

/**
 *
 * @author MUSAB
 */
public class Style {
    //        Function to set padding for component
    public static void setPadding(double padding, Control... controls) {
        for (Control control : controls) {
            control.setPadding(new Insets(padding));
        }

    }

    //  Function to set font for component
    public static void setFont(double size, Control... controls) {
        for (Control control : controls) {
            if (control instanceof TextInputControl) {
                ((TextInputControl) control).setFont(new Font(size));
            } else if (control instanceof Labeled) {
                ((Labeled) control).setFont(new Font(size));
            }
        }

    }

//        Function to style text
    public static void styleText(String color, String weight, Control... controls) {
        for (Control control : controls) {
            control.setStyle("-fx-font-weight:" + weight + ';'
                    + "-fx-text-fill:" + color + ';');
        }
    }

    public static void styleText(String color, Control... controls) {
        for (Control control : controls) {
            control.setStyle("-fx-text-fill:" + color + ';');
        }
    }
}
