public class Uranium extends Material {
	public void mined() {
		if(asteroid.getNearSun()) {this.nearSunEffect();}
		
		else {asteroid.setCore(null);}
		
	}
	
	public void nearSunEffect() {
		asteroid.explode();
	}

	//modell change
	protected int getID() {
		return(769);
	}
}
