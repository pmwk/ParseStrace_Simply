package src.StraceProtocol;

import src.Tree.CommandTree;
import src.Tree.NodeCommandTree;

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
            if (command.getStatus() == Status.NoN) {
                commands_status.add(command);
            }
        }
        return commands_status;
    } // возвращает список команд, учитывая статус (1 - будет добавлено, 0 - не будет добавлено)

    public Set<String> getCommands_name() {
        ArrayList<Command> commands_current = getCommands_status();
        Set<String> names = new LinkedHashSet<>();
        for (Command command : commands_current) {
            names.add(command.getName());
        }
        return names;
    } //возвращает список всех употребляемых имён для команд (статус = 1)

    public NodeCommandTree getTreeArgs (String command_name) {
        NodeCommandTree tree = new NodeCommandTree("no value");

        ArrayList<Command> commands = getCommands_status();
        for (Command command : commands) {
            if (command.getName().equals(command_name)) {
                tree.addBranch(command.getArgs().getArgs(), command.getResult());
            }
        }
        //tree.print();
        return tree;
    }


    public void refactorElementsByCommand(String command_name, ArrayList<String> args, String result, Status status, boolean inversion) {

        for (Command command : commands) {
            //System.out.println(command.getId());
            boolean isFit = true;

            if (command.getStatus() == Status.Hide) {
                continue;
            }

            if (command_name != null) {
                if (!command.getName().equals(command_name)) {
                    isFit = false;
                }
            }

            if (isFit && args != null) {
                if (args.size() <= command.getArgs().getArgs().size()) {
                    ArrayList<String> args_command = command.getArgs().getArgs();
                    for (int i = 0; i < args.size(); i++) {
                        if (args.get(i) != null) {
                            if (!args.get(i).equals(args_command.get(i))) {
                                isFit = false;
                            }
                        }
                    }
                } else {
                    isFit = false;
                }
            }

            if (isFit && result!= null) {
                if (!result.equals(command.getResult())) {
                    isFit = false;
                }
            }

            if (inversion) {
                isFit = !isFit;
            }
            if (isFit) {
                command.setStatus(status);
            }

        }
    }

}


