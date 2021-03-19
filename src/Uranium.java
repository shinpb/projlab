public class Uranium extends Material {
	public void mined() {
		if(asteroid.getNearSun()) {this.nearSunEffect();}
		
		else {asteroid.setCore(null);}
		
	}
	
	public void nearSunEffect() {
		asteroid.explode();
	}
	
	private int getID() {
		return(769);
	}
}
