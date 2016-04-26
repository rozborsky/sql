package controller;

import view.Console;

public class Help extends Command {
    private Console wiev;
    private String [] commandArray;
    private String line = "_____________________________________________________________________";

    public Help(Console wiev){
        this.wiev = wiev;
    }
    @Override
    public void process() {
        wiev.write("\n\n" + line +
                "\nHELP" +
                "\nAvailable commands:" +
                "\n" + line);

        for (int i = 0; i < commandArray.length; i++) {
            wiev.write(commandArray[i]);
            wiev.write(line);
        }
    }

    @Override
    protected String format() {
        String command = "help";
        return command;
    }

    @Override
    protected String description() { // TODO
        return "'help' - to read help";
    }

    public void addCommands(Command [] commands){
        String [] command = new String[commands.length];
        for (int i = 0; i < commands.length; i++) {
            command[i] = commands[i].description();
        }
        this.commandArray =  command;
    }
}