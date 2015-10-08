package spacegame.map;

public class RenderComponent extends Component {

	String iPath;
	
	public RenderComponent(String imagePath){
		iPath = imagePath;
	}

	public String getImagePath(){
		return iPath;
	}

	@Override
	public String serialize() {
		return "imagePath: "+iPath;
	}

	@Override
	public void sync(Component c) {
		// iPath shouldn't change
	}

	@Override
	public Component clone(Entity entity) {
		return new RenderComponent(iPath);
	}

	@Override
	public boolean hasVariable(String varname) {
		return varname.equals("imagePath");
	}

	@Override
	public String getVariable(String varname) {
		if(varname.equals("imagePath")){
			return iPath;
		}else{
			return null;
		}
	}

	@Override
	public boolean setVariable(String varname, String value) {
		if(varname.equals("imagePath")){//Please only use this for entity deserialization!!!
			iPath = value;
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public void createReferences() {
	}
}
