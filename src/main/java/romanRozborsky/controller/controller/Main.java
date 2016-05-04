package romanRozborsky.controller.controller;

import view.Console;

public class Main {
    public static void main(String[] args) {
        Console wiew = new Console();
        try {
            new MainController(wiew);
        }catch (ExitExeption e){
            //do nothing
        }
    }
}

//public|postgres|mainuser