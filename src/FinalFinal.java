import java.awt.*;
import javax.swing.*;

public class FinalFinal extends JFrame {
  private static final long serialVersionUID = 1234567L;
  private static Game g;

  public FinalFinal(){
    g=new Game();
    try {
      g.start();
    } catch(Exception e) {e.printStackTrace();};

    setTitle("Game test");
    setSize(600, 600);
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  };

  @Override
  public void paint(Graphics gr) {
      g.paint(gr);
  }


	public static void main(String args[]) {
    FinalFinal instance = new FinalFinal();
    try {
      for(int i=0; i<20; i++) {
        Thread.sleep(1000);
        g.test();
        instance.repaint(); // TRIGGER, IMPORTANT
        System.err.println("move "+i);
      }
    } catch(Exception e) {e.printStackTrace();};

  }

}
