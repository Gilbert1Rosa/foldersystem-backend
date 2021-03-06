package foldersystem.app.repository;

import foldersystem.app.model.StorageObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StorageObjectRepositoryTest {
    @Autowired
    private StorageObjectRepository repository;

    @Test
    public void notNullInjection() {
        assertThat(repository, is(notNullValue()));
    }

    @Test
    public void getRootTest() {
        StorageObject rootObject = repository.getRoot();
        assertThat(rootObject, is(notNullValue()));
    }

    @Test
    public void getChildrenTest() {
        assertThat(repository.getChildren(1).size(), not(equalTo(0)));
    }

    @Test
    public void testAddObject() {
        int recordCount = repository.getChildren(2).size();
        repository.addObject(2, "testFolder", 0, "");
        repository.addObject(2, "testFile", 1, "7326e3dc-e371-4a17-98c2-ca1803ddacc7");
        List<StorageObject> objs = repository.getChildren(2);
        assertThat(objs.size(), equalTo(recordCount + 2));
    }

    @Test
    public void testMoveObject() {
        repository.addObject(2, "testFolder", 0, "");
        List<StorageObject> objs = repository.getChildren(2);
        StorageObject found = objs.stream().filter(obj -> obj.getObjectName().equals("testFolder")).findFirst().get();
        repository.moveObject(found.getId(), 4);
        objs = repository.getChildren(4);
        found = objs.stream().filter(obj -> obj.getObjectName().equals("testFolder")).findFirst().get();
        assertThat(found.getObjectName(), equalTo("testFolder"));
    }

    @Test
    public void getHashNameTest() {
        String hashName = repository.getHashName(60);
        assertThat(hashName, equalTo("5d33f886eb964f45a6ac9e3a15cef61b"));
    }
}
