package spacegame.map;

public abstract class UpdateComponent extends Component {

	public UpdateComponent(Entity entity) {
		super(entity);
	}

	public abstract void update(long timeElapsed);
	
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
}
