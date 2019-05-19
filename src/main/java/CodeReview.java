public class CodeReview {
    User requester;
    User codeReviewer;
    PullRequest pullRequest;
    public CodeReview(PullRequest pullRequest, User developer, User nonDeveloper) {
        this.pullRequest = pullRequest;
        this.requester = developer;
        this.codeReviewer = nonDeveloper;
        pullRequest.addCodeReview(this);
    }

    public User getCodeReviewer() {
        return codeReviewer;
    }
}
