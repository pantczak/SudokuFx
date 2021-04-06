package pl.sudoku.view;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JavaFX App.
 */
public class App extends Application {

    /**
     * Primary stage of javaFX GUI.
     */
    private static Stage primaryStage;
    /**
     * Logger for message logging.
     */
    private static Logger LOGGER;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        String path = App.class.getResource("jul-log.properties").getFile();
        System.setProperty("java.util.logging.config.file", path);
        LOGGER = LoggerFactory.getLogger(App.class);

        ResourceBundle bundle = ResourceBundle.getBundle("pl.sudoku.view.bundles.menu");
        FXMLLoader loader = new FXMLLoader(App.class.getResource("menu.fxml"), bundle);
        MenuController menuController = new MenuController();
        loader.setController(menuController);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(bundle.getString("MainWindowTitle"));
        primaryStage.show();
    }

    /**
     * Method for setting new scene.
     * @param root new scene bo be set
     */
    static void setScene(Parent root) {
        primaryStage.setScene(new Scene(root));
        primaryStage.centerOnScreen();
    }

    /**
     * Main method required for GUI to launch when JAR file is created with JavaFX Packager tool.
     * @param args extra arguments when launching GUI.
     */
    public static void main(String[] args) {
        launch();
    }

}