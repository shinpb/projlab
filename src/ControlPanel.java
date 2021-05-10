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
    private JPanel kivalasztottCore;
    private JLabel kivalasztottLayer;
    private Astronaut theKivalasztott;
    private ArrayList<Astronaut> aKorbenLepettMar = new ArrayList<Astronaut>();
    private JMenu mnCrft;
    private JMenuItem mnItemRobo;
    private JMenuItem mnItemGate;
    private enum epitmod { nemtom, robot, gate}
    private epitmod activeEpitmod=epitmod.nemtom;
    private JMenu mnAster;
    private ArrayList<Place> ideLephetHelyek;
    private Place ideLepj;
    private ArrayList<Asteroid> asteroidField=null;

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
        if(newround) {
          //minden asztronauta lépett már
          aKorbenLepettMar.clear();
          try {
            game.step();
          } catch(Exception e) {e.printStackTrace();}
          //ronda, de ez van ...
          setAsteroidField(game.getAsteroids());
        }
        for(int i=0; i<astronauts.size(); i++) {
          Astronaut a = astronauts.get(i);
          if(aKorbenLepettMar.contains(a)) continue;
          JMenuItem item = new JMenuItem(nameOfAstronaut(a));
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

    private Color mat2col(Material m) {
      Color c=new Color(255, 255, 255, 80);
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
      return c;
    }
    private String nameOfAsteroid(Place ast) {
      if(asteroidField!=null) {
        int id=asteroidField.indexOf((Asteroid)ast);
        if(id>=0)return "Asteroid "+(id+1);
      }
      return (""+ast).replace("@", " #");
    }
    private String nameOfAstronaut(Astronaut astro) {
      return astro.getName();
    }

    private void selectAstronaut(Astronaut a){
      //a can be null
      int i=0;
      Color c=mat2col(null);
      mnAster.removeAll();
      mnAster.setText("Place");
      if(a==null) {
        lblAsterID.setText("");
        mnCrft.setText("Mit epits");
        activeEpitmod=epitmod.nemtom;
        kivalasztottCore.setBackground(new Color(255,255,255,0));
        kivalasztottLayer.setText("");
        ideLepj=null;
        ideLephetHelyek=null;
      }
      else
      {
        lblAsterID.setText(nameOfAsteroid(a.getPosition()));
        for(Material m: a.getInventory()){
          invarr[i].setBackground(mat2col(m));
          i++;
        }
        kivalasztottCore.setBackground(mat2col(a.getPosition().getCore()));
        kivalasztottLayer.setText(""+a.getPosition().getLayer());
        ideLephetHelyek=new ArrayList<Place>(a.getPosition().getNeighbours());
        for(int j=0; j<ideLephetHelyek.size(); j++) {
          Place p = ideLephetHelyek.get(j);
          JMenuItem item = new JMenuItem(nameOfAsteroid(p));
          item.setName(String.valueOf(j));
          item.addActionListener(new java.awt.event.ActionListener() {
             public void actionPerformed(java.awt.event.ActionEvent evt)
             {
                int id= Integer.parseInt(item.getName());
                mnAster.setText(item.getText());
                ideLepj=ideLephetHelyek.get(id);
             }
          });
          mnAster.add(item);
        }
      }
      for(; i<10; i++) {
        invarr[i].setBackground(c);
      }
    }

    private void votma(Astronaut a) {
      aKorbenLepettMar.add(a);
      updateAstronautList();
    }

    public ControlPanel(){

        astronauts = new ArrayList<Astronaut>();
        try {
          game=new Game();
          ArrayList<String> asd=new ArrayList<String>();
          asd.add("Joe");
          asd.add("Mari");
          asd.add("Hai");
          asd.add("Mao");
          game.start(asd);
        } catch(Exception e) {e.printStackTrace();}

        initControlPanel();
    }
    public ControlPanel(Game g0){
      game=g0;
      initControlPanel();
      astronauts = game.getAstronauts();
    }
    public ControlPanel(Game g0, ArrayList<Asteroid> af0){
      this(g0);
      setAsteroidField(af0);
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

      kivalasztottCore=new JPanel();
      kivalasztottCore.setBounds(240, 32, 20, 20);
      kivalasztottCore.setMinimumSize(new Dimension(20, 20));
      kivalasztottCore.setPreferredSize(new Dimension(20, 20));
      kivalasztottCore.setBackground(new Color(255,255,255,0));
      propertyPanel.add(kivalasztottCore);

      kivalasztottLayer=new JLabel();
      kivalasztottLayer.setBounds(225, 32, 15, 15);
      propertyPanel.add(kivalasztottLayer);

      JLabel lblInventory = new JLabel("Inventory");
      lblInventory.setBounds(32, 52, 114, 15);
      propertyPanel.add(lblInventory);

      for(int i=0; i<10; i++) {
          invarr[i]=new JPanel();
          invarr[i].setBounds(32+((i%5)*30), 72+(i/5)*30, 20, 20);
          invarr[i].setMinimumSize(new Dimension(20, 20));
          invarr[i].setPreferredSize(new Dimension(20, 20));
          invarr[i].setBackground(mat2col(null));
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

      mnAster = new JMenu("Asteroid");
      astermenubar.add(mnAster);

      astermenubar.setBounds(120, 32, 70, 15);
      operationPanel.add(astermenubar);

      JLabel lblFuras = new JLabel("Furás");
      lblFuras.setBounds(32, 52, 40, 15);
      operationPanel.add(lblFuras);

      JLabel lblCraft = new JLabel("Craftolas");
      lblCraft.setBounds(32, 72, 70, 15);
      operationPanel.add(lblCraft);

      JMenuBar craftMenuBar = new JMenuBar();

      mnCrft = new JMenu("Mit épits");
      craftMenuBar.add(mnCrft);

      mnItemRobo=new JMenuItem("robot");
      mnItemGate=new JMenuItem("gate");
      mnItemRobo.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(java.awt.event.ActionEvent evt)
       {
          mnCrft.setText(mnItemRobo.getText());
          activeEpitmod=epitmod.robot;
        }
      });
      mnItemGate.addActionListener(new java.awt.event.ActionListener() {
     public void actionPerformed(java.awt.event.ActionEvent evt)
       {
          mnCrft.setText(mnItemGate.getText());
          activeEpitmod=epitmod.gate;
        }
      });
      mnCrft.add(mnItemRobo);
      mnCrft.add(mnItemGate);

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
             if(activeEpitmod==epitmod.robot) {
               theKivalasztott.craftRobot();
               votma(theKivalasztott);
             } else if(activeEpitmod==epitmod.gate) {
               theKivalasztott.craftGate();
               votma(theKivalasztott);
             } else {}
           } catch(Exception e) {e.printStackTrace();}
         }
      });

      mozogjgomb.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(java.awt.event.ActionEvent evt)
         {
         if(theKivalasztott==null) return;
         if(ideLepj==null) return;
           try {
               theKivalasztott.moveTo(ideLepj);
               votma(theKivalasztott);
           } catch(Exception e) {e.printStackTrace();}
         }
      });



    }

    public void setAsteroidField(ArrayList<Asteroid> af){
      asteroidField=af;
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
