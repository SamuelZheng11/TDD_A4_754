public class User {
    private int reviewCount = 0;
    public User(String name, UserType type){}

    public boolean isSignedIn(){
        return true;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void incrementReviewCount() {
        reviewCount +=1;
    }
}
