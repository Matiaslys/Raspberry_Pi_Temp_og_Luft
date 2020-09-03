package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Controller{
    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private Label temp;

    @FXML
    private Label luft;

    @FXML
    private Label dato;

    private XYChart.Series<String, Number> series;

    public void Chart(SensorData sensorData) throws IOException {

        sensorData.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        InsertTempAndHum(sensorData);
                    }
                });
            }
        });
        Klient klient = new Klient();
        klient.recieveData(sensorData);
    }

    private void InsertTempAndHum(SensorData sensorData) {
        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            series = new XYChart.Series<String, Number>();
            series.getData().add(new XYChart.Data<String, Number>("Temp", sensorData.getTemp()));
            series.getData().add(new XYChart.Data<String, Number>("hum", sensorData.getHum()));
            chart.getData().add(series);
            dato.setText("Tidspunkt for sidste aflæsning " + formattedDate);
            temp.setText("Temperaturen er: " + sensorData.getTemp());
            luft.setText("Luftfugtigheden er: " + sensorData.getHum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
