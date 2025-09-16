package Helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * class Helper.java handles many of the miscellaneous and repeated tasks throughout the program
 * @author Logan Trujillo
 */
public class Helper {


    /**
     * this function creates error messages to display as a popup
     * @param errorMessage the message to display
     */
    public static void Error(String errorMessage) {

        System.out.println("Inside Helper.Error");

        Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
        alert.showAndWait();

    }


    /**
     * this function creates alert messages to display as a popup
     * @param alertMessage the alert to display
     */
    public static void Alert(String alertMessage) {

        System.out.println("Inside Helper.Alert");

        Alert alert = new Alert(Alert.AlertType.INFORMATION, alertMessage);
        alert.showAndWait();

    }

    public static int Choice(String alertMessage) {

        System.out.println("Inside Helper.Alert");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertMessage);
        Optional<ButtonType> userOption = alert.showAndWait();

        if (userOption.get() == ButtonType.OK) return 1;
        else return 0;

    }

    /**
     * this function closes the requested window
     * @param closeStageWithThisObj the stage with this selected button will be closed
     */
    public static void Close(Button closeStageWithThisObj) {

        Stage stageToClose = (Stage) closeStageWithThisObj.getScene().getWindow();
        stageToClose.close();

    }


}
