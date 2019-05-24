
public interface Network_Interface {
	
	String CreateConnection();
	String Auto_ReviewSent(User nonDeveloper, String str1);
	String Auto_ReviewRecieved();
	String NondevReviewSent(PullRequest pullrequest, String review_msg);
	
}
