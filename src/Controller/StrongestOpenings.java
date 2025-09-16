package Controller;

import Model.Opening;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StrongestOpenings implements Initializable {

    public BarChart TheChart;
    public Button TheBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized!");

        ObservableList<Opening> listOfOpenings = FXCollections.observableArrayList();
        int averageRating;
        float playerWinPercentage = 0;
        float playerCombinedPercentage = 0;
        float winPercentage = -1;
        float drawPercentage = -1;
        float combinedPercentage = -1;
        Opening first = null;
        Opening second = null;
        Opening third = null;

        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        for (int i = 0; i < listOfOpenings.size(); i++) {
            if (winPercentage < (listOfOpenings.get(i).getPercentageWin())) {
                first = listOfOpenings.get(i);
                winPercentage = listOfOpenings.get(i).getPercentageWin();
            }
        }
        System.out.println(first.getOpeningName());

        listOfOpenings.removeAll();
        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        winPercentage = -1;
        for (int i = 0; i < listOfOpenings.size(); i++) {
            if (!(listOfOpenings.get(i).getOpeningName().equals(first.getOpeningName()))) {
                if (winPercentage < (listOfOpenings.get(i).getPercentageWin())) {
                    second = listOfOpenings.get(i);
                    winPercentage = listOfOpenings.get(i).getPercentageWin();
                }
            }
        }
        System.out.println(second.getOpeningName());

        listOfOpenings.removeAll();
        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        winPercentage = -1;
        for (int i = 0; i < listOfOpenings.size(); i++) {
            if ((!(listOfOpenings.get(i).getOpeningName() == first.getOpeningName())) && (!(listOfOpenings.get(i).getOpeningName() == second.getOpeningName()))){
                if (winPercentage < (listOfOpenings.get(i).getPercentageWin())) {
                    third = listOfOpenings.get(i);
                    winPercentage = listOfOpenings.get(i).getPercentageWin();
                }
            }
        }
        System.out.println(third.getOpeningName());

        float firstWinPercentage;
        float secondWinPercentage;
        float thirdWinPercentage;
        float firstDrawPercentage;
        float secondDrawPercentage;
        float thirdDrawPercentage;
        float firstLossPercentage;
        float secondLossPercentage;
        float thirdLossPercentage;

        firstWinPercentage = (((first.getPercentageWin()) / (first.getPercentageWin() + first.getPercentageDraw() + first.getPercentageLoss()))); System.out.println(firstWinPercentage);
        secondWinPercentage = (((second.getPercentageWin()) / (second.getPercentageWin() + second.getPercentageDraw() + second.getPercentageLoss()))); System.out.println(secondWinPercentage);
        thirdWinPercentage = (((third.getPercentageWin()) / (third.getPercentageWin() + third.getPercentageDraw() + third.getPercentageLoss()))); System.out.println(thirdWinPercentage);

        firstDrawPercentage = (((first.getPercentageDraw()) / (first.getPercentageWin() + first.getPercentageDraw() + first.getPercentageLoss()))); System.out.println(firstDrawPercentage);
        secondDrawPercentage = (((second.getPercentageDraw()) / (second.getPercentageWin() + second.getPercentageDraw() + second.getPercentageLoss()))); System.out.println(secondDrawPercentage);
        thirdDrawPercentage = (((third.getPercentageDraw()) / (third.getPercentageWin() + third.getPercentageDraw() + third.getPercentageLoss()))); System.out.println(thirdDrawPercentage);

        firstLossPercentage = (((first.getPercentageLoss()) / (first.getPercentageWin() + first.getPercentageDraw() + first.getPercentageLoss()))); System.out.println(firstLossPercentage);
        secondLossPercentage = (((second.getPercentageLoss()) / (second.getPercentageWin() + second.getPercentageDraw() + second.getPercentageLoss()))); System.out.println(secondLossPercentage);
        thirdLossPercentage = (((third.getPercentageLoss()) / (third.getPercentageWin() + third.getPercentageDraw() + third.getPercentageLoss()))); System.out.println(thirdLossPercentage);

        first.setPercentageWin(firstWinPercentage);
        second.setPercentageWin(secondWinPercentage);
        third.setPercentageWin(thirdWinPercentage);

        first.setPercentageDraw(firstDrawPercentage);
        second.setPercentageDraw(secondDrawPercentage);
        third.setPercentageDraw(thirdDrawPercentage);

        first.setPercentageLoss(firstLossPercentage);
        second.setPercentageLoss(secondLossPercentage);
        third.setPercentageLoss(thirdLossPercentage);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Opening");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("Win", "Draw", "Loss")));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Results");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Win");
        series1.getData().add(new XYChart.Data<>(first.getOpeningName(), first.getPercentageWin()));
        series1.getData().add(new XYChart.Data<>(second.getOpeningName(), second.getPercentageWin()));
        series1.getData().add(new XYChart.Data<>(third.getOpeningName(), third.getPercentageWin()));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Draw");
        series2.getData().add(new XYChart.Data<>(first.getOpeningName(), first.getPercentageDraw()));
        series2.getData().add(new XYChart.Data<>(second.getOpeningName(), second.getPercentageDraw()));
        series2.getData().add(new XYChart.Data<>(third.getOpeningName(), third.getPercentageDraw()));

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Loss");
        series3.getData().add(new XYChart.Data<>(first.getOpeningName(), first.getPercentageLoss()));
        series3.getData().add(new XYChart.Data<>(second.getOpeningName(), second.getPercentageLoss()));
        series3.getData().add(new XYChart.Data<>(third.getOpeningName(), third.getPercentageLoss()));

        TheChart.getData().addAll(series1, series2, series3);


    }

    public void onTheBackButtonAction(ActionEvent actionEvent) throws IOException {

        Helper.Helper.Close(TheBackButton);

    }
}
