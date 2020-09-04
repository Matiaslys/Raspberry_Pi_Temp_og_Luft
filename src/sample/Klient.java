package sample;

import com.sun.javafx.image.impl.BaseByteToIntConverter;

import java.io.*;
import java.net.Socket;

public class Klient {
    private double temp;
    private double hum;
    public void recieveData(SensorData sensorData) throws IOException {
        new Thread(new Runnable() {
            Socket socket = new Socket("192.168.43.125", 8000);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            @Override
            public void run() {
                try {
                    while (true) {
                        String humid = stdIn.readLine();
                        String in = stdIn.readLine();
                        System.out.println(humid);
                        System.out.println(in);
                        temp = Double.parseDouble(in);
                        hum = Double.parseDouble(humid);
                        System.out.println("temp " + temp);
                        System.out.println("hum " + hum);
                        sensorData.setTemp(temp);
                        sensorData.setHum(hum);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        }
    }
