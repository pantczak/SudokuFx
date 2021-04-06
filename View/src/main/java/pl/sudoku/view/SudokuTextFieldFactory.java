package pl.sudoku.view;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Font;

public class SudokuTextFieldFactory {

    /**
     * Creates JavaFX TextField corresponding to single field in sudoku board.
     *
     * @param fieldValue    value to display in TextField
     * @param maxDigitsEnum max number of digits in TextField
     * @return Formatted TextField with input length restriction
     */
    public static TextField getSudokuTextField(int fieldValue, MaxDigitsEnum maxDigitsEnum) {
        TextField sudokuTextField = new TextField();

        if (fieldValue != 0) {
            sudokuTextField.setText(Integer.toString(fieldValue));
        }

        sudokuTextField.setAlignment(Pos.CENTER);
        sudokuTextField.setPrefSize(54.0, 54.0);
        sudokuTextField.setFont(Font.font(20.0));
        sudokuTextField.setId("sudoku-field");

        int maxDigits = maxDigitsEnum.getDigits();
        sudokuTextField.setTextFormatter(
                new TextFormatter<String>((TextFormatter.Change change) -> {
                    String newText = change.getControlNewText();
                    String oldText = change.getControlText();
                    if (newText.length() > maxDigits) {
                        return null;
                    }

                    if (newText.length() == maxDigits && oldText.length() == maxDigits) {
                        return null;
                    } else {
                        return change;
                    }
                }));

        return sudokuTextField;
    }
}
