import java.util.List;

public class PullRequest {

    private boolean _completed = false;
    //module 4
    List<GitComment> _comments ;
    
    public PullRequest(User user, String title, GitBranch source, GitBranch target){

    }
    //module 4 changes
    public void postComment(GitComment discussion){
    	_comments.add(discussion);
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

    public void removeCodeReviwer(User developer, List<User> reviewers) {
    }

    public List<User> getCodeReviewers(){return null;};

    public List<CodeReviewAllocation> createCodeReview(User requester, List<User> codeReviewers){
        return null;
    }

}
