package nondeveloper;

public class DevelopersSideTool {

	NetworkAPI networkinterface;
	AbstractionForNetwork abstractionextension;

	public String ConnectionEstablished()
	{
		return networkinterface.CreateConnection();
	
	}
	public String changesByToolRecieved()
	{
		return abstractionextension.fetchAbstraction_fromTool();
	}
	public String changesByReviewerRecieved(String str)
	{
		return abstractionextension.generateReviewerAbstraction();
	}

}
