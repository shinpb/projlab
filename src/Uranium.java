/*
A Material osztály leszármazottja
Saját ID-vel rendelkezik
Felüldefiniálja a mine() és nearSunEffect() metódusokat

*/
public class Uranium extends Material {
	
	//Bányászat közben meghívja a nearSunEffect()-et, ellenőrzi, hogy van e még felette kőzetréteg
	public void mined() {
		Logger.call("uranium.mined","");
		if(asteroid.getNearSun()) {this.nearSunEffect();}
		
		else {asteroid.setCore(null);}

		Logger.ret("");
		
	}
	//ha nincs kőzetréteg és napközelben van,robban
	public void nearSunEffect() {
		Logger.call("uranium.nearSunEffect","");
		asteroid.explode();
		Logger.ret("");
	}

	//modell change
	protected int getID() {
		return(769);
	}
}
