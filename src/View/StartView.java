package src.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import src.Configuration;
import src.StraceProtocol.StraceProtocol;

import java.io.File;

public class StartView extends Pane {

    private StringProperty filePath = new SimpleStringProperty();

    public StartView() {
        super();

        filePath.setValue(Configuration.getLastFile());

        setPrefHeight(400);

        BorderPane root = new BorderPane();
        root.setPrefWidth(1000);
        root.setPrefHeight(400);

        VBox top_root = new VBox();
        Label filePath_lab = new Label();
        Button fileChooser_but = new Button("Выбрать файл");
        filePath_lab.textProperty().bind(filePath);
        filePath_lab.setStyle("-fx-font-size: 20px;"); //сверху можно выбрать нужный файл

        Button start_but = new Button("The battle begins!");
        start_but.setStyle("-fx-font-size: 25px; " +
                "-fx-background-color: hotpink; " +
                ""); // кнопка для начала работы

        root.setTop(top_root);
        root.setCenter(start_but);

        top_root.getChildren().addAll(filePath_lab, fileChooser_but);

        getChildren().addAll(root);

        BorderPane.setAlignment(start_but, Pos.CENTER);

        prefWidthProperty().bind(root.prefWidthProperty());
        prefHeightProperty().bind(root.prefHeightProperty());

        start_but.setOnMouseClicked(event -> {
            File log = new File(filePath.get());
            StraceProtocol straceProtocol = new StraceProtocol(log);
            ((Stage)getScene().getWindow()).close();

            MainView.startMainView(straceProtocol);
        });

        fileChooser_but.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            String filePath_new = file.getAbsolutePath();
            filePath.setValue(filePath_new);
            Configuration.setLastFile(filePath_new);
        });
    }
}
