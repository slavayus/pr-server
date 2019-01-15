package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

public class DictionaryRepositoryDBTest {

    private Session session;
    private DictionaryRepository repository;

    @Before
    public void init() {
        repository = new DictionaryRepositoryDB();
        session = HibernateUtil.getSession();
        clearDictionaryEntity();
    }

    private void clearDictionaryEntity() {
        session.beginTransaction();
        session.createQuery("DELETE FROM DictionaryEntity ").executeUpdate();
        session.getTransaction().commit();
    }

    @After
    public void destroy() {
        session.close();
    }

    @Test(expected = NoResultException.class)
    public void selectMissingRecord() {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("YYEE");
        dictionary.setDescription("YYEE");
        repository.select(dictionary);
    }

    @Test
    public void select() {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYEE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        Dictionary select = repository.select(new Dictionary(dictionaryEntity.getWord(), dictionaryEntity.getDescription()));

        Assert.assertEquals("YYEE", select.getWord());
        Assert.assertEquals("YYEE", select.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectNullAll() {
        repository.select(new Dictionary(null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void selectNullWord() {
        repository.select(new Dictionary(null, "SYF"));
    }

    @Test(expected = NoResultException.class)
    public void selectNullDescription() {
        repository.select(new Dictionary("YEE", null));
    }

    @Test
    public void find() {
    }

    @Test
    public void insertOnce() {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("YYEE");
        dictionary.setDescription("EEE");
        Dictionary insert = repository.insert(dictionary);

        DictionaryEntity dictionaryEntity = session.get(DictionaryEntity.class, insert.getId());

        Assert.assertEquals("YYEE", dictionaryEntity.getWord());
        Assert.assertEquals("EEE", dictionaryEntity.getDescription());
    }

    @Test(expected = PersistenceException.class)
    public void insertTwice() {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("YEEE");
        dictionary.setDescription("EEE");
        repository.insert(dictionary);
        repository.insert(dictionary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNulls() {
        Dictionary dictionary = new Dictionary();
        repository.insert(dictionary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNullWord() {
        Dictionary dictionary = new Dictionary();
        dictionary.setDescription("YEYE");
        repository.insert(dictionary);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insertNullDescription() {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("YEYE");
        repository.insert(dictionary);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

}