package romanRozborsky.controller.controller;

import model.DBManager;
import view.Console;

/**
 * Created by roman on 04.05.2016.
 */
public class WorkWithTables {
    private Command [] commands;
    private String[] tables;
    private DBManager manager;
    private Console view;

    public WorkWithTables(Command [] commands, String[] tables, DBManager manager, Console view){
        this.commands = commands;
        this.tables = tables;
        this.manager = manager;
        this.view = view;
    }

    public void chooseTable() {
        String command = "";

        while (!command.equals("back")){
            view.write("\nChoose table");
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
            view.write("\nTo work with table '" + manager.getTable() + "' insert command, " +
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
}
