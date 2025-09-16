package Controller;

import Helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualizationsMenu {
    public Button TheStrongestOpeningsButton;
    public Button TheMostPlayedButton;
    public Button TheActualWinButton;
    public Button TheBackButton;

    public void onTheStrongestOpeningsButtonAction(ActionEvent actionEvent) throws IOException {

        Stage mainMenuStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/StrongestOpenings.fxml"));
        mainMenuStage.setTitle("Visualization");
        mainMenuStage.setScene(new Scene(root, 1200, 800));
        mainMenuStage.show();

    }

    public void onTheMostPlayedButton(ActionEvent actionEvent) throws IOException {

        Stage mainMenuStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MostPlayed.fxml"));
        mainMenuStage.setTitle("Visualization");
        mainMenuStage.setScene(new Scene(root, 1200, 800));
        mainMenuStage.show();

    }

    public void onTheActualWinButtonAction(ActionEvent actionEvent) throws IOException {

        Stage mainMenuStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/ExpectedWin.fxml"));
        mainMenuStage.setTitle("Visualization");
        mainMenuStage.setScene(new Scene(root, 1200, 800));
        mainMenuStage.show();

    }

    public void onTheBackButtonAction(ActionEvent actionEvent) throws IOException {

        Helper.Close(TheBackButton);

        Stage mainMenuStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        mainMenuStage.setTitle("Main Menu");
        mainMenuStage.setScene(new Scene(root, 600, 600));
        mainMenuStage.show();

    }
}
