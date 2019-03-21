package src.StraceProtocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    private int id;
    private String time;
    private String name;
    private Args args;
    private String result;
    private Status status = Status.NoN;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Command(int id, String time, String name, Args args, String result) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.args = args;
        this.result = result;
    }

    public Command(int id) {
        this(id, "???", "???", new Args(""), "???");
    }

    private String regex = "^([\\w]+)\\((.*?)\\)[ ]*=[ ]+((.*))$";
    public Command (String command) {
        this(-1);
        //System.out.println("заглушка по созданию строки");
        //System.out.println(command);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            String name_primal = matcher.group(1);
            String args_primal = matcher.group(2);
            String result_primal = matcher.group(3);

            args = new Args(args_primal);
            args.calculate();
            name = name_primal;
            result = result_primal;

        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Args getArgs() {
        return args;
    }

    public void setArgs(Args args) {
        this.args = args;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
