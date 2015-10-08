package spacegame.render;

import spacegame.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class ImageLoader {

	private Hashtable<String, BufferedImage> imageMap;
	
	public static final String PATH_IMAGE_CONFIG = GameConstants.RESOURCE_PATH+"images.cfg";
	
	public ImageLoader(){
		imageMap = new Hashtable<String, BufferedImage>();
	}
	
	public void loadImages() throws IOException{
		List<String> images = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(PATH_IMAGE_CONFIG)));
		String line = null;
		while((line = reader.readLine()) != null){
			images.add(line);
		}
		reader.close();
		for(String imagePath: images){
			String fullPath = GameConstants.RESOURCE_PATH+imagePath;
			BufferedImage image = ImageIO.read(new File(fullPath));
			imageMap.put(imagePath, image);
		}
	}
	
	public BufferedImage getImage(String name){
		return imageMap.get(name);
	}
	
}
