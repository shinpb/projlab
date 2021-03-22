//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Gate.java
//  @ Date : 2021. 03. 19.
//  @ Author : Mate Simko
//
//


public class Gate extends Place {
	/**
	 * A kapu allapotat tarolo valtozo
	 */
	private boolean isActive;
	/**
	 * A kapu poziciojat tarolo valtozo
	 */
	private Asteroid position;
	/**
	 * A kapu parjat tarolo valtozo
	 */
	private Gate otherEnd;
	
	/**
	 * Konstruktor
	 */
	public Gate() {
		isActive = false;
	}
	
	/**
	 * Pozicio beallitasa
	 * @param a Aszteroida
	 */
	public void setPosition(Asteroid a) {
		Logger.call("Gate.setPosition ", "Asterioda = " + a);
		position = a;
		Logger.ret("");
	}
	
	/**
	 * A kapu parjanak beallitasa
	 * @param g Kapu
	 */
	public void setOtherEnd(Gate g) {
		Logger.call("Gate.setOtherEnd", " Gate = " + g);
		otherEnd = g;
		Logger.ret("");
	}
	
	/**
	 * Entitas belepese a kapun
	 * @param e Entitas
	 */
	public void addEntity(Entity e) {
		Logger.call("Gate.AddEntity", " Entity = " + e);
		if(isActive) {
			otherEnd.getPosition().addEntity(e);
		} else {
			position.addEntity(e);
		}
		Logger.ret("");
	}
	
	/**
	 * Robbanas fuggveny
	 * Eltavolitja onmagat es a parjat
	 */
	public void explode() {
		Logger.call("Gate.explode", "");
		otherEnd.setPosition(null);
		otherEnd.getPosition().removeGate(otherEnd);
		otherEnd.setOtherEnd(null);
		position.removeGate(this);
		Logger.ret("");
	}
	
	/**
	 * Kapu aktivalasa
	 */
	public void enable() {
		Logger.call("Gate.enable", "");
		isActive = true;
		Logger.ret("");
	}
	
	/**
	 * Kapu inaktivalasa
	 */
	public void disable() {
		Logger.call("Gate.disable", "");
		isActive = false;
		Logger.ret("");
	}
	
	/**
	 * A kapu poziciojanak lekerese
	 * @return Aszteroida
	 */
	public Asteroid getPosition() {
		Logger.call("Gate.getPosition", "");
		Logger.ret("position");
		return position;
	}
}
