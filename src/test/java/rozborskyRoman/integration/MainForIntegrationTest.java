package rozborskyRoman.integration;

import rozborskyRoman.controller.ExitException;
import rozborskyRoman.controller.MainController;

import java.util.NoSuchElementException;

/**
 * Created by roman on 13.05.2016.
 */
public class MainForIntegrationTest {
    public static void main(String[] args) {
        ConsoleForTest view = new ConsoleForTest();
        MainController mainController = new MainController(view);
        try {
            mainController.action();
        } catch (ExitException e) {
            //do nothing
        } catch (NoSuchElementException e) {

        }
    }
}
