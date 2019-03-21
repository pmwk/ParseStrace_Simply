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

    static void printArray(String array[]) {
        if (array == null) {
            System.out.println("null");
        } else if (array.length == 0) {
            System.out.println("0 length");
        } else {
            for (String str : array) {
                System.out.println(str);
            }
            System.out.println();
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(new StartView());
        stage.setScene(scene);
        stage.show();
    }
}
