import spacegame.client.AbstractProtocol;
import spacegame.client.Client;

public class MapProtocol extends AbstractProtocol {

	public MapPanel panel;
	public MapProtocol(Client client) {
		super(client);
		panel = new MapPanel();
	}

	@Override
	public void process(String message) {
		System.out.println("Recieved Message: " + message);
		String[] mess = message.split(" ");
		if(mess[0].compareTo("set") == 0)
		{
			if(mess[1].compareTo("posX") == 0)
			{
				panel.setX((int)Double.parseDouble(mess[2]));
			}
			if(mess[1].compareTo("posY") == 0)
			{
				panel.setY((int)Double.parseDouble(mess[2]));
			}
		}
		
	}

}
