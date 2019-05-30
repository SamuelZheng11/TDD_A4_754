package nondeveloper;

public class DevelopersSideTool {

	NetworkAPI networkinterface;
	AbstractionForNetwork abstractionextension;

	public String ConnectionEstablished()
	{
		return networkinterface.CreateConnection();
	
	}
	public String Changes_ByTool_Recieved()
	{
		return abstractionextension.fetchAbstraction_fromTool();
	}
	public String Changes_ByReviewer_Recieved(String str)
	{
		return abstractionextension.generateReviewerAbstraction();
	}

}
