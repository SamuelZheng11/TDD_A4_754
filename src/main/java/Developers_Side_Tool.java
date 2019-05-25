
public class Developers_Side_Tool {

	Network_API networkinterface;
	IAbstractionExtension abstractionextension;

	public String ConnectionEstablished()
	{
		return networkinterface.CreateConnection();
	
	}
	public String Changes_ByTool_Recieved()
	{
		return abstractionextension.generateCodeAbstraction();
	}


}
