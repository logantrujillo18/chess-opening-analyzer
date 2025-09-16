package Controller;

import Helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * FXML Controller class MainMenu.java controls the main menu screen.
 * @author Logan Trujillo
 */
public class MainMenu {
    public Label TheWelcomeLabel;
    public Label TheReminderLabel;
    public Button TheSuggestOpeningButton;
    public Button TheInputResultsButton;
    public Button TheReportsButton;
    public Button TheLogoffButton;

    interface LogOff {

        Button exitStageWithThisButton();

    }

    /**
     * This method handles the action of user selected the Customers button. It launches the customers screen.
     */
    public void onSuggestOpeningAction(ActionEvent actionEvent) throws IOException {

        Helper.Close(TheSuggestOpeningButton);

        Stage customerStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/OpeningMenu.fxml"));
        customerStage.setTitle("Input Results");
        customerStage.setScene(new Scene(root, 800, 400));
        customerStage.show();

    }

    /**
     * This method handles the action of user selected the Appointments button. It launches the appointments screen.
     */
    public void onInputAction(ActionEvent actionEvent) throws IOException {

        Helper.Close(TheInputResultsButton);

        Stage appointmentStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/InputMenu.fxml"));
        appointmentStage.setTitle("Openings");
        appointmentStage.setScene(new Scene(root, 1000, 400));
        appointmentStage.show();

    }

    /**
     * This method handles the action of user selected the Reports button. It launches the reports screen.
     */
    public void onReportsAction(ActionEvent actionEvent) throws IOException {

        Helper.Close(TheReportsButton);

        Stage reportsStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/VisualizationsMenu.fxml"));
        reportsStage.setTitle("Reports");
        reportsStage.setScene(new Scene(root, 800, 400));
        reportsStage.show();

    }

    /**
     * This method handles the action of user selected the logoff button. It logs the user out and returns to the login screen.
     * It functions by using a lambda expression to locate the stage to close.
     */
    public void onLogoffAction(ActionEvent actionEvent) throws IOException {

        LogOff logOff;
        logOff = () -> TheLogoffButton;

        Helper.Close(logOff.exitStageWithThisButton());

        Stage loginStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        loginStage.setTitle("Login Menu");
        loginStage.setScene(new Scene(root, 600, 250));
        loginStage.show();

    }
}
