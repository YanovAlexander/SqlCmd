package controller.command;

import main.java.ua.com.juja.view.View;

/**
 * Created by Alexandero on 17.06.2017.
 */
public class TestView implements View {

    private String messages = "";

    @Override
    public void write(String message) {
        messages += message + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    public String getContent() {
        return messages;
    }
}
