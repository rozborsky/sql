package rozborskyRoman.controller;

import rozborskyRoman.view.InputOutput;

public class Exit extends Command {
    private InputOutput view;

    public Exit(InputOutput view) {
        this.view = view;
    }

    @Override
    public String format() {
        return "exit";
    }

    @Override
    public void process() {
        exit();
    }

    @Override
    public String description() {
        return "'exit' - for exit from the program";
    }

    private void exit() {
        view.write("Bye!\n_____________________________________________________________________");
        throw new ExitException();
    }
}