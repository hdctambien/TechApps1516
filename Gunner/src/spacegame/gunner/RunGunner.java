package spacegame.gunner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class RunGunner 
{
	private static GunnerGraphics game;
	public static void main(String[] args)
	{
		String iAddress = "10.11.1.92";
		int port = 8080;
		String name = "Ship.1";
		boolean runGame = true;
		boolean good = false;
		JFrame frame = new JFrame();
		int option = 0;
		
		boolean testing = true;
		
		
		if(testing == false)
		{
			do
			{
				iAddress = JOptionPane.showInputDialog("Enter IP Address");
				System.out.println(iAddress);
				port = Integer.parseInt(JOptionPane.showInputDialog("Enter port (8080 for default)"));
				System.out.println(port);
				name = JOptionPane.showInputDialog("Enter Desired Name");
				System.out.println(name);
				
				boolean temp = false;
				try {
					temp = InetAddress.getByName(iAddress).isReachable(5000);
				} catch (UnknownHostException e) {
					temp = false;
				} catch (IOException e) {
					temp = false;
				}
				good = temp; 
				if(good = true)
					JOptionPane.showMessageDialog(frame,"Connecting to "+iAddress + " on port " + port + " with name "+ name);
				else
					JOptionPane.showMessageDialog(frame,"Connection failed. Retry or close.");
				option = JOptionPane.showConfirmDialog(frame,
						"Connection Failed. Retry?", "Connecting Failed",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
			while(!good && option != 2);
			if(runGame)
			{
				game = new GunnerGraphics(iAddress, port, name);
			}
		}
		else
		{ 
			game = new GunnerGraphics(iAddress, port, name);
		}
	}
}
