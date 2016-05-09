package romanRozborsky.controller.controller;

import view.Console;

public class Main {
    public static void main(String[] args) {
        Console wiew = new Console();
            MainController mainController = new MainController(wiew);
        try {
            mainController.action();
        }catch (ExitException e){
            //do nothing
        }
    }
}

//public|postgres|mainuser