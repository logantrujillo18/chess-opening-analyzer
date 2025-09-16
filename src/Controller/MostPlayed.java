package Controller;

import Helper.Helper;
import Model.Opening;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MostPlayed implements Initializable {

    public Button TheBackButton;
    public PieChart TheChart;
    ObservableList<Opening> listOfOpenings = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 0; i < Opening.playerResults.size(); i++){
            listOfOpenings.add(Opening.playerResults.get(i));
            pieChartData.add(new PieChart.Data(listOfOpenings.get(i).getOpeningName(), listOfOpenings.get(i).getNumGames()));
        }

        TheChart.setData(pieChartData);
        TheChart.setLegendSide(Side.LEFT);
        TheChart.setTitle("Comparative view of how often I play each opening");
        TheChart.setClockwise(false);

    }

    public void onTheBackButtonAction(ActionEvent actionEvent) {

        Helper.Close(TheBackButton);

    }
}
