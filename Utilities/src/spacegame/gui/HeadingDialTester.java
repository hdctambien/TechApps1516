package spacegame.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class HeadingDialTester {

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				JFrame frame = new JFrame("GUITest");
				frame.setSize(200, 200);
				HeadingDial dial = new HeadingDial();
				dial.setHeading(0);
				JButton button = new JButton("Change Heading");
				button.addActionListener(new ActionListener(){
					private HeadingDial dial;
					public ActionListener setDial(HeadingDial dial){
						this.dial = dial;
						return this;
					}
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String str = JOptionPane.showInputDialog("Please enter new Heading:");
						dial.setHeading(Double.parseDouble(str));
						dial.repaint();
					}
				}.setDial(dial));
				frame.add(dial, BorderLayout.NORTH);
				frame.add(button, BorderLayout.CENTER);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
}
