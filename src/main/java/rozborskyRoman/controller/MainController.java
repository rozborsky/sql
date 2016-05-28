package rozborskyRoman.controller;

import rozborskyRoman.model.DBManager;
import rozborskyRoman.view.InputOutput;

import java.sql.SQLException;

public class MainController {
    private InputOutput view = null;
    private Command[] commands;
    private DBManager manager;
    private String[] tables;

    public MainController(InputOutput view) {
        this.view = view;
    }

    public void action() {
        view.write("SQLCMD manager\n");
        preparation();
        inputCommand();
    }

    private void preparation() {
        Connector connector = new Connector();
        manager = connector.createConnection(view);

        findAvailableTables();

        Help help = new Help(view);
        commands = new Command[]{
                new List(manager, tables, view),
                new Find(manager, view),
                new Insert(manager, view),
                new Update(manager, view),
                new Delete(manager, view),
                new Clear(manager, view),
                help,
                new Exit(view)};
        help.addCommands(commands);
    }

    private void inputCommand() {
        String command;

        while (true) {
            view.write("Insert 'list' to show available tables, 'help' for help or 'exit' to close program");
            command = view.read();

            if (checkingCommand(command, commands)) {
                if (command.equals("help")) {
                    continue;
                }
                WorkWithTables workWithTables = new WorkWithTables(commands, tables, manager, view);
                workWithTables.chooseTable();
            } else {
                view.write("Wrong command\n");
                continue;
            }
            view.write("\n");
        }
    }

    private void findAvailableTables() {
        try {
            this.tables = manager.list();
        } catch (SQLException e) {
            view.error("Can't show tables\n", e);
        }
    }

    private boolean checkingCommand(String enteredCommand, Command[] commands) {
        for (Command availableCommands : commands) {
            if (availableCommands.format().equals(enteredCommand)) {
                if (enteredCommand.equals("list") || enteredCommand.equals("help") || enteredCommand.equals("exit")) {
                    try {
                        availableCommands.process();
                    } catch (SQLException e) {
                        view.error(String.format("Cant execute command '%s'\n", enteredCommand), e);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}