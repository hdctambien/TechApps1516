package spacegame.server.chat;

import java.util.Calendar;

public class ChatMessage {

	private String name;
	private String job;
	private String message;
	private int chatterUID;
	private Calendar timestamp;
	//TODO: add view permissions
	
	public ChatMessage(String name, String job, int clientUID, String msg){
		timestamp = Calendar.getInstance();
		this.name = name;
		this.job = job;
		chatterUID = clientUID;
		message = msg;
	}
	
	public String getName(){
		return name;
	}
	
	public String getJob(){
		return job;
	}
	
	public int getChatterUID(){
		return chatterUID;
	}
	
	public String getMessage(){
		return message;
	}
	
	public String getTimeString(){
		int hour = timestamp.get(Calendar.HOUR);
		int mins = timestamp.get(Calendar.MINUTE);
		int secs = timestamp.get(Calendar.SECOND);
		int ampm = timestamp.get(Calendar.AM_PM);
		return Integer.toString(ampm==Calendar.AM?hour:hour+12)+(mins<10?"h:0":"h:")+Integer.toString(mins)
				+(secs<10?"m:0":"m:")+Integer.toString(secs)+"s";
	}
	
	public String toString(){
		return "["+job+":"+name+":"+chatterUID+" @ "+getTimeString()+"] #"+message;
	}
	
}
