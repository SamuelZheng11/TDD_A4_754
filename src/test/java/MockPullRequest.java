import java.util.*;

public class MockPullRequest extends PullRequest {
    private List<GitComment> commentsPosted = new ArrayList<GitComment>();
    private List<CodeReviewAllocation> codeReviewAllocations = new ArrayList<CodeReviewAllocation>();
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

    public void addCodeReview(CodeReviewAllocation codeReviewAllocation) {
        codeReviewAllocations.add(codeReviewAllocation);
    }

    public CodeReviewAllocation createCodeReview(User requester, User codeReviewer){
        CodeReviewAllocation codeReviewAllocation = new CodeReviewAllocation(this, requester, codeReviewer);
        this.codeReviewAllocations.add(codeReviewAllocation);
        return codeReviewAllocation;
    }

    public List<User> getCodeReviewers(){
        List<User> codeReviewers = new ArrayList<User>();
        for (CodeReviewAllocation codeReviewAllocation : codeReviewAllocations){
            codeReviewers.add(codeReviewAllocation.getCodeReviewer());
        }
        return codeReviewers;
    }

    public void removeCodeReviwer(User developer, User nonDeveloper) {
        for (CodeReviewAllocation cr: codeReviewAllocations){
            if (cr.getCodeReviewer().equals(nonDeveloper)){
                codeReviewAllocations.remove(cr);
                cr.getCodeReviewer().decrementReviewCount();
                cr.remover(developer);
                return;
            }
        }
    }

}
