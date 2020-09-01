package sample;

import com.sun.javafx.image.impl.BaseByteToIntConverter;

import java.io.*;
import java.net.Socket;

public class Klient {
    DataInputStream fromServer;
    int temp;
    int hum;
    public void recieveData() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        fromServer = new DataInputStream(socket.getInputStream());
        String humid = stdIn.readLine();
        String in = stdIn.readLine();
        temp = Integer.parseInt(in);
        hum = Integer.parseInt(humid);
        System.out.println(temp);
        System.out.println(hum);
    }
}
