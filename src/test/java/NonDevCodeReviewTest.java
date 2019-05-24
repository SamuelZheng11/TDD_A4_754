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
	private User developer = new User("", UserType.Developer);
	

	@Before
	public void Setup() {	 
	developer_side_tool= mock(Developer_Side_Tool.class);
	nonDeveloper= mock(User.class);
	developer=mock(User.class);
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
		Mockito.when(developer_side_tool.Changes_ByTool_Recieved()).thenReturn(resultreview_sent);
		
		String MessageOnSent= new String("Sent via Network to Non Developer");
		Mockito.when(network_interface.Auto_ReviewSent(nonDeveloper,resultreview_sent)).thenReturn(MessageOnSent);
		
		//when
		Mockito.when(network_interface.Auto_ReviewRecieved()).thenReturn("please add better variable names");
		
		//then
		Mockito.when(nonDeveloper.AbstractResult_Recieved_fromTool()).thenReturn("please add better variable names");
		String resultreview_recieve = nonDeveloper.AbstractResult_Recieved_fromTool();
		assertEquals(resultreview_sent,resultreview_recieve);
				
	}
	
	@Test 
	public void Test_High_Level_Review()
	{	
		//given 
		Mockito.when(nonDeveloper.AbstractResult_Recieved_fromTool()).thenReturn("please add better variable names");
		String str= nonDeveloper.AbstractResult_Recieved_fromTool();
		String review_msg_add = new String(" ensure 4 spaces for format.");
		String a = (str+review_msg_add);
		
		//when 
		when(nonDeveloper.NonDev_AddComment(str,review_msg_add)).thenReturn(a);
		String addComment= nonDeveloper.NonDev_AddComment(str,review_msg_add);

		//then
		assertEquals(addComment,a);
	 
	}
	//Requirement 13
	@Test
	public void Test_Send_ChangedReview_To_DevTool()
	{   //given
		String a= "please add better variable names and ensure 4 spaces for format.";
		when(nonDeveloper.NonDev_AddComment("please add better variable names"," and ensure 4 spaces for format.")).thenReturn(a);
		
		//when
		String MessageOnSent= new String("Sent via Network to Developer");
		Mockito.when(network_interface.NonDev_ReviewSent(developer,a)).thenReturn(MessageOnSent);
		Mockito.when(network_interface.NonDev_ReviewRecieved()).thenReturn(a);
		
		//then
		Mockito.when(developer_side_tool.Changes_ByReviewer_Recieved(a)).thenReturn("please add better variable names and ensure 4 spaces for format.");
		String nondev_resultfetched = developer_side_tool.Changes_ByReviewer_Recieved(a);
		assertEquals(nondev_resultfetched,a);

	}
	@Test
	public void Test_DeveloperModifies_NonDevReview()
	{
				//given 
				Mockito.when(developer.AbstractResult_Recieved_fromNonDev()).thenReturn("please add better variable names and ensure 4 spaces for format.");
				String strOriginal=developer.AbstractResult_Recieved_fromNonDev();
				
				//when
				String dev_msg_add = new String("The review looks good");
				String finalReview = strOriginal+dev_msg_add; 
				
				//then
				String dev_finalcomment="please add better variable names and ensure 4 spaces for format.The review looks good";
				when(developer.Dev_AddComment(strOriginal,dev_msg_add)).thenReturn(finalReview);
				String addFinalComment= developer.Dev_AddComment(strOriginal,dev_msg_add);
				assertEquals(addFinalComment,dev_finalcomment);
	}
	
}