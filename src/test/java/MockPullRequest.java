import java.util.*;

public class MockPullRequest extends PullRequest {
    private List<GitComment> commentsPosted = new ArrayList<GitComment>();
    private List<CodeReview> codeReviews = new ArrayList<CodeReview>();
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

    public void addCodeReview(CodeReview codeReview) {
        codeReviews.add(codeReview);
    }

    public List<User> getCodeReviewers(){
        List<User> codeReviewers = new ArrayList<User>();
        for (CodeReview codeReview : codeReviews){
            codeReviewers.add(codeReview.getCodeReviewer());
        }
        return codeReviewers;
    }
    public void removeCodeReviwer(User developer, User nonDeveloper) {
        for (CodeReview cr: codeReviews){
            if (cr.getCodeReviewer().equals(nonDeveloper)){
                codeReviews.remove(cr);
                cr.getCodeReviewer().decrementReviewCount();
                return;
            }
        }
    }

    public CodeReview randomAllocateReviewer() {
        Random rand = new Random();
        List<User> allCodeReviewers = ReviewerPersistence.getInstance().getAllCodeReviewers();
        Map<User, Integer> reviewChanceMap = new LinkedHashMap<>();
        int chance = 0;
        double totalReviewCount = 0;
        for (User cr: allCodeReviewers){
            totalReviewCount += cr.getReviewCount();
        }
        if(totalReviewCount == 0){
            totalReviewCount = allCodeReviewers.size();
        }
        for (User cr: allCodeReviewers){
            if (cr.getReviewCount()==0){
                chance += totalReviewCount;
            }else{
                chance += 1.0/(double)cr.getReviewCount()*totalReviewCount;
            }
            reviewChanceMap.put(cr, chance);
        }

        int randomValue = rand.nextInt(chance);

        for (User cr: reviewChanceMap.keySet()){
            if (randomValue < reviewChanceMap.get(cr)){
                return new CodeReview(this, null, cr);
            }
        }

        return null;
    }

}
