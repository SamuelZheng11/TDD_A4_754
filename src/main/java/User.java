public class User {
	//IAbstractionExtension used for module 4
	IAbstractionExtension abstractextension;
	
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
		return abstractextension.generateCodeAbstraction();
	}
    
    //requirement 12
    public String NonDev_AddComment(String str1, String str2) {
    	
    	str = str1+str2;
    	//AbstractResult_Recieved_fromNonDev();
    	return str;
    }
    //Final comments received from the Non Developer
    public String AbstractResult_Recieved_fromNonDev()
	{
		return abstractextension.generateReviewerAbstraction();
	}
    
    //requirement 13
    public String Dev_AddComment(String str1, String str2) {
    	
    	str = str1+str2;
    	//AbstractResult_FinalChange_fromDev();
    	return str;

     }
    //Final comments received from the Developer
    public String AbstractResult_FinalChange_fromDev() {
		return abstractextension.generateDevCodeAbstraction();

    }
}

