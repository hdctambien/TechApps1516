package spacegame.client;

public class ServerTime {

	private final long serverEpoch;
	private final long clientEpoch;
	
	public ServerTime(long epoch, long client) {
		clientEpoch = client;
		serverEpoch = epoch;
	}
	public ServerTime(long epoch){
		clientEpoch = System.nanoTime();
		serverEpoch = epoch;
	}
	
	public long getServerEpoch(){
		return serverEpoch;
	}
	
	public long getClientEpoch(){
		return clientEpoch;
	}
	
	public long getClientTime(long serverTime){
		long diff = serverTime-serverEpoch;
		return clientEpoch+diff;
	}
	public long getServerTime(long clientTime){
		long diff = clientTime-clientEpoch;
		return serverEpoch+diff;
	}
	public long getTimeElapsed(long serverTime){//time to fast-forward by
		return System.nanoTime()-getClientTime(serverTime);
	}

}
