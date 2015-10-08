package spacegame.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;

public class ImagePathTester {

	public static void main(String[] args){
		BufferedImage img = null;
		try {
			File file = new File("resources/MayMime.png");
			if(!file.exists()){
				System.out.println("File doesn't exist");
			}
		    img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if(img!=null){
			System.out.println("File load successful!");
			JOptionPane.showMessageDialog(null, "IT WORKS", "Mr. Mime",
					JOptionPane.INFORMATION_MESSAGE, new ImageIcon(img));
		}		
	}
	
}
