package sample;

import java.io.*;
import java.net.Socket;

public class Klient {
    DataInputStream fromServer;
    public void recieveData() throws IOException {
        Socket socket = new Socket("localhost", 8000);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        fromServer = new DataInputStream(socket.getInputStream());
        String in = stdIn.readLine();
        String humid = stdIn.readLine();
        System.out.println(in);
        System.out.println(humid);

    }
}
