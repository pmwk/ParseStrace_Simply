package src.View;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import src.StraceProtocol.Command;

import java.util.ArrayList;

public class CommandProperty {

    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty time = new SimpleStringProperty();
    private final SimpleStringProperty name = new SimpleStringProperty();
    private final SimpleStringProperty args = new SimpleStringProperty();
    private final SimpleStringProperty result = new SimpleStringProperty();


    public CommandProperty(int id, String time, String name, String args, String result) {
        this.id.set(id);
        this.time.set(time);
        this.name.set(name);
        this.args.set(args);
        this.result.set(result);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getArgs() {
        return args.get();
    }

    public SimpleStringProperty argsProperty() {
        return args;
    }

    public String getResult() {
        return result.get();
    }

    public SimpleStringProperty resultProperty() {
        return result;
    }

    public CommandProperty(Command command) {
        this(command.getId(), command.getTime(), command.getName(), command.getArgs().toString(), command.getResult());
    }

    public static ArrayList<CommandProperty> convert(ArrayList<Command> commands) {
        ArrayList<CommandProperty> commandProperties = new ArrayList<>();
        for (Command command : commands) {
            commandProperties.add(new CommandProperty(command));
        }
        return commandProperties;
    }
}
