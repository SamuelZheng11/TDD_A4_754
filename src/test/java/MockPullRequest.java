import java.util.ArrayList;
import java.util.List;

public class MockPullRequest extends PullRequest {
    private List<GitComment> commentsPosted = new ArrayList<GitComment>();

    public MockPullRequest(User user, String title, GitBranch source, GitBranch target) {
        super(user, title, source, target);
    }

    @Override
    public void postComment(GitComment discussion) {
        commentsPosted.add(discussion);
    }

    public List<GitComment> getCommentsPosted() {
        return commentsPosted;
    }

    public List<User> getCodeReviewers(){}
}
