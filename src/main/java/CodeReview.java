public class CodeReview {
    User requester;
    User codeReviewer;
    PullRequest pullRequest;
    public CodeReview(PullRequest pullRequest, User requester, User codeReviewer) {
        this.pullRequest = pullRequest;
        this.requester = requester;
        this.codeReviewer = codeReviewer;
        codeReviewer.incrementReviewCount();
        pullRequest.addCodeReview(this);
    }

    public User getCodeReviewer() {
        return codeReviewer;
    }

}
