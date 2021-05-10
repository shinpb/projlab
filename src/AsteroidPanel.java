import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AsteroidPanel extends JPanel {
	Asteroid asteroid;
	JPanel headerPanel, picturePanel;
	JPanel mainPanel;

	public AsteroidPanel(Asteroid a, JPanel _mainPanel) {
		asteroid = a;
		mainPanel = _mainPanel;
		init();
	}

	private void init() {
		headerPanel = new JPanel();
		JLabel label = new JLabel(asteroid.toString());
		JButton button = new JButton("Back");

		button.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CardLayout c = (CardLayout)(mainPanel.getLayout());
					    c.show(mainPanel, "GAMEPANEL");
					}
				}
		);

		headerPanel.setPreferredSize(new Dimension(200, 40));
		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(label);
		headerPanel.add(button);


		picturePanel = new JPanel();
		picturePanel.setPreferredSize(new Dimension(200, 200));
		picturePanel.setBackground(new Color(0, 51, 102));


		ArrayList<Entity> entities = new ArrayList<Entity>(asteroid.getEntities());
		picturePanel.setLayout(new FlowLayout());

		try {

			for(Entity e : entities) {
				String imageFileName = e.getImageFileName();
				BufferedImage image;
				try{
					image = ImageIO.read(new File("src/pictures", imageFileName));
				} catch (IOException eeee) {
					image = ImageIO.read(new File("pictures", imageFileName));
				}
				ImageIcon icon = new ImageIcon(image);
				JButton entityButton = new JButton();
				entityButton.setIcon(icon);
				entityButton.setPreferredSize(new Dimension(50, 50));
				picturePanel.add(entityButton);

			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.setLayout(new BorderLayout());
		this.add(headerPanel, BorderLayout.NORTH);
		this.add(picturePanel, BorderLayout.CENTER);
	}
}
