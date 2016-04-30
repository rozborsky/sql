package controller;

import model.*;
import view.Console;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private Map workParameters = new HashMap<>();
    private Console view = null;

    private Command[] commands;
    private DBManager manager;
    private String[] tables;

    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("Can't find jdbc driver ");
            System.exit(0);
        }
    }

    public MainController(Console view){
        this.view = view;
        view.write("SQLCMD manager");

        Connector connect = new Connector();
        manager = connect.create(view);

        availableTables();

        Help help = new Help(view);
        commands = new Command[]{
                new List(manager, tables, view),
                new Find(manager, workParameters, view),
                new Insert(manager, workParameters, view),
                new Update(manager, workParameters, view),
                new Delete(manager, workParameters, view),
                new Clear(manager, workParameters, view),
                help,
                new Exit(view)};
        help.addCommands(commands);
        action();
    }

    private void action(){
        String command;

        while(true){
            view.write("Insert 'list' for show available tables, 'help' for help  or 'exit' to close program");
            command = view.read();

            if (showTables(command, commands, tables)){
                chooseTable();
            }else{
                view.write("Wrong command\n");
                continue;
            }

            view.write("\n");
        }
    }

    private void chooseTable() {
        String command = "";

        while (!command.equals("back")){
            view.write("Choose table");
            command = view.read();

            boolean isExist = false;
            for (int i = 0; i < tables.length; i++) {
                if (tables[i].equals(command)){
                    manager.setTable(command);
                    isExist = true;
                }
            }
            if (isExist == false && !command.equals("back")){
                view.write(String.format("Table '%s'  does not exist\n", command));
                continue;
            }
            if (command.equals("back")){
                return;
            }
            workWithTable();
        }
    }

    private void workWithTable() {
        String command = "";

        boolean isExecute = false;
        while (!command.equals("back")) {
            view.write("\nTo work with table '" + workParameters.get("table") + "' insert command, " +
                    "to see available tables write 'back' or 'exit' to live program, to read help insert 'help'");
            command = view.read();

            for (Command availableCommand : commands) {
                if (availableCommand.canProcess(command)) {
                    availableCommand.process();
                    isExecute = true;
                }
            }
            if (isExecute == false){
                view.write("\nWrong command");
            }
        }
    }

    private void availableTables(){
        try {
            this.tables = manager.list();
        } catch (SQLException e) {
            view.error("",e);
        }
    }


    private boolean showTables(String enteredCommand, Command [] commands, String [] tables){
        for (Command availableCommands : commands){
            if (availableCommands.format().equals(enteredCommand)){
                if (enteredCommand.equals("help") || enteredCommand.equals("exit")){
                    availableCommands.process();
                    return true;
                }
                else if(enteredCommand.equals("list") ){
                    availableCommands.process();
                    return true;
                }
            }
        }
        return false;
    }
}
