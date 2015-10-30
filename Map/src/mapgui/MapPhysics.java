package src.mapgui;

import java.awt.Component;

import spacegame.map.*;
import spacegame.map.components.*;

public class MapPhysics {
	public GameMap game;
	public Entity pEntity;
	public spacegame.map.components.Component pComponent;
	public MapPhysics(GameMap g)
	{
		game = g;
		pEntity = game.getEntityByName("Physics");
		pComponent = pEntity.getComponent("Physics");
		
	}
	public void setVelocityX(double vX)
	{
		pComponent.setVariable("velocityX",Double.toString(vX));
	}
	public void setVelocityY(double vY)
	{
		pComponent.setVariable("velocityY",Double.toString(vY));
	}
	public void setAccX(double accX)
	{
		pComponent.setVariable("xAcc",Double.toString(accX));
	}
	public void setAccY(double accY)
	{
		pComponent.setVariable("yAcc",Double.toString(accY));
	}

}
