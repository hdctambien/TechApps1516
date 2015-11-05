package spacegame.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import spacegame.map.Entity;
import spacegame.map.EntityFactory;
import spacegame.map.GameMap;
import spacegame.map.components.PositionComponent;
import spacegame.render.ImageLoader;

public class ImageLoaderTest extends JComponent {

	//private BufferedImage ship;
	//private BufferedImage asteroid;
	private ImageLoader il;
	private GameMap map;
	private static ImageLoader loader;
	
	public ImageLoaderTest(ImageLoader loader, GameMap map){
		il = loader;
		this.map= map;
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		BufferedImage image;
		int x, y;
		for(Entity e: map.getEntities()){
			if(e.hasComponent(EntityFactory.POSITION)&&e.hasComponent(EntityFactory.RENDER)){
				image = loader.getImage(e.getComponent(EntityFactory.RENDER).getVariable("imagePath"));
				PositionComponent pos = (PositionComponent)e.getComponent(EntityFactory.POSITION);
				x = (int)Math.round(pos.getDouble("posX"));
				y = (int)Math.round(pos.getDouble("posY"));
				g2.drawImage(image, x, y, null);
			}
		}
	}
	
	public static void main(String[] args){
		loader = new ImageLoader();
		System.out.println("Loading Images...");
		try {
			loader.loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				setupGUI();
			}
		});		
	}
	public static void setupGUI(){
		JFrame frame = new JFrame("Image Load Test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageLoaderTest test = new ImageLoaderTest(loader, createTestMap());
		frame.add(test);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}
	
	private static GameMap createTestMap(){
		GameMap map = new GameMap();
		EntityFactory factory = new EntityFactory();
		Entity asteroid = factory.createAsteroid();
		asteroid.setVariable("posX", "100");
		asteroid.setVariable("posY", "100");
		map.addEntity(asteroid);
		map.addEntity(factory.createShip());
		return map;
	}
	
}
