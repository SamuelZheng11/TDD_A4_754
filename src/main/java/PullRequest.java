import java.util.List;

public class PullRequest {

    private boolean _completed = false;

    public PullRequest(User user, String title, GitBranch source, GitBranch target){

    }

    public void postComment(GitComment discussion){

    }

    public void setCompletedStatus(boolean status){
        _completed = status;
    }

    public boolean isCompleted() {
        return _completed;
    }

    public CodeReviewAllocation createCodeReview(User requester, User codeReviewer){
        return null;
    }


    public void addCodeReview(CodeReviewAllocation codeReviewAllocation) {
    }

    public void removeCodeReviwer(User developer, User nonDeveloper) {
    }

    public List<User> getCodeReviewers(){return null;};
    
}
