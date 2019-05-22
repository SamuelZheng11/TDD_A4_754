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

    public GitCommit commit;
    public GitCommit[] committed_code;
    public GitBranch sourceBranch;
    public GitBranch targetBranch;
    public GitComment nonDevComment = new GitComment("This code is great", nonDeveloper);
    public List<GitComment> commentList = Arrays.asList(nonDevComment);

    private GithubApi _github;

    @Before
    public void initialize(){
        _github = new MockGithubModule(this);
        _github.signIn(username, password);
    }

    //Requirement (1)
    @Test
    public void GithubSignInTest() {
        //Given
        _github.signOut(developer);
        //When
        User signInDeveloper = _github.signIn(username, password);
        //Assert
        assertTrue(_github.isSignedIn(signInDeveloper));
    }

    @Test
    public void GithubSignOutTest() {
        //Given developer is signed in from initialise

        //When
        _github.signOut(developer);
        //Assert
        assertFalse(_github.isSignedIn(developer));
    }

    //Requirement (2)
    @Test
    public void GithubPullRequestFetchTest() {
        //Given
        commit = new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit");
        committed_code = new GitCommit[]{commit};
        sourceBranch = new GitBranch(sourceBranchName, committed_code);
        targetBranch = new GitBranch(targetBranchName, null);
        //When
        _github.createPullRequest("", sourceBranch, targetBranch);
        //Assert
        List<GitCommit> commits = _github.getCommits(sourceBranchName);
        assertTrue(commits.contains(commit));
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
