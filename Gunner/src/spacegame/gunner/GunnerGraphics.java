package spacegame.gunner;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;

import mapgui.GunnerViewPanel;
import spacegame.client.BasicProtocol;
import spacegame.client.Client;
import spacegame.client.ClientUpdater;
import spacegame.client.MapUpdateProtocol;
import spacegame.client.ProtocolAggregator;
import spacegame.client.SerialProtocol;
import spacegame.map.GameMap;

public class GunnerGraphics
{
	public final String SHIP_NAME;
	Client c;
	private static Thread clientThread;
	private static Thread protocolThread;
	private static Thread updaterThread;
	static ProtocolAggregator aggregator;
	private static GameMap map;
	private static ClientUpdater clientUpdater;
	
	private JFrame gunFrame;
	private JPanel panel, gunPanel;
	private GunnerViewPanel gunnerView;
	private JSlider gunPow;
	private Border border;
	
	boolean shoot = false;

	public GunnerGraphics(String iAddress, int port, String name)
	{
		SHIP_NAME = name;
		try {
			c = new Client(iAddress,port);
			BasicProtocol basic = new BasicProtocol(c);
			SerialProtocol serial = new SerialProtocol(c);
			aggregator = new ProtocolAggregator(c);
			aggregator.addProtocol(basic);
			aggregator.addProtocol(serial);
			
			clientThread = new Thread(c);
			clientThread.start();
			protocolThread = new Thread(aggregator);
			protocolThread.start();
			
			c.sendMessage("set name " + name);
			c.sendMessage("set job GroundControl");
			c.sendMessage("set ship " + SHIP_NAME);
			
			c.sendMessage("get map");
			System.out.println("Getting Map...");
			while(!serial.hasSerial()){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("Building Map...");
			map = serial.getMapFromSerial();
			System.out.println("Map obtained!");
			
			clientUpdater = new ClientUpdater(map);
			updaterThread = new Thread(clientUpdater);
			updaterThread.start();
			MapUpdateProtocol update = new MapUpdateProtocol(c, clientUpdater, SHIP_NAME, serial);
			aggregator.addProtocol(update);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GunnerWindow();
		
		run();
	}
	
	public void GunnerWindow()
	{
		border = BorderFactory.createEmptyBorder(10, 10, 0, 10);
		gunFrame = new JFrame("Gunner");
		gunFrame.setLayout(new BorderLayout());
		gunFrame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
			    c.sendMessage("exit");
			}
		});				
		panel = new JPanel();		
		gunFrame.add(panel, BorderLayout.CENTER);
		
		gunnerView = new GunnerViewPanel(SHIP_NAME, clientUpdater, c);
		panel.setLayout(new BorderLayout());
		panel.add(gunnerView, BorderLayout.CENTER);
		panel.setVisible(true);
		
		gunPanel = new JPanel();
		panel.add(gunPanel, BorderLayout.SOUTH);
		gunPanel.setLayout(new GridLayout(2, 2));
		
		gunPow = new JSlider();
		gunPow.setEnabled(false);
		gunPow.setVisible(true);
		gunPanel.add(gunPow);
		gunPanel.setBorder(border);	
		
		gunFrame.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) 
			{
	//			clientUpdater.addUserAction(SHIP_NAME, "shoot", "true", c);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				clientUpdater.addUserAction(SHIP_NAME, "shoot", "true", c);
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				clientUpdater.addUserAction(SHIP_NAME, "shoot", "false", c);
			}
			
		});
		
		gunFrame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		gunFrame.setUndecorated(true);
		gunFrame.pack();
		gunFrame.setVisible(true);
		gunFrame.requestFocus();
		
	}
	
	private void run()
	{
		while(true)
		{
			gunPow.setValue(Integer.parseInt(map.getEntityByName(SHIP_NAME).getComponent("Power").getVariable("powerGuns")));
			if(!(clientUpdater.isDirty() || clientUpdater.isRenderLocked()) && clientUpdater.isDrawDirty())
			{
				continue;
			}
			else
			{
				clientUpdater.setRenderLock(true); 
				panel.repaint();
				clientUpdater.setRenderLock(false);
				clientUpdater.setDrawDirty(false);
			}
		}
	}
}
