package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

import javax.persistence.PersistenceException;

public class DictionaryRepositoryDBTest {

    private Session session;
    private DictionaryRepository repository;

    @Before
    public void init() {
        repository = new DictionaryRepositoryDB();
        session = HibernateUtil.getSession();
    }

    @After
    public void destroy() {
        session.close();
    }

    @Test
    public void select() {
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