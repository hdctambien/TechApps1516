package spacegame.map.components;

import spacegame.map.Entity;
import spacegame.map.GameMap;

public abstract class ReferenceComponent extends Component {

	public ReferenceComponent() {
	}

	public ReferenceComponent(Entity entity) {
		super(entity);
	}

	public abstract void createReferences(GameMap map);
	public abstract void sync(Component c);
	public abstract Component clone(Entity entity);
	public abstract boolean hasVariable(String varname);
	public abstract String getVariable(String varname);
	public abstract boolean setVariable(String varname, String value);

}
