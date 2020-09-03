package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

public class Controller {

    @FXML
    private LineChart<Number, Number> tempchart;

    @FXML
    private LineChart<Number, Number> humchart;

    @FXML
    private Label temp;

    @FXML
    private Label luft;

    @FXML
    private Label dato;

    private XYChart.Series<Number, Number> tempChartSeries;
    private XYChart.Series<Number, Number> humChartSeries;


    public void Chart(SensorData sensorData) throws IOException {
        tempChartSeries = new XYChart.Series<Number, Number>();
        humChartSeries = new XYChart.Series<Number, Number>();
        tempChartSeries.setName("Temperatur");
        humChartSeries.setName("Luftfugtighed");
        tempchart.getData().add(tempChartSeries);
        humchart.getData().add(humChartSeries);
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
            LocalTime localTime = LocalTime.now();
            double time = (double) localTime.getMinute();
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            tempChartSeries.getData().add(new XYChart.Data<Number, Number>(time, sensorData.getTemp()));
            humChartSeries.getData().add(new XYChart.Data<Number, Number>(time, sensorData.getHum()));
            dato.setText("Tidspunkt for sidste aflæsning " + formattedDate);
            temp.setText("Temperaturen er: " + sensorData.getTemp());
            luft.setText("Luftfugtigheden er: " + sensorData.getHum());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
