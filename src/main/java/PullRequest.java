import java.util.List;

public class PullRequest {


    public PullRequest(User user, String title, GitBranch source, GitBranch target){

    }

    public boolean isCompleted(){
        return true;
    }

    public void postComment(GitComment discussion){

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
