package data.db.repository;

import data.db.entity.DictionaryEntity;
import data.db.model.Dictionary;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

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
    public void findManyRecord() {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYEE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYYE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        List<Dictionary> dictionaries = repository.find(new Dictionary("Y", null));

        Assert.assertEquals(2, dictionaries.size());
        Assert.assertEquals("YYEE", dictionaries.get(0).getWord());
        Assert.assertEquals("YYEE", dictionaries.get(0).getDescription());
        Assert.assertEquals("YYYE", dictionaries.get(1).getWord());
        Assert.assertEquals("YYEE", dictionaries.get(1).getDescription());
    }

    @Test
    public void findOneRecord() {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYEE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        List<Dictionary> dictionaries = repository.find(new Dictionary("Y", null));

        Assert.assertEquals(1, dictionaries.size());
        Assert.assertEquals("YYEE", dictionaries.get(0).getWord());
        Assert.assertEquals("YYEE", dictionaries.get(0).getDescription());
    }

    @Test
    public void findEmptyList() {
        List<Dictionary> dictionaries = repository.find(new Dictionary("Y", null));
        Assert.assertEquals(0, dictionaries.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findNullWord() {
        Dictionary dictionary = new Dictionary();
        dictionary.setDescription("EEE");
        repository.find(dictionary);
    }

    @Test
    public void findNullDescription() {
        Dictionary dictionary = new Dictionary();
        dictionary.setWord("EEE");
        List<Dictionary> dictionaries = repository.find(dictionary);
        Assert.assertEquals(0, dictionaries.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findNullAll() {
        repository.find(new Dictionary());
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
    public void updateMissingRecord() {
        repository.update(new Dictionary("YEE", "YEE"));
    }

    @Test
    public void updateExistingRecord() {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYEE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        session.close();

        repository.update(new Dictionary("YYEE", "YEE"));

        session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM DictionaryEntity E WHERE E.word = :word");
        query.setParameter("word", "YYEE");
        DictionaryEntity singleResult = (DictionaryEntity) query.getSingleResult();
        session.getTransaction().commit();

        Assert.assertEquals("YYEE", singleResult.getWord());
        Assert.assertEquals("YEE", singleResult.getDescription());
    }

    @Test
    public void updateExistingRecordTheSameValue() {
        DictionaryEntity dictionaryEntity = new DictionaryEntity();
        dictionaryEntity.setWord("YYEE");
        dictionaryEntity.setDescription("YYEE");
        session.beginTransaction();
        session.save(dictionaryEntity);
        session.getTransaction().commit();
        session.close();

        repository.update(new Dictionary("YYEE", "YYEE"));

        session = HibernateUtil.getSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM DictionaryEntity E WHERE E.word = :word");
        query.setParameter("word", "YYEE");
        DictionaryEntity singleResult = (DictionaryEntity) query.getSingleResult();
        session.getTransaction().commit();

        Assert.assertEquals("YYEE", singleResult.getWord());
        Assert.assertEquals("YYEE", singleResult.getDescription());
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullWord() {
        repository.update(new Dictionary(null, "YEE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullDescription() {
        repository.update(new Dictionary(null, "YEE"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateNullAll() {
        repository.update(new Dictionary(null, null));
    }

    @Test
    public void delete() {
    }

}