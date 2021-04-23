import java.awt.*;

public interface IDraw {
	  public void paint(Graphics gr);
    public void setDrawPos(int x, int y);
    public void setDrawScale(float s);
}
