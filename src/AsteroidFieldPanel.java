import java.awt.CardLayout;
import java.awt.Dimension;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AsteroidFieldPanel extends JPanel {
	private Game game;
	private JPanel mainPanel;
	
	
	public AsteroidFieldPanel(JPanel _mainPanel, Game _game) {
		//super();
		game = _game;
		mainPanel =_mainPanel;
		try {
			
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init() throws IOException {
		this.setPreferredSize(new Dimension(600, 400));
		
		final int numOfButtonsInOneRow = 5;
		
		ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>(game.getAsteroids());
		final int numOfAsteroids = asteroids.size();		
		this.setLayout(new GridLayout((numOfAsteroids / numOfButtonsInOneRow) + 1, numOfButtonsInOneRow, 10, 10));
		
		for(int i = 0; i < numOfAsteroids; i++) {
			String buttonText = asteroids.get(i).toString();
			buttonText = buttonText.split("@")[1];
			
			
			//JButton button = new JButton((i + 1) + " " + buttonText);
			
			JButton button = new JButton("Asteroid " + (i + 1));
			
			button.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {  
							JButton b = (JButton) e.getSource();
							String text = b.getText();
							String[] splittedText = text.split(" ");
							
							int i = Integer.parseInt(splittedText[1]) - 1;   
							showAsteroid(i);
						} 
					}
			);
			
			
			this.add(button);
		}
	}
	
	private void showAsteroid(int i) {
		Asteroid a = game.getAsteroids().get(i);
		
		mainPanel.add(new AsteroidPanel(a, mainPanel), "ASTEROID");
		
		CardLayout c = (CardLayout)(mainPanel.getLayout());
	    c.show(mainPanel, "ASTEROID");
	}
}
