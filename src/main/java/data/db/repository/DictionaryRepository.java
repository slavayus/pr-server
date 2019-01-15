package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
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

            return new Dictionary(singleResult.getWord(), singleResult.getDescription());
        }
    }

    public List<Dictionary> find(Dictionary record) {
        if (record == null || record.getWord() == null) {
            throw new IllegalArgumentException("Searching word must not be null");
        }

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("FROM DictionaryEntity E WHERE lower(E.word) LIKE lower(:record_word)");
            query.setParameter("record_word", "%" + record.getWord() + "%");
            List<DictionaryEntity> result = ((List<DictionaryEntity>) query.list());
            session.getTransaction().commit();

            ArrayList<Dictionary> dictionaryList = new ArrayList<>();
            result.forEach(dictionaryEntity -> dictionaryList.add(new Dictionary(dictionaryEntity.getWord(), dictionaryEntity.getDescription())));
            return dictionaryList;
        }
    }

    public Dictionary insert(Dictionary record) {
        if (record == null || record.getWord() == null || record.getDescription() == null) {
            throw new IllegalArgumentException("Searching word must not be null");
        }

        DictionaryEntity entity = new DictionaryEntity();
        entity.setWord(record.getWord());
        entity.setDescription(record.getDescription());
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return record;
        }
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
