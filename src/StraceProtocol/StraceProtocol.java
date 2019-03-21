package src.StraceProtocol;

import src.Tree.CommandTree;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class StraceProtocol {

    private ArrayList<Command> commands_original = new ArrayList<>();
    private ArrayList<Command> commands;

    public StraceProtocol(ArrayList<Command> commands) {
        this.commands_original = commands;
    }

    public StraceProtocol (File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            int id = 0;
            while ((line = br.readLine()) != null) {
                Command command = new Command(line);
                command.setId(id);
                addCommand(command);
                id++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("файл не найден");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        commands = new ArrayList<>(commands_original); //сделали копию
    } // считываем файл, каждая строка - отдельная команда, создаём объекты Command

    public void addCommand(Command command) {
        commands_original.add(command);
    }

    public ArrayList<Command> getCommands_status() {
        ArrayList<Command> commands_status = new ArrayList<>();
        for (Command command : commands) {
            if (command.getStatus() == 1) {
                commands_status.add(command);
            }
        }
        return commands;
    } // возвращает список команд, учитывая статус (1 - будет добавлено, 0 - не будет добавлено)

    public Set<String> getCommands_name() {
        ArrayList<Command> commands_current = getCommands_status();
        Set<String> names = new LinkedHashSet<>();
        for (Command command : commands_current) {
            names.add(command.getName());
        }
        return names;
    } //возвращает список всех употребляемых имён для команд (статус = 1)

    public CommandTree getTreeArgs (String command_name) {
        CommandTree tree = new CommandTree(null);

        ArrayList<Command> commands = getCommands_status();
        for (Command command : commands) {
            if (command.getName().equals(command_name)) {
                tree.addBranch(command.getArgs().getArgs(), command.getResult());
            }
        }
        tree.print();
        return tree;
    }
}


