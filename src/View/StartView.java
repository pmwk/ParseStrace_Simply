package src.View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.StraceProtocol.StraceProtocol;

import java.io.File;

public class StartView extends Pane {

    private String filePath = "/home/kanumba/Projects/ParseStrace_Simply/src/src/log.strace";

    public StartView() {
        super();

        setPrefHeight(400);

        BorderPane root = new BorderPane();
        root.setPrefWidth(800);
        root.setPrefHeight(350);

        HBox top_root = new HBox();
        Label filePath_lab = new Label();
        filePath_lab.setText(filePath);
        filePath_lab.setStyle("-fx-font-size: 20px;");

        Button start_but = new Button("The battle begins!");
        start_but.setStyle("-fx-font-size: 25px; " +
                "-fx-background-color: hotpink; " +
                "");

        root.setTop(top_root);
        root.setCenter(start_but);

        top_root.getChildren().addAll(filePath_lab);

        getChildren().addAll(root);

        BorderPane.setAlignment(start_but, Pos.CENTER);

        prefWidthProperty().bind(root.prefWidthProperty());
        prefHeightProperty().bind(root.prefHeightProperty());

        start_but.setOnMouseClicked(event -> {
            File log = new File(filePath);
            StraceProtocol straceProtocol = new StraceProtocol(log);
            ((Stage)getScene().getWindow()).close();

            MainView.startMainView(straceProtocol);
        });
    }
}
