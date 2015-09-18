package spacegame.server;

/**
 * An interface that listens for GameState variable updates
 * @author Caleb Wilson
 */
public interface GameStateListener {

	/**
	 * Called when a GameState variable was updated. The GameStateEvent contains the variable name and the variable value that
	 * it was changed to.
	 * @param e The GameStateEvent representing what variable changed
	 */
	public void stateUpdated(GameStateEvent e);
	
}
