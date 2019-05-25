public class User {
	//IAbstractionExtension used for module 4	
	Abstraction_ForNetwork abstractionextension;
    private final String name;
    public String str ;
    private int reviewCount = 0;
    public User(String name, UserType type){
        this.name = name;
    }

    public User(String userName, UserType nonDeveloper, int reviewCount) {
        this.name = userName;
        this.reviewCount = reviewCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void incrementReviewCount() {
        reviewCount +=1;
        ReviewerPersistence.getInstance().updateReviewCount(this);
    }

    public void decrementReviewCount() {
        reviewCount -=1;
        ReviewerPersistence.getInstance().updateReviewCount(this);
    }

    public String getName(){
        return name;
    }
    //module 4
    
    //requirement 11
    //Final comments received from the automated tool
    public String AbstractResult_Recieved_fromTool()
	{
    	return abstractionextension.fetchAbstraction_fromTool();
	}
    
    
 
   

}