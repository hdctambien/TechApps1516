package spacegame.server.chat;

import java.util.Calendar;

/**
 * This class represents a Chat message on the server. 
 * @author Caleb Wilson
 */
public class ChatMessage {

	private String name;
	private String job;
	private String message;
	private int chatterUID;
	private Calendar timestamp;
	//TODO: add view permissions
	
	/**
	 * Create a new Chat message
	 * @param name The name of the chatter
	 * @param job The job of the chatter
	 * @param clientUID the UID of the chatter
	 * @param msg the chat message
	 */
	public ChatMessage(String name, String job, int clientUID, String msg){
		timestamp = Calendar.getInstance();
		this.name = name;
		this.job = job;
		chatterUID = clientUID;
		message = msg;
	}
	
	/**
	 * @return the name of the chatter
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return the job of the chatter
	 */
	public String getJob(){
		return job;
	}
	
	/**
	 * @return the UID of the chatter
	 */
	public int getChatterUID(){
		return chatterUID;
	}
	
	/**
	 * @return the chat message
	 */
	public String getMessage(){
		return message;
	}
	
	/**
	 * This method creates a String representation of the time the chat was created and returns it.
	 * @return the String representation of the timestamp
	 */
	public String getTimeString(){
		int hour = timestamp.get(Calendar.HOUR);
		int mins = timestamp.get(Calendar.MINUTE);
		int secs = timestamp.get(Calendar.SECOND);
		int ampm = timestamp.get(Calendar.AM_PM);
		return Integer.toString(ampm==Calendar.AM?hour:hour+12)+(mins<10?"h:0":"h:")+Integer.toString(mins)
				+(secs<10?"m:0":"m:")+Integer.toString(secs)+"s";
	}
	
	/**
	 * This method returns a String representation of the entire message in the syntax: 
	 * '[job:name:UID @ timestamp] #'
	 *  @return the String representation of this chat
	 */
	public String toString(){
		return "["+job+":"+name+":"+chatterUID+" @ "+getTimeString()+"] #"+message;
	}
	
}
