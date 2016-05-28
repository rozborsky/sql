package rozborskyRoman.view;

/**
 * Created by roman on 13.05.2016.
 */
public interface InputOutput {
    public void write(String message);

    public void error(String message, Exception exception);

    public String read();
}