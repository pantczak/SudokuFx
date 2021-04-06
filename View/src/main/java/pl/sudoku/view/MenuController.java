package pl.sudoku.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sudoku.dao.Dao;
import pl.sudoku.filesudokuboarddao.SudokuBoardDaoFactory;
import pl.sudoku.fxmodel.FXsudokuBoard;
import pl.sudoku.jdbcsudokuboarddao.JdbcSudokuBoardDao;
import pl.sudoku.model.BoardSizeEnum;
import pl.sudoku.model.SudokuBoard;

public class MenuController implements Initializable {

    private static Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @FXML
    public ToggleGroup size;

    @FXML
    public RadioButton smallRadioButton;

    @FXML
    public RadioButton classicRadioButton;

    @FXML
    public RadioButton largeRadioButton;

    @FXML
    public Button easyButton;

    /**
     * Starts new game on Easy difficulty.
     * @param event onClick Event
     */
    @FXML
    private void handleEasyButtonAction(ActionEvent event) {
        startNewGame(GameDifficultyEnum.EASY);
    }

    @FXML
    public Button mediumButton;

    /**
     * Starts new game on Medium difficulty.
     * @param event onClick Event
     */
    @FXML
    private void handleMediumButtonAction(ActionEvent event) {
        startNewGame(GameDifficultyEnum.MEDIUM);
    }

    @FXML
    public Button hardButton;

    /**
     * Starts new game on Hard difficulty.
     * @param event onClick Event
     */
    @FXML
    private void handleHardButtonAction(ActionEvent event) {
        startNewGame(GameDifficultyEnum.HARD);
    }

    @FXML
    public Button fileLoadButton;

    /**
     * Loads game state from file when "Load from file" button is clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleFileLoadButtonAction(ActionEvent event) {
        loadGameFromFile();
    }

    @FXML
    public ComboBox dbLoadComboBox;

    @FXML
    public Button dbLoadButton;

    /**
     * Loads game state from database when "Load from database" button is clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleDbLoadButtonAction(ActionEvent event) {
        loadGameFromDatabase();
    }


    @FXML
    public Button exitButton;

    /**
     * Quits application when "Exit" button is clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleExitButtonAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public Button langEN;

    /**
     * Changes GUI language to ENG when "English" button is clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleLangEnButtonAction(ActionEvent event) {
        changeUiLanguage(new Locale("en"));
    }

    @FXML
    public Button langPL;

    /**
     * Changes GUI language to PL when "Polish" button is clicked.
     * @param event onClick Event
     */
    @FXML
    private void handleLangPlButtonAction(ActionEvent event) {
        changeUiLanguage(new Locale("pl"));
    }

    @FXML
    public Label authorOne;

    @FXML
    public Label authorTwo;

    /**
     * Method changes resource bundle corresponding to new locale wanted by user.
     * @param locale new locale
     */
    private void changeUiLanguage(Locale locale) {
        Locale.setDefault(locale);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("pl.sudoku.view.bundles.menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"), resourceBundle);

        try {
            loader.setController(this);
            Parent root = loader.load();
            App.setScene(root);
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        langEN.setOnAction(this::handleLangEnButtonAction);
        langPL.setOnAction(this::handleLangPlButtonAction);
        easyButton.setOnAction(this::handleEasyButtonAction);
        mediumButton.setOnAction(this::handleMediumButtonAction);
        hardButton.setOnAction(this::handleHardButtonAction);
        fileLoadButton.setOnAction(this::handleFileLoadButtonAction);
        dbLoadButton.setOnAction(this::handleDbLoadButtonAction);
        exitButton.setOnAction(this::handleExitButtonAction);
        smallRadioButton.setUserData(BoardSizeEnum.SMALL);
        classicRadioButton.setUserData(BoardSizeEnum.CLASSIC);
        largeRadioButton.setUserData(BoardSizeEnum.LARGE);
        setAuthors();
        loadAvailableSavesFormDB();
    }

    /**
     * Method starts new game with difficulty desired by user.
     * @param gameDifficulty game difficulty chosen by user
     */
    private void startNewGame(GameDifficultyEnum gameDifficulty) {
        BoardSizeEnum boardSizeEnum = (BoardSizeEnum) size.getSelectedToggle().getUserData();
        GameController gameController = new GameController(gameDifficulty, boardSizeEnum);
        openGameView(gameController);
    }

    /**
     * Loads game from file using fileSudokuBoardDao.
     */
    private void loadGameFromFile() {
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        FileChooser fileChooser = new FileChooser();
        File saveFile = fileChooser.showOpenDialog(stage);

        if (saveFile != null) {
            try {
                try (Dao<SudokuBoard> fileSudokuBoardDao =
                             SudokuBoardDaoFactory.getFileDao(saveFile.getAbsolutePath())) {
                    FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(fileSudokuBoardDao.read());
                    GameController gameController = new GameController(fxSudokuBoard);
                    openGameView(gameController);
                } catch (Exception e) {
                    throw new GameLoadingException(e);
                }
            } catch (GameLoadingException e) {
                LOGGER.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    /**
     * Loads game from file using JdbcSudokuBoardDao.
     */
    private void loadGameFromDatabase() {
        String selectedSave = (String) dbLoadComboBox.getValue();

        try (Dao<SudokuBoard> sudokuBoardDao = SudokuBoardDaoFactory.getDatabaseDao(selectedSave)) {
            FXsudokuBoard fxSudokuBoard = new FXsudokuBoard(sudokuBoardDao.read());
            GameController gameController = new GameController(fxSudokuBoard);
            openGameView(gameController);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Method quits main menu and changes view to game view with sudoku board.
     * @param gameController game controller
     */
    private void openGameView(GameController gameController) {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.sudoku.view.bundles.game");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("game.fxml"), bundle);
        loader.setController(gameController);

        try {
            Parent newRoot = loader.load();
            App.setScene(newRoot);
        } catch (IOException e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * Method responsible for internationalizing Authors when changing language.
     */
    private void setAuthors() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "pl.sudoku.view.bundles.Authors");

        authorOne.setText(resourceBundle.getString("224326"));
        authorTwo.setText(resourceBundle.getString("224248"));
    }

    /**
     * Method gets all saves stored in database and shows in choiceBox.
     */
    private void loadAvailableSavesFormDB() {
        try (JdbcSudokuBoardDao jdbcSudokuBoardDao = new JdbcSudokuBoardDao("")) {
            List<String> available = jdbcSudokuBoardDao.readAvailable();

            dbLoadComboBox.getItems().addAll(available);
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }

        dbLoadComboBox.getSelectionModel().selectedIndexProperty()
                .addListener((observable, oldValue, newValue) -> dbLoadButton.setDisable(false));
    }

}
