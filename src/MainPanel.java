import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPanel extends JFrame {
	private static final long serialVersionUID = 2949687444255909471L;
	JPanel jp_top;
	JPanel jp_mid;
	JPanel jp_bot;
	JTextField tf;
	JLabel text;
	
	/**
	 * Konstruktor
	 */
	public MainPanel() {
		super("Aszteriodab�ny�szat");
		create();
	}
	
	/**
	 * A frame l�trehoz�sa
	 */
	private void create() {
		//Felso panel
		jp_top = new JPanel();
		text = new JLabel("<html><center><h1>�dv�z�lj�k!</h1></center><br/>K�rem adja meg, hogy h�ny j�t�kossal szeretne j�t�kot l�trehozni:</html>");
		jp_top.add(text);
		
		//Kozepso panel
		jp_mid = new JPanel();
		tf = new JTextField(2);
		jp_mid.add(tf);
		
		//Also panel
		jp_bot = new JPanel();
		JButton bt_start = new JButton("Ind�t�s");
		bt_start.setActionCommand("start");
		ButtonActionListener bal = new  ButtonActionListener();
		bt_start.addActionListener(bal);
		jp_bot.add(bt_start);
		
		//Panelek felv�tele
		add(jp_top,BorderLayout.NORTH);
		add(jp_mid,BorderLayout.CENTER);
		add(jp_bot,BorderLayout.SOUTH);
		pack();
	}
	
	/**
	 * 
	 * A frame-ben l�v� gombot figyel� ActionListener
	 *
	 */
	class ButtonActionListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand() == "start") {
				if(Integer.parseInt(tf.getText()) > 5 || Integer.parseInt(tf.getText()) < 1) {
					text.setText("<html><center><h1>�dv�z�lj�k!</h1></center>"
							+ "<br/>K�rem adja meg, hogy h�ny j�t�kossal szeretne j�t�kot l�trehozni:"
							+ "<br/><p style=\"color:red;\">A j�t�kosok sz�m�nak egy 1 �s 5 k�z�tti eg�sz sz�mnak kell lennie!</p></html>");
					pack();
				} else {
					PlayersPanel pp = new PlayersPanel(Integer.parseInt(tf.getText()));
					setVisible(false);
					pp.setVisible(true);
				}
			}
		}
		
	}
	
}
