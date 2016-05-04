package romanRozborsky.controller.controller;

import view.Console;

public class Help extends Command {
    private Console view;
    private String [] commandArray;
    private String line = "_____________________________________________________________________";

    public Help(Console view){
        this.view = view;
    }
    @Override
    public void process() {
        view.write("\n\n" + line +
                "\nHELP" +
                "\nAvailable commands:" +
                "\n" + line);

        for (int i = 0; i < commandArray.length; i++) {
            view.write(commandArray[i]);
            view.write(line);
        }
    }

    @Override
    protected String format() {
        return "help";
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