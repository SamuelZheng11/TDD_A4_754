public class User {
	//IAbstractionExtension used for module 4
	IAbstractionExtension abstractextension;
    private final String name;
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
    //module 4
    public String AbstractResult_recieve()
	{
		return abstractextension.generateCodeAbstraction();
	}

}
