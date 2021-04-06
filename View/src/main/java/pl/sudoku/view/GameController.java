package pl.sudoku.view;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.dao.Dao;
import pl.sudoku.filesudokuboarddao.FileDaoWriteException;
import pl.sudoku.filesudokuboarddao.SudokuBoardDaoFactory;
import pl.sudoku.fxmodel.FXsudokuBoard;
import pl.sudoku.jdbcsudokuboarddao.JdbcSudokuBoardDao;
import pl.sudoku.model.BacktrackingSudokuSolver;
import pl.sudoku.model.BoardSizeEnum;
import pl.sudoku.model.SudokuBoard;

public class GameController implements Initializable {

    private static Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @FXML
    public Button cancelButton;

    /**
     * Closes current game and returns to main menu when "Cancel" button clicked.
     * @param event onClick event
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.sudoku.view.bundles.menu", locale);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("menu.fxml"), bundle);
        MenuController primaryController = new MenuController();
        loader.setController(primaryController);
        try {
            Parent newRoot = loader.load();
            App.setScene(newRoot);
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @FXML
    public Button fileSaveButton;

    /**
     * Saves game to file when "Save to File" button clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleFileSaveButtonAction(ActionEvent event) {
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        FileChooser fileChooser = new FileChooser();
        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            try (Dao<SudokuBoard> fileSudokuBoardDao =
                         SudokuBoardDaoFactory.getFileDao(saveFile.getAbsolutePath())) {
                fileSudokuBoardDao.write(fxSudokuBoard.getSudokuBoardPlaceholder());
            } catch (FileDaoWriteException e) {
                LOGGER.error(ExceptionUtils.getStackTrace(e));
                // MOZNA ZROBIC JEDEN BLOCK CATCH ALE JEST TAK ZEBY POKAZAC ZE LAPIEMY SWOJ WYJAEK
            } catch (Exception e) {
                LOGGER.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    @FXML
    public Button dbSaveButton;

    /**
     * Saves game to database when "Save to Database" button clicked.
     * @param event onClick Event
     */

    @FXML
    private void handleDbFileSaveButtonAction(ActionEvent event) {
        String saveName = dbSaveTextField.getText();
        ResourceBundle resourceBundle
                = ResourceBundle.getBundle("pl.sudoku.view.bundles.game");
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao(saveName)) {
            List<String> available = jdbcSudokuBoardDao.readAvailable();

            if (available.contains(saveName)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(resourceBundle.getString("UpdateSaveConfirmationDialogTitle"));
                alert.setHeaderText(resourceBundle.getString(
                        "UpdateSaveConfirmationDialogHeaderText"));
                alert.getDialogPane().setMinWidth(550.0);

                Optional<ButtonType> choice = alert.showAndWait();

                if (choice.get() == ButtonType.OK) {
                    jdbcSudokuBoardDao.update(fxSudokuBoard.getSudokuBoardPlaceholder());
                    showSuccessDialog(resourceBundle);
                }
            } else {
                jdbcSudokuBoardDao.write(fxSudokuBoard.getSudokuBoardPlaceholder());
                showSuccessDialog(resourceBundle);
            }

        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            dbSaveButton.setStyle("-fx-text-fill: tomato");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("SaveErrorDialogTitle"));
            alert.setHeaderText(resourceBundle.getString("SaveErrorDialogHeaderText"));
            alert.setContentText(e.getLocalizedMessage());
            alert.getDialogPane().setMinWidth(550.0);
            alert.showAndWait();
        }
    }

    /**
     * Method shows dialog window when correctly saved game state to database.
     * @param resourceBundle language bundle for internationalized messages
     */
    private void showSuccessDialog(ResourceBundle resourceBundle) {
        dbSaveButton.setStyle("-fx-text-fill: forestgreen");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(resourceBundle.getString("SaveConfirmationDialogTitle"));
        alert.setHeaderText(resourceBundle.getString("SaveConfirmationDialogHeaderText"));
        alert.getDialogPane().setMinWidth(300.0);
        alert.getDialogPane().setGraphic(new ImageView(this.getClass().getResource(
                "confirmation_icon.jpg").toString()));
        alert.showAndWait();
    }

    @FXML
    public TextField dbSaveTextField;

    @FXML
    public GridPane sudokuGrid;

    FXsudokuBoard fxSudokuBoard;

    int boardSize;

    String[] possibleValues;

    private Locale locale;

    /**
     * Game controller constructor used for creating new game.
     *
     * @param gameDifficultyEnum chosen difficulty
     * @param boardSizeEnum      chosen board size
     */
    public GameController(GameDifficultyEnum gameDifficultyEnum, BoardSizeEnum boardSizeEnum) {
        fxSudokuBoard = new FXsudokuBoard(new SudokuBoard(new BacktrackingSudokuSolver(),
                boardSizeEnum));
        fxSudokuBoard.getSudokuBoardPlaceholder().solveGame();
        gameDifficultyEnum.clearSudokuFields(fxSudokuBoard.getSudokuBoardPlaceholder());
    }

    /**
     * Game controller constructor used for resuming saved game.
     *
     * @param fxSudokuBoard game to resume
     */
    public GameController(FXsudokuBoard fxSudokuBoard) {
        this.fxSudokuBoard = fxSudokuBoard;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locale = resources.getLocale();
        cancelButton.setOnAction(this::handleCancelButtonAction);
        fileSaveButton.setOnAction(this::handleFileSaveButtonAction);
        dbSaveButton.setOnAction(this::handleDbFileSaveButtonAction);
        boardSize = fxSudokuBoard.getSudokuBoardPlaceholder().getBoardSize();
        generatePossibleValues();
        fxSudokuBoard.addPropertyChangeListener(
                FXsudokuBoard.FIELD_VALUE_PROPERTY, new SudokuFieldValueListener());
        fillSudokuGrid();
        setWarnings();
        initializeDbSaveTextField();
    }

    class SudokuFieldValueListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            if (FXsudokuBoard.FIELD_VALUE_PROPERTY.equals(propertyName)) {
                IndexedPropertyChangeEvent event = (IndexedPropertyChangeEvent) evt;
                int row = event.getIndex() / boardSize;
                int column = event.getIndex() % boardSize;
                int newFieldValue = (int) event.getNewValue();
                TextField textField = getNodeByRowColumnIndex(row, column, sudokuGrid);

                if (newFieldValue == 0) {
                    textField.setText("");
                } else {
                    textField.setText(Integer.toString(newFieldValue));
                }

                setWarnings();
            }
        }
    }

    /**
     * Sets red background in fields which are part of row/column/box that is not correctly filled.
     */
    private void setWarnings() {
        ObservableList<Node> children = sudokuGrid.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                int nodeRow = GridPane.getRowIndex(node);
                int nodeColumn = GridPane.getColumnIndex(node);

                boolean rowIsCorrect =
                        fxSudokuBoard.getSudokuBoardPlaceholder().getRow(nodeRow).verify();
                boolean columnIsCorrect =
                        fxSudokuBoard.getSudokuBoardPlaceholder().getColumn(nodeColumn).verify();
                boolean boxIsCorrect =
                        fxSudokuBoard.getSudokuBoardPlaceholder().getBox(nodeRow, nodeColumn)
                                .verify();

                if (rowIsCorrect && columnIsCorrect && boxIsCorrect) {
                    node.setStyle("-fx-background-color: White; -fx-border-color: Grey;");
                } else {
                    node.setStyle("-fx-background-color: Tomato; -fx-border-color: Grey;");
                }
            }
        }
    }

    /**
     * Creates proper number of rows and columns in sudokuGrid then fills grid with TextFields.
     */
    private void fillSudokuGrid() {
        double textFieldPixelSize = 54.0; //+ boardSize / 10 * 14.0;

        for (int i = 0; i < boardSize; i++) {
            RowConstraints row = new RowConstraints(textFieldPixelSize, textFieldPixelSize,
                    textFieldPixelSize, Priority.ALWAYS, VPos.CENTER, true);
            ColumnConstraints column = new ColumnConstraints(textFieldPixelSize, textFieldPixelSize,
                    textFieldPixelSize, Priority.ALWAYS, HPos.CENTER, true);
            sudokuGrid.getRowConstraints().add(row);
            sudokuGrid.getColumnConstraints().add(column);
        }

        MaxDigitsEnum maxDigitsEnum;
        if (boardSize >= 1 && boardSize <= 9) {
            maxDigitsEnum = MaxDigitsEnum.ONE;
        } else {
            maxDigitsEnum = MaxDigitsEnum.TWO;
        }

        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                int fieldValue = fxSudokuBoard.get(row, column);
                TextField textField = SudokuTextFieldFactory.getSudokuTextField(fieldValue,
                        maxDigitsEnum);
                addFieldValueListener(textField, row, column);
                sudokuGrid.add(textField, column, row);
            }
        }
    }

    /**
     * Searches for TextField in GridPane with given coordinates.
     *
     * @param row      number of row in which TextField is
     * @param column   number of column in which TextField is
     * @param gridPane GridPane in which TextField is
     * @return TextField at given position in GridPane
     */
    private TextField getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }
        }
        return (TextField) result;
    }

    /**
     * Adds TextProperty ValueListener to given TextField.
     *
     * @param textField TextField to add ValueListener to
     * @param row       number of row in which corresponding SudokuField from SudokuBoard is
     * @param column    number of column in which corresponding SudokuField from SudokuBoard is
     */
    private void addFieldValueListener(TextField textField, final int row, final int column) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String oldVal, String newVal) {
                if (!validateValue(newVal)) {
                    if (newVal.equals("")) {
                        fxSudokuBoard.set(row, column, 0);
                    } else {
                        Platform.runLater(textField::clear);
                    }
                } else {
                    fxSudokuBoard.set(row, column, Integer.parseInt(newVal));
                }
            }
        });
    }

    /**
     * Checks whether given value can be inserted into used SudokuBoard.
     *
     * @param newVal value to check
     * @return true is can be inserted, otherwise false
     */
    private boolean validateValue(final String newVal) {
        for (String value : possibleValues) {
            if (value.equals(newVal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates string representations of values than can inserted into used SudokuBoard.
     */
    private void generatePossibleValues() {
        possibleValues = new String[boardSize];
        for (int i = 0; i < possibleValues.length; i++) {
            possibleValues[i] = Integer.toString(i + 1);
        }
    }

    /**
     * Generates database textfield for save name input.
     */
    private void initializeDbSaveTextField() {
        dbSaveTextField.setTextFormatter(
                new TextFormatter<String>((TextFormatter.Change change) -> {
                    String newText = change.getControlNewText();
                    String oldText = change.getControlText();
                    if (newText.length() > 15) {
                        return null;
                    }

                    if (newText.length() == 15 && oldText.length() == 15) {
                        return null;
                    } else {
                        return change;
                    }
                }));

        dbSaveTextField.textProperty()
                .addListener((observable, oldValue, newValue) -> {
                    dbSaveButton.setDisable(newValue.equals(""));
                });
    }

}
