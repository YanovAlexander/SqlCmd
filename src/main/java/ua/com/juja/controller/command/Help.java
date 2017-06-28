package ua.com.juja.controller.command;

import ua.com.juja.view.View;

/**
 * Created by Alexandero on 14.06.2017.
 */
public class Help implements Command {

    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        doHelp();
    }

    private void doHelp() {
        view.write("Сущеуствующие команды");

        view.write("\tconnect|database|username|password");
        view.write("\t\tдля получения к базе данных, с которой необъходимо работать");

        view.write("\ttables");
        view.write("\t\tдля получения списка всех таблиц базы");

        view.write("\tfind|tableName");
        view.write("\t\tдля получения содержимого таблицы 'tableName'");

        view.write("\tclear|tableName");
        view.write("\t\tдля очистки всей таблицы с названием 'tableName'");

        view.write("\tcreate|tableName|column1|value1|column2|value2...columnN|valueN");
        view.write("\t\tдля создания записи в таблице с названием 'tableName'"); //TODO если ввел команду, переспросить

        view.write("\texit");
        view.write("\t\tдля завершения работы приложения");

        view.write("\thelp");
        view.write("\t\tдля вывода этого списка на экран");
    }
}
