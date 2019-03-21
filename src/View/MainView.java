package src.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import src.StraceProtocol.StraceProtocol;

public class MainView extends Pane {

    private StraceProtocol protocol;
    private TableView<CommandProperty> table;

    private static final double HEIGHT1 = 100;
    private static final double WIDTH1 = 400;

    private MainView(StraceProtocol protocol) {
        this.protocol = protocol;

        SettingsView settingsView = new SettingsView(protocol);

        VBox root = new VBox();

        HBox top_root = new HBox();

        Button settings_but = new Button("Settings");
        settings_but.setPrefHeight(HEIGHT1);
        settings_but.setPrefWidth(WIDTH1);
        Button stats_but = new Button("Stats");
        stats_but.setPrefHeight(HEIGHT1);
        stats_but.setPrefWidth(WIDTH1);

        createTable();

        top_root.getChildren().addAll(settings_but, stats_but);
        root.getChildren().addAll(top_root, table);
        getChildren().add(root);

        table.prefWidthProperty().bind(widthProperty());
        table.prefHeightProperty().bind(heightProperty());

        settings_but.setOnMouseClicked(event -> {
            settingsView.show();
        });
    }

    private void createTable() {
        table = new TableView<>();

        TableColumn id_col = new TableColumn("id");
        TableColumn time_col = new TableColumn("time");
        TableColumn name_col = new TableColumn("name");
        TableColumn args_col = new TableColumn("args");
        TableColumn result_col = new TableColumn("result");
        table.getColumns().addAll(id_col, time_col, name_col, args_col, result_col);

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        time_col.setCellValueFactory(new PropertyValueFactory<>("time"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        args_col.setCellValueFactory(new PropertyValueFactory<>("args"));
        result_col.setCellValueFactory(new PropertyValueFactory<>("result"));

        ObservableList<CommandProperty> commands = FXCollections.observableArrayList(CommandProperty.convert(protocol.getCommands_status()));
        table.setItems(commands);
    }

    public static void startMainView(StraceProtocol protocol) {
        Stage stage = new Stage();
        MainView mainView = new MainView(protocol);
        Scene scene = new Scene(mainView);
        stage.setScene(scene);
        stage.show();

        mainView.prefWidthProperty().bind(stage.widthProperty());
        mainView.prefHeightProperty().bind(stage.heightProperty());
    }
}
