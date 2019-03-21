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
import src.Tree.NodeCommandTree;

import java.util.ArrayList;
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

    private StructLabCB commands = new StructLabCB("Command");
    private StructLabCB results = new StructLabCB("Result");
    private HBox arguments_root = new HBox();
    private NodeCommandTree currentTree;

    private void createParametersRoot() {
        Set<String> list_commands = straceProtocol.getCommands_name();
        commands.setList(FXCollections.observableArrayList(list_commands));
        Action commands_action = (observableValue, oldValue, newValue) -> {
            currentTree = straceProtocol.getTreeArgs(newValue.toString());
            createFirstArg();
        };
        commands.setAction(commands_action);


        parameters_root.getChildren().addAll(commands, arguments_root, results);
    }

    private void createFirstArg() {
        removeArgsMore(-1); // удаляем все сущесвтующие
        StructLabCB_arg arg = new StructLabCB_arg("Argument 0");
        arg.id = 0;
        if (commands.getValueCB() != null) {
            arg.setList(FXCollections.observableArrayList(currentTree.getChildsValues()));
        }
        arguments_root.getChildren().addAll(arg);
        refreshResultCB();
    }

    private ArrayList<String> getCurrentArgs() {
        ArrayList<String> args = new ArrayList<>();

        for (int i = 0; i < arguments_root.getChildren().size(); i++) {
            StructLabCB structLabCB = (StructLabCB) (arguments_root.getChildren().get(i));

            args.add(structLabCB.getValue());

        }
        return args;
    }

    private void fillArgs(int index, ObservableList items) {
        if (index < arguments_root.getChildren().size()) {
            StructLabCB structLabCB = (StructLabCB) arguments_root.getChildren().get(index);
            structLabCB.cb.setItems(items);
        }
    }

    private void removeArgsMore(int id) {
        int size = arguments_root.getChildren().size();

        if (id + 1 < size) {
            arguments_root.getChildren().remove(id + 1, size);
        }

    }

    private void refreshResultCB () {
        Set<String> results = currentTree.getAllExistResultsFrom(getCurrentArgs());
        if (results != null) {
            this.results.setList(FXCollections.observableArrayList(results));
        } else {
            System.out.println("results == null");
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

        public String getValue() {
            Object v = cb.getValue();
            if (v == null) {
                return null;
            }
            return cb.getValue().toString();
        }

        public Object getValueCB() {
            return cb.getValue();
        }
    }

    interface Action<T> {

        public void action(ObservableValue observableValue, T oldValue, T newValue);

    }

    class StructLabCB_arg extends StructLabCB {

        private int id;
        Action args_action = (observableValue, oldValue, newValue) -> {
            if (oldValue != null) {
                removeArgsMore(id);
            }
            ArrayList<String> childsForNewArg = currentTree.getChildsFor(getCurrentArgs());
            if (childsForNewArg != null && childsForNewArg.size() > 0) {
                StructLabCB_arg newArg = new StructLabCB_arg("Argument " + (id + 1));
                newArg.id = id + 1;
                newArg.setList(FXCollections.observableArrayList(childsForNewArg));
                arguments_root.getChildren().addAll(newArg);
            }
            refreshResultCB();
        };


        private StructLabCB_arg() {
            super();
        }

        public StructLabCB_arg(String text) {
            super(text);
            setAction(args_action);
        }
    }


}
