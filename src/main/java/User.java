public class User {
    private final String name;
    private int reviewCount = 0;
    public User(String name, UserType type){
        this.name = name;
    }

    public User(String userName, UserType nonDeveloper, int reviewCount) {
        this.name = userName;
        this.reviewCount = reviewCount;
    }

    public boolean isSignedIn(){
        return true;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void incrementReviewCount() {
        reviewCount +=1;
        ReviewerPersistence.getInstance().addReviewCount(this);
    }

    public void decrementReviewCount() {
        reviewCount -=1;
        ReviewerPersistence.getInstance().addReviewCount(this);
    }

    public String getName(){
        return name;
    }
}
