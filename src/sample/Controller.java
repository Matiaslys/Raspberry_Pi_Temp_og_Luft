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
    // fxml variabler og id.
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
        // laver 2 instancer af XYchart og giver dem data.
        tempChartSeries = new XYChart.Series<Number, Number>();
        humChartSeries = new XYChart.Series<Number, Number>();
        tempChartSeries.setName("Temperatur");
        humChartSeries.setName("Luftfugtighed");
        tempchart.getData().add(tempChartSeries);
        humchart.getData().add(humChartSeries);
        // tilføjer en Observer.
        sensorData.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                // ændre vores GUI på en ny tråd.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        InsertTempAndHum(sensorData);
                    }
                });
            }
        });
        // laver en ny instans af Klient og giver den vores sensorData som parameter.
        Klient klient = new Klient();
        klient.recieveData(sensorData);
    }

    private void InsertTempAndHum(SensorData sensorData) {
        try {
            // bruger localtime klassen til at få hvad klokken er lige nu i forhold til sekunder.
            LocalTime localTime = LocalTime.now();
            double time = (double) localTime.getSecond();
            // bruger localDateTime klassen til at få dato og klokkeslæt.
            LocalDateTime myDateObj = LocalDateTime.now();
            // formatter det så det ser pænt ud.
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            // giver vores chart data og tilføjer tekst til vores labels.
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
