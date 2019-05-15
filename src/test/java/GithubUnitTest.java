import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GithubUnitTest {

    private User _developer;
    private GithubModule _github;

    @Before public void initialize(){
        _github = GithubModule.getInstance();
        _developer = new User("", UserType.Developer);
        _github.signIn("username", "password");
    }


    //Requirement(4)
    @Test
    public void AddCommentsAndCodeRequestsToGithubAutomaticallyTest() {
        //Given
        String branchName = "GithubPullRequestFetchTest_Branch";
        GitCommit[] committed_code = {new GitCommit("Commit Message", "GithubPullRequestFetchTest_Commit")};
        GitBranch branch = new GitBranch(branchName, committed_code);
        PullRequest pullrequest = _github.createPullRequest(branch);
        User commenter = new User("", UserType.NonDeveloper);
        //When
        GitComment comment = new GitComment("This code is great");
        pullrequest.postDiscussion(commenter, comment);
        GitCodeRequest request = new GitCodeRequest("Please make line 34 better");
        pullrequest.postDiscussion(commenter, request);
        //Assert
        List<GitDiscussion> discussion = _github.getPullRequestDiscussion(branchName);
        assertTrue(discussion.contains(comment));
        assertTrue(discussion.contains(request));
    }
}
