import java.util.List;

public class MockGithubModule implements GithubApi{

    public User signIn(String username, String password) {
        return null;
    }

    public void signOut() {

    }

    public PullRequest createPullRequest(String title, GitBranch head, GitBranch target) {
        return null;
    }

    public PullRequest getPullRequest(String branchName) {
        return null;
    }

    public List<GitCommit> getCommits(String branchName) {
        return null;
    }

    public void approvePullRequest(String branchName) {

    }

    public List<GitComment> getPullRequestComments(String branchName) {
        return null;
    }
}
