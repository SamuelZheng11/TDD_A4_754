import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GithubUnitTest {

    public String sourceBranchName = "GithubPullRequestFetchTest_Branch";
    public String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";

    public String username = "username";
    public String password = "password";

    public User developer = new User("", UserType.Developer);
    public User nonDeveloper = new User("", UserType.NonDeveloper);

    public GitCommit commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
    public GitCommit[] committed_code = {commit};
    public GitBranch sourceBranch = new GitBranch(sourceBranchName, committed_code);
    public GitBranch targetBranch = new GitBranch(targetBranchName, committed_code);
    public GitComment nonDevComment = new GitComment("This code is great", nonDeveloper);
    public List<GitComment> commentList = Arrays.asList(nonDevComment);

    private GithubApi _github;

    @Before public void initialize(){
        _github = new MockGithubModule();
        _github.signIn(username, password);
    }

    //Requirement (1)
    @Test
    public void GithubSignInTest() {
        //Given
        _github.signOut(developer);
        User signInDeveloper = developer;
        //When
        _github.signIn(username, password);
        //Assert
        assertTrue(developer.isSignedIn());
    }

    @Test
    public void GithubSignOutTest() {
        //Given developer is signed in from initialise

        //When
        _github.signOut(developer);
        //Assert
        assertFalse(developer.isSignedIn());
    }


    //Requirement(4)
    @Test
    public void AddCommentsAndCodeRequestsToGithubAutomaticallyTest() {
        //Given
        PullRequest pullrequest = _github.createPullRequest("Test adding comments automatically pull request", sourceBranch, targetBranch);
        //When
        pullrequest.postComment(nonDevComment);
        //Assert
        List<GitComment> discussion = _github.getPullRequestComments(pullrequest);
        assertTrue(discussion.contains(nonDevComment));
}
}
