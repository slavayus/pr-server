package data.db.repository;

import data.db.model.Dictionary;

import java.util.List;

public interface DictionaryRepository {
    Dictionary select(Dictionary record);

    List<Dictionary> find(Dictionary record);

    Dictionary insert(Dictionary record);

    Dictionary update(Dictionary record);

    Dictionary delete(Dictionary record);
}
