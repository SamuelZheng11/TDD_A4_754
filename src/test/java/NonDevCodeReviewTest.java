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
	
	private Developers_Side_Tool developer_side_tool= new Developers_Side_Tool();
	private Network_API network_interface;
	private User nonDeveloper = new User("", UserType.NonDeveloper);	


	@Before
	public void Setup() {	 
	developer_side_tool= mock(Developers_Side_Tool.class);
	network_interface=mock(Network_API.class);
	nonDeveloper= mock(User.class);

	}
	//Requirement 11
	@Test
	public void Should_establish_network_connection_success() {		
		
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
	public void Should_establish_network_connection_fail() {				
		
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
	public void Should_Get_InitialReview_viaNetwork()
	{
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            		
		//given 
		String resultreview_sent = new String("please add better variable names");
		Mockito.when(developer_side_tool.Changes_ByTool_Recieved()).thenReturn(resultreview_sent);
		
		String MessageOnSent= new String("Sent via Network to Non Developer");
		Mockito.when(network_interface.Auto_ReviewSent(nonDeveloper,resultreview_sent)).thenReturn(MessageOnSent);
		
		//when
		Mockito.when(network_interface.Auto_ReviewRecieved()).thenReturn(resultreview_sent);
		
		//then
		Mockito.when(nonDeveloper.AbstractResult_Recieved_fromTool()).thenReturn("please add better variable names");
		String resultreview_recieve = nonDeveloper.AbstractResult_Recieved_fromTool();
		assertEquals(resultreview_sent,resultreview_recieve);
				
	}
	//Requirement 12
		@Test 
		public void Should_Perform_HighLevel_Review_ByNonDev()
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
	
}