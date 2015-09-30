package spacegame.map;

public class SerialException extends RuntimeException{
	
	public SerialException(){super();}
	public SerialException(String message){super(message);}
	public SerialException(String message, Exception inner){super(message,inner);}
	public SerialException(Exception inner){super(inner);}
}
