import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import static com.mongodb.client.model.Filters.eq;
import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.mockito.Mockito;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class CodeReviewAllocationTest {
    public String sourceBranchName = "GithubPullRequestFetchTest_Branch";
    public String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";

    public User developer = new User("", UserType.Developer);
    public User nonDeveloper = new User("codeReviewer", UserType.NonDeveloper);

    public GitCommit commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
    public GitCommit[] committed_code = {commit};
    public GitBranch sourceBranch = new GitBranch(sourceBranchName, committed_code);
    public GitBranch targetBranch = new GitBranch(targetBranchName, committed_code);

    private GithubApi _github;

    MongoClient mongoClient;
    String usersDBName;
    String usersCollectionName;
    MongoDatabase mongoDatabase;
    MongoCollection<Document> collection;

    @Before
    public void initialize(){
        _github = new MockGithubModule();
        // Given
        mongoClient = Mockito.mock(MongoClient.class);
        usersDBName = "users-db";
        usersCollectionName = "user-collection";

        mongoDatabase =  Mockito.mock(MongoDatabase.class);
        Mockito.doReturn(mongoDatabase).when(mongoClient).getDatabase(usersDBName);

        collection = Mockito.mock(MongoCollection.class);
        Mockito.doReturn(collection).when(mongoDatabase).getCollection(usersCollectionName);
        ReviewerPersistence.getInstance().addDatabase(mongoClient, usersDBName, usersCollectionName);

    }

    @Test
    public void shouldInitializeMongoDBClientWhenCreatingMyCash() {

        // Given
        Mockito.doReturn(mongoDatabase).when(mongoClient).getDatabase(usersDBName);

        MongoCollection<Document> collection = Mockito.mock(MongoCollection.class);
        Mockito.doReturn(collection).when(mongoDatabase).getCollection(usersCollectionName);

        FindIterable iterable = Mockito.mock(FindIterable.class);
        MongoCursor cursor = Mockito.mock(MongoCursor.class);
        Document bob = new Document("_id", new ObjectId("579397d20c2dd41b9a8a09eb")).append("firstName", "Bob").append("lastName", "Bobberson");

        Mockito.when(collection.find(new Document("lastName", "Bobberson"))).thenReturn(iterable);
        Mockito.when(iterable.iterator()).thenReturn(cursor);
        Mockito.when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(cursor.next()).thenReturn(bob);

        List<Document> people = new ArrayList<>();
        FindIterable<Document> documents = collection.find(new Document("lastName", "Bobberson"));
        MongoCursor<Document> iterator = documents.iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            people.add(document);
        }


        assertEquals(bob, people.get(0));

    }

    /**
     * 8. The developer can add/delete one or more non-developer reviewers in this
     * tool. A database is used to store the reviewers’ information.
     */
    @Test
    public void TestDeveloperCanAddCodeReviewer() {
        //Given
        PullRequest pullRequest = _github.createPullRequest("Test add code reviewers", sourceBranch, targetBranch);
        int initialReviewCount = nonDeveloper.getReviewCount();

        //When
        CodeReview codeReview = new CodeReview(pullRequest, developer, nonDeveloper);

        FindIterable iterable = Mockito.mock(FindIterable.class);
        MongoCursor cursor = Mockito.mock(MongoCursor.class);
        Document nonDeveloperDocument = Mockito.mock(Document.class);

        Mockito.when(collection.find(new Document(ReviewerPersistence.FIRST_NAME_KEY, nonDeveloper.getName()))).thenReturn(iterable);
        Mockito.when(iterable.iterator()).thenReturn(cursor);
        Mockito.when(cursor.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(cursor.next()).thenReturn(nonDeveloperDocument);
        Mockito.when(nonDeveloperDocument.get(ReviewerPersistence.REVIEW_COUNT_KEY)).thenReturn(nonDeveloper.getReviewCount());

        //Then
        List<User> codeReviewers = _github.getCodeReviewers(pullRequest);
        assertTrue(codeReviewers.contains(nonDeveloper));

        assertEquals(initialReviewCount+1,nonDeveloper.getReviewCount());

        int databaseReviewCount = ReviewerPersistence.getInstance().getReviewCountForUser(nonDeveloper);
        assertEquals(nonDeveloper.getReviewCount(), databaseReviewCount);

    }

    @Test
    public void TestDeveloperCanRemoveCodeReviewer() {
        //Given
        PullRequest pullRequest = _github.createPullRequest("Test remove code reviewers", sourceBranch, targetBranch);
        //When
        CodeReview codeReview = new CodeReview(pullRequest, developer, nonDeveloper);
        List<User> codeReviewers = _github.getCodeReviewers(pullRequest);
        assertTrue(codeReviewers.contains(nonDeveloper));
        //Assert
        pullRequest.removeCodeReviwer(developer, nonDeveloper);
        codeReviewers = _github.getCodeReviewers(pullRequest);
        assertFalse(codeReviewers.contains(nonDeveloper));
    }

    /**
     * 9) The tool can randomly choose a reviewer and allocate code review task to
     * this reviewer. The chance of being allocated is related to the count of
     * reviews previously done by a reviewer, the lower count, the higher chance.
     */
    @Test
    public void TestRandomAllocateCRerToPR() {

        //Given
        PullRequest pullRequest = _github.createPullRequest("Test random code reviewers", sourceBranch, targetBranch);
        //When
        pullRequest.randomAllocateReviewer();
        //Assert
        List<User> codeReviewers = _github.getCodeReviewers(pullRequest);
        if(codeReviewers.size() != 1){
            fail("Random allocation did not allocate ONE code reviewer");
        }
        assertNotNull(codeReviewers.get(0));
    }

    /*10)The count will be updated after the allocation. The count information is stored in the database as well.
     * */
    @Test
    public void TestUserCodeReviewIncreases() {
        //Given
        List<User> allUsers = MockDatabasePersistence.getInstance().getAllCodeReviewers();
        Map<User, Integer> userReviewCountMap = new HashMap<User, Integer>();
        //getting all review counts of all users
        for(User u : allUsers){
            userReviewCountMap.put(u, u.getReviewCount());
        }
        PullRequest pullRequest = _github.createPullRequest("Test random code reviewers", sourceBranch, targetBranch);

        //When
        pullRequest.randomAllocateReviewer();

        //Assert
        List<User> users = _github.getCodeReviewers(pullRequest);
        User codeReviewer = users.get(0);
        int initialReviewCount = userReviewCountMap.get(codeReviewer);
        assertEquals(codeReviewer.getReviewCount(), initialReviewCount+1);

    }
}
