import java.util.Vector;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Place.java
//  @ Date : 2021. 03. 19.
//  @ Author : 
//
//




public class Place {
	
	protected Vector<Entity> entities;
	protected Vector<Place> neighbours;
	
	public Place() {
		entities = new Vector<Entity>();
		neighbours = new Vector<Place>();
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		for(int i = 0; i < entities.size(); i++) {
			if(entities.elementAt(i).equals(e)) {
				entities.remove(i);
			}
		}
	}
	
	public void explode() {
		for(int i = 0; i < entities.size(); i++) {
			entities.elementAt(i).explosionEffect();
		}
		for(int i = 0; i < neighbours.size(); i++) {
			neighbours.elementAt(i).removeNeighbour(this);
		}
	}
	
	public void addNeighbour(Place p) {
		neighbours.add(p);
	}
	
	public void removeNeighbour(Place p) {
		for(int i = 0; i < neighbours.size(); i++) {
			if(neighbours.elementAt(i).equals(p)) {
				neighbours.remove(i);
			}
		}
	}
}