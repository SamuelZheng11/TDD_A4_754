import java.util.ArrayList;
import java.util.List;

public class MockPullRequest extends PullRequest {
    private List<GitComment> commentsPosted = new ArrayList<GitComment>();
    private List<CodeReview> codeReviews = new ArrayList<CodeReview>();
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

    public void addCodeReview(CodeReview codeReview) {
        codeReviews.add(codeReview);
    }

    public List<User> getCodeReviewers(){
        List<User> codeReviewers = new ArrayList<User>();
        for (CodeReview codeReview : codeReviews){
            codeReviewers.add(codeReview.getCodeReviewer());
        }
        return codeReviewers;
    }
    public void removeCodeReviwer(User developer, User nonDeveloper) {
        for (CodeReview cr: codeReviews){
            if (cr.getCodeReviewer().equals(nonDeveloper)){
                codeReviews.remove(cr);
                return;
            }
        }
    }

    public void randomAllocateReviewer() {
        User nonDeveloper = new User("", UserType.NonDeveloper);
        new CodeReview(this, null, nonDeveloper);
    }

}
