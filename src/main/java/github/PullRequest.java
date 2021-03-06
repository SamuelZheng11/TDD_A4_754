package github;

import developer.CodeReviewAllocation;

import java.util.ArrayList;
import java.util.List;

public class PullRequest {

    private boolean _completed = false;
    private List<CodeReviewAllocation> codeReviewAllocations = new ArrayList<CodeReviewAllocation>();

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

    public void addCodeReview(CodeReviewAllocation codeReviewAllocation) {
        codeReviewAllocations.add(codeReviewAllocation);
    }

    public CodeReviewAllocation createCodeReview(User requester, User codeReviewer){
        CodeReviewAllocation codeReviewAllocation = new CodeReviewAllocation(this, requester, codeReviewer);
        this.codeReviewAllocations.add(codeReviewAllocation);
        return codeReviewAllocation;
    }

    public List<CodeReviewAllocation> createCodeReview(User requester, List<User> codeReviewers){
        List<CodeReviewAllocation> newCodeReviewAllocations = new ArrayList<CodeReviewAllocation>();
        for (User codeReviewer : codeReviewers){
            CodeReviewAllocation codeReviewAllocation = this.createCodeReview(requester, codeReviewer);
            newCodeReviewAllocations.add(codeReviewAllocation);
        }
        return newCodeReviewAllocations;
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

    public void removeCodeReviwer(User developer, List<User> codeReviewers) {
        for (User u: codeReviewers){
            removeCodeReviwer(developer, u);
        }
    }
}
