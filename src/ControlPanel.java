import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class ControlPanel extends JPanel{
    protected static final long serialVersionUID = 42L;
    private ArrayList<Astronaut> astronauts;

    private Game game;
    private JPanel selectPanel = new JPanel();
    private JPanel propertyPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private JLabel lblAsterID;
    private JMenu mnAstronaut;
    private JPanel[] invarr=new JPanel[10];
    private Astronaut theKivalasztott;
    private ArrayList<Astronaut> aKorbenLepettMar = new ArrayList<Astronaut>();

    private void initControlPanel(){
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      add(selectPanel);
      add(propertyPanel);
      add(operationPanel);
      initSelectPanel();
      initOperationPanel();
      initPropertyPanel();
      setPreferredSize(new Dimension(300, 384));
      setMinimumSize(new Dimension(200, 400));

      updateAstronautList();
    }

    private void updateAstronautList() {
        astronauts=game.getAstronauts();
        theKivalasztott=null;
        mnAstronaut.removeAll();
        mnAstronaut.setText("Astronaut");
        boolean newround=true;
        for(Astronaut a: astronauts)
          newround = newround && aKorbenLepettMar.contains(a);
        if(newround) aKorbenLepettMar.clear();
        for(int i=0; i<astronauts.size(); i++) {
          Astronaut a = astronauts.get(i);
          if(aKorbenLepettMar.contains(a)) continue;
          JMenuItem item = new JMenuItem((""+a).replace("@", " #"));
          item.setName(String.valueOf(i));
          item.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt)
             {
                int id= Integer.parseInt(item.getName());
                mnAstronaut.setText(item.getText());
                theKivalasztott=astronauts.get(id);
                selectAstronaut(theKivalasztott);
             }
          });
          mnAstronaut.add(item);
          selectAstronaut(theKivalasztott);
        }
    }

    private void selectAstronaut(Astronaut a){
      //a can be null
      int i=0;
      Color c=new Color(255, 255, 255, 80);
      if(a==null) {
        lblAsterID.setText("");
      }
      else
      {
        lblAsterID.setText((""+a.getPosition()).replace("@", " #"));
        for(Material m: a.getInventory()){
          switch((""+m).split("@")[0].toLowerCase()) {
            case "ice":
              c=new Color(185,232,234);
              break;
            case "carbon":
              c=new Color(	54, 69, 79);
              break;
            case "uran":
              c=new Color(93, 202, 49);
              break;
            case "iron":
              c=new Color(161,157,148);
              break;
          }
          invarr[i].setBackground(c);
          i++;
        }
      }
      for(; i<10; i++) {
        invarr[i].setBackground(c);
      }

 //   invarr[10];


    }

    private void votma(Astronaut a) {
      aKorbenLepettMar.add(a);
      updateAstronautList();
    }

    public ControlPanel(){

        astronauts = new ArrayList<Astronaut>();
        try {
          game=new Game();
          game.start();
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
          astronauts.add(new Astronaut(new Asteroid()));
        } catch(Exception e) {e.printStackTrace();}

        initControlPanel();
    }

    public ControlPanel(Game g0){
      game=g0;
      initControlPanel();
      astronauts = game.getAstronauts();
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
      menuBar.setBounds(100, 12, 170, 23);
      selectPanel.add(menuBar);

      mnAstronaut = new JMenu("Astronaut");
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
      lblAsterID.setBounds(70, 32, 150, 15);
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
      mozogjgomb.setMargin(new Insets(0, 0, 0, 0));
      mozogjgomb.setBounds(200, 32, 90, 15);
      operationPanel.add(mozogjgomb);

      JButton furjgomb = new JButton("fúrj");
      furjgomb.setMargin(new Insets(0, 0, 0, 0));
      furjgomb.setBounds(200, 52, 90, 15);
      operationPanel.add(furjgomb);

      JButton epitsgomb = new JButton("craft");
      epitsgomb.setMargin(new Insets(0, 0, 0, 0));
      epitsgomb.setBounds(200, 72, 90, 15);
      operationPanel.add(epitsgomb);

      JLabel lblMine = new JLabel("Bányássz");
      lblMine.setBounds(32, 92, 70, 15);
      operationPanel.add(lblMine);

      JButton banyagomb = new JButton("Bányássz");
      banyagomb.setMargin(new Insets(0, 0, 0, 0));
      banyagomb.setBounds(200, 92, 90, 15);
      operationPanel.add(banyagomb);

      banyagomb.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
           if(theKivalasztott==null) return;
           try {
             theKivalasztott.mine();
           } catch(Exception e) {e.printStackTrace();}
           votma(theKivalasztott);
         }
      });

      furjgomb.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
           if(theKivalasztott==null) return;
           try {
             theKivalasztott.drill();
           } catch(Exception e) {e.printStackTrace();}
           votma(theKivalasztott);
         }
      });

      epitsgomb.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt)
         {
           if(theKivalasztott==null) return;
           try {
             //TODO if (
             //TODO list state var

             }
             theKivalasztott.mine();
           } catch(Exception e) {e.printStackTrace();}
           votma(theKivalasztott);
         }
      });

      mozogjgomb.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(java.awt.event.ActionEvent evt)
         {
         if(theKivalasztott==null) return;
           try {
               theKivalasztott.mine();
               //TODO list change on select player
               //TODO THIS
           } catch(Exception e) {e.printStackTrace();}
             votma(theKivalasztott);
         }
      });



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
