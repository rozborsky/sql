package romanRozborsky.controller.controller;

import view.Console;

public class Main {
    public static void main(String[] args) {
        Console wiew = new Console();
        new MainController(wiew);
    }
}

//public|postgres|mainuser