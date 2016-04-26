package controller;

import view.Console;

import java.util.Map;


public class TableController {

    public TableController(Map workParameters, Console wiev){
        String enteredCommand;
        do{
            boolean isExecute = false;
            wiev.write("\nTo work with table '" + workParameters.get("table") + "' insert command, " +
                    "to see available tables write 'back' or 'exit' to live program, to read help insert 'help'");
            enteredCommand = wiev.read();

            for (Command checkCommand : MainController.commands) {
                if (checkCommand.canProcess(enteredCommand)){
                    checkCommand.process();
                    isExecute = true;
                }
            }
            if(!"back".equals(enteredCommand) && isExecute == false){
                wiev.write("Wrong command");
            }
        }while(!"back".equals(enteredCommand));
    }
}