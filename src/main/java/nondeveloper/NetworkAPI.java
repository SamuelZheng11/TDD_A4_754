package nondeveloper;

import github.User;

public interface NetworkAPI {
	//requirement 11
		String CreateConnection();
	//requirement 12
		String autoReviewSent(User nonDeveloper, String str1);
		String autoReviewRecieved();
	//requirement 13
		String nonDevReviewSent(User developer, String str2);
		String nonDevReviewRecieved();

}
