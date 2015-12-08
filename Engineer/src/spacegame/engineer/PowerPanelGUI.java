package spacegame.engineer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import spacegame.client.Client;
import spacegame.client.ClientUpdater;
import spacegame.map.GameMap;

public class PowerPanelGUI extends JPanel
{
	GameMap map;
	
	EngineerGame game;
	
	int height, width, labelW;
	
	private double maxPower;
	private double pSt = 25;
	private double pGt = 25;
	private double pFt = 25;
	private double pCt = 25;
	
	ClientUpdater cUpdater;
	
	Client eClient;
	
	private final String SHIP_NAME;
	
	int pF, pC, pS, pG;
	
	JSlider com, shi, fue, gun;
	
	JLabel cLab, sLab, fLab, gLab;
	
	JPanel labels, slides, cont;
	
	Border border;

	public PowerPanelGUI(GameMap m, ClientUpdater c, Client eC, String n)
	{
		SHIP_NAME = n;
				
		map = m;		
		
		cUpdater = c;
		
		eClient = eC;
		
		labelW = (int) Math.floor(getWidth()/4);
		
		cont = new JPanel();
		cont.setLayout(new BorderLayout());
		
		border = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(border);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		labels = new JPanel(new GridLayout(1, 4));
		labels.setOpaque(false);
		
		slides = new JPanel(new GridLayout(1, 4));
		slides.setOpaque(false);
		
		com = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		com.setVisible(true);
		com.setOpaque(false);
		com.setMajorTickSpacing(10);
		com.setMinorTickSpacing(5);
		com.setPaintTicks(true);
		com.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pC = ((JSlider) e.getSource()).getValue();
					powerComms(pC);
				}					
			});
		
		cLab = new JLabel("Comms");
		cLab.setPreferredSize(new Dimension(labelW, 25));
		cLab.setHorizontalAlignment(JLabel.CENTER);
		
		shi = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		shi.setVisible(true);
		shi.setOpaque(false);
		shi.setMajorTickSpacing(10);
		shi.setMinorTickSpacing(5);
		shi.setPaintTicks(true);
		shi.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pS = ((JSlider) e.getSource()).getValue();
					powerShield(pS);
				}					
			});
		
		sLab = new JLabel("Shield");
		sLab.setPreferredSize(new Dimension(labelW, 25));
		sLab.setHorizontalAlignment(JLabel.CENTER);
		
		fue = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		fue.setVisible(true);
		fue.setOpaque(false);
		fue.setMajorTickSpacing(10);
		fue.setMinorTickSpacing(5);
		fue.setPaintTicks(true);
		fue.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pF = ((JSlider) e.getSource()).getValue();
					powerFuel(pF);
				}					
			});
		
		fLab = new JLabel("Fuel");
		fLab.setPreferredSize(new Dimension(labelW, 25));
		fLab.setHorizontalAlignment(JLabel.CENTER);
		
		gun = new JSlider(JSlider.VERTICAL, 0, 100, 0);
		gun.setVisible(true);
		gun.setOpaque(false);
		gun.setMajorTickSpacing(10);
		gun.setMinorTickSpacing(5);
		gun.setPaintTicks(true);
		gun.addChangeListener(new ChangeListener()
			{
				public void stateChanged(ChangeEvent e)
				{
					pG = ((JSlider) e.getSource()).getValue();
					powerGuns(pG);
				}					
			});
		
		gLab = new JLabel("Gun");
		gLab.setPreferredSize(new Dimension(labelW, 25));
		gLab.setHorizontalAlignment(JLabel.CENTER);
		
		labels.setLayout(new GridLayout(1, 4));
		labels.setBorder(border);
		
		labels.add(cLab);
		labels.add(sLab);
		labels.add(fLab);
		labels.add(gLab);
		
		slides.add(com);
		slides.add(shi);
		slides.add(fue);
		slides.add(gun);
		
		cont.add(labels, BorderLayout.NORTH);
		cont.add(slides, BorderLayout.SOUTH);
		add(cont);
	}
	
	public void disableEnable(boolean set)
	{
		com.setEnabled(set);
		fue.setEnabled(set);
		shi.setEnabled(set);
		gun.setEnabled(set);
	}
	
	public void powerDist(double pS, double pF, double pC, double pG, String change, double mP)
	{
		maxPower = mP;
		switch(change)
		{
			case "pS":
				if(pS + pG + pF + pC > maxPower)
				{
					if(pFt > 0)
						pFt--;
					if(pCt > 0)
						pCt--;
					if(pGt > 0)
						pGt--;
				}
				pSt = pS;				

				com.setValue((int)pCt);
				fue.setValue((int)pFt);
				shi.setValue((int)pSt);
				gun.setValue((int)pGt);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pF": 
				if(pS + pG + pF + pC > maxPower)
				{					
					if(pCt > 0)
						pCt--;
					if(pGt > 0)
						pGt--;
					if(pSt > 0)
						pSt--;
				}				
				pFt = pF;
				
				com.setValue((int)pCt);
				fue.setValue((int)pFt);
				shi.setValue((int)pSt);
				gun.setValue((int)pGt);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pC": 
				if(pS + pG + pF + pC > maxPower)
				{
					if(pFt > 0)
						pFt--;
					if(pGt > 0)
						pGt--;
					if(pSt > 0)
						pSt--;
				}
				pCt = pC;
				
				com.setValue((int)pCt);
				fue.setValue((int)pFt);
				shi.setValue((int)pSt);
				gun.setValue((int)pGt);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "pG": 
				if(pS + pG + pF + pC > maxPower)
				{					
					if(pFt > 0)
						pFt--;
					if(pCt > 0)
						pCt--;
					if(pSt > 0)
						pSt--;
				}				
				pGt = pG;
				
				com.setValue((int)pCt);
				fue.setValue((int)pFt);
				shi.setValue((int)pSt);
				gun.setValue((int)pGt);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			case "mP":
				if(pS + pG + pF + pC > maxPower)
				{	
					int dif = (int)(maxPower - (pS + pG + pF + pC));
					int temp = (int)Math.max(Math.max(pS, pG), Math.max(pC, pF));
					if(temp == pS)
						pS -= dif;
					else
						if(temp == pF)
							pF -= dif;
						else
							if(temp == pG)
								pG -= dif;
							else
								if(temp == pC)
									pC -= dif;
				}				
				
				com.setValue((int)pC);
				fue.setValue((int)pF);
				shi.setValue((int)pS);
				gun.setValue((int)pG);
				
				cUpdater.addUserAction(SHIP_NAME, "powerFuel", Integer.toString((int)pFt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerComms", Integer.toString((int)pCt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerShield", Integer.toString((int)pSt), eClient);
				cUpdater.addUserAction(SHIP_NAME, "powerGuns", Integer.toString((int)pGt), eClient);break;
				
			default:
				pCt = 25;
				pSt = 25;
				pFt = 25;
				pGt = 25;break;
		}
	}
	
	public void power(int mP)
	{
		maxPower = 100 + mP;
		powerDist(pSt, pFt, pCt, pGt, "mP", maxPower);
		cUpdater.addUserAction(SHIP_NAME, "power", Integer.toString((int)maxPower), eClient);
	}
	
	public void powerShield(int pShield)
	{
		powerDist(pShield, pFt, pCt, pGt, "pS", maxPower);
	}
	
	public void powerFuel(int pFuel)
	{
		powerDist(pSt, pFuel, pCt, pGt, "pF", maxPower);
	}
	
	public void powerComms(int pComms)
	{
		powerDist(pSt, pFt, pComms, pGt, "pC", maxPower);
	}
	
	public void powerGuns(int pGuns)
	{
		powerDist(pSt, pFt, pCt, pGuns, "pG", maxPower);
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D G = (Graphics2D)g;
		
		width  = getWidth();
		height = getHeight();
		
		G.clearRect(0, 0, width, height);
		
		G.setColor(Color.GREEN);
		G.setBackground(Color.GREEN);
		G.fillRoundRect(5, 5, width-10, height-10, 25, 25);
		G.setColor(Color.WHITE);
		G.fillRoundRect(10, 10, width-20, height-20, 25, 25);
		G.setColor(null);
	}
}
