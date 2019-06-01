package acceptance;

import developer.CodeReviewAllocation;
import github.*;
import mocks.MockGithubModule;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DefaultSteps {

	private String sourceBranchName = "GithubPullRequestFetchTest_Branch";
	private String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";

	private User developer = new User("", UserType.Developer);
	private User nonDeveloper = new User("codeReviewer", UserType.NonDeveloper);

	private GitCommit commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
	private GitCommit[] committed_code = {commit};
	private GitBranch sourceBranch = new GitBranch(sourceBranchName, committed_code);
	private GitBranch targetBranch = new GitBranch(targetBranchName, committed_code);

	private GithubApi _github;
	private PullRequest mockPullRequest;

	@BeforeScenario
	public void initialise(){
		_github = new MockGithubModule();
	}
	
	@Given("a pull request")
	public void givenAPullRequest() {
		mockPullRequest = Mockito.spy(_github.createPullRequest("Test developer can add code reviewers", sourceBranch, targetBranch));
		int initialReviewCount = nonDeveloper.getReviewCount();
	}

	@When("a non-developer is added")
	public void whenANonDevIsAdded(){
		CodeReviewAllocation codeReviewAllocation = mockPullRequest.createCodeReview(developer, nonDeveloper);
		mockDatabaseBehaviourWhenAddReviewCountIsCalled(nonDeveloper);

	}

	@Then("the user is added to the database")
	public void thenTheUserIsAddedToTheDatabase() {
		List<User> codeReviewers = mockPullRequest.getCodeReviewers();
		assertTrue(codeReviewers.contains(nonDeveloper));
	}
}
