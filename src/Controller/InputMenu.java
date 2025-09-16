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

public class InputMenu  {
    public Button TheWhiteButton;
    public Button TheBlackButton;
    public Label TheOpeningLabel;
    public TextField TheMyEloField;
    public TextField TheOppEloField;
    public RadioButton TheWhiteWonRadioButton;
    public RadioButton TheWhiteDrawRadioButton;
    public RadioButton TheWhiteLostRadioButton;
    public RadioButton TheBlackWonRadioButton;
    public RadioButton TheBlackDrawRadioButton;
    public RadioButton TheBlackLostRadioButton;
    public ComboBox TheOpeningBox;
    public Button TheBackButton;
    public Button TheSubmitButton;

    String side;
    String result;
    String opening;
    boolean eloValid = false;

    public void onTheWhiteWonRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteDrawRadioButton.setSelected(false);
        TheWhiteLostRadioButton.setSelected(false);
        TheBlackWonRadioButton.setSelected(false);
        TheBlackDrawRadioButton.setSelected(false);
        TheBlackLostRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.whiteOpenings);

    }

    public void onTheWhiteDrawRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteWonRadioButton.setSelected(false);
        TheWhiteLostRadioButton.setSelected(false);
        TheBlackWonRadioButton.setSelected(false);
        TheBlackDrawRadioButton.setSelected(false);
        TheBlackLostRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.whiteOpenings);

    }

    public void onTheWhiteLostRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteDrawRadioButton.setSelected(false);
        TheWhiteWonRadioButton.setSelected(false);
        TheBlackWonRadioButton.setSelected(false);
        TheBlackDrawRadioButton.setSelected(false);
        TheBlackLostRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.whiteOpenings);

    }

    public void onTheBlackWonRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteDrawRadioButton.setSelected(false);
        TheWhiteLostRadioButton.setSelected(false);
        TheWhiteWonRadioButton.setSelected(false);
        TheBlackDrawRadioButton.setSelected(false);
        TheBlackLostRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.blackOpenings);


    }

    public void onTheBlackDrawRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteDrawRadioButton.setSelected(false);
        TheWhiteLostRadioButton.setSelected(false);
        TheBlackWonRadioButton.setSelected(false);
        TheWhiteWonRadioButton.setSelected(false);
        TheBlackLostRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.blackOpenings);

    }

    public void onTheBlackLostRadioButtonAction(ActionEvent actionEvent) {

        TheWhiteDrawRadioButton.setSelected(false);
        TheWhiteLostRadioButton.setSelected(false);
        TheBlackWonRadioButton.setSelected(false);
        TheBlackDrawRadioButton.setSelected(false);
        TheWhiteWonRadioButton.setSelected(false);

        TheOpeningBox.setItems(Opening.blackOpenings);


    }


    public void onTheBackButtonAction(ActionEvent actionEvent) throws IOException {

        Helper.Close(TheBackButton);

        Stage mainMenuStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        mainMenuStage.setTitle("Main Menu");
        mainMenuStage.setScene(new Scene(root, 950, 600));
        mainMenuStage.show();

    }

    public void onTheSubmitButtonAction(ActionEvent actionEvent) throws Exception {

        try {
            if ((Integer.parseInt((TheMyEloField.getText())) < 0) || (Integer.parseInt((TheOppEloField.getText())) < 0)
                    || (Integer.parseInt((TheMyEloField.getText())) > 3000) || (Integer.parseInt((TheOppEloField.getText())) > 3000))
                Helper.Error("Elo outside of range. Please enter the correct Elo between 0-3000.");
            else {
                if (!(TheWhiteWonRadioButton.isSelected()) && !(TheWhiteDrawRadioButton.isSelected()) && !(TheWhiteLostRadioButton.isSelected())
                    && !(TheBlackWonRadioButton.isSelected()) && !(TheBlackDrawRadioButton.isSelected()) && !(TheBlackLostRadioButton.isSelected())) {
                    Helper.Error("Please select a radio button. You cannot leave a selection empty.");
                }
                else {
                    if (TheOpeningBox.getSelectionModel().isEmpty()) {
                        Helper.Error("To record the outcome, you must select an opening using the choicebox.");
                    }
                    else {
                        String selectedOpening = TheOpeningBox.getSelectionModel().getSelectedItem().toString();
                        System.out.println(selectedOpening);
                        for (int i = 0; i < Opening.allOpenings.size(); i++) {
                            if (selectedOpening.equals(Opening.allOpenings.get(i).getOpeningName())) {

                                System.out.println("Valid opening selected");

                                Opening bookOpening = Opening.allOpenings.get(i);
                                Opening openingToAdd = bookOpening;
                                boolean hasPlayedBefore = false;

                                for (int j = 0; j < Opening.playerResults.size(); j++) {
                                    if (selectedOpening.equals(Opening.playerResults.get(j).getOpeningName())) {

                                        System.out.println("Played before");

                                        hasPlayedBefore = true;

                                        Opening.playerResults.get(j).setNumGames((Opening.playerResults.get(j).getNumGames() + 1));

                                        if (TheWhiteWonRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("white")) {
                                            Opening.playerResults.get(j).setPercentageWin((Opening.playerResults.get(j).getPercentageWin() + 1));
                                        }
                                        if (TheWhiteDrawRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("white")) {
                                            Opening.playerResults.get(j).setPercentageDraw((Opening.playerResults.get(j).getPercentageDraw() + 1));
                                        }
                                        if (TheWhiteLostRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("white")) {
                                            Opening.playerResults.get(j).setPercentageLoss((Opening.playerResults.get(j).getPercentageLoss() + 1));
                                        }
                                        if (TheBlackWonRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("black")) {
                                            Opening.playerResults.get(j).setPercentageWin((Opening.playerResults.get(j).getPercentageWin() + 1));
                                        }
                                        if (TheBlackDrawRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("black")) {
                                            Opening.playerResults.get(j).setPercentageDraw((Opening.playerResults.get(j).getPercentageDraw() + 1));
                                        }
                                        if (TheBlackLostRadioButton.isSelected() && Opening.allOpenings.get(i).getSide().equals("black")) {
                                            Opening.playerResults.get(j).setPercentageLoss((Opening.playerResults.get(j).getPercentageLoss() + 1));
                                        }

                                    }
                                }

                                if (hasPlayedBefore == false) {

                                    System.out.println("Not played before");

                                    openingToAdd.setOpeningName(bookOpening.getOpeningName());
                                    openingToAdd.setSide(bookOpening.getSide());
                                    openingToAdd.setNumGames(1);
                                    openingToAdd.setPerformanceRating(0);
                                    openingToAdd.setAverageRating(0);

                                    if (TheWhiteWonRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(1);
                                        openingToAdd.setPercentageDraw(0);
                                        openingToAdd.setPercentageLoss(0);
                                    }
                                    if (TheWhiteDrawRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(0);
                                        openingToAdd.setPercentageDraw(1);
                                        openingToAdd.setPercentageLoss(0);
                                    }
                                    if (TheWhiteLostRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(0);
                                        openingToAdd.setPercentageDraw(0);
                                        openingToAdd.setPercentageLoss(1);
                                    }
                                    if (TheBlackWonRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(1);
                                        openingToAdd.setPercentageDraw(0);
                                        openingToAdd.setPercentageLoss(0);
                                    }
                                    if (TheBlackDrawRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(0);
                                        openingToAdd.setPercentageDraw(1);
                                        openingToAdd.setPercentageLoss(0);
                                    }
                                    if (TheBlackLostRadioButton.isSelected()) {
                                        openingToAdd.setPercentageWin(0);
                                        openingToAdd.setPercentageDraw(0);
                                        openingToAdd.setPercentageLoss(1);
                                    }

                                    openingToAdd.setMoves(bookOpening.getMoves());

                                    Opening.WriteToCsv(openingToAdd);

                                }


                            }
                        }
                        System.out.println("Submitted");
                    }
                    Helper.Alert("Your result has been submitted.");
                    Helper.Close(TheBackButton);
                    Stage mainMenuStage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
                    mainMenuStage.setTitle("Main Menu");
                    mainMenuStage.setScene(new Scene(root, 600, 600));
                    mainMenuStage.show();
                }
            }
        }
        catch (NumberFormatException e) {
            Helper.Error("Your Elo input was invalid. Please ensure it is only a number between 0-3000.");
        }

    }
}
