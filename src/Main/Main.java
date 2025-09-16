package Main;

import Model.Opening;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Main.java This class launches the first screen of the system.
 * @author Logan Trujillo
 */
public class Main extends Application {

    public final static String high_elo_opening = "C:\\IdeaProjects\\C964\\high_elo_opening.csv";
    public final static String my_results = "C:\\IdeaProjects\\C964\\my_results.csv";

    /**
     * This method helps starts the app. It launches the login screen.
     * @param primaryStage the first stage.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{


        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        primaryStage.setTitle("Login Menu");
        primaryStage.setScene(new Scene(root, 600, 250));
        primaryStage.show();

        Opening.loadOpenings();
        Opening.loadResults();

    }

    /**
     * This method helps starts the app. It is the main function.
     * @param args helps launch the program.
     */
    public static void main(String[] args) throws SQLException {

        launch(args);

    }
}
