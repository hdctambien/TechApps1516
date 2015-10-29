package pilotgui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PilotViewport {
	public JViewport view;
	public PilotViewport()
	{
		view = new JViewport();
		view.addChangeListener(new ChangeListener()
				{
					@Override
					public void stateChanged(ChangeEvent arg0) 
					{
						PilotFrame.mapRepaint();
					}
				});
		view.setViewSize(new Dimension(400,400));
		view.setExtentSize(new Dimension(800,800));
		view.setViewPosition(new Point(200,200));
		view.add(map);
	}

}
