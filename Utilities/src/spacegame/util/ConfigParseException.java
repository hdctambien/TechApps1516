package spacegame.util;

import java.io.IOException;

public class ConfigParseException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public ConfigParseException(){
		super();
	}
	public ConfigParseException(String message){
		super(message);
	}
	public ConfigParseException(Exception inner){
		super(inner);
	}
	public ConfigParseException(String message, Exception inner){
		super(message,inner);
	}
	
}
