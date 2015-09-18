package spacegame.server;

/**
 * Represents a change in the GameState
 * @author Caleb Wilson
 */
public class GameStateEvent {

	private String varName;	
	private String strValue;
	
	/**
	 * Creates a new GameStateEvent containing information about what variable changed and how
	 * @param name the name of the variable
	 * @param value the value that the variable was changed to (as a String)
	 */
	public GameStateEvent(String name, String value){
		varName = name;
		strValue = value;
	}
	
	/**
	 * Returns the name of the variable that was changed in the GameState
	 * @return the name of the GameState variable
	 */
	public String getVarName(){
		return varName;
	}
	
	/**
	 * Returns the String representation of what the variable value was changed to in the GameState
	 * @return the value of the GameState variable
	 */
	public String getVarStrValue(){
		return strValue;
	}
}
