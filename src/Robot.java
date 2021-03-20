import java.util.Random;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : projlab
//  @ File Name : Robot.java
//  @ Date : 2021. 03. 19.
//  @ Author : Levente Vigh
//
//




public class Robot extends Entity {
	public Robot(Asteroid a) {
		super(a);
	}

	public void solarStormEffect() {
		die();
	}
	
	public void explosionEffect() {
		move();
	}
	
	public void step() {
		Random r = new Random();
		int stepOption = r.nextInt() %  2;
		
		if(0 == stepOption) 
			move();
		else 
			drill();
	}
	
	public void move() {
		Place[] neighbours = (Place[]) position.getNeighbours().toArray();
		
		Random r = new Random();
		int nextIndex = r.nextInt() % neighbours.length; 		
		Place nextPosition = neighbours[nextIndex];
		
		position.removeEntity(this);
		nextPosition.addEntity(this);
		//TODO beallitani a poziciot
	}
}