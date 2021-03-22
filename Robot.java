import java.util.Random;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : projlab
//  @ File Name : Robot.java
//  @ Date : 2021. 03. 22.
//  @ Author : Levente Vigh
//
//




public class Robot extends Entity {
	public Robot(Asteroid a) {
		super(a);
	}

	//napvihar hatasara a robot elromlik
	public void solarStormEffect() {
		die();

		Logger.ret("");
	}
	
	//robbanas hatasara a robot veletlenszeruen egy masik szomszedos aszteroidara esik
	public void explosionEffect() {
		Logger.call("Robot.explosionEffect()","");

		move(); //milyen szerencse hogy a robot mozgaskor is veletlenszeruen egy szomszedos aszteroidara lep

		Logger.ret("");
	}
	
	//a robot veletlenszeruen vegrehajt egy lepest
	public void step() {
		Logger.call("Robot.step()","");

		Random r = new Random();
		int stepOption = r.nextInt() %  2;
		
		if(0 == stepOption) 
			move();
		else 
			drill();

		Logger.ret("");
	}
	
	//a robot veletlenszeruen egy szomszedos aszteroidara lep
	public void move() {
		Logger.call("Robot.move()","");

		Place[] neighbours = (Place[]) position.getNeighbours().toArray();
		
		//veletlenszzeruen valasztunk egy uj helyet
		Random r = new Random();
		int nextIndex = r.nextInt() % neighbours.length; 		
		Place nextPosition = neighbours[nextIndex];
		
		position.removeEntity(this); //eltavolitja magat a regi helyerol
		nextPosition.addEntity(this); //felteszi magat az uj helyere
		
		Logger.ret("");
	}

	
}