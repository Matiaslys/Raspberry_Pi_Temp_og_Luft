package sample;

import java.util.Observable;

public class SensorData extends Observable {
    // dette er vores sensordata klasse hvor vi bruger gætter og setter samt observers til at håndtere den information vi får fra python serveren
    private double temp;
    private double hum;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
            setChanged();
            notifyObservers(temp);
    }

    public double getHum() {
        return hum;
    }

    public void setHum(double hum) {
        this.hum = hum;
            setChanged();
            notifyObservers(hum);
    }
}
