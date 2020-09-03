package sample;

import java.util.Observable;

public class SensorData extends Observable {
    private double temp;
    private double hum;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
            setChanged();
            notifyObservers(temp);
//        System.out.println(temp);
    }

    public double getHum() {
        return hum;
    }

    public void setHum(double hum) {
        this.hum = hum;
            setChanged();
            notifyObservers(hum);
//        System.out.println(hum);
    }
}
