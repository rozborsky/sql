package rozborskyRoman.integration;

import rozborskyRoman.view.InputOutput;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by roman on 13.05.2016.
 */
public class ConsoleForTest implements InputOutput {

    public void write(String message) {
        System.out.println(message);
    }

    public void error(String message, Exception exception) {
        System.out.println(String.format(message + exception));
    }

    public String read() {
        String line = "";
        Scanner scanner = new Scanner(System.in);


        line = scanner.nextLine();

        try {
            System.in.reset();
            System.in.skip(line.length() + 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.mark(line.length() + 1);


        return line;
    }
}
