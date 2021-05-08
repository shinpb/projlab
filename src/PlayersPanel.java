import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayersPanel extends JFrame{
	private static final long serialVersionUID = -645382040089403747L;
	private int playercnt;
	private JLabel[] labels;
	private JTextField[] tfs;
	private JLabel errormsg;
	
	public PlayersPanel(int cnt) {
		super("Aszteroidab�ny�szat");
		playercnt = cnt;
		labels = new JLabel[cnt];
		tfs = new JTextField[cnt];
		create();
	}
	
	private void create() {
		//Labelek �s TextFieldek l�trehoz�sa �s felv�tele
		JPanel jp = new JPanel();
		errormsg = new JLabel();
		jp.setLayout(new BoxLayout(jp,BoxLayout.Y_AXIS));
		jp.add(errormsg);
		for(int i = 0; i < playercnt; i++) {
			labels[i] = new JLabel(String.valueOf(i+1) + ". j�t�kos: ");
			tfs[i] = new JTextField(30);
			jp.add(labels[i]);
			jp.add(tfs[i]);
		}
		
		//Gomb felv�tele
		JPanel jp_bot = new JPanel();
		JButton bt_ok = new JButton("OK");
		bt_ok.setActionCommand("ok");
		ButtonActionListener bal = new ButtonActionListener();
		bt_ok.addActionListener(bal);
		jp_bot.add(bt_ok);
		
		add(jp,BorderLayout.NORTH);
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
			if(e.getActionCommand() == "ok") {
				boolean isUres = false;
				for(int i = 0; i < playercnt; i++) {
					if(tfs[i].getText().equals(""))
						isUres = true;
				}
				if(isUres) {
					errormsg.setText("<html><p style=\"color:red;\">Adjon nevet az �sszes j�t�kosnak!</p></html>");
					pack();
				} else {
					//J�t�k ind�t�sa
				}
			}
		}
		
	}
}
