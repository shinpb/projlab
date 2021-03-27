import java.util.Random;

public class Ufo extends Entity {
	
	public Ufo(Asteroid a) {
		super(a);
	}
	
	@Override
	public void move() {
		Logger.call("Ufo.move()","");

		Place[] neighbours = (Place[]) position.getNeighbours().toArray();
		
		//veletlenszzeruen valasztunk egy uj helyet
		Random r = new Random();
		int nextIndex = r.nextInt() % neighbours.length; 		
		Place nextPosition = neighbours[nextIndex];
		
		position.removeEntity(this); //eltavolitja magat a regi helyerol
		nextPosition.addEntity(this); //felteszi magat az uj helyere
		
		Logger.ret("");
	}

	@Override
	public void solarStormEffect() {
		
	}

	@Override
	public void explosionEffect() {
		
	}
	
	public void drill() {
		
	}
	
	public void die() {
		
	}
	
	public void mine() {
		position.mineCore();
	}
}
