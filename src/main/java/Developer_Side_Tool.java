
public class Developer_Side_Tool {
	//for module 4 
	Network_Interface networkinterface;
	IAbstractionExtension abstractionextension;
	public String ConnectionEstablished()
	{
		return networkinterface.CreateConnection();
	}
	
	public String Changes_ByTool_Recieved()
	{
			return abstractionextension.generateCodeAbstraction();
	}
	
	public String Changes_ByReviewer_Recieved(String str)
	{
		return abstractionextension.generateReviewerAbstraction();
	}
	
}
