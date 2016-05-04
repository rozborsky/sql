package romanRozborsky.controller.controller;

import model.*;
import view.Console;

import java.sql.SQLException;

public class MainController {
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
                new Find(manager, view),
                new Insert(manager, view),
                new Update(manager, view),
                new Delete(manager, view),
                new Clear(manager, view),
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

            if (showTables(command, commands)){
                new ChooseTable(commands, tables, manager, view);
            }else{
                view.write("Wrong command\n");
                continue;
            }
            view.write("\n");
        }
    }

    private void availableTables(){
        try {
            this.tables = manager.list();
        } catch (SQLException e) {
            view.error("",e);
        }
    }

    private boolean showTables(String enteredCommand, Command [] commands){
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
