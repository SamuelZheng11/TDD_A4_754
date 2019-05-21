import java.util.List;

public interface GithubApi {

    public User signIn(String username, String password);

    public void signOut(User user);

    public PullRequest createPullRequest(String title, GitBranch head, GitBranch target);

    public List<GitComment> getPullRequestComments(PullRequest pullrequest);

}
