package developer;

import github.PullRequest;
import github.User;

import java.util.*;

public class CodeReviewAllocation {
    private User requester;
    private User codeReviewer;
    private PullRequest pullRequest;
    private User codeReviewerRemover;

    public CodeReviewAllocation(PullRequest pullRequest) {
        this.pullRequest = pullRequest;
    }

    public CodeReviewAllocation(PullRequest pullRequest, User requester, User codeReviewer) {
        this.pullRequest = pullRequest;
        this.requester = requester;
        this.codeReviewer = codeReviewer;
        codeReviewer.incrementReviewCount();
    }

    public User getCodeReviewer() {
        return codeReviewer;
    }

    public int generateRandomInt(int chance){
        Random rand = new Random();
        return rand.nextInt(chance);
    }

    public User randomAllocateReviewer() {
        List<User> allCodeReviewers = ReviewerPersistence.getInstance().getAllCodeReviewers();
        Map<User, Integer> reviewChanceMap = new LinkedHashMap<User, Integer>();
        int chance = 0;
        double totalReviewCount = 0;
        for (User cr: allCodeReviewers){
            totalReviewCount += cr.getReviewCount();
        }

        for (User cr: allCodeReviewers){
            if (cr.getReviewCount()==0){
                chance += totalReviewCount;
            }else{
                chance += 1.0/(double)cr.getReviewCount()*totalReviewCount;
            }
            reviewChanceMap.put(cr, chance);
        }

        int randomValue = generateRandomInt(chance);

        Set<User> reviewChanceKeySet = reviewChanceMap.keySet();
        for (User cr: reviewChanceKeySet){
            if (randomValue < reviewChanceMap.get(cr)){
                this.codeReviewer = cr;
                codeReviewer.incrementReviewCount();
                pullRequest.addCodeReview(this);
                break;
            }
        }

        return this.codeReviewer;
    }

    public void remover(User developer) {
        this.codeReviewerRemover = developer;
    }
}
