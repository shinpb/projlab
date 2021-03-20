import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Program{
	
	public static void chosen(String s) {
		switch (s) {
		  case "exit":
			  System.exit(0);
		    break;
		  case "no":
			  System.out.println("Okay than:(");
			  System.exit(0);
		  case "yes":
			  System.out.println("That's the spirit!\nChoose your fighter\n");
			  try {readFile("panic_skeleton.txt");}
			  catch(Exception e) {e.printStackTrace();}
			  break;
  
		  case "help":
			  try {readFile("panic_skeleton.txt");}
			  catch(Exception e) {e.printStackTrace();}
			  break;
			  
		  case "1":
			  moveAstronaut();
			  System.out.println("Astronaut is in a better place now");
			  break; 
		  case "6":
			  robot();
			  System.out.println("Robot is ready to go");
			  break;
		  case "7":
			  portal();
			  System.out.println("You have a portal in your pocket");
			  break;
		  case "8":
			  buildGate();
			  System.out.println("Cake is a lie");
			  break;
		  case "9":
			  bumm();
			  System.out.println("bumm");
			  break;
		}
		
	}
	
	public static void readFile(String str)throws FileNotFoundException{
		File file = new File(str);
		Scanner filescn = new Scanner(file);
        while(filescn.hasNextLine()){
            String line = filescn.nextLine();
            System.out.println(line);
        }
	}

	public static void bumm() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		Uranium uranium = new Uranium();
		Robot robot = new Robot(asteroid);
		Gate gate = new Gate();
		asteroid.setCore(uranium);
		asteroid.addEntity(astronaut);
		asteroid.addGate(gate);
		asteroid.setNearSun(true);
	}
	
	public static void moveAstronaut() {
		Asteroid asteroid1 = new Asteroid();
		Asteroid asteroid2 = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid1);
		asteroid1.addNeighbour(asteroid2);
		asteroid1.addEntity(astronaut);
		//astronaut.move(asteroid2);
	}
	
	public static void portal() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		asteroid.addEntity(astronaut);
		try{
		astronaut.craftGate();}catch(Exception e){}
	}
	
	public static void buildGate() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		asteroid.addEntity(astronaut);
		try{
			astronaut.craftGate();}catch(Exception e){}
		try{
			astronaut.deployGate();}catch(Exception e){}
	}
	
	public static void robot() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		asteroid.addEntity(astronaut);
		astronaut.craftRobot();
	}
	
	public static void drill() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		asteroid.addEntity(astronaut);
		
	}
	
	
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to the Szoftverprojektlaboratóriumprojekt!\nWanna try? (type yes/no) ");
		String s = "";
				
		while(true){
			s = input.next();
			chosen(s);
		}	

	}
	
	
}
