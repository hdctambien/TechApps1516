package spacegame.gunner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;

import src.mapgui.MapComponent;
import src.mapgui.MapPanel;

public class GunnerFrame extends JFrame {//this is a frame

    public void GunnerFrame() {
        
        initUI();
    }
    
    private void initUI() {
        
    	MapComponent map = new MapComponent();
    	setSize(new Dimension(1600,900));
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	add(map,BorderLayout.CENTER);
    	setVisible(true);

    	setResizable(false);
    	pack();
        
        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GunnerFrame gun = new GunnerFrame();
                gun.setVisible(true);
            }
        });
    }
}