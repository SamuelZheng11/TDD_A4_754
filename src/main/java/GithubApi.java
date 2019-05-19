import java.util.List;

public interface GithubApi {

    public User signIn(String username, String password);

    public void signOut();

    public PullRequest createPullRequest(String title, GitBranch head, GitBranch target);

    public PullRequest getPullRequest(String branchName);

    public List<GitCommit> getCommits(String branchName);

    public void approvePullRequest(String branchName);

    public List<GitComment> getPullRequestComments(PullRequest pullrequest);

    public List<User> getCodeReviewers(PullRequest pullRequest);

}
