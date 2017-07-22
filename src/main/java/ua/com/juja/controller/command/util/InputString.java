package ua.com.juja.controller.command.util;

/**
 * Created by Alexandero on 08.07.2017.
 */
public class InputString {

    private String command;

    public InputString(String inputString) {
        this.command = inputString;
    }


    public void validationParameters(String inputString){
        int formatLength = inputString.split("\\|").length;
        if (formatLength != getLength()){
            throw new IllegalArgumentException(String.format("Invalid number of parameters" +
                    " separated by '|', expected %s, but was: %s", formatLength, getLength()));
        }
    }

    public void validationPairs(String inputString){
        if (getLength() % 2 != 0){
            throw new IllegalArgumentException("Invalid command, you must enter and even " +
                    "number of parameters in the following format : " + inputString);
        }
    }

    public int getLength() {
        return getParameters().length;
    }

    public String[] getParameters(){
        return command.split("\\|");
    }

    @Override
    public String toString() {
        return command;
    }
}
