package rozborskyRoman.view;

import java.util.Scanner;

public class Console implements InputOutput{
    public void write(String message) {
        System.out.println(message);
    }

    public void error(String message, Exception exception) {
        System.out.println(String.format(message + exception));
    }

    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}