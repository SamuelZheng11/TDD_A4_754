import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class GithubUnitTest {

    private User _developer;
    private GithubApi _github;

    @Before public void initialize(){
        _github = new MockGithubModule();
        _developer = new User("", UserType.Developer);
        _github.signIn("username", "password");
    }


    //Requirement(4)
    @Test
    public void AddCommentsAndCodeRequestsToGithubAutomaticallyTest() {
        //Given
        String branchName = "GithubPullRequestFetchTest_Branch";
        String targetBranchName = "GithubPullRequestFetchTest_TargetBranch";
        GitCommit[] committed_code = {new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit")};
        GitBranch sourceBranch = new GitBranch(branchName, committed_code);
        GitBranch targetBranch = new GitBranch(branchName, committed_code);
        PullRequest pullrequest = _github.createPullRequest("Test adding comments automatically pull request", sourceBranch, targetBranch);
        User commenter = new User("", UserType.NonDeveloper);
        //When
        GitComment comment = new GitComment("This code is great");
        pullrequest.postDiscussion(commenter, comment);
        //Assert
        List<GitComment> discussion = _github.getPullRequestComments(branchName);
        assertTrue(discussion.contains(comment));
}
}
