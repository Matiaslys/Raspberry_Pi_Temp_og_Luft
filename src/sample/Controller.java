package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Controller {
    @FXML
    private LineChart<String, Number> chart;

    public void Chart() {
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String,Number>("jan",200));
        series.getData().add(new XYChart.Data<String,Number>("feb",400));
        series.getData().add(new XYChart.Data<String,Number>("mart",500));
        series.getData().add(new XYChart.Data<String,Number>("april",600));
        chart.getData().add(series);
    }
}
