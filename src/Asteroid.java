import java.util.Collection;
import java.util.Random;
import java.util.ArrayList;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Asteroid.java
//  @ Date : 2021. 03. 19.
//  @ Author : Mate Simko
//
//




public class Asteroid extends Place {
	/**
	 * Az aszteroida kulso retegeinek szama
	 */
	private int layers;
	/**
	 * Az aszteroida napkozelseget tarolo valtozo
	 */
	private boolean isNearSun;
	/**
	 * Az aszteroida magja
	 */
	private Material core;
	Random rand = new Random();

	/**
	 * Konstruktor
	 */
	public Asteroid() {
		layers = rand.nextInt(4) + 1;
		isActive = true;
	}

	/**
	 * Konstruktor with setCore
	 */
	public Asteroid(Material m) {
		layers = rand.nextInt(4) + 1;
		setCore(m);
		isActive = true;
	}
	/**
	 * Furas eseten lefuto figgveny
	 * @throws Exception
	 */
	public void getDrilled() throws Exception {
		Logger.call("Asteroid.getDrilled", "");
		if(layers == 0) {
			coreSunEffectTrigger();
		} else {
			layers = layers - 1;
		}
		Logger.ret("");
	}

	/**
	 * Az aszteroida banyaszasa
	 * @return Nyersanyag
	 */
	public Material mineCore() {
		Logger.call("Asteroid.mineCore", "");
		if(core != null && layers == 0) { //edit: balint
			Material temp = core;
			core.mined();

			core = null;
			Logger.ret("Material =" + temp.toString());
			return temp;
		}
		Logger.ret("null");
		return null;
	}

	/**
	 * Az aszteroida nyersanyag visszarakasaert
	 * felelos fuggveny
	 * @param m Nyersanyag
	 * @return bool sikeresseg
	 * @throws Exception
	 */
	public boolean replaceCore(Material m) throws Exception {
		Logger.call("Asteroid.replaceCore", "Material = " + m);
		if(core == null) {
		core = m;
		if(isNearSun)
			coreSunEffectTrigger();
		Logger.ret("true");
		return true;
		}
		Logger.ret("false");
		return false;
	}

	/**
	 * Napkozeliseg lekerese
	 * @throws Exception
	 */
	public void checkNearSun() throws Exception {
		Logger.call("Asteroid.checkNearSun", "");
		setNearSun(rand.nextBoolean());
		Logger.ret("");
	}

	/**
	 * A szomszedokat tartalmazo lista getterje
	 * @return Szomszedok
	 */
	public Collection<Place> getNeighbours() {
		Logger.call("Asteroid.getNeighbours", "");
		Logger.ret("neighbours");
		return neighbours;
	}

	/**
	 * Az entitasokat tartalmazo lista getterje
	 * @return Entitasok
	 */
	public Collection<Entity> getEntities() {
		Logger.call("Asteroid.getEntities", "");
		Logger.ret("entities");
		return entities;
	}

	/**
	 * Kapu eltavolitasa az aszteroidara
	 * @param g Kapu
	 */
	public void removeGate(Gate g) {
		Logger.call("Asteroid.removeGate ", "Gate = " + g);
		for(int i = 0; i < gates.size(); i++) {
			if(gates.elementAt(i).equals(g))
				gates.remove(i);
		}
		Logger.ret("");
	}

	/**
	 * Napkozeliseg setterje
	 * @param b Napkozeliseg
	 * @throws Exception
	 */
	public void setNearSun(boolean b) throws Exception {
		Logger.call("Asteroid.setNearSun", "isNarSun = " + b);
		isNearSun = b;
		if(isNearSun) {
			coreSunEffectTrigger();
		}
		Logger.ret("");
	}

	/**
	 * Napkozeliseg, es kifurt retegek eseten
	 * szol a magnak errol.
	 * @throws Exception
	 */
	private void coreSunEffectTrigger() throws Exception {
		Logger.call("Asteroid.coreSunEffectTrigger", "");
		if(isNearSun && layers == 0) {
			if(core != null)
				core.nearSunEffect();
		}
		Logger.ret("");
	}

	/**
	 * Napviharert felelos fuggveny
	 */
	public void solarStorm() {
		Logger.call("Asteroid.solarStorm", "");
		if(core != null && layers != 0) {
			for(int i = 0; i < entities.size(); i++) {
				entities.elementAt(i).solarStormEffect();
			}
		}
		for(int i = 0; i < gates.size(); i++) {
			gates.elementAt(i).setBolond(true);
		}
		Logger.ret("");
	}

	/**
	 * A mag setterje
	 * @param m Nyersanyag
	 */
	public void setCore(Material m) {
		Logger.call("Asteroid.setCore", "Material = " + m);
		core = m;
		if(core != null)
			core.setAsteroid(this);
		Logger.ret("");
	}

	/**
	 * A napkozelseg getterje
	 * @return Napkozelseg
	 */
	public boolean getNearSun() {
		Logger.call("Asteroid.getNearSun", "");
		Logger.ret("isNearSun");
		return isNearSun;
	}

	/**
	 * Entitas felvetele az aszteroidara
	 */
	public void addEntity(Entity e) {
		Logger.call("Asteroid.addEntity", "Entity = " + e);
		try{
			//sorrend fontos (balint)
			e.setPosition(this);
			entities.add(e);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		Logger.ret("");
	}

	/**
	 * Az aszeroidan levo osszes entitas
	 * nyersanyagainak lekeresere alkalmas fuggveny
	 * @return Nyersanyagok
	 */
	public ArrayList<Material> allMaterial() {
		Logger.call("Asteroid.allMaterial", "");
		ArrayList<Material> r=new ArrayList<Material>();
		for(Entity e: entities) {
			 Collection<Material> c= e.getInventory();
			 for(Material m:c) {
			 	r.add(m);
			 }
		}
		Logger.ret("materials");
		return r;
	}

	/**
	 * Retegvastagsag beallitasa
	 * @param n vastagsag
	 */
	public void setLayer(int n){
		Logger.call("Asteroid.setLayer", "layers = " + n);
		layers=n;
		Logger.ret("");
	}

	/**
	 * Retegvastagsag lekérése
	 * @return rétegvastagság
	 */
	public int getLayer(){
		Logger.call("Asteroid.getLayer", "");
		Logger.ret(""+layers);
		return layers;
	}

	/**
	 * Kapu felvetele az aszteroidara
	 * @param g Kapu
	 */
	public void addGate(Gate g) {
		Logger.call("Asteroid.addGate ", "Gate = " + g);
		gates.add(g);
		g.setPosition(this);
		Logger.ret("");
	}

	/**
	 * Core getter
	 * @return Core
	 */
	public Material getCore() {
		return core;
	}

}
