package ua.com.juja.controller.command;

import ua.com.juja.controller.command.util.InputString;
import ua.com.juja.model.DatabaseManager;
import ua.com.juja.view.View;

/**
 * Created by Alexandero on 30.06.2017.
 */
public class CreateTable implements Command {
    private View view;
    private DatabaseManager manager;
    private final static Integer TABLE_NAME = 0;
    private boolean interruptCreate;
    String query = "";

    public CreateTable(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public String format() {
        return "createTable";
    }

    @Override
    public void process(InputString userInput) {
        interruptCreate = false;
        query = "";
        createQuery();
        userInput.validationParameters(format());

        if (!interruptCreate){
            manager.createTable(query);
            String table = query.split("\\(")[TABLE_NAME];
            view.write(String.format("Table %s created successful !", table));
        }else {
            view.write("Exit to main menu !");
        }
    }

    private void createQuery() {
        if (!interruptCreate){
            createTableName();
        }
        if (!interruptCreate){
            createColumnPrimaryKey();
        }
        if (!interruptCreate){
            createColumn();
        }
    }

    private void createColumn() {
        boolean exit = false;
        while (!exit){
            view.write("Enter the name for next column or 'finish' to create table with entered columns" +
                    " or type 'cancel' for exit to main menu");
            String input = view.read();
            if (input.equals("finish")){
                query += ")";
                exit = true;
            }else if (input.equals("cancel")){
                exit = true;
                interruptCreate = true;
            }else if (input.equals("")){
                view.write("Type name for column of the table");
            }else {
                if (userInputStartWithLetters(input)){
                    query += "," + input + " varchar(255)";
                    view.write("Name of column: " + input);
                    createColumn();
                    exit = true;
                }
            }
        }
    }

    private void createColumnPrimaryKey() {
        boolean exit = false;
        while (!exit){
            view.write("Enter the name for PRIMARY KEY column(often it's an identifier):");
            String input = view.read();
            if (input.equals("cancel")){
                exit = true;
                interruptCreate = true;
            }else if (input.equals("")){
                view.write("Type name for column of the table");
            }else {
                if (userInputStartWithLetters(input)){
                    view.write("Name of PRIMARY KEY column : " + input);
                    query += input + " SERIAL NOT NULL PRIMARY KEY";
                    exit = true;
                }
            }
        }
    }

    private void createTableName() {
        boolean exit = false;
        while (!exit){
            view.write("Type name of creating table(name should start from letter) or type 'cancel' for exit to main menu");
            String input = view.read();

            if (input.equalsIgnoreCase("cancel")){
                exit = true;
                interruptCreate = true;
            }else if (input.equalsIgnoreCase("")){
                view.write("Type name for create table");
            }else
                if (userInputStartWithLetters(input)){
                query += input + "(";
                view.write("Name of new table : " + input);
                exit = true;
            }
        }
    }

    private boolean userInputStartWithLetters(String input) {
        char fistChar = input.charAt(0);
        if (!(fistChar >= 'a' && fistChar <= 'z') && !(fistChar >= 'A' && fistChar <= 'Z')){
            view.write(String.format("Name should start from letter, but you type '%s'", fistChar));
            return false;
        }
        return true;
    }
}
