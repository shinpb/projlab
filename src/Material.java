
public abstract class Material {
	//modell change
	protected Asteroid asteroid;

	public void setAsteroid(Asteroid a) {
		asteroid =a;
	}
	
	public void mined() {
		asteroid.setCore(null);
	}
	
	public void nearSunEffect() {
		return;
	}
	
	protected int getID() {
		return (-1);
	}
	
	public boolean isSameType(Material m) {
		if(m.getID() == this.getID()) {return true;}
		else {return false;}
	}
}
