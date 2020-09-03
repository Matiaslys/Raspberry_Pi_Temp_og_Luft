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
    SensorData sensorData;
    @FXML
    private LineChart<String, Number> chart;

    @FXML
    private Label temp;

    @FXML
    private Label luft;

    @FXML
    private Label dato;

    private XYChart.Series<String, Number> series;

    public void Chart() throws IOException {
        sensorData.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
//                try {
//                    Klient klient = new Klient();
//                    klient.recieveData();
//                    LocalDateTime myDateObj = LocalDateTime.now();
//                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//                    String formattedDate = myDateObj.format(myFormatObj);
//                    series = new XYChart.Series<String, Number>();
//                    series.getData().add(new XYChart.Data<String, Number>("Temp", klient.temp));
//                    series.getData().add(new XYChart.Data<String, Number>("hum", klient.hum));
//                    chart.getData().add(series);
//                    dato.setText("Tidspunkt for sidste aflæsning "+ formattedDate);
//                    temp.setText("Temperaturen er: " + klient.temp);
//                    luft.setText("Luftfugtigheden er: " + klient.hum);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
                System.out.println("hej" + sensorData.getTemp());
                System.out.println("hej2" + sensorData.getHum());
                InsertTempAndHum();
//            }
//        });
            }
        });
        System.out.println("hej" + sensorData.getTemp());
        System.out.println("hej2" + sensorData.getHum());
        Klient klient = new Klient();
        klient.recieveData();
    }

    private void InsertTempAndHum() {
        //        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
        try {
            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = myDateObj.format(myFormatObj);
            series = new XYChart.Series<String, Number>();
//            series.getData().add(new XYChart.Data<String, Number>("Temp", klient.temp));
//            series.getData().add(new XYChart.Data<String, Number>("hum", klient.hum));
            chart.getData().add(series);
            dato.setText("Tidspunkt for sidste aflæsning " + formattedDate);
            temp.setText("Temperaturen er: " + sensorData.getTemp());
            luft.setText("Luftfugtigheden er: " + sensorData.getHum());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //            }
//        });
    }
}
