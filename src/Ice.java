/*
A Material osztály leszármazottja
Saját ID-vel rendelkezik
Felüldefiniálja a nearSunEffect() metódust

*/
public class Ice extends Material {
	//Elszublimál napközelségre, tehát eltűnik, az aszteroidája magjából
	public void nearSunEffect() {
		asteroid.setCore(null);
	}

	//modell change
	protected int getID() {
		return(305);
	}
}
