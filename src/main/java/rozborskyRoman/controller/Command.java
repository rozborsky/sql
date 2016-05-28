package rozborskyRoman.controller;

import java.sql.SQLException;

public abstract class Command {

    public boolean canProcess(String enteredCommand) {
        return format().equals(enteredCommand);
    }

    protected abstract String format();

    public abstract void process() throws SQLException;

    protected abstract String description();
}