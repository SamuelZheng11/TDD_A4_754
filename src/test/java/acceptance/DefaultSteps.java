package acceptance;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import developer.CodeReviewAllocation;
import developer.ReviewerPersistence;
import github.*;
import junit.framework.TestCase;
import mocks.MockGithubModule;
import org.bson.Document;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.Mockito;

import java.util.List;

import static developer.ReviewerPersistence.FIRST_NAME_KEY;
import static developer.ReviewerPersistence.REVIEW_COUNT_KEY;
import static developer.ReviewerPersistence.USERTYPE_KEY;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;

public class DefaultSteps {

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

	private int initialReviewCount;
	private PullRequest mockPullRequest;


	@BeforeScenario
	public void setUp(){
		mongoClient = new MongoClient("localhost", 27017);

		MongoDatabase mongoDatabase = this.mongoClient.getDatabase(usersDBName);
		MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(usersCollectionName);

		Document d = new Document(FIRST_NAME_KEY, nonDeveloper.getName()).append(REVIEW_COUNT_KEY, nonDeveloper.getReviewCount()).append(USERTYPE_KEY, UserType.NonDeveloper.ordinal());
		mongoCollection.insertOne(d);

		rp = ReviewerPersistence.getInstance();
		rp.addDatabase(mongoClient, usersDBName, usersCollectionName);

		_github = new MockGithubModule();
	}

	@Given("a pull request")
	public void givenAPullRequest() {
		mockPullRequest = _github.createPullRequest("Test developer can add code reviewers", sourceBranch, targetBranch);
		initialReviewCount = nonDeveloper.getReviewCount();
	}

	@When("a non-developer is added")
	public void whenANonDevIsAdded(){
		CodeReviewAllocation codeReviewAllocation = mockPullRequest.createCodeReview(developer, nonDeveloper);
	}

	@Then("the user is added to the database")
	public void thenTheUserIsAddedToTheDatabase() {
		List<User> codeReviewers = mockPullRequest.getCodeReviewers();
		assertTrue(codeReviewers.contains(nonDeveloper));

		assertEquals(initialReviewCount+1,nonDeveloper.getReviewCount());

		System.out.println("User Code Review Count save action perfomed!!!");
	}
}
