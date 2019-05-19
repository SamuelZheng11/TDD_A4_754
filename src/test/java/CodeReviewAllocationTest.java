import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;
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

    private GithubApi _github;

    @Before
    public void initialize(){
        _github = new MockGithubModule();
    }

    /**
     * 8. The developer can add/delete one or more non-developer reviewers in this
     * tool. A database is used to store the reviewers’ information.
     */
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
        List<User> allUsers = DatabaseManager.getInstance().getAllUsers();
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
