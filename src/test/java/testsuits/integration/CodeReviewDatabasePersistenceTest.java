package testsuits.integration;

import developer.ReviewerPersistence;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;

import com.mongodb.MongoClient;

import java.util.List;

public class CodeReviewDatabasePersistenceTest {
    private ReviewerPersistence rp;

    MongoClient mongoClient;
    String usersDBName;
    String usersCollectionName;

    @Before
    public void setUp(){
        //Given
        mongoClient = new MongoClient("localhost", 27017);
        usersDBName = "users-db";
        usersCollectionName = "users-collection";
        rp = ReviewerPersistence.getInstance();
        rp.addDatabase(mongoClient, usersDBName, usersCollectionName);
    }

    @Test
    public void shouldInitializeMongoDBClientWhenDatabaseIsAdded(){
        assertFalse(rp.isMongoDBClientNull());
    }

    @Test
    public void shouldHaveDatabaseWithANameWhenDatabaseIsAdded() {
        // When
        String dbName = rp.getDBName();

        // Then
        assertEquals(usersDBName, dbName);
    }

}
