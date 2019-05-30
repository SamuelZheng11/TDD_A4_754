package testsuits.integration;

import developer.ReviewerPersistence;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;

import com.mongodb.MongoClient;

import java.util.List;

public class CodeReviewDatabasePersistenceTest {
    private ReviewerPersistence rp;

    @Before
    public void setUp(){
        //Given
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        String usersDBName = "users-db";
        String usersCollectionName = "users-collection";
        rp = ReviewerPersistence.getInstance();
        rp.addDatabase(mongoClient, usersDBName, usersCollectionName);
    }

    @Test
    public void shouldInitializeMongoDBClientWhenDatabaseIsAdded(){
        assertFalse(rp.isMongoDBClientNull());
    }
}
