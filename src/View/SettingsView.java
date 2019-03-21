package src.View;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.StraceProtocol.StraceProtocol;

import java.util.Set;

public class SettingsView {

    private StraceProtocol protocol;

    private Stage stage;

    public SettingsView(StraceProtocol protocol) {
        this.protocol = protocol;
        refreshListCommands();

        stage = new Stage();
        Scene scene = new Scene(getRoot());
        stage.setScene(scene);

        /*stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                stage.hide();
            }
        });*/
    }

    private Set<String> listCommands;
    private Pane getRoot() {
        Pane root = new Pane();

        RowForHide test = new RowForHide();


        root.getChildren().addAll(test);
        return root;
    }

    public void refreshListCommands() {
        listCommands = protocol.getCommands_name();
    }

    public void show() {
        refreshListCommands();
        stage.show();
    }

    class RowForHide extends Pane {
        public RowForHide() {
            super();

            FilterRow filterRow = new FilterRow(protocol);
            getChildren().addAll(filterRow);
            /*VBox command_root = new VBox();
            Label commands_lab = new Label("Command");
            ComboBox<String> commands_cb = new ComboBox<>();
            commands_cb.setItems(FXCollections.observableArrayList(listCommands));
            command_root.getChildren().addAll(commands_lab, commands_cb);

            VBox args_root = new VBox();
            Label args_lab = new Label("Args");
            ComboBox<String> args_cb = new ComboBox<>();
            args_root.getChildren().addAll(args_lab, args_cb);

            getChildren().addAll(command_root);

            commands_cb.valueProperty().addListener((ov, old_value, new_value) -> {
                CommandTree tree = protocol.getTreeArgs(new_value.toString());
            });*/
        }
    }
}
