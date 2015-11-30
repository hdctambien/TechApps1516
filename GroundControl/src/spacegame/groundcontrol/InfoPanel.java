package spacegame.groundcontrol;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import spacegame.map.GameMap;
import spacegame.map.components.*;

public class InfoPanel extends JPanel
{
	private final String SHIP_NAME;
	private GameMap renderMap;
	
	private JPanel dataTable;
	private JPanel throttlePanel;
	
	private JSlider throttle;
	private JLabel throttleLabel;
	private JLabel posX;
	private JLabel posXLabel;
	private JLabel posY;
	private JLabel posYLabel;
	private JLabel velocityX;
	private JLabel veloxityXLabel;
	private JLabel velocityY;
	private JLabel veloxityYLabel;
	private JLabel maxPower;
	private JLabel maxPowerLabel;
	private JLabel shield;
	private JLabel shieldLabel;
	private JLabel health;
	private JLabel healthLabel;
	private JLabel fuel;
	private JLabel fuelLabel;
	
	public InfoPanel(GameMap m, String SHIPNAME)
	{
		SHIP_NAME = SHIPNAME;
		renderMap = m;
		dataTable = new JPanel(new GridLayout(8,2));
		throttlePanel = new JPanel(new GridLayout(2,1));
		setLayout(new BorderLayout());
		throttle = new JSlider();
		throttle.setOrientation(throttle.HORIZONTAL);
		throttle.setEnabled(false);
		throttle.setMaximum(100);
		throttle.setMinimum(0);
		throttle.setMajorTickSpacing(10);
		throttle.setMinorTickSpacing(5);
		throttle.setPaintTicks(true);
		
		
		throttleLabel = new JLabel("Throttle", SwingConstants.CENTER);
		posX = new JLabel("x");
		posXLabel = new JLabel("X Position:");
		posY = new JLabel("y");
		posYLabel = new JLabel("Y Position:");
		velocityX = new JLabel("xV");
		veloxityXLabel = new JLabel("X Velocity:");
		velocityY = new JLabel("yV");
		veloxityYLabel = new JLabel("Y Velocity:");
		maxPower = new JLabel("mp");
		maxPowerLabel = new JLabel("Max Power:");
		shield = new JLabel("s");
		shieldLabel = new JLabel("Shield Health:");
		health = new JLabel("h");
		healthLabel = new JLabel("Ship Health:");
		fuel = new JLabel("f");
		fuelLabel = new JLabel("Current Fuel:");
		
		
		dataTable.add(posXLabel);
		dataTable.add(posX);
		dataTable.add(posYLabel);
		dataTable.add(posY);
		dataTable.add(veloxityXLabel);
		dataTable.add(velocityX);
		dataTable.add(veloxityYLabel);
		dataTable.add(velocityY);
		dataTable.add(maxPowerLabel);
		dataTable.add(maxPower);
		dataTable.add(shieldLabel);
		dataTable.add(shield);
		dataTable.add(healthLabel);
		dataTable.add(health);
		dataTable.add(fuelLabel);
		dataTable.add(fuel);
		
		
		add(throttleLabel,BorderLayout.NORTH);
		add(throttle,BorderLayout.CENTER);
		add(dataTable,BorderLayout.SOUTH);
		
		
		updateInfo();
	}
	public void updateInfo()
	{
		PhysicsComponent Physics = (PhysicsComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Physics");
		PositionComponent Position = (PositionComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Position");
		PowerComponent Power = (PowerComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Power");
		ShieldComponent Shield = (ShieldComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Shield");
		HealthComponent Health = (HealthComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Health");
		FuelComponent Fuel = (FuelComponent) renderMap.getEntityByName(SHIP_NAME).getComponent("Fuel");
		
		posX.setText(Position.getVariable("posX"));
		posY.setText(Position.getVariable("posY"));
		velocityX.setText(Physics.getVariable("velocityX"));
		velocityY.setText(Physics.getVariable("velocityY"));
		maxPower.setText(Power.getVariable("maxPower"));
	//	shield.setText(Shield.getVariable("shield"));
	//	health.setText(Health.getVariable("health"));
		fuel.setText(Fuel.getVariable("fuel"));
		
		throttle.setValue((int)Double.parseDouble(Fuel.getVariable("throttle")));
	}
}
