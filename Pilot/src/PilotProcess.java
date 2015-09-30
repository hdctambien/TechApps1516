import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PilotProcess {
	public static JFrame frame;
	public static double heading;
	
	public static void pilotProcess()
	{
		
		final PilotClient pilot = new PilotClient();
		pilot.setup();
		pilot.subscribe();
		Scanner input = new Scanner( System.in );
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,800);
		
		//labels
		JLabel aLabel = new JLabel("Exit");
		frame.add(aLabel);
		
		//buttons
		JButton a = new JButton();
		a.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pilot.exit();
				
			}
			});
		
		JButton b = new JButton();
		JButton c = new JButton();
		JButton d = new JButton();
		
		frame.add(a);
		
		//sliders
		JSlider headingSlider = new JSlider(JSlider.HORIZONTAL,-180,180,0);
		headingSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				double value = source.getValue();
				System.out.println("Value:" +value);
				pilot.setHeading(value);
			}
		});
		headingSlider.setPaintLabels(true);	
		headingSlider.setPaintTicks(true);
	    headingSlider.setMajorTickSpacing(90);
	    headingSlider.setMinorTickSpacing(10);
		frame.add(headingSlider);
		
		//options
		String choice;
		boolean running = true;
		
		while(running == true)
		{
			System.out.println("Do something: ");
			choice = input.nextLine();
			if(choice.compareTo("exit") == 0)
			{
				pilot.exit();
				running = false;
			}
			if(choice.compareTo("open") == 0)
			{
				frame.setVisible(true);
			}
			if(choice.compareTo("close") == 0)
			{
				frame.setVisible(false);
			}
			if(choice.compareTo("Set Heading") == 0)
			{
				System.out.println("What is the new Heading?");
				double head = input.nextDouble();
				pilot.setHeading(head);
			}
			System.out.println(running);
		}
	}
	public static void main(String[] args)
	{
		pilotProcess();
	}
	public static void setHeading(double head)
	{
		heading = head;
	}
}
class PilotPanel extends JPanel
{
	public int startX,startY, endX, endY;
	
	public static JPanel panel;
	
	public PilotPanel()
	{
		panel = new JPanel();
		panel.setSize(600,600);
		
	}
	public JPanel getPanel()
	{
		return panel;
	}
}
class SliderListener implements ChangeListener
{
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider)e.getSource();
		double value = source.getValue();
		System.out.println("Value:" +value);
	//	pilot.setHeading(value);
	}

}
class Action implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		
	}
}
