public class User {
	//IAbstractionExtension used for module 4	
    private final String name;
    public String str ;
    private int reviewCount = 0;
    public User(String name, UserType type){
        this.name = name;
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