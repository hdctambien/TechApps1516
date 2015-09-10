package spacegame.server;

public class GameState {

	public static final String BUTTON_STATUS = "buttonStatus";
	
	private boolean buttonStatus;
	
	public GameState(){
		buttonStatus = false;
		
	}
	
	public boolean getButtonStatus(){
		return buttonStatus;
	}
	public void setButtonStatus(boolean status){
		buttonStatus = status;
	}
	
	public void doGet(String var, ClientInfo info, Request r){
		switch(var){
			case BUTTON_STATUS:
				info.sendMessage("set buttonStatus "+buttonStatus);
				break;
			default:
				r.reply("UNK");
				break;
		}
	}
	public void doSet(String var, String val, ClientInfo info, Request r){
		switch(var){
		case BUTTON_STATUS:
			if(tryBooleanParse(var,val)){
				r.reply("OK");
			}else{
				r.reply("ERR "+SpacegameNetworkProtocol.ERR_PARSE_VAL);
			}
			break;
		default:
			r.reply("UNK");
			break;
		}
	}
	public boolean tryBooleanParse(String var, String val){
		boolean temp = Boolean.parseBoolean(val);
		if(!temp&&!val.toLowerCase().equals("false")){
			return false;
		}
		switch(var){
			case BUTTON_STATUS:
				buttonStatus = temp;
				break;
		}
		return true;
	}
}
