import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ControlPanel extends JPanel{
    private ArrayList<Astronaut> astronauts;

    private JPanel selectPanel = new JPanel();
    private JPanel propertyPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JLabel lblAsterID;
    private JPanel[] invarr=new JPanel[10];

    public ControlPanel(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(selectPanel);
        add(propertyPanel);

        add(operationPanel);
        initSelectPanel();
        initOperationPanel();
        initPropertyPanel();
        setPreferredSize(new Dimension(300, 384));
    	  setMinimumSize(new Dimension(200, 400));

        astronauts = new ArrayList<Astronaut>();
        try {
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
        } catch(Exception e) {e.printStackTrace();}
    }

    public void initSelectPanel(){
      selectPanel.setLayout(null);
      JLabel jatekos = new JLabel("Játékos", JLabel.CENTER);
      jatekos.setBounds(32, 12, 65, 19);
      jatekos.setBackground(Color.MAGENTA);
      selectPanel.add(jatekos);
      selectPanel.setBackground(new Color(0, 0, 255));
      selectPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

      JMenuBar menuBar = new JMenuBar();
      menuBar.setBounds(108, 5, 73, 23);
      selectPanel.add(menuBar);

      JMenu mnAstronaut = new JMenu("Astronaut");
      menuBar.add(mnAstronaut);
    }
    public void initPropertyPanel(){
    	propertyPanel.setMinimumSize(new Dimension(100, 100));
      propertyPanel.setLayout(null);
      propertyPanel.setBackground(Color.GREEN);

      JLabel lblTulajdonsgok = new JLabel("Tulajdonságok");
      lblTulajdonsgok.setBounds(12, 12, 114, 15);
      propertyPanel.add(lblTulajdonsgok);

      JLabel lblHely = new JLabel("Hely");
      lblHely.setBounds(32, 32, 41, 15);
      propertyPanel.add(lblHely);

      lblAsterID = new JLabel("");
      lblAsterID.setBounds(70, 32, 49, 15);
      propertyPanel.add(lblAsterID);

      JLabel lblInventory = new JLabel("Inventory");
      lblInventory.setBounds(32, 52, 114, 15);
      propertyPanel.add(lblInventory);

      for(int i=0; i<10; i++) {
          invarr[i]=new JPanel();
          invarr[i].setBounds(32+((i%5)*30), 72+(i/5)*30, 20, 20);
          invarr[i].setMinimumSize(new Dimension(20, 20));
          invarr[i].setPreferredSize(new Dimension(20, 20));
          invarr[i].setBackground(Color.WHITE);
          propertyPanel.add(invarr[i]);
      }

    }

    public void initOperationPanel(){
      operationPanel.setMinimumSize(new Dimension(100, 100));
      operationPanel.setLayout(null);
      operationPanel.setBackground(Color.MAGENTA);


      JLabel lblMuveletek = new JLabel("Műveletek");
      lblMuveletek.setBounds(12, 12, 114, 15);
      operationPanel.add(lblMuveletek);

      JLabel lblmozgide = new JLabel("Mozgás ide");
      lblmozgide.setBounds(32, 32, 100, 15);
      operationPanel.add(lblmozgide);

      JMenuBar astermenubar = new JMenuBar();

      JMenu mnAster = new JMenu("Asteroid");
      astermenubar.add(mnAster);

      mnAster.add(new JMenuItem("A1"));
      mnAster.add(new JMenuItem("A2"));
      mnAster.add(new JMenuItem("A3"));

      astermenubar.setBounds(120, 32, 70, 15);
      operationPanel.add(astermenubar);

      JLabel lblFuras = new JLabel("Furás");
      lblFuras.setBounds(32, 52, 40, 15);
      operationPanel.add(lblFuras);

      JLabel lblCraft = new JLabel("Craftolas");
      lblCraft.setBounds(32, 72, 70, 15);
      operationPanel.add(lblCraft);

      JMenuBar craftMenuBar = new JMenuBar();

      JMenu mnCrft = new JMenu("Mit");
      craftMenuBar.add(mnCrft);

      mnCrft.add(new JMenuItem("robot"));
      mnCrft.add(new JMenuItem("gate"));

      craftMenuBar.setBounds(120, 72, 70, 15);
      operationPanel.add(craftMenuBar);

      JButton mozogjgomb = new JButton("mozogj");
      mozogjgomb.setBounds(200, 32, 90, 15);
      operationPanel.add(mozogjgomb);

      JButton furjgomb = new JButton("fúrj");
      furjgomb.setBounds(200, 52, 90, 15);
      operationPanel.add(furjgomb);

      JButton epitsgomb = new JButton("mozogj");
      epitsgomb.setBounds(200, 72, 90, 15);
      operationPanel.add(epitsgomb);
    }


    public void playerSelected(){

    }

    public static void main(String[] args){
        ControlPanel cp = new ControlPanel();
        JFrame frame = new JFrame();
        frame.setBackground(Color.red);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(cp);
        frame.setSize(200, 350);
        frame.setPreferredSize(frame.getPreferredSize());
        frame.getContentPane().setLayout(new GridLayout());
        frame.pack();
        frame.setVisible(true);
    }
}
