package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // laver en ny instance af SensorData, som vi kan bruge når vi får vores data.
        SensorData sensorData = new SensorData();
        // får vores fxml fil.
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent mineForloebParent = (Parent) fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        // sender dataen fra vores SensorData klasse videre
        controller.Chart(sensorData);
        primaryStage.setTitle("Temp og Luft");
        primaryStage.setScene(new Scene(mineForloebParent, 1100, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
