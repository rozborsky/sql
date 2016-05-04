package romanRozborsky.controller.controller;

import view.Console;

public class Exit extends Command{
    private Console view;

    public Exit(Console view){
        this.view = view;
    }

    @Override
    public String format() {
        return "exit";
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
        view.write("Bye!");
        throw new ExitExeption();
    }
}