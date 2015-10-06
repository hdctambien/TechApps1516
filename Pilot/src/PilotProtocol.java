import spacegame.client.*;

public class PilotProtocol extends AbstractProtocol {

	public PilotProtocol(Client client) {
		super(client);
	}

	@Override
	public void process(String message) {
		String sub = message.substring(0,7);
		if(sub.compareTo("moveShip")==0)
		{
			//convert to doubles
			String xstring = message.substring(9,12);
			int x = Integer.parseInt(xstring);
			String ystring = message.substring(14,17);
			int y = Integer.parseInt(ystring);
			ShipPanel.moveShip(x,y);
		}
	}

}
