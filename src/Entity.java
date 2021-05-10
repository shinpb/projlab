import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : projlab
//  @ File Name : Entity.java
//  @ Date : 2021. 03. 29.
//  @ Author : Levente Vigh
//
//




public abstract class Entity {

	/**
	 *  az aszteroida amin az entitas all
	 */
	protected Asteroid position;

	/**
	 *  konstruktor
	 */
	public Entity() { }

	/**
	 * konstruktor
	 * @param a - az aszteroida amin el lesz helyezve az entitas
	 */
	public Entity(Asteroid a) {
		position = a;
		a.addEntity(this);
	}

	/**
	 * meghal
	 */
	public void die() {
		Logger.call("Enity.die()","");

		//amikor meghal lekerul az aszteroidarol
		position.removeEntity(this);
		position = null;

		Logger.ret("");
	}

	/**
	 * aszteroidak kozott mozog/teleportal
	 */
	public abstract void move() throws Exception;

	/**
	 * napviharban milyen hatas eri az entitast
	 */
	public abstract void solarStormEffect();

	/**
	 * robbanas milyen hatasssal van az entitasra
	 */
	public abstract void explosionEffect() throws Exception;


	/**
	 * egy lepest vegrehajt az entitas
	 * @throws Exception - lasd: a leszarmazottakban hivott fuggvenyek
	 */
	public void step() throws Exception { Logger.call("Enity.step()","");  Logger.ret(""); }

	public void setPosition(Asteroid a) throws Exception {
		String s = (null == a) ? "null" : a.toString();
		Logger.call("Enity.setPosition()", "Asteroid a: " + s);

		if(null == a)
			throw new Exception("Argument passed to Entity.setPosition(...) is null");
		if(position != null) //edit: Balint
			position.removeEntity(this); //eloszor eltavolitja magat a regi helyerol
		position = a;

		Logger.ret("");
	}

	/**
	 * @return az entitasnal levo nyersanyagok
	 */
	public Collection<Material> getInventory() {
		Logger.call("Enity.getInventory()", "");
		ArrayList<Material> inv = new ArrayList<Material>();
		Logger.ret("inv: " + inv);
		return inv;
	}

	public Asteroid getPosition() {
		Logger.call("Enity.getPosition()","");
		Logger.ret("position: " + position);
		return position;
	}

	public void moveTo(Place destination) throws Exception {
		String s = (null == destination) ? "null" : destination.toString();
		Logger.call("Enity.moveTo()","destination: " + s);

		if(null == destination)
			throw new Exception("Argument passed to Entity.moveTo(...) is null");

		destination.addEntity(this); //ezutan felrakja magat az uj helyere
		Logger.ret("");
	}

	public abstract String getImageFileName();
	
}
