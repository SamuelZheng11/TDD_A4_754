import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class UserPersistence {
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> mongoCollection;

    public UserPersistence(MongoClient mongoClient,
                             String myCashDatabaseName, String myCashCollectionName) {
        this.mongoClient = mongoClient;
        this.mongoDatabase = this.mongoClient.getDatabase(myCashDatabaseName);
        this.mongoCollection = this.mongoDatabase.getCollection(myCashCollectionName);
    }

    public boolean isMongoDBClientNull() {
        return this.mongoClient == null;
    }

    public String getDBName() {
        return this.mongoDatabase.getName();
    }

    public void getAllUsers() {

    }


}
