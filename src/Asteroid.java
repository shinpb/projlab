import java.util.Collection;
import java.util.Random;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collection;

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
	 * Az aszteroidan levo kapukat tarolo lista
	 */
	private Vector<Gate> gates;
	/**
	 * Az aszteroida magja
	 */
	private Material core;
	Random rand = new Random();
	
	/**
	 * Konstruktor
	 */
	public Asteroid() {
		gates = new Vector<Gate>();
		layers = rand.nextInt(4) + 1;  //4+1?
	}

	/**
	 * Konstruktor with setCore
	 */
	public Asteroid(Material m) {
		gates = new Vector<Gate>();
		layers = rand.nextInt(4) + 1;  //4+1?
		setCore(m);
	}
	/**
	 * Furas eseten lefuto figgveny
	 */
	public void getDrilled() {
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
		if(core != null) {
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
	 */
	public boolean replaceCore(Material m) {
		Logger.call("Asteroid.replaceCore", "Material = " + m.toString());
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
	 */
	public void checkNearSun() {  
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
	 * Kapu felvetele az aszteroidara
	 * @param g Kapu
	 */
	public void addGate(Gate g) {
		Logger.call("Asteroid.addGate ", "Gate = " + g.toString());
		gates.add(g);
		Logger.ret("");
	}
	
	/**
	 * Kapu eltavolitasa az aszteroidara
	 * @param g Kapu
	 */
	public void removeGate(Gate g) {
		Logger.call("Asteroid.removeGate ", "Gate = " + g.toString());
		for(int i = 0; i < gates.size(); i++) {
			if(gates.elementAt(i).equals(g))
				gates.remove(i);
		}
		Logger.ret("");
	}
	
	/**
	 * Napkozeliseg setterje
	 * @param b Napkozeliseg
	 */
	public void setNearSun(boolean b) {
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
	 */
	private void coreSunEffectTrigger() {
		Logger.call("Asteroid.coreSunEffectTrigger", "");
		if(isNearSun && layers == 0) {
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
		Logger.ret("");
	}
	
	/**
	 * A mag setterje
	 * @param m Nyersanyag
	 */
	public void setCore(Material m) {
		Logger.call("Asteroid.setCore", "Material = " + m.toString());
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
		Logger.call("Asteroid.addEntity", "Entity = " + e.toString());
		try{
			entities.add(e);
			e.setPosition(this);
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
	
	public void setLayer(int n){
		Logger.call("Asteroid.setLayer", "layers = " + n);
		layers=n;
		Logger.ret("");
	}
	
}
