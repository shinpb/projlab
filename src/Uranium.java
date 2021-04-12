/*
A Material osztály leszármazottja
Saját ID-vel rendelkezik
Felüldefiniálja a mine() és nearSunEffect() metódusokat

*/
public class Uranium extends Material {

	private int exp = 0;

	//Bányászat közben meghívja a nearSunEffect()-et, ellenőrzi, hogy van e még felette kőzetréteg
	public void mined() {
		Logger.call("uranium.mined","");
		//szerintem ez ide nem kell
		/*if(asteroid.getNearSun()) {this.nearSunEffect();}

		else {asteroid.setCore(null);}*/
		asteroid.setCore(null);

		Logger.ret("");

	}
	//ha nincs kőzetréteg és napközelben van,robban
	public void nearSunEffect() throws Exception {
		Logger.call("uranium.nearSunEffect","");
		exp++;
		if(exp == 3){asteroid.explode();}
		Logger.ret("");
	}

	public void setExp(int n) { //balint
		exp=n;
	}

	//modell change
	protected int getID() {
		return(769);
	}
}
