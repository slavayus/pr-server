package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class DictionaryRepositoryDB implements DictionaryRepository {
    /**
     * Select record in db by word.
     *
     * @param record word for searching.
     * @return Selected record from db.
     */
    @Override
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

            return new Dictionary(singleResult.getId(), singleResult.getWord(), singleResult.getDescription());
        }
    }

    /**
     * Searching words in db according by mask.
     *
     * @param record mask for searching.
     * @return Found list of records.
     */
    @Override
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
            result.forEach(dictionaryEntity -> dictionaryList.add(new Dictionary(dictionaryEntity.getId(), dictionaryEntity.getWord(), dictionaryEntity.getDescription())));
            return dictionaryList;
        }
    }

    /**
     * Insert record to db.
     *
     * @param record @see Dictionary with not null word and description .
     * @return Inserted record to db with id.
     */
    @Override
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
            record.setId(entity.getId());
            return record;
        }
    }

    /**
     * Update record in db.
     *
     * @param record @see Dictionary with not null word and description .
     * @return Updated record in db.
     */
    @Override
    public Dictionary update(Dictionary record) {
        if (record == null || record.getWord() == null || record.getDescription() == null) {
            throw new IllegalArgumentException("Searching word must not be null");
        }

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("UPDATE DictionaryEntity SET description = :description WHERE word = :word");
            query.setParameter("word", record.getWord());
            query.setParameter("description", record.getDescription());
            query.executeUpdate();
            session.getTransaction().commit();
            return record;
        }
    }

    /**
     * Delete record from db.
     *
     * @param record @see Dictionary with not null word.
     * @return Deleted record to db with id.
     */
    @Override
    public Dictionary delete(Dictionary record) {
        if (record == null || record.getWord() == null) {
            throw new IllegalArgumentException("Searching word must not be null");
        }

        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM DictionaryEntity WHERE word = :word");
            query.setParameter("word", record.getWord());
            query.executeUpdate();
            session.getTransaction().commit();
            return record;
        }
    }
}
