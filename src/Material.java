/*

Nyersanyagok ősosztálya
ő nem kerül példányosításra

*/
public abstract class Material {
	//modell change
	protected Asteroid asteroid;

	//Beállítja, hogy a nyersanyag melyik aszteoidában van
	public void setAsteroid(Asteroid a) {
		asteroid = a;
	}
	
	//Ha kibányásszák, null-t tesz saját maga helyére
	public void mined() {
		asteroid.setCore(null);
	}
	
	//Felüldefiniálja minden nyersanyag, amely reagál a napközelségre
	public void nearSunEffect() {
		return;
	}
	
	//minden nyersanyagnak saját ID-je van
	protected int getID() {
		return (-1);
	}
	
	//Képes összehasonlítani 2 nyersanyagot az ID-jük alapján
	public boolean isSameType(Material m) {
		if(m.getID() == this.getID()) {return true;}
		else {return false;}
	}
}
