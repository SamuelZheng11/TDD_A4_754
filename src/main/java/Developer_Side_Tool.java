
public class Developer_Side_Tool {
	//for module 4 
	Network_Interface networkinterface;
	IAbstractionExtension abstractionextension;
	public String ConnectionEstablished()
	{
		return networkinterface.CreateConnection();
	}
	public String ReviewResults()
	{
			return abstractionextension.generateCodeAbstraction();
	}
	
}
