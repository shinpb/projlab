import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;



//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : projlab
//  @ File Name : Astronaut.java
//  @ Date : 2021. 03. 19.
//  @ Author : Levente Vigh
//
//




public class Astronaut extends Entity {
	private Collection<Gate> gates;
	private Collection<Material> collectedMaterials;
	
	public Astronaut(Asteroid a) {
		super(a);
		
		gates = new LinkedList<Gate>();
		collectedMaterials = new LinkedList<Material>();
	}
	
	public void move() {
		
		ArrayList<Place> neighbours = new ArrayList<Place>(position.getNeighbours());
		
		
		System.out.println("\nJelenlegi pozicionak " + neighbours.size() + " szomszedos helye van.");
		System.out.println("Add meg a sorszamat az uticelodnak!" + " (1 - " + neighbours.size() + ")");
		
		Scanner input = new Scanner(System.in);
		int destinationID = input.nextInt();
		
		while(destinationID < 1 || destinationID > neighbours.size()) {
			System.out.println("\nFiam annyi eszed van, mint egy furik majomnak!");
			System.out.println("\nJelenlegi pozicionak " + neighbours.size() + " szomszedos helye van.");
			System.out.println("Add meg a sorszamat az uticelodnak!" + " (1 - " + neighbours.size() + ")");
			
			destinationID = input.nextInt();
		}
		input.close();
		
		Place nextPosition = neighbours.get(destinationID - 1);
		
		position.removeEntity(this);
		nextPosition.addEntity(this);
		//TODO beallitani a poziciot
	}
	
	
	
	public void craftRobot() {
		BillCreator bc = new BillCreator();
		BillOfMaterial b = bc.createRobotBill();
		
		Collection<Material> newInventory = b.checkInventory(collectedMaterials);
		
		if(newInventory != null) {
			collectedMaterials = newInventory;
			Robot r = new Robot(position);
			position.addEntity(r);
		}
	}
	
	public void craftGate() throws Exception {
		if(gates.size() > 2)
			throw new Exception("Missing place for new teleport gate(s) in inventory!");
		
		BillCreator bc = new BillCreator();
		BillOfMaterial b = bc.createGateBill();
		
		Collection<Material> newInventory = b.checkInventory(collectedMaterials);
		
		if(newInventory != null) {
			collectedMaterials = newInventory;
			
			Gate g1 = new Gate();
			Gate g2 = new Gate();
			
			g1.setOtherEnd(g2);
			g2.setOtherEnd(g1);
			
			gates.add(g1);
			gates.add(g2);
		}
	}
	
	public void mine() throws Exception {
		if(collectedMaterials.size() == 10)
			throw new Exception("Missing place for new material in inventory!");
		
		position.mineCore();
	}
	
	public void solarStormEffect() {
		die();
	}
	
	public void deployGate() throws Exception {
		if(gates.size() == 0)
			throw new Exception("Can not deploy portal gate: You have no portal gate(s) in your inventory");
		
		Gate[] portalGates = (Gate[]) gates.toArray();
		
		position.addGate(portalGates[0]);
		gates.remove(portalGates[0]);
	}
	
	public void step() throws Exception {
		System.out.println("\nValassz egy muveletet es add meg a sorszamat!");
		System.out.println("1 - mozgas\t2 - furas\t3 - banyaszas");
		System.out.println("4 - nyersanyag lehelyezes\t5 - portal kapu keszites\t6 - robot epites");
		
		Scanner input = new Scanner(System.in);
		int selectedID = input.nextInt();
		
		while(selectedID < 1 || selectedID > 6) {
			System.out.println("\nValassz egy muveletet es add meg a sorszamat!");
			System.out.println("1 - mozgas\t2 - furas\t3 - banyaszas");
			System.out.println("4 - nyersanyag lehelyezes\t5 - portal kapupar keszites\t6 - robot epites");
			
			selectedID = input.nextInt();
		}
		input.close();
		
		switch(selectedID) {
			case 1:
				move();
				break;
			case 2:
				drill();
				break;
			case 3:
				mine();
				break;
			case 4:
				putMaterialInAsteroid();
				break;
			case 5:
				craftGate();
				break;
			case 6:
				craftRobot();
				break;
		}
	}
	
	public void explosionEffect() {
		die();
	}
	
	public void putMaterialInAsteroid() throws Exception {
		Material[] materials = (Material[]) collectedMaterials.toArray();
		
		System.out.println("\nJelenleg " + materials.length + " nyersanyag van nalad.");
		for(int i = 0; i < materials.length; i++) 
			System.out.println("sorszam: " + i+1 + " nyersanyag: " +  materials[i].toString());
		System.out.println("\nAdd meg a sorszamat a nyersanyagnak, amit le szeretnel helyezni!" + " (1 - " + materials.length + ")");
		
		Scanner input = new Scanner(System.in);
		int selectedID = input.nextInt();
		
		while(selectedID < 1 || selectedID > materials.length) {
			System.out.println("\nFiam annyi eszed van, mint egy furik majomnak!");
			System.out.println("\nJelenleg " + materials.length + " nyersanyag van nalad.");
			for(int i = 0; i < materials.length; i++) 
				System.out.println("sorszam: " + i+1 + " nyersanyag: " +  materials[i].toString());
			System.out.println("\nAdd meg a sorszamat a nyersanyagnak, amit le szeretnel helyezni!" + " (1 - " + materials.length + ")");
			
			selectedID = input.nextInt();
		}
		
		
		if( !position.replaceCore(materials[selectedID - 1]) ) 
			throw new Exception("Can not replace asteroid core. Core is not empty.");
		
		collectedMaterials.remove(materials[selectedID - 1]);
	}
}
