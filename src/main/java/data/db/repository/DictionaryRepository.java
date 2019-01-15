package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class DictionaryRepository {
    public Dictionary select(Dictionary record) {
        if (record == null || record.getWord() == null) {
            throw new IllegalArgumentException("Searching word must not be null");
        }

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM DictionaryEntity E WHERE E.word = :record_word");
            query.setParameter("record_word", record.getWord());
            DictionaryEntity singleResult = (DictionaryEntity) query.getSingleResult();
            session.getTransaction().commit();

            Dictionary dictionary = new Dictionary();
            dictionary.setWord(singleResult.getWord());
            dictionary.setDescription(singleResult.getDescription());
            return dictionary;
        }
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
