package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Klient klient = new Klient();
        klient.recieveData();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent mineForloebParent = (Parent) fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.Chart();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(mineForloebParent, 550, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
