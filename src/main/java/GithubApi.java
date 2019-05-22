import java.util.List;

public interface GithubApi {

    User signIn(String username, String password);

    void signOut(User user);

    boolean isSignedIn(User user);

    PullRequest createPullRequest(String title, GitBranch head, GitBranch target);

    List<GitComment> getPullRequestComments(PullRequest pullrequest);

    PullRequest getPullRequest(String branchName);

    List<GitCommit> getCommits(String branchName);

    public List<User> getCodeReviewers(PullRequest pullRequest);

    boolean approvePullRequest(PullRequest pr);
}
