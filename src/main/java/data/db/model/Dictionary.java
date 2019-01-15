package data.db.model;

public class Dictionary {
    private int id;
    private String word;
    private String description;

    public Dictionary() {
    }

    public Dictionary(String word, String description) {
        this.word = word;
        this.description = description;
    }

    public Dictionary(int id, String word, String description) {
        this.id = id;
        this.word = word;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
