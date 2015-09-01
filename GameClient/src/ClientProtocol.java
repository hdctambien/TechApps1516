import java.io.PrintWriter;


public interface ClientProtocol {

	
	public void process(String command);
	/**
	 * This method is guaranteed to be called before the process(String command) method by any Client.
	 * Use it to obtain the Output PrintWriter to the server.
	**/
	public void setOuput(PrintWriter writer);
	
}
