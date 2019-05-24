
public interface Network_Interface {
	
	String CreateConnection();
	String ReviewSent(User nonDeveloper, String str1);
	String ReviewRecieved();
	String NondevReviewSent(PullRequest pullrequest, String review_msg);
}
