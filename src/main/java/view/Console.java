package view;

import java.util.Scanner;

public class Console {
    public void write(String message){
        System.out.println(message);
    }

    public void error(String message, Exception e) {
        System.out.println(String.format(message + e));
    }

    public  String read(){
        String line;
        Scanner scanner = new Scanner(System.in);
        line = scanner.nextLine();
        return line;
    }
}