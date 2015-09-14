package spacegame.server;

public class GameState {

	public static final String BUTTON_STATUS = "buttonStatus";
	public static final String ROCKET_VELOCITY = "rocketVelocity";
	public static final String ROCKET_HEADING = "rocketHeading";
	public static final String ROCKET_POS = "rocketPos";
	
	private volatile boolean buttonStatus;
	private volatile double rocketVelocity; // m/s
	private volatile double rocketHeading;  // radians
	private volatile double rocketPos;      //Position (m?)
	
	public GameState(){
		buttonStatus = false;
		rocketVelocity = 0.0;
		rocketHeading = Math.PI/2;
		rocketPos = 0.0;
	}
	
	public synchronized boolean getButtonStatus(){
		return buttonStatus;
	}
	public synchronized void setButtonStatus(boolean status){
		buttonStatus = status;
	}
	public synchronized double getRocketVelocity(){
		return rocketVelocity;
	}
	public synchronized void setRocketVelocity(double velocity){
		rocketVelocity = velocity;
	}
	public synchronized double getRocketHeading(){
		return rocketHeading;
	}
	public synchronized void setRocketHeading(double heading){
		rocketHeading = heading;
	}
	public synchronized double getRocketPosition(){
		return rocketPos;
	}
	public synchronized void setRocketPosition(double position){
		rocketPos = position;
	}
	public synchronized void doGet(String var, ClientInfo info, Request r){
		switch(var){
			case BUTTON_STATUS:
				info.sendMessage("set buttonStatus "+buttonStatus);
				break;
			case ROCKET_VELOCITY:
				info.sendMessage("set rocketVelocity "+rocketVelocity);
				break;
			case ROCKET_HEADING:
				info.sendMessage("set rocketHeading "+rocketHeading);
				break;
			case ROCKET_POS:
				info.sendMessage("set rocketPos "+rocketPos);
				break;
			default:
				r.reply("UNK");
				break;
		}
	}
	public synchronized void doSet(String var, String val, ClientInfo info, Request r){
		switch(var){
			case BUTTON_STATUS:
				if(tryBooleanParse(var,val)){
					r.reply("OK");
				}else{
					r.reply("ERR "+SpacegameNetworkProtocol.ERR_PARSE_VAL);
				}
				break;
			case ROCKET_VELOCITY: case ROCKET_HEADING: case ROCKET_POS:
				if(tryDoubleParse(var,val)){
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
	public boolean tryDoubleParse(String var, String val){
		boolean success = true;
		double d = Double.NaN;
		try{
			d = Double.parseDouble(val);
			switch(var){
				case ROCKET_VELOCITY:
					rocketVelocity = d;
					break;
				case ROCKET_HEADING:
					rocketHeading = d;
					break;
				case ROCKET_POS:
					rocketPos = d;
					break;
			}
		}catch(NumberFormatException e){
			success = false;
		}		
		return success;
	}
}