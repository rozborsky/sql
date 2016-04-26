package controller;


public abstract class Command {
    public boolean canProcess(String enteredCommand){
        return format().equals(enteredCommand);
    }

    protected abstract String format();

    public abstract void process();

    protected abstract String description();
}