package testsuits.integration;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import junit.framework.TestCase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import static developer.ReviewerPersistence.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import developer.CodeReviewAllocation;
import developer.ReviewerPersistence;
import github.*;
import mocks.MockGithubModule;

import com.mongodb.MongoClient;

import java.util.List;

public class CodeReviewDatabasePersistenceTest {

    private String sourceBranchName = "GithubPullRequestFetchTest_Branch";
    private String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";

    private User developer = new User("", UserType.Developer);
    private User nonDeveloper = new User("codeReviewer", UserType.NonDeveloper);

    private GitCommit commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
    private GitCommit[] committed_code = {commit};
    private GitBranch sourceBranch = new GitBranch(sourceBranchName, committed_code);
    private GitBranch targetBranch = new GitBranch(targetBranchName, committed_code);

    private ReviewerPersistence rp;

    private MongoClient mongoClient;
    private String usersDBName = "users-db";
    private String usersCollectionName = "users-collection";
    private GithubApi _github;

    @Before
    public void setUp(){

        //Given

        mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase mongoDatabase = this.mongoClient.getDatabase(usersDBName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(usersCollectionName);
        Document d = new Document(FIRST_NAME_KEY, nonDeveloper.getName()).append(REVIEW_COUNT_KEY, nonDeveloper.getReviewCount()).append(USERTYPE_KEY, UserType.NonDeveloper.ordinal());
        mongoCollection.insertOne(d);
        rp = ReviewerPersistence.getInstance();
        rp.addDatabase(mongoClient, usersDBName, usersCollectionName);

        _github = new MockGithubModule();
        
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

    @Test
    public void shouldUpdateDatabaseWhenADeveloperAddsACodeReviewerForCodeReview(){
        //Given

        PullRequest mockPullRequest = _github.createPullRequest("Test developer can add code reviewers", sourceBranch, targetBranch);
        int initialReviewCount = nonDeveloper.getReviewCount();

        //When
        CodeReviewAllocation codeReviewAllocation = mockPullRequest.createCodeReview(developer, nonDeveloper);

        //Then
        List<User> codeReviewers = mockPullRequest.getCodeReviewers();
        assertTrue(codeReviewers.contains(nonDeveloper));

        assertEquals(initialReviewCount+1,nonDeveloper.getReviewCount());

        System.out.println("User Code Review Count save action perfomed!!!");
    }

    @Test
    public void shouldUpdateDatabaseWhenADeveloperAllowDeveloperToRemoveCodeReviewer() {

        //Given
        int initialReviewCount = nonDeveloper.getReviewCount();
        PullRequest mockPullRequest = _github.createPullRequest("Test developer can add code reviewers", sourceBranch, targetBranch);

        CodeReviewAllocation codeReviewAllocation = mockPullRequest.createCodeReview(developer, nonDeveloper);

        //check that the database is initially correct and the count has actually been increased
        int databaseReviewCount = rp.getReviewCountForUser(nonDeveloper);
        TestCase.assertEquals(nonDeveloper.getReviewCount(), databaseReviewCount);
        TestCase.assertEquals(initialReviewCount+1,nonDeveloper.getReviewCount());

        //When
        mockPullRequest.removeCodeReviwer(developer, nonDeveloper);

        //Then
        List<User> codeReviewers = mockPullRequest.getCodeReviewers();
        assertFalse(codeReviewers.contains(nonDeveloper));
        TestCase.assertEquals(initialReviewCount,nonDeveloper.getReviewCount());

    }
}
