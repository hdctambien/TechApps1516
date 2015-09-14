import java.util.Scanner;
import spacegame.client.*;

public class PilotClient {
	public static final int PORT = 8080;
	public static void setup()
	{
		Scanner input = new Scanner( System.in );
		System.out.println("What is the IP?");
		String ipadress = input.nextLine();
		
		try{
			Client client = new Client(ipaddress, PORT);
		}catch(IOException e){
			
		}
	}
}
