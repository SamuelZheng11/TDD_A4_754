public class User {
	//IAbstractionExtension used for module 4
	IAbstractionExtension abstractextension;
	
    private final String name;
    public String str1 ;
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
    public String AbstractResult_Recieved_fromTool()
	{
		return abstractextension.generateCodeAbstraction();
	}
public String addComment(String str, String str2) {
    	
	str1 = str+str2;
	return "";
    }

}

