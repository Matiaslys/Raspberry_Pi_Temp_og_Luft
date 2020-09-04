package sample;

import com.sun.javafx.image.impl.BaseByteToIntConverter;

import java.io.*;
import java.net.Socket;

public class Klient {
    DataInputStream fromServer;
    double temp;
    double hum;
    public void recieveData() throws IOException {
        Socket socket = new Socket("192.168.43.125", 8000);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        fromServer = new DataInputStream(socket.getInputStream());
        String humid = stdIn.readLine();
        String in = stdIn.readLine();
        temp = Double.parseDouble(in);
        hum = Double.parseDouble(humid);
        System.out.println(temp);
        System.out.println(hum);
    }
}
