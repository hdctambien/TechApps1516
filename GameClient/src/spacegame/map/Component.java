package spacegame.map;

public abstract class Component {

	public abstract void update();
	public abstract void sync(Component c);
	public abstract Component clone();
	
}
