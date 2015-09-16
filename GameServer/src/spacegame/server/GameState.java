package spacegame.server;

import java.util.ArrayList;

/**
 * This class hold Game-specific variables and contains functionality to get and set those variables
 * @author Caleb Wilson
 */
public class GameState {

	public static final String BUTTON_STATUS = "buttonStatus";
	public static final String ROCKET_VELOCITY = "rocketVelocity";
	public static final String ROCKET_HEADING = "rocketHeading";
	public static final String ROCKET_POS_X = "rocketPosX";
	public static final String ROCKET_POS_Y = "rocketPosY";
	public static final String HAS_LINK = "hasLink";
	public static final String THROTTLE = "throttle";
	
	private volatile boolean buttonStatus;
	private volatile boolean hasLink;
	private volatile double rocketVelocity; // m/s
	private volatile double rocketHeading;  //degrees (-180 to 180)
	private volatile double rocketPosX;     //m
	private volatile double rocketPosY;     //m 
	private volatile double throttle;       //%
	
	private ArrayList<GameStateListener> listeners;
	
	public GameState(){
		buttonStatus = false;
		hasLink = true;
		rocketVelocity = 0.0;
		rocketHeading = 0.0;
		rocketPosX = 0.0;
		rocketPosY = 0.0;
		throttle = 0.0;
		listeners = new ArrayList();
	}
	public void addGameStateListener(GameStateListener listener){
		listeners.add(listener);
	}
	public boolean removeGameStateListener(GameStateListener listener){
		return listeners.remove(listener);
	}
	
	public synchronized boolean getButtonStatus(){
		return buttonStatus;
	}
	public synchronized void setButtonStatus(boolean status){
		buttonStatus = status;
	}
	public synchronized boolean getHasLink(){
		return buttonStatus;
	}
	public synchronized void setHasLink(boolean link){
		hasLink = link;
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
	public synchronized double getRocketPosX(){
		return rocketPosX;
	}
	public synchronized void setRocketPosX(double position){
		rocketPosX = position;
	}
	public synchronized double getRocketPosY(){
		return rocketPosY;
	}
	public synchronized void setRocketPosY(double position){
		rocketPosY = position;
	}
	public synchronized double getThrottle(){
		return throttle;
	}
	public synchronized void setThrottle(double throttle){
		this.throttle = throttle;
	}
	public synchronized void doGet(String var, ClientInfo info, Request r){
		switch(var){
			case BUTTON_STATUS:
				info.sendMessage("set buttonStatus "+buttonStatus);
				break;
			case HAS_LINK:
				info.sendMessage("set hasLink "+hasLink);
				break;
			case ROCKET_VELOCITY:
				info.sendMessage("set rocketVelocity "+rocketVelocity);
				break;
			case ROCKET_HEADING:
				info.sendMessage("set rocketHeading "+rocketHeading);
				break;
			case ROCKET_POS_X:
				info.sendMessage("set rocketPosX "+rocketPosX);
				break;
			case ROCKET_POS_Y:
				info.sendMessage("set rocketPosY "+rocketPosY);
				break;
			case THROTTLE:
				info.sendMessage("set throttle "+throttle);
				break;
			default:
				r.reply("UNK");
				break;
		}
	}
	public synchronized void doSet(String var, String val, ClientInfo info, Request r){
		switch(var){
			case BUTTON_STATUS: case HAS_LINK:
				if(tryBooleanParse(var,val)){
					r.reply("OK");
					fireGameStateEvent(new GameStateEvent(var, val));
				}else{
					r.reply("ERR "+SpacegameNetworkProtocol.ERR_PARSE_VAL);
				}
				break;
			case ROCKET_VELOCITY: case ROCKET_HEADING: case ROCKET_POS_X: case ROCKET_POS_Y: case THROTTLE:
				if(tryDoubleParse(var,val)){
					r.reply("OK");
					fireGameStateEvent(new GameStateEvent(var, val));
				}else{
					r.reply("ERR "+SpacegameNetworkProtocol.ERR_PARSE_VAL);
				}
				break;
			default:
				r.reply("UNK");
				break;
		}
	}
	private void fireGameStateEvent(GameStateEvent e){
		if(!listeners.isEmpty()){
			for(GameStateListener listener: listeners){
				listener.stateUpdated(e);
			}
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
			case HAS_LINK:
				hasLink = temp;
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
				case ROCKET_POS_X:
					rocketPosX = d;
					break;
				case ROCKET_POS_Y:
					rocketPosY = d;
					break;
				case THROTTLE:
					throttle = d;
					break;
			}
		}catch(NumberFormatException e){
			success = false;
		}		
		return success;
	}
}