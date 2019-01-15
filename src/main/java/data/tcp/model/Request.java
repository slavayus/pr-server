package data.tcp.model;

import data.db.model.Dictionary;

public class Request {
    private String command;
    private Dictionary dictionary;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
