package controller;

import view.Console;

public class Exit extends Command{
    private Console wiev;

    public Exit(Console wiev){
        this.wiev = wiev;
    }

    @Override
    public String format() {
        String command = "exit";
        return command;
    }

    @Override
    public void process(){
        exit();
    }

    @Override
    public String description() {
        return "'exit' - for exit from the program";
    }

    private void exit(){
        wiev.write("Bye!");
        System.exit(0);
    }
}