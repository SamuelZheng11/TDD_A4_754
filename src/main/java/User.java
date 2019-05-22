public class User {
    private int reviewCount = 0;
    public User(String name, UserType type){}

    public int getReviewCount() {
        return reviewCount;
    }

    public void incrementReviewCount() {
        reviewCount +=1;
    }

}
