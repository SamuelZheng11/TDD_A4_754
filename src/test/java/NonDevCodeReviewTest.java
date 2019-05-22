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

	//Requirement 11
	
	@Test
	public void test_establish_network_connection_success() {
		//given
		String MessageOnConnection="Network Connection Successful";
		Mockito.when(Network_Interface.Connect()).thenReturn(MessageOnConnection);
		
		//when
		Mockito.when(Developer_Side_Tool).ConnectionEstablished().thenReturn("Network Connection Successful");
		String checkMessage = Developer_Side_Tool.ConnectionEstablished();
		
		//then
		assertEquals(MessageOnConnection,checkMessage);
				
	}

}
