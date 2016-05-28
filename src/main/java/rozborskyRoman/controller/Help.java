package rozborskyRoman.controller;

import rozborskyRoman.view.InputOutput;

public class Help extends Command {
    private InputOutput view;
    private String[] commands;
    private String line = "_____________________________________________________________________";

    public Help(InputOutput view) {
        this.view = view;
    }

    @Override
    public void process() {
        view.write("\n\n" + line +
                "\nHELP" +
                "\nAvailable commands:" +
                "\n" + line);

        for (int i = 0; i < commands.length; i++) {
            view.write(commands[i]);
            view.write(line);
        }
    }

    @Override
    protected String format() {
        return "help";
    }

    @Override
    protected String description() {
        return "'help' - to read help";
    }

    public void addCommands(Command[] commands) {
        String[] command = new String[commands.length];
        for (int i = 0; i < commands.length; i++) {
            command[i] = commands[i].description();
        }
        this.commands = command;
    }
}