package mocks;

import developer.CodeReviewAllocation;
import github.GitBranch;
import github.GitComment;
import github.PullRequest;
import github.User;

import java.util.*;

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

}
