package spacegame.map;

public abstract class UpdateComponent extends Component {

	public UpdateComponent(Entity entity) {
		super(entity);
	}

	public abstract void update(long timeElapsed);
	
}
