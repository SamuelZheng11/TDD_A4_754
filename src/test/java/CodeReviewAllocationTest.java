import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CodeReviewAllocationTest {
    public String sourceBranchName = "GithubPullRequestFetchTest_Branch";
    public String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";

    public User developer = new User("", UserType.Developer);
    public User nonDeveloper = new User("", UserType.NonDeveloper);

    public GitCommit commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
    public GitCommit[] committed_code = {commit};
    public GitBranch sourceBranch = new GitBranch(sourceBranchName, committed_code);
    public GitBranch targetBranch = new GitBranch(targetBranchName, committed_code);

    private User _developer;
    private GithubApi _github;

    @Before
    public void initialize(){
        _github = new MockGithubModule();
    }

    /**
     * 8. The developer can add/delete one or more non-developer reviewers in this
     * tool. A database is used to store the reviewers’ information.
     */
    //Requirement(8)
    @Test
    public void TestDeveloperCanAddCodeReviewer() {
        //Given
        PullRequest pullRequest = _github.createPullRequest("Test add code reviewers", sourceBranch, targetBranch);
        //When
        CodeReview codeReview = new CodeReview(pullRequest, developer, nonDeveloper);
        //Assert
        List<User> codeReviewers = _github.getCodeReviewers(pullRequest);
        assertTrue(codeReviewers.contains(nonDeveloper));
    }

    @Test
    public void TestDeveloperCanRemoveCodeReviewer() {
        //Given
        PullRequest pullRequest = _github.createPullRequest("Test add code reviewers", sourceBranch, targetBranch);
        //When
        CodeReview codeReview = new CodeReview(pullRequest, developer, nonDeveloper);
        //Assert
        List<User> codeReviewers = _github.getCodeReviewers(pullRequest);
        assertTrue(codeReviewers.contains(nonDeveloper));
        pullRequest.removeCodeReviwer(developer, nonDeveloper);
        assertFalse(codeReviewers.contains(nonDeveloper));
    }
}
