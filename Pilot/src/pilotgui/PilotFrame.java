package pilotgui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import mapgui.MapComponent;
import spacegame.client.ProtocolAggregator;
import spacegame.client.chat.ChatListener;
import spacegame.client.chat.ChatPanel;
import spacegame.client.chat.ChatProtocol;
import spacegame.map.Entity;
import spacegame.map.GameMap;
import spacegame.server.chat.ChatMessage;
import spacegame.server.chat.Chats;


public class PilotFrame{
	public JFrame frame;
	public JPanel buttons;
	public MapComponent map;
	public Chats chat;
	public GameMap game;
	public ChatPanel chatp;
	public PilotFrame(GameMap g)
	{
		game = g;
		final PilotGuiClient pilot = new PilotGuiClient();
		pilot.setup();
		
	//	ProtocolAggregator aggregator = new ProtocolAggregator(pilot.getClient());
		
		chat = new Chats();
		buttons = new JPanel();
		chatp = new ChatPanel(250,250);
		ChatProtocol cProtocol = new ChatProtocol(pilot.getClient(),chatp);
		chatp.addChatListener(cProtocol);
	//	aggregator.addProtocol(cProtocol);
		JSlider hSlider = new JSlider(0,6);
		
		JButton up = new JButton("UP");
		up.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
			//	int y = Integer.parseInt(game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posY"));
			//	y -= 10;
			//	game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posY",Integer.toString(y));
                System.out.println("You went up");
                mapRepaint();
            }
		});
		
		JButton down = new JButton("DOWN");
		down.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
			//	int y = Integer.parseInt(game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posY"));
			//	y += 10;
			//	game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posY",Integer.toString(y));
                System.out.println("You went down");
                mapRepaint();
            }
		});
		
		JButton left = new JButton("LEFT");
		left.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
			//	int x = Integer.parseInt(game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posX"));
			//	x += 10;
			//	game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posX",Integer.toString(x));
                System.out.println("You went left");
                mapRepaint();
            }
		});
		JButton right = new JButton("RIGHT");
		right.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e)
            {
			//	int x = Integer.parseInt(game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posX"));
			//	x -= 10;
			//	game.getEntityByName("Position").getComponent("PositionComponent").setVariable("posX",Integer.toString(x));
                System.out.println("You went right");
                mapRepaint();
            }
		});
		
		buttons.add(left);
		buttons.add(up);
		buttons.add(down);
		buttons.add(right);
		buttons.add(hSlider);
		
		
		
		
	//	Entity positionEntity = game.getEntityByName("Position");
	//	positionEntity.getComponent("PositionComponent");
		
		JLabel title = new JLabel("PILOT");
		title.setForeground(Color.RED);
		title.setBackground(Color.YELLOW); 
		map = new MapComponent();
		frame = new JFrame("Pilot");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,800);
		frame.add(map,BorderLayout.CENTER);
		frame.add(title,BorderLayout.NORTH);
		frame.add(buttons,BorderLayout.SOUTH);
		frame.add(chatp,BorderLayout.EAST);
		
		
		
	}
	public void mapRepaint()
	{
	//	map.setPosition(game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posX"),game.getEntityByName("Position").getComponent("PositionComponent").getVariable("posY"));
	//	map.setHeading(game.getEntityByName("Heading").getComponent("HeadingComponent").getVariable("heading"));
	//	map.repaint();
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
	

}
