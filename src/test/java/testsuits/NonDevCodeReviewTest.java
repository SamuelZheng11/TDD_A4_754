package testsuits;

import github.User;
import github.UserType;
import nondeveloper.DevelopersSideTool;
import nondeveloper.NetworkAPI;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import org.junit.Before;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;


import static org.junit.Assert.assertTrue;

public class NonDevCodeReviewTest {
	
	private DevelopersSideTool developerSideTool = new DevelopersSideTool();
	private NetworkAPI networkInterface;
	private User nonDeveloper = new User("", UserType.NonDeveloper);
	private User developer = new User("", UserType.NonDeveloper);	


	@Before
	public void Setup() {
	developerSideTool = mock(DevelopersSideTool.class);
	networkInterface =mock(NetworkAPI.class);
	nonDeveloper= mock(User.class);
    developer= mock(User.class);


	}
	//Requirement 11
	@Test
	public void ShouldEstablishNetworkConnectionSuccess() {
		
		//given
		String MessageOnConnection = "Network Connection Successful";
		Mockito.when(networkInterface.CreateConnection()).thenReturn(MessageOnConnection);
		
		//when
		Mockito.when(developerSideTool.ConnectionEstablished()).thenReturn("Network Connection Successful");
		String checkMessage = developerSideTool.ConnectionEstablished();
		
		//then
		assertEquals(MessageOnConnection, checkMessage);
				
	}
	@Test
	public void ShouldEstablishNetworkConnectionFail() {
		
		//given
		String MessageOnConnection="Network Connection error";
		Mockito.when(networkInterface.CreateConnection()).thenReturn(MessageOnConnection);
		
		//when
		Mockito.when(developerSideTool.ConnectionEstablished()).thenReturn("Network Connection error");
		String checkMessage = developerSideTool.ConnectionEstablished();
		
		//then
		assertEquals(MessageOnConnection, checkMessage);
				
	}
	@Test
	public void ShouldGetInitialReviewViaNetwork()
	{
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            		
		//given 
		String resultReviewSent = new String("please add better variable names");
		Mockito.when(developerSideTool.changesByToolRecieved()).thenReturn(resultReviewSent);
		
		String MessageOnSent= new String("Sent via Network to Non Developer");
		Mockito.when(networkInterface.autoReviewSent(nonDeveloper,resultReviewSent)).thenReturn(MessageOnSent);
		
		//when
		Mockito.when(networkInterface.autoReviewRecieved()).thenReturn(resultReviewSent);
		
		//then
		Mockito.when(nonDeveloper.abstractResultRecievedFromTool()).thenReturn("please add better variable names");
		String resultReviewRecieved = nonDeveloper.abstractResultRecievedFromTool();
		assertEquals(resultReviewSent, resultReviewRecieved);
				
	}
	//Requirement 12
		@Test 
		public void ShouldPerformHighLevelReviewByNonDev()
		{	
			//given 
			Mockito.when(nonDeveloper.abstractResultRecievedFromTool()).thenReturn("please add better variable names");
			String str = nonDeveloper.abstractResultRecievedFromTool();
			String reviewMsgAdd = new String(" ensure 4 spaces for format.");
			String a = (str+reviewMsgAdd);
			
			//when 
			when(nonDeveloper.nonDevAddComment(str,reviewMsgAdd)).thenReturn(a);
			String addComment = nonDeveloper.nonDevAddComment(str,reviewMsgAdd);

			//then
			assertEquals(addComment, a);
		}
		//Requirement 13
		@Test
		public void ShouldSendChangedReviewToDevTool()
		{   //given
			String a= "please add better variable names and ensure 4 spaces for format.";
			when(nonDeveloper.nonDevAddComment("please add better variable names"," and ensure 4 spaces for format.")).thenReturn(a);
			
			//when
			String MessageOnSent= new String("Sent via Network to Developer");
			Mockito.when(networkInterface.nonDevReviewSent(developer,a)).thenReturn(MessageOnSent);
			Mockito.when(networkInterface.nonDevReviewRecieved()).thenReturn(a);
			
			//then
			Mockito.when(developerSideTool.changesByReviewerRecieved(a)).thenReturn("please add better variable names and ensure 4 spaces for format.");
			String nonDevResultFetched = developerSideTool.changesByReviewerRecieved(a);
			assertEquals(nonDevResultFetched, a);

		}
		@Test
		public void ShouldAllowDeveloperToModifyNonDevReview()
		{
					//given 
					Mockito.when(developer.abstractResultRecievedFromNonDev()).thenReturn("please add better variable names and ensure 4 spaces for format.");
					String strOriginal = developer.abstractResultRecievedFromNonDev();
					
					//when
					String devMsgAdd = new String("The review looks good");
					String finalReview = strOriginal + devMsgAdd;
					
					//then
					String devFinalComment = "please add better variable names and ensure 4 spaces for format.The review looks good";
					when(developer.devAddComment(strOriginal,devMsgAdd)).thenReturn(finalReview);
					String addFinalComment = developer.devAddComment(strOriginal,devMsgAdd);
					assertEquals(addFinalComment,devFinalComment);
		}
		@Test
	    public void ShouldBeApprovedByDev()
	    {
			//given
			Mockito.when(developer.abstractResultFinalChangeFromDev()).thenReturn("please add better variable names and ensure 4 spaces for format.The review looks good");
			String finalReview= developer.abstractResultFinalChangeFromDev();
			//when
			String verdict="yes";
			Mockito.when(developer.FinalVeredict(finalReview,verdict)).thenReturn("approved");
			String devFinalReview = developer.FinalVeredict(finalReview,verdict);
			//then
			assertEquals(devFinalReview,"approved");
	    }

		
}