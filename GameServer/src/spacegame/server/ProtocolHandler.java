package spacegame.server;

/**
 * This interface represents an object that can handle client Requests given to it by a RequestProcessor
 * @author Caleb Wilson
 */
public interface ProtocolHandler {

	/**
	 * Process the given client Request. See Request class for details on what information a Request object
	 * contains
	 * @param r the Request of the client
	 */
	public void processRequest(Request r);
}
