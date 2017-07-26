package ua.com.juja.controller.command.util;

public class InputString {

    private String command;

    public InputString(String inputString) {
        this.command = inputString;
    }


    public void validateParameters(String inputString){
        int formatLength = inputString.split("\\|").length;
        if (formatLength != getLength()){
            throw new IllegalArgumentException(String.format("Invalid number of parameters" +
                    " separated by '|', expected %s, but was: %s", formatLength, getLength()));
        }
    }

    public void validatePairs(String inputString){
        if (getLength() % 2 != 0){
            throw new IllegalArgumentException("Invalid command, you must enter and even " +
                    "number of parameters in the following format: " + inputString);
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
