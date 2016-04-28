package controller;

import model.*;
import view.Console;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    private Map workParameters = new HashMap<>();
    private Connection connection = null;
    private Console wiev = null;

    public Command[] commands;
    DBManager dbManager;
    public String[] tables;

    static{
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            System.out.println("Can't find jdbc driver ");
            System.exit(0);
        }
    }

    public MainController(Console wiev){
        this.wiev = wiev;
        wiev.write("SQLCMD manager");

        Connector connect = new Connector();
        this.connection = connect.create(connection, workParameters, wiev);
        this.dbManager = new DBManager(connection, workParameters);

        availableTables();

        Help help = new Help(wiev);
        commands = new Command[]{
                new List(workParameters, tables, wiev),
                new Find(dbManager, workParameters, wiev),
                new Insert(dbManager, workParameters, wiev),
                new Update(dbManager, workParameters, wiev),
                new Delete(dbManager, workParameters, wiev),
                new Clear(dbManager, workParameters, wiev),
                help,
                new Exit(wiev)};
        help.addCommands(commands);
        action();
    }

    private void action(){
        String command;

        while(true){
            wiev.write("Insert 'list' for show available tables, 'help' for help  or 'exit' to close program");
            command = wiev.read();

            if (showTables(command, commands, tables)){
                chooseTable();
            }else{
                wiev.write("Wrong command\n");
                continue;
            }

            wiev.write("\n");
        }
    }

    private void chooseTable() {
        String command = "";

        while (!command.equals("back")){
            wiev.write("Choose table");
            command = wiev.read();

            boolean isExist = false;
            for (int i = 0; i < tables.length; i++) {
                if (tables[i].equals(command)){
                    workParameters.put("table", command);
                    isExist = true;
                }
            }
            if (isExist == false && !command.equals("back")){
                wiev.write(String.format("Table '%s'  does not exist\n", command));
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
            wiev.write("\nTo work with table '" + workParameters.get("table") + "' insert command, " +
                    "to see available tables write 'back' or 'exit' to live program, to read help insert 'help'");
            command = wiev.read();

            for (Command availableCommand : commands) {
                if (availableCommand.canProcess(command)) {
                    availableCommand.process();
                    isExecute = true;
                }
            }
            if (isExecute == false){
                wiev.write("\nWrong command");
            }
        }
    }

    private void availableTables(){
        try {
            this.tables = dbManager.list();
        } catch (SQLException e) {
            wiev.error("",e);
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
