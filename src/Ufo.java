import java.util.Random;

public class Ufo extends Entity {
	
	public Ufo(Asteroid a) {
		super(a);
	}

	public Ufo() {

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
		Logger.call("Ufo.solarStormEffect()","");
		Logger.ret("");
	}

	@Override
	public void explosionEffect() {
		Logger.call("Ufo.explosionEffect()","");
		Logger.ret("");
	}
	
	public void drill() {
		Logger.call("Ufo.drill()","");
		Logger.ret("");
	}
	
	public void die() {
		Logger.call("Ufo.die()","");
		Logger.ret("");
	}
	
	public void mine() {
		Logger.call("Ufo.mine()","");
		position.mineCore();
		Logger.ret("");
	}
}
