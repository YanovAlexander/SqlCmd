package ua.com.juja.controller.command.util;

/**
 * Created by Alexandero on 08.07.2017.
 */
public class InputValidation {

    private String command;

    public InputValidation(String command) {
        this.command = command;
    }


    public void validationParameters(String commands){
        int formatLength = commands.split("\\|").length;
        if (formatLength != getLength()){
            throw new IllegalArgumentException(String.format("Invalid number of parameters" +
                    " separated by '|', expected %s, but was: %s", formatLength, getLength()));
        }
    }

    public void validationPairs(String commands){
        if (getLength() % 2 != 0){
            throw new IllegalArgumentException("Invalid command, you must enter and even " +
                    "number of parameters in the following format : " + commands);
        }
    }

    public int getLength() {
        return command.split("\\|").length;
    }

    public String[] getParameters(){
        return command.split("\\|");
    }

    @Override
    public String toString() {
        return command;
    }
}
