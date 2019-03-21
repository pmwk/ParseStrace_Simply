package src;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.View.StartView;

import static javafx.application.Application.launch;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        src.Configuration.initConfiguration();

        Scene scene = new Scene(new StartView());
        stage.setScene(scene);
        stage.show();
    } //открываем стартовое окно
}
