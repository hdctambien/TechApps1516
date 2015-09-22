package spacegame.map;

public class MapAction {

	public static final int ENTITY_ADD = 1;
	public static final int ENTITY_REMOVE = 2;
	
	private int action;
	private int ufid;
	private int mapIndex;
	
	public MapAction(int ufid, int mapindex, int action){
		this.action = action;
		this.ufid = ufid;
		mapIndex = mapindex;
	}
	
	public int getAction(){
		return action;
	}
	
	public int getUFID(){
		return ufid;
	}
	
	public int getMapIndex(){
		return mapIndex;
	}
	
}
