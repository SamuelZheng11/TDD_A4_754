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

    public void addCodeReview(CodeReview codeReview) {
    }

    public void removeCodeReviwer(User developer, User nonDeveloper) {
    }

    public void randomAllocateReviewer() {
    }
}
