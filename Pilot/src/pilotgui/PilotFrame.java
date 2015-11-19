package pilotgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;

import mapgui.MapComponent;
import mapgui.MapViewPanel;
import spacegame.client.ClientUpdater;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatListener;
import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.server.chat.ChatMessage;
import spacegame.server.chat.Chats;
import spacegame.util.gui.HeadingDial;


public class PilotFrame{
	public JFrame frame;
	public JPanel buttons;
	public MapComponent map;
	public MapViewPanel mapView;
	public Chats chat;
	public GameMap game;
	public ChatPanel chatp;
	public static final String SHIP_NAME = "Ship.1";
	public static final String ROTATE_LEFT = "rotate left", ROTATE_RIGHT = "rotate right", THROTTLE_FORWARD = "throttle forward", THROTTLE_BACKWARD = "throttle backward";
	public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	public JLabel obj1;
	public HeadingDial hd;
	public InputMap input;
	public ActionMap action;
	final PilotGuiClient pilot;
	public PilotFrame(GameMap g)
	{
		pilot = new PilotGuiClient();
		game = pilot.setup(SHIP_NAME);
		
		input = new InputMap();
		action = new ActionMap();
		
		obj1 = new JLabel();
		
		
		buttons = new JPanel();
		
		chatp = new ChatPanel(250,250);
		ChatProtocol cProtocol = new ChatProtocol(pilot.getClient(),chatp);
		chatp.addChatListener(cProtocol);
		pilot.addProtocol(cProtocol);
		
		JButton fuel = new JButton("Max Fuel");
		fuel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				pilot.getUpdater().addUserAction(SHIP_NAME,"fuel",game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("maxFuel"),pilot.getClient());
			}
		});
		JButton up = new JButton("Throttle inc");
		up.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
				pilot.getUpdater().addUserAction(SHIP_NAME,"fuel",game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("maxFuel"),pilot.getClient());
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				t += 10;
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
                System.out.println("Throttle: "+t);
                mapRepaint();
            }
		});
		
		JButton down = new JButton("Throttle dec");
		down.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				t -= 10;
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
                System.out.println("Throttle: "+t);
                mapRepaint();
            }
		});
		
		JButton left = new JButton("LEFT");
		left.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h -= Math.PI/12;
		//		game.getEntityByName(SHIP_NAME).getComponent("Heading").setVariable("heading",Double.toString(h));
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                System.out.println("You went left");
                mapRepaint();
            }
		});
		JButton right = new JButton("RIGHT");
		right.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h += Math.PI/12;
			//	game.getEntityByName(SHIP_NAME).getComponent("Heading").setVariable("heading",Double.toString(h));
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                System.out.println("You went right");
                mapRepaint();
            }
		});
		
		buttons.add(left);
		buttons.add(up);
		buttons.add(down);
		buttons.add(right);
	
		
		hd = new HeadingDial();
		hd.setRadius(100);
		mapView = new MapViewPanel(game,SHIP_NAME);
		frame = new JFrame("Pilot");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1440,860);
	//	frame.add(map,BorderLayout.CENTER);
		frame.add(mapView,BorderLayout.CENTER);
		frame.add(buttons,BorderLayout.SOUTH);
		JPanel utilities = new JPanel();
		utilities.setLayout(new BoxLayout(utilities, BoxLayout.Y_AXIS));
		utilities.add(hd);
	//	utilities.add(chatp);
		utilities.add(fuel);
		
		frame.add(utilities,BorderLayout.EAST);
		frame.add(chatp,BorderLayout.WEST);
	//	frame.add(hd,BorderLayout.EAST);
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("LEFT"),ROTATE_LEFT);
		obj1.getActionMap().put(ROTATE_LEFT, new RotateAction("left"));
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"),ROTATE_RIGHT);
		obj1.getActionMap().put(ROTATE_RIGHT, new RotateAction("right"));
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"),THROTTLE_FORWARD);
		obj1.getActionMap().put(THROTTLE_FORWARD, new ThrottleAction("forward"));
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"),THROTTLE_BACKWARD);
		obj1.getActionMap().put(THROTTLE_BACKWARD, new ThrottleAction("backward"));
		
		frame.add(obj1,BorderLayout.NORTH);
		mapView.initStars();
		run();
		
	}
	public void run()
	{
		while(true)
		{
		/*	try{
				Thread.sleep(10);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}*/
			hd.setHeading(-Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
			mapRepaint();
			frame.revalidate();
		}
		
	}
	public void mapRepaint()
	{
		hd.repaint();
		mapView.repaint();
	}
	public void sendChat(String message)
	{
		ChatMessage chatM = new ChatMessage("Drew","Pilot",4,message);
		chat.addChat(chatM);
	}
	public void setX(int x)
	{
		game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posX",Integer.toString(x));
	}
	public void setY(int y)
	{
		game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posY",Integer.toString(y));
	}
	public void setHeading(Double h)
	{
		game.getEntityByName("Heading").getComponent("HeadingComponent").setVariable("heading",Double.toString(h));
	}
	public class RotateAction extends AbstractAction
	{
		String direction;
		RotateAction(String d)
		{
			direction = d;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(direction.compareTo("left") == 0)
			{
				System.out.println("Rotate Left");
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h -= Math.PI/12;
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                mapRepaint();
			}
			if(direction.compareTo("right") == 0)
			{
				System.out.println("Rotate Right");
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h += Math.PI/12;
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                mapRepaint();
			}
		}
		
	}
	public class ThrottleAction extends AbstractAction
	{
		String direction;
		ThrottleAction(String d)
		{
			direction = d;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(direction.compareTo("forward") == 0)
			{
				pilot.getUpdater().addUserAction(SHIP_NAME,"fuel",game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("maxFuel"),pilot.getClient());
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				System.out.println("Throttle: "+t);
				t += 10;
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
                mapRepaint();
			}
			if(direction.compareTo("backward") == 0)
			{
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				System.out.println("Throttle: "+t);
				t -= 10;
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
                mapRepaint();
			}
		}
		
	}

}
