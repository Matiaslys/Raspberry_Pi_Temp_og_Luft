package sample;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Klient {
    private double temp;
    private double hum;
    public void recieveData(SensorData sensorData) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        new Thread(new Runnable() {
            // prøver og få connection til serveren.
            final Socket socket = new Socket("192.168.43.125", 8000);
            private final String keyb2 = "This is a key123";
            final BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            @Override
            public void run() {
                try {
                    while (true) {
                        // får information hele tiden imens vi har en connection.
                        String humid = stdIn.readLine();
                        String in = stdIn.readLine();

                        // sout
                        System.out.println(humid);
                        System.out.println(in);

                        // Decrypt
                        String decrptedMessHum = Decrypt(keyb2,humid);
                        String decrptedMessIn = Decrypt(keyb2, in);
                        // sout
                        System.out.println(decrptedMessHum);
                        System.out.println(decrptedMessIn);

                        // laver information om til double så vi kan bruge den.
                        temp = Double.parseDouble(decrptedMessIn);
                        hum = Double.parseDouble(decrptedMessHum);
                        System.out.println("temp " + temp);
                        System.out.println("hum " + hum);
                        // sætter informationen i vores klasse
                        sensorData.setTemp(temp);
                        sensorData.setHum(hum);

                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            // starter tråden
          }).start();
        }

        public String Decrypt(String key,String ciphertext) {
            try {
                byte[] cipherbytes = Base64.getDecoder().decode(ciphertext);

                byte[] initVector = Arrays.copyOfRange(cipherbytes,0,16);

                byte[] messagebytes = Arrays.copyOfRange(cipherbytes,16,cipherbytes.length);

                IvParameterSpec iv = new IvParameterSpec(initVector);
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

                byte[] byte_array = cipher.doFinal(messagebytes);


                return new String(byte_array, StandardCharsets.UTF_8);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
