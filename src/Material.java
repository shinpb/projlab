
public abstract class Material {
	private Asteroid asteroid;
	
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
