public class PullRequest {


    public PullRequest(User user, String title, GitBranch source, GitBranch target){

    }

    public boolean isCompleted(){
        return true;
    }

    public void postComment(GitComment discussion){

    }

    public void addCodeReview(CodeReview codeReview) {
    }

    public void removeCodeReviwer(User developer, User nonDeveloper) {
    }

    public CodeReview randomAllocateReviewer() {
        return null;
    }
}
