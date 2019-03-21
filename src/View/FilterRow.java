package src.View;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import src.StraceProtocol.StraceProtocol;
import src.Tree.CommandTree;

import java.util.Set;

public class FilterRow extends Pane {

    private StraceProtocol straceProtocol;

    private VBox root = new VBox();

    private HBox parameters_root = new HBox();
    private HBox settings_root = new HBox();
    private HBox addFilters_root = new HBox();

    private FilterRow () {

    }

    public FilterRow(StraceProtocol straceProtocol) {
        this.straceProtocol = straceProtocol;
        createParametersRoot();

        getChildren().addAll(parameters_root, settings_root, addFilters_root);
    }

    private ComboBox<String> commands_cb = new ComboBox<>();
    private ComboBox<String> results_cb = new ComboBox<>();
    private HBox arguments_root = new HBox();
    private CommandTree currentTree;

    private void createParametersRoot() {
        StructLabCB commands = new StructLabCB("Command");
        Set<String> list_commands = straceProtocol.getCommands_name();
        commands.setList(FXCollections.observableArrayList(list_commands));
        Action commands_action = (observableValue, oldValue, newValue) -> {
            currentTree = straceProtocol.getTreeArgs(newValue.toString());
            fillArgs(0, currentTree.ge);
        };
        commands.setAction(commands_action);


        StructLabCB arg = new StructLabCB("Argument");



        StructLabCB result  = new StructLabCB("Result");

        arguments_root.getChildren().addAll(arg);
        parameters_root.getChildren().addAll(commands, arguments_root, result);
    }

    private void fillArgs(int index, ObservableList items) {
        if (index < arguments_root.getChildren().size()) {
            ComboBox comboBox = (ComboBox) arguments_root.getChildren().get(index);
            comboBox.setItems(items);
        }
    }

    class StructLabCB extends Pane {

        Label label = new Label();
        ComboBox cb = new ComboBox();
        Action action;


        private StructLabCB() {
            VBox root = new VBox();

            root.getChildren().addAll(label, cb);
            getChildren().addAll(root);

            cb.valueProperty().addListener(((observable, oldValue, newValue) -> {
                if (action != null) {
                    action.action(observable, oldValue, newValue);
                }
            }));
        }

        public StructLabCB(String text) {
            this();

            setText(text);
        }

        public void setText(String text) {
            label.setText(text);
        }

        public void setList(ObservableList list) {
            cb.setItems(list);
        }

        public void setAction(Action action) {
            this.action = action;
        }
    }

    interface Action<T> {

        public void action(ObservableValue observableValue, T oldValue, T newValue);

    }
}
