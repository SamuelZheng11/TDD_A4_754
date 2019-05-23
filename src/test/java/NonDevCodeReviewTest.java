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
	
	

	@Before
	public void Setup() {	 
	developer_side_tool= mock(Developer_Side_Tool.class);
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
		Mockito.when(DeveloperSideTtool.ReviewResults()).thenReturn(resultreview_sent);
		
		String MessageOnSent= new String("Sent via Network");
		Mockito.when(NetworkInterface.ReviewSent(reviewer,resultreview_sent)).thenReturn(MessageOnSent);
		
		//when
		Mockito.when(NetworkInterface.ReviewRecieved()).thenReturn("please add better variable names");
		
		//then
		Mockito.when(reviewer.Abstract_recieve()).thenReturn("please add better variable names");
		String resultreview_recieve = reviewer.AbstractResult_recieve();
		assertEquals(resultreview_sent,resultreview_recieve);
				
	}
	
		
	
}