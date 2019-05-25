public class User {
	//IAbstractionExtension used for module 4	
	IAbstractionExtension abstractionextension;
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
    //module 4
    
    //requirement 11
    //Final comments received from the automated tool
    public String AbstractResult_Recieved_fromTool()
	{
    	return abstractionextension.fetchAbstraction_fromTool();
	}
    
    
 
   

}