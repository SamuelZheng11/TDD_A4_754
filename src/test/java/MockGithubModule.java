import java.util.List;

public class MockGithubModule implements GithubApi{

    private GithubUnitTest testvalues = new GithubUnitTest();
    private User _currentUser;
    private MockPullRequest mockPR;

    public User signIn(String username, String password) {
        if (username == testvalues.username && password == testvalues.password){
            _currentUser = testvalues.developer;
            return _currentUser;
        }
        return null;
    }

    public void signOut() {
        _currentUser = null;
    }

    public PullRequest createPullRequest(String title, GitBranch head, GitBranch target) {
        mockPR = new MockPullRequest(_currentUser, title, head, target);
        return mockPR;
    }

    public PullRequest getPullRequest(String branchName) {
        return null;
    }

    public List<GitCommit> getCommits(String branchName) {
        return null;
    }

    public void approvePullRequest(String branchName) {

    }

    public List<GitComment> getPullRequestComments(PullRequest pullRequest) {
        if (pullRequest == mockPR){
            return mockPR.getCommentsPosted();
        }
        return null;
    }
}
