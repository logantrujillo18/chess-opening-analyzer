package Controller;

import Helper.Helper;
import Model.Opening;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class OpeningMenu {
    public Label TheOpeningLabel;
    public TextField TheMyEloField;
    public TextField TheOppEloField;
    public RadioButton TheWhiteRadioButton;
    public RadioButton TheBlackRadioButton;
    public Button TheSubmitButton;
    public RadioButton TheSuggestRadioButton;
    public RadioButton ThePredictRadioButton;
    public ComboBox TheOpeningBox;
    public Button TheBackButton;

    public void onTheWhiteRadioButtonAction(ActionEvent actionEvent) {

        TheBlackRadioButton.setSelected(false);
        if (TheOpeningBox.getSelectionModel().isEmpty()) {
            TheOpeningBox.setItems(Opening.whiteOpenings);
        }

    }

    public void onTheBlackRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteRadioButton.setSelected(false);
        if (TheOpeningBox.getSelectionModel().isEmpty()) {
            TheOpeningBox.setItems(Opening.blackOpenings);
        }

    }

    public void onTheSuggestRadioButtonAction(ActionEvent actionEvent) {

        ThePredictRadioButton.setSelected(false);
        TheOpeningBox.setItems(null);

    }

    public void onThePredictRadioButtonAction(ActionEvent actionEvent) {

        TheSuggestRadioButton.setSelected(false);

        if (TheWhiteRadioButton.isSelected()) {
            TheOpeningBox.setItems(Opening.whiteOpenings);
        }
        if (TheBlackRadioButton.isSelected()) {
            TheOpeningBox.setItems(Opening.blackOpenings);
        }


    }

    public void onTheSubmitButtonAction(ActionEvent actionEvent) throws Exception {

        try {
            if ((Integer.parseInt((TheMyEloField.getText())) < 0) || (Integer.parseInt((TheOppEloField.getText())) < 0)
                    || (Integer.parseInt((TheMyEloField.getText())) > 3000) || (Integer.parseInt((TheOppEloField.getText())) > 3000))
                Helper.Error("Elo outside of range. Please enter the correct Elo between 0-3000.");
            else {
                if ((!(TheWhiteRadioButton.isSelected() || TheBlackRadioButton.isSelected()))
                        ||(!(ThePredictRadioButton.isSelected() || TheSuggestRadioButton.isSelected()))) {
                    Helper.Error("Please select the the corresponding radio buttons. You cannot leave a selection empty.");
                }
                else {
                    if (TheOpeningBox.getSelectionModel().isEmpty() && ThePredictRadioButton.isSelected()) {
                        Helper.Error("To predict the outcome, you must select an opening using the choicebox.");
                    }
                    else {
                        if (ThePredictRadioButton.isSelected()) {
                            String selectedOpening = TheOpeningBox.getSelectionModel().getSelectedItem().toString();
                            System.out.println(selectedOpening);
                            for (int i = 0; i < Opening.allOpenings.size(); i++) {
                                if (selectedOpening.equals(Opening.allOpenings.get(i).getOpeningName())) {
                                    Opening openingToAdd = Opening.allOpenings.get(i);
                                    int myElo = Integer.parseInt(TheMyEloField.getText());
                                    int oppElo = Integer.parseInt(TheOppEloField.getText());
                                    Opening.Prediction(openingToAdd, myElo, oppElo);
                                }
                            }
                        }
                        if (TheSuggestRadioButton.isSelected()) {
                            String side;
                            int myElo = Integer.parseInt(TheMyEloField.getText());
                            int oppElo = Integer.parseInt(TheOppEloField.getText());
                            if (TheWhiteRadioButton.isSelected()) {
                                side = "white";
                            }
                            else side = "black";
                            System.out.println("Submitted" + side + " " + myElo + " " + oppElo);
                            Opening.Suggestion(side, myElo, oppElo);
                        }
                    }
                }
            }
        }
        catch (NumberFormatException e) {
            Helper.Error("Your Elo input was invalid. Please ensure it is only a number between 0-3000.");
        }


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
