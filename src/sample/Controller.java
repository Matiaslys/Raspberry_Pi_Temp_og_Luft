package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;

public class Controller {
    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private Label temp;

    @FXML
    private Label luft;

    public void Chart() throws IOException {
        Klient klient = new Klient();
        klient.recieveData();
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        series.getData().add(new XYChart.Data<String,Number>("Temp", klient.temp));
        series.getData().add(new XYChart.Data<String,Number>("hum", klient.hum));
        chart.getData().add(series);

        temp.setText("Temperaturen er: " + klient.temp);
        luft.setText("Luftfugtigheden er: " + klient.hum);
    }
}
