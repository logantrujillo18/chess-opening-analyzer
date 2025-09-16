package Controller;

import Helper.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * FXML Controller class LoginScreen.java controls the login screen.
 * @author Logan Trujillo
 */
public class LoginScreen implements Initializable {

    public Label TheUsernameLabel;
    public Label TheTitleLabel;
    public Label TheLoginLabel;
    public Label ThePasswordLabel;
    public TextField TheUsernameField;
    public TextField ThePasswordField;
    public Button TheLoginButton;

    /**
     * this initializes the Customer screen
     * @param url the url needed to initialize
     * @param resourceBundle the resourcebundle needed to initialize
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized!");

    }

    /**
     * This method handles the action of user selected the login button. It launches the main menu if login is successful.
     */
    public void OnButtonAction(ActionEvent actionEvent) throws IOException, SQLException {

        System.out.println("Button clicked.");

        try {
            String currentUsername = (TheUsernameField.getText()).toLowerCase();
            String currentPassword = ThePasswordField.getText();

            if(currentUsername.length() == 0  || currentPassword.length() == 0) {
                Helper.Error("Incorrect Credentials!");
            }
            else {

                System.out.println(currentUsername + " " + currentPassword);
                if (currentUsername.equals("admin") && currentPassword.equals("123456")) {
                    System.out.println("success");
                    Stage mainMenuStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                    mainMenuStage.setTitle("Main Menu");
                    mainMenuStage.setScene(new Scene(root, 600, 600));
                    mainMenuStage.show();

                    Helper.Close(TheLoginButton);
                }
                else {
                    Helper.Error("User not found!");
                }

            }

        }
        catch (NumberFormatException e) {
            Helper.Error("Invalid format!");
        }

    }

}
