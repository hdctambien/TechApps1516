package spacegame.map.components;

import spacegame.map.Entity;

public class MarkerComponent extends Component {

	@Override
	public String serialize() {
	
		return null;
	}

	@Override
	public void sync(Component c) {
	
		
	}

	@Override
	public Component clone(Entity entity) {
				return null;
	}

	@Override
	public boolean hasVariable(String varname) {
	
		return false;
	}

	@Override
	public String getVariable(String varname) {

		return null;
	}

	@Override
	public boolean setVariable(String varname, String value) {

		return false;
	}
	

}
