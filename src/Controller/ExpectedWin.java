package Controller;

import Model.Opening;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ExpectedWin implements Initializable {

    @FXML
    public BarChart TheChart;
    public Button TheBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Initialized!");

        ObservableList<Opening> listOfOpenings = FXCollections.observableArrayList();
        int averageRating;
        float playerWinPercentage = 0;
        float playerCombinedPercentage = 0;
        float winPercentageOverExpected = -100;
        float drawPercentage = -1;
        float combinedPercentage = -1;
        Opening first = null;
        Opening second = null;
        Opening third = null;

        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        //the master win rate is now stored in the local "first" loss rate. Kind of confusing but makes sense.
        //Now we can just compare first win rate to first loss rate.
        for (int i = 0; i < listOfOpenings.size(); i++) {
            Opening changed = listOfOpenings.get(i);
            listOfOpenings.get(i).setPercentageWin((((changed.getPercentageWin()) / (changed.getPercentageWin() + changed.getPercentageDraw() + changed.getPercentageLoss())))); System.out.println(listOfOpenings.get(i).getPercentageWin());
            for (int j = 0; j < Opening.allOpenings.size(); j++) {
                if (Opening.allOpenings.get(j).getOpeningName().equals(changed.getOpeningName())) {
                    changed.setPercentageLoss((Opening.allOpenings.get(j).getPercentageWin()) / 100);
                    System.out.println(changed.getPercentageLoss());
                }
            }
        }

        for (int i = 0; i < listOfOpenings.size(); i++) {
            if (winPercentageOverExpected < (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss())) {
                first = listOfOpenings.get(i);
                winPercentageOverExpected = (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss());
                first.setPercentageDraw(winPercentageOverExpected);
            }
        }
        System.out.println(first.getOpeningName() + " " + first.getPercentageDraw());

        listOfOpenings.removeAll();
        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        winPercentageOverExpected = -100;
        for (int i = 0; i < listOfOpenings.size(); i++) {
            if (!(listOfOpenings.get(i).getOpeningName().equals(first.getOpeningName()))) {
                if (winPercentageOverExpected < (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss())) {
                    second = listOfOpenings.get(i);
                    winPercentageOverExpected = (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss());
                    second.setPercentageDraw(winPercentageOverExpected);
                }
            }
        }
        System.out.println(second.getOpeningName() + " " + second.getPercentageDraw());

        listOfOpenings.removeAll();
        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
        }
        winPercentageOverExpected = -100;
        for (int i = 0; i < listOfOpenings.size(); i++) {
            if ((!(listOfOpenings.get(i).getOpeningName() == first.getOpeningName())) && (!(listOfOpenings.get(i).getOpeningName() == second.getOpeningName()))){
                if (winPercentageOverExpected < (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss())) {
                    third = listOfOpenings.get(i);
                    winPercentageOverExpected = (listOfOpenings.get(i).getPercentageWin() - listOfOpenings.get(i).getPercentageLoss());
                    third.setPercentageDraw(winPercentageOverExpected);
                }
            }
        }
        System.out.println(third.getOpeningName() + " " + third.getPercentageDraw());

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Opening");
        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("My win percentage", "Expected win percentage given historical data")));

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Results");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("My win percentage");
        series1.getData().add(new XYChart.Data<>(first.getOpeningName(), first.getPercentageWin()));
        series1.getData().add(new XYChart.Data<>(second.getOpeningName(), second.getPercentageWin()));
        series1.getData().add(new XYChart.Data<>(third.getOpeningName(), third.getPercentageWin()));
        System.out.println(first.getOpeningName() + first.getPercentageWin());
        System.out.println(second.getOpeningName() + second.getPercentageWin());
        System.out.println(third.getOpeningName() + third.getPercentageWin());

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Expected win percentage given historical data");
        series2.getData().add(new XYChart.Data<>(first.getOpeningName(), first.getPercentageLoss()));
        series2.getData().add(new XYChart.Data<>(second.getOpeningName(), second.getPercentageLoss()));
        series2.getData().add(new XYChart.Data<>(third.getOpeningName(), third.getPercentageLoss()));
        System.out.println(first.getOpeningName() + first.getPercentageLoss());
        System.out.println(second.getOpeningName() + second.getPercentageLoss());
        System.out.println(third.getOpeningName() + third.getPercentageLoss());

        TheChart.getData().addAll(series1, series2);

    }

    public void onTheBackButtonAction(ActionEvent actionEvent) throws IOException {

        Helper.Helper.Close(TheBackButton);

    }
}
