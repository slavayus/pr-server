package data.db.repository;

import data.db.model.Dictionary;

import java.util.Collections;
import java.util.List;

public class DictionaryRepository {
    public Dictionary select(Dictionary record) {
        record = record == null ? new Dictionary() : record;
        record.setWord("select");
        record.setDescription("Getting description");
        return record;
    }

    public List<Dictionary> find(Dictionary record) {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("find");
        dictionary.setDescription("Searching by mask");
        return Collections.singletonList(dictionary);
    }

    public Dictionary insert(Dictionary record) {
        record = new Dictionary();
        record.setWord("inser");
        record.setDescription("Inserting new record");
        return record;
    }

    public Dictionary update(Dictionary record) {
        record = new Dictionary();
        record.setWord("update");
        record.setDescription("Updating the record");
        return record;
    }

    public Dictionary delete(Dictionary record) {
        record = new Dictionary();
        record.setWord("delete");
        record.setDescription("Deleting the record");
        return record;
    }
}
