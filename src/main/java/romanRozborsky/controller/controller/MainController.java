package romanRozborsky.controller.controller;

import model.*;
import view.Console;

import java.sql.SQLException;

public class MainController {
    private Console view = null;
    private Command [] commands;
    private DBManager manager;
    private String[] tables;

    public MainController(Console view){
        this.view = view;
        view.write("SQLCMD manager");

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

    public void action(){
        String command;

        while(true){
            view.write("Insert 'list' to show available tables, 'help' for help  or 'exit' to close program");
            command = view.read();

            if (checkCommand(command, commands)){
                WorkWithTables workWithTables = new WorkWithTables(commands, tables, manager, view);
                workWithTables.chooseTable();
            }else{
                view.write("Wrong command\n");
                continue;
            }
            view.write("\n");
        }
    }

    private void findAvailableTables(){
        try {
            this.tables = manager.list();
        } catch (SQLException e) {
            view.error("Can't show tables",e);
        }
    }

    private boolean checkCommand(String enteredCommand, Command [] commands){
        for (Command availableCommands : commands){
            if (availableCommands.format().equals(enteredCommand)){
                if (enteredCommand.equals("list") || enteredCommand.equals("help") || enteredCommand.equals("exit")){
                    availableCommands.process();
                    return true;
                }
            }
        }
        return false;
    }
}
