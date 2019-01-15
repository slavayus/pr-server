package data.db.repository;

import data.db.model.Dictionary;

public class DictionaryRepository {
    public void select(Dictionary record) {
        System.out.println("Getting description");
    }

    public void find(Dictionary record) {
        System.out.println("Searching by mask");
    }

    public void insert(Dictionary record) {
        System.out.println("Inserting new record");
    }

    public void update(Dictionary record) {
        System.out.println("Updating the record");
    }

    public void delete(Dictionary record) {
        System.out.println("Deleting the record");
    }
}
