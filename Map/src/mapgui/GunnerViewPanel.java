package mapgui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PhysicsComponent;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

public class GunnerViewPanel extends JPanel
{
	private GameMap map;
	private BufferedImage shipIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private ImageLoader loader;
	private Random rand;
	private Star[] starList;
	private Star[] backgroundStars;
	private Point mousePos;
	
	private final boolean DYNAMIC_STARS_ENABLED = false;
	
	public GunnerViewPanel(GameMap m, String shipname)
	{
		starList = new Star[0];
		backgroundStars = new Star[5000];
		rand = new Random();
		map = m;
		SHIP_NAME = shipname;
		loader = new ImageLoader();
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shipIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Render").getVariable("imagePath"));
		if(DYNAMIC_STARS_ENABLED)
			starList = new Star[250];
		
		initStars();
	}
	public void initStars()
	{
		int rInt = 0;
		int rHeight = 0;
		if(DYNAMIC_STARS_ENABLED)
			for(int x = 0; x < starList.length; x++)
			{
				rInt = rand.nextInt(100)+154;
				rHeight = (int)Math.floor((2+rand.nextInt(20))/2);
				starList[x] = new Star(rand.nextInt(1600), rand.nextInt(900),rHeight*5,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)));
			}
		for(int x = 0; x < backgroundStars.length; x++)
		{
			rInt = rand.nextInt(100)+154;
			rHeight = (int)Math.floor((2+rand.nextInt(10))/2);
			backgroundStars[x] = new Star(rand.nextInt(1600), rand.nextInt(900),rHeight,rHeight,new Color(rInt,rInt,rand.nextInt(255),rand.nextInt(100)+154));
		}
	}
	public void paintComponent(Graphics G) 
	{
		Graphics2D g = (Graphics2D) G;
		g.setBackground(Color.BLACK);
		
		g.clearRect(0,0, getWidth(), getHeight());
		PhysicsComponent physShip = (PhysicsComponent)map.getEntityByName(SHIP_NAME).getComponent("Physics");
		PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
		
		int x, y;
		if(DYNAMIC_STARS_ENABLED)
			for(Star s: starList)
			{
				s.redrawStar(physShip.getDouble("velocityX"),physShip.getDouble("velocityY"));
				g.drawImage(s.image, s.at, null);
			}
		for(Star s: backgroundStars)
		{
			s.moveStar(physShip.getDouble("velocityX"),physShip.getDouble("velocityY"));
			g.drawImage(s.image, s.at, null);
		}
		
		at = new AffineTransform();
		int cx = getWidth()/2 - shipIMG.getWidth() / 2, cy = getHeight()/2 - shipIMG.getHeight() / 2;
		at.translate(getWidth()/2 - shipIMG.getWidth() / 2,getHeight()/2 - shipIMG.getHeight() / 2);
		at.translate(shipIMG.getHeight() / 2,shipIMG.getWidth() / 2);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
        at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        
        int sx = (int)Math.round(posShip.getDouble("posX")), sy = (int)Math.round(posShip.getDouble("posY"));
		BufferedImage image;
		
		for(Entity e: map.getEntities()){
			if(e.hasComponent(EntityFactory.POSITION)&&e.hasComponent(EntityFactory.RENDER)&&!e.getName().equals(SHIP_NAME)){
				image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				PositionComponent pos = (PositionComponent)e.getComponent(EntityFactory.POSITION);
				x = (int)Math.round(pos.getDouble("posX"))-sx+cx;
				y = (int)Math.round(pos.getDouble("posY"))-sy+cy;
				if(x>(0-image.getWidth())&&y>(0-image.getHeight())&&x<getWidth()&&y<getHeight())
				{
					if(e.hasComponent(EntityFactory.HEADING))
					{
						AffineTransform transformer = new AffineTransform();
						transformer.translate(x,y);
						transformer.translate(image.getWidth()/2, image.getHeight()/2);
						transformer.rotate(e.getComponent("Heading").getDouble("heading"));
						transformer.translate(-image.getWidth()/2, -image.getHeight()/2);
						g.drawImage(image,transformer,null);
					}
					else
					{
						g.drawImage(image, x, y, null);
					}
				}				
			}
		}
		mousePos = this.getMousePosition();
		g.setColor(Color.RED);
		g.drawLine(getWidth()/2, getHeight()/2, (int) mousePos.getX(), (int) mousePos.getY());
		g.drawImage(shipIMG, at, null);
	}	
}


