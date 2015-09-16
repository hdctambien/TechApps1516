package spacegame.server;

public class GameStateEvent {

	private String varName;	
	private String strValue;
	public GameStateEvent(String name, String value){
		varName = name;
		strValue = value;
	}
	public String getVarName(){
		return varName;
	}
	public String getVarStrValue(){
		return strValue;
	}
}
