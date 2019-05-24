import static org.junit.Assert.*;


import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import java.util.Arrays;
import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.nio.file.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;

public class NonDevCodeReviewTest {
	
	
	private Developer_Side_Tool developer_side_tool= new Developer_Side_Tool();
	private Network_Interface network_interface;
	private User nonDeveloper = new User("", UserType.NonDeveloper);
	private PullRequest pullrequest;
	private GitComment gitcomment1;
	private GitComment gitcomment2;

	

	@Before
	public void Setup() {	 
	developer_side_tool= mock(Developer_Side_Tool.class);
	nonDeveloper= mock(User.class);
	network_interface=mock(Network_Interface.class);
	
	}
	//Requirement 11
	@Test
	public void test_establish_network_connection_success() {
		
				
		
		//given
		String MessageOnConnection="Network Connection Successful";
		Mockito.when(network_interface.CreateConnection()).thenReturn(MessageOnConnection);
		
		//when
		Mockito.when(developer_side_tool.ConnectionEstablished()).thenReturn("Network Connection Successful");
		String checkMessage = developer_side_tool.ConnectionEstablished();
		
		//then
		assertEquals(MessageOnConnection,checkMessage);
				
	}
	@Test
	public void test_establish_network_connection_fail() {
		developer_side_tool= mock(Developer_Side_Tool.class);
		network_interface=mock(Network_Interface.class);
				
		
		//given
		String MessageOnConnection="Network Connection error";
		Mockito.when(network_interface.CreateConnection()).thenReturn(MessageOnConnection);
		
		//when
		Mockito.when(developer_side_tool.ConnectionEstablished()).thenReturn("Network Connection error");
		String checkMessage = developer_side_tool.ConnectionEstablished();
		
		//then
		assertEquals(MessageOnConnection,checkMessage);
				
	}
	@Test
	public void Test_InitialReview_viaNetwork()
	{
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            		
		//given 
		String resultreview_sent = new String("please add better variable names");
		Mockito.when(developer_side_tool.ReviewResults()).thenReturn(resultreview_sent);
		
		String MessageOnSent= new String("Sent via Network to Non Developer");
		Mockito.when(network_interface.Auto_ReviewSent(nonDeveloper,resultreview_sent)).thenReturn(MessageOnSent);
		
		//when
		Mockito.when(network_interface.Auto_ReviewRecieved()).thenReturn("please add better variable names");
		
		//then
		Mockito.when(nonDeveloper.AbstractResult_recieve()).thenReturn("please add better variable names");
		String resultreview_recieve = nonDeveloper.AbstractResult_recieve();
		assertEquals(resultreview_sent,resultreview_recieve);
				
	}
	
	@Test 
	public void Test_High_Level_Review()
	{	
		//given 
		Mockito.when(nonDeveloper.AbstractResult_recieve()).thenReturn("please add better variable names");
		String str= nonDeveloper.AbstractResult_recieve();
		String review_msg_add = new String(" ensure 4 spaces for format");
		String a = (str+review_msg_add);
		
		//when 
		when(nonDeveloper.addComment(str,review_msg_add)).thenReturn(a);
		String addComment= nonDeveloper.addComment(str,review_msg_add);

		//then
		assertEquals(addComment,a);
	 
	}
	@Test
	public void Test_SendReview_To_NonDevTool()
	{   //given
		String a= "please add better variable names ensure 4 spaces for format";
		when(nonDeveloper.addComment("tools abstract test comments","and reviewers comments")).thenReturn(a);
		
		
		//when
		String MessageOnSent= new String("Sent via Network to Developer");
		Mockito.when(network_interface.NonDev_ReviewSent(developer,a)).thenReturn(MessageOnSent);
		Mockito.when(network_interface.NonDev_ReviewRecieved()).thenReturn(a);
		
		//then
		String nondev_resultfetched = developer_side_tool.Changes_ByReviewer_Recieved(a);
		assertEquals(nondev_resultfetched,a);

	}
	
}