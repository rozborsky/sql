package rozborskyRoman.controller;

import rozborskyRoman.view.Console;

public class Main {
    public static void main(String[] args) {
        Console view = new Console();
            MainController mainController = new MainController(view);
        try {
            mainController.action();
        }catch (ExitException e){
            //do nothing
        }
    }
}

//public|postgres|mainuser