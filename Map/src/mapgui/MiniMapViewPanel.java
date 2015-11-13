package mapgui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

public class MiniMapViewPanel extends JPanel
{
	private GameMap map;
	private BufferedImage shipIMG;
	private final String SHIP_NAME;
	private AffineTransform at;
	private ImageLoader loader;
	private double scaleFactor = .5;
	int width;
	int height;
	
	
	public MiniMapViewPanel(GameMap m, String shipname)
	{
		map = m;
		SHIP_NAME = shipname;
		loader = new ImageLoader();
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		shipIMG = loader.getImage(map.getEntityByName(SHIP_NAME).getComponent("Render").getVariable("imagePath"));
	}
	
	
	public void setSize(int size){
		this.width=size;
		this.height=size;
		//setPreferredSize(new Dimension(width,height));
		setSize(new Dimension(width, height));
	}
	
	public void paintComponent(Graphics G) 
	{
		Graphics2D g = (Graphics2D) G;
		
		at = AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
		//g.setTransform(at);
		g.setBackground(Color.BLUE);
		g.clearRect(0,0, width, height);
		
		
		System.out.println("width: "+getWidth());
		System.out.println("height: "+getHeight());
		
		
		//g.clearRect(0,0, getWidth(), getHeight());
		//g.clearRect(0,0, (int)(getWidth()*scaleFactor), (int)(getHeight()*scaleFactor));
	
		//
		//int cx = (int) ((int)(getWidth()/2 - shipIMG.getWidth() / 2)*scaleFactor), cy = (int) ((int) (getHeight()/2 - shipIMG.getHeight() / 2)*scaleFactor);
		int cx = width/2 - shipIMG.getWidth() / 2, cy = height/2 - shipIMG.getHeight() / 2;
		
		
		at.translate(getWidth()/2 - shipIMG.getWidth() / 2 , getHeight()/2 - shipIMG.getHeight() / 2);
		at.translate(shipIMG.getHeight() / 2 , shipIMG.getWidth() / 2);
        at.rotate(Double.parseDouble(map.getEntityByName(SHIP_NAME).getComponent("Heading").getVariable("heading")));
        at.translate(-shipIMG.getHeight() / 2,-shipIMG.getWidth() / 2);
        
        PositionComponent posShip = (PositionComponent)map.getEntityByName(SHIP_NAME).getComponent("Position");
        int sx = (int)Math.round(posShip.getDouble("posX")), sy = (int)Math.round(posShip.getDouble("posY"));
        
		
      
		BufferedImage image;
		int x, y;
		for(Entity e: map.getEntities()){
			if(e.hasComponent(EntityFactory.POSITION)&&e.hasComponent(EntityFactory.RENDER)&&!e.getName().equals(SHIP_NAME)){
				image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				PositionComponent pos = (PositionComponent)e.getComponent(EntityFactory.POSITION);
				x = (int)Math.round(pos.getDouble("posX"))-sx+cx;
				y = (int)Math.round(pos.getDouble("posY"))-sy+cy;
				if(x>0&&y>0&&x<getWidth()&&y<getHeight())
				{
					if(e.hasComponent(EntityFactory.HEADING))
					{
						AffineTransform transformer = new AffineTransform();
						transformer=AffineTransform.getScaleInstance(scaleFactor,scaleFactor);
						transformer.translate(x,y);
						transformer.translate(image.getWidth()/2, image.getHeight()/2);
						transformer.rotate(e.getComponent("Heading").getDouble("heading"));
						transformer.translate(-image.getWidth()/2, -image.getHeight()/2);
						g.drawImage(image,transformer,null);
					}
					else
					{
						
						g.drawImage(image, x, y, null);
						//g.drawImage(image, at, null);
						

					     
					}
				}
				
			}
		}
		//g.drawImage(image, x, y, null);
		g.drawImage(shipIMG, at, null);
		
	}
	
}