package spacegame.map;

import java.awt.Graphics2D;

public abstract class DrawComponent extends Component {

	public abstract void draw(Graphics2D g2);
	
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
	@Override
	public void sync(Component c) {
		
	}
	
	public void unserialize(String serial){
		//Nothing needs to happen
	}
	
}
