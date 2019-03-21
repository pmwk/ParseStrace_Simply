package src.View;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import src.StraceProtocol.Status;
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
        createSettingsRoot();

        root.getChildren().addAll(parameters_root, settings_root, addFilters_root);
        getChildren().addAll(root);
    }

    private StructLabCB commands = new StructLabCB("Command");
    private StructLabCB results = new StructLabCB("Result");
    private HBox arguments_root = new HBox();
    private NodeCommandTree currentTree;

    private void createParametersRoot() {
        refreshCommandsList();
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

    private ArrayList<String> getCurrentArgs_consistenly() {
        ArrayList<String> args = new ArrayList<>();

        for (int i = 0; i < arguments_root.getChildren().size(); i++) {
            StructLabCB structLabCB = (StructLabCB) (arguments_root.getChildren().get(i));
            if (structLabCB.getValue() != null) {
                args.add(structLabCB.getValue());
            } else {
                break;
            }

        }
        return args;
    } //возвращает текущие выбранные аргументы. Если встречаем null, то он и дальнейшие аргументы не учитываются

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
        Set<String> results = currentTree.getAllExistResultsFrom(getCurrentArgs_consistenly());
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
            ArrayList<String> childsForNewArg = currentTree.getChildsFor(getCurrentArgs_consistenly());
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


    private void createSettingsRoot() {
        StructLabCB mode = new StructLabCB("Mode");
        mode.setList(FXCollections.observableArrayList(new String[]{"Consistently", "Globally"}));
        mode.cb.valueProperty().setValue("Consistently");
        mode.setAction((observableValue, oldValue, newValue) -> {
            if (newValue.toString().equals("Globally")) {
                System.out.println("Функционал в разработке");
            }
        });


        VBox checkb_root = new VBox();
        HBox rb_tg_root = new HBox();
        RadioButton hidden_rb = new RadioButton("Hide");
        hidden_rb.setSelected(true);
        RadioButton deleted_rb = new RadioButton("Delete");

        ToggleGroup rb_tg = new ToggleGroup();
        rb_tg.getToggles().addAll(hidden_rb, deleted_rb);

        CheckBox withoutArg_cb = new CheckBox("Without arguments");
        rb_tg_root.getChildren().addAll(hidden_rb, deleted_rb);
        checkb_root.getChildren().addAll(rb_tg_root, withoutArg_cb);

        HBox but_root = new HBox();

        Button apply_but = new Button("Apply");
        Button reset_but = new Button("Reset");

        but_root.getChildren().addAll(apply_but, reset_but);

        ImageView tree_iv = new ImageView(new Image("Resource/treant_protector1.jpg"));
        tree_iv.setFitHeight(90);
        tree_iv.setFitWidth(90);

        Label add_lab = new Label("+");
        add_lab.setStyle("-fx-font-size: 45px;");

        settings_root.getChildren().addAll(mode, checkb_root, but_root, tree_iv, add_lab);

        apply_but.setOnMouseClicked(event -> {
            boolean isConsistenly = mode.cb.getValue().equals("Consistently"); //последовательно аргументы применять, или глобально
            boolean isHide = hidden_rb.isSelected();
            boolean inversion = false;

            if (commands.getValueCB() == null) {
                System.out.println("НЕ ВЫБРАНА КОМАНДА, СОРРИ");
            } else if (isConsistenly) {
                ArrayList<String> args = getCurrentArgs_consistenly();
                String result = "";
                if (results.getValueCB() == null) {
                    result = null;
                } else {
                    result = results.getValueCB().toString();
                }

                if (isHide) {
                    straceProtocol.refactorElementsByCommand(commands.getValueCB().toString(), args, result, Status.Hide, inversion);
                }
                MainView.refreshTable();
                refreshCommandsList();
            } else {

            }
        });
    }

    private void refreshCommandsList () {
        Set<String> list_commands = straceProtocol.getCommands_name();
        commands.setList(FXCollections.observableArrayList(list_commands));
    }

}
