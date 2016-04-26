package controller;

import view.Console;

public class Help extends Command {
    private Console wiev;
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

        for (int i = 0; i < MainController.commands.length; i++) {
            wiev.write(MainController.commands[i].description());
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
}