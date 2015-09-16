package spacegame.server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StateUpdateHandler implements GameStateListener {

	public static final String ALL = "all";
	
	private HashMap<String,List<ClientInfo>> subscribers;
	
	public StateUpdateHandler(){
		subscribers = new HashMap<String,List<ClientInfo>>();
	}
	
	public synchronized void addSubsriber(String var, ClientInfo client){
		if(subscribers.containsKey(var)){
			List<ClientInfo> list = subscribers.get(var);
			list.add(client);
		}else{
			LinkedList<ClientInfo> list = new LinkedList<ClientInfo>();
			list.add(client);
			subscribers.put(var,list);
		}		
	}
	
	public synchronized boolean removeSubscriber(String var, ClientInfo client){
		if(subscribers.containsKey(var)){
			List<ClientInfo> list = subscribers.get(var);
			return list.remove(client);
		}else{
			return false;
		}
	}
	
	@Override
	public synchronized void stateUpdated(GameStateEvent e) {
		if(subscribers.containsKey(ALL)){
			List<ClientInfo> list = subscribers.get(ALL);
			if(!list.isEmpty()){
				for(ClientInfo client: list){
					client.sendMessage("set "+e.getVarName()+" "+e.getVarStrValue());
				}
			}
		}	
		if(subscribers.containsKey(e.getVarName())){
			List<ClientInfo> list = subscribers.get(e.getVarName());
			if(!list.isEmpty()){
				for(ClientInfo client: list){
					client.sendMessage("set "+e.getVarName()+" "+e.getVarStrValue());
				}
			}
		}
	}
	
}