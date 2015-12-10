package pilotgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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
import spacegame.util.gui.HeadingDial;


public class PilotFrame{
	public JFrame frame;
	public JPanel buttons;
	public MapComponent map;
	public MapViewPanel mapView;
	public GameMap game;
	public ChatPanel chatp;
	public static final String SHIP_NAME = "Ship.1";
	public static final String ROTATE_LEFT = "rotate left", ROTATE_RIGHT = "rotate right", THROTTLE_FORWARD = "throttle forward", THROTTLE_BACKWARD = "throttle backward";
	public static final String RELEASE_UP = "release up";
	public static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
	public JLabel obj1;
	public HeadingDial hd;
	public InputMap input;
	public ActionMap action;
	final PilotGuiClient pilot;
	public JSlider fuelSlider;
	public boolean moveRight = false, moveLeft = false, incThrottle = false;
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
		
		JLabel fuelLabel = new JLabel("Fuel");
		fuelSlider = new JSlider(0,100,0);
		fuelSlider.setMajorTickSpacing(10);
		fuelSlider.setPaintTicks(true);
	
		
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
		utilities.add(fuelLabel);
		utilities.add(fuelSlider);
		utilities.add(chatp);
	//	utilities.add(fuel);
		
		frame.add(utilities,BorderLayout.EAST);
	//	frame.add(chatp,BorderLayout.WEST);
	//	frame.add(hd,BorderLayout.EAST);
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false),ROTATE_LEFT);
		obj1.getActionMap().put(ROTATE_LEFT, new RotateAction("left"));
		
	//	obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true),ROTATE_LEFT);
	//	obj1.getActionMap().put(ROTATE_LEFT, new RotateAction("rLeft"));
		
		
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false),ROTATE_RIGHT);
		obj1.getActionMap().put(ROTATE_RIGHT, new RotateAction("right"));
		
	//	obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("released RIGHT"),ROTATE_RIGHT);
	//	obj1.getActionMap().put(ROTATE_RIGHT, new RotateAction("rRight"));
		
		
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"),THROTTLE_FORWARD);
		obj1.getActionMap().put(THROTTLE_FORWARD, new ThrottleAction("forward"));
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("released UP"), RELEASE_UP);
		obj1.getActionMap().put(RELEASE_UP, new ThrottleAction("stop"));
		
		
		
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"),THROTTLE_BACKWARD);
		obj1.getActionMap().put(THROTTLE_BACKWARD, new ThrottleAction("backward"));
		
		final String LU = "L&U";
		obj1.getInputMap(IFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,KeyEvent.CTRL_DOWN_MASK),LU);
		obj1.getActionMap().put(LU, new CombinedAction("L&U"));
		
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
		//	System.out.println(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("currentFuel"));
			if(moveLeft == true)
			{
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h -= Math.PI/36;
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                mapRepaint();
                System.out.println("rotated left");
                moveLeft = false;
			}
			if(moveRight == true)
			{
				double h = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"));
				h += Math.PI/36;
				pilot.getUpdater().addUserAction(SHIP_NAME,"heading",Double.toString(h),pilot.getClient());
                mapRepaint();
                moveRight = false;
			}
			if(incThrottle == true)
			{
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				if(t < 100)
				{
					t += 10;
				}
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
				mapRepaint();
				incThrottle = false;
			}
			int fuel = (int) Math.round(Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("currentFuel"))); 			
			fuelSlider.setValue(fuel);
			hd.setHeading((-Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading"))));
			mapRepaint();
			frame.revalidate();
		}
		
	}
	public void mapRepaint()
	{
		hd.repaint();
		mapView.repaint();
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
				moveLeft = true;
			}
			if(direction.compareTo("right") == 0)
			{
				System.out.println("Rotate Right");
				moveRight = true;
			}
			if(direction.compareTo("rLeft") == 0)
			{
				System.out.println("Ended Left");
				moveLeft = false;
			}
			if(direction.compareTo("rRight") == 0)
			{
				System.out.println("Ended Right");
				moveRight = false;
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
				System.out.println("Throttle: "+Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")));
                incThrottle = true;
			}
			if(direction.compareTo("backward") == 0)
			{
				double t = Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
			//	System.out.println("Throttle: "+t);
				t -= 10;
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(t),pilot.getClient());
				System.out.println("Throttle: "+Double.parseDouble(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle")));
                mapRepaint();
			}
			if(direction.compareTo("stop") == 0)
			{
				System.out.println("stopping");
				pilot.getUpdater().addUserAction(SHIP_NAME,"throttle",Double.toString(0),pilot.getClient());
				System.out.println(game.getEntityByName(SHIP_NAME).getComponent("Fuel").getVariable("throttle"));
				mapRepaint();
			}
		}
		
	}
	public class CombinedAction extends AbstractAction
	{
		public String direction;
		public CombinedAction(String d){
			direction = d;
		}
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			System.out.println("Went L&U");
		}
		
	}
}
