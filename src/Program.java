import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.math.random;




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
		
		/*case "copyright":
			  try {readFile("copyright.txt");}
			  catch(Exception e) {e.printStackTrace();}
			  break;*/
	  
		case "1":
			  moveAstronaut();
			  System.out.println("Astronaut is on a better place now\n");
			  break;
			  
		case "2":
				drill();
				System.out.println("Getting closer...\n");
				break;

		case "3":
				getThatNyersanyag();
				System.out.println("You golddigger\n");
				break;
		case "4":
				//moveRobot();  A robot.move() nem mukodik
				System.out.println("It can move without your help anyways\n");
				break;
		case "5":
				drillWithRobot();
				System.out.println("Just like a real human\n");
				break;
		case "6":
			  robot();
			  System.out.println("Robot is ready to go\n");
			  break;
		case "7":
			  portal();
			  System.out.println("You have a portal in your pocket\n");
			  break;
		case "8":
			  buildGate();
			  System.out.println("Cake is a lie\n");
			  break;
		case "9":
			  bumm();
			  System.out.println("bumm\n");
			  break;
		case "10":
			  serialKilling();
			  System.out.println("My friend was mining uranium you murderer:(\n");
			  break;
		case "11":
			  purge();
			  System.out.println("Congrats, you killed everyone\n");
			  break;
		case "12":
			  ice();
			  System.out.println("Water escaped\n");
			  break;
		case "13":
			  putCore();
			  System.out.println("The inside, is what really matters\n");
			  break;
		case "14":
			  teleport();
			  System.out.println("Portal2 < Portal1 < This game\n");
			  break;
		}
		
	}
	
	public static void randomAnswer()throws FileNotFoundException{
		File file = new File("randomString.txt");
		Scanner filescn = new Scanner(file);
		ArrayList<String> answers = new ArrayList<String>();
        while(filescn.hasNextLine()){
            String line = filescn.nextLine();
            answers.add(line);
        }
		System.out.println(answers.get((int) ((Math.random() * (answers.size() - 0)) + 0)));
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
		asteroid.setNearSun(true);
	}
	
	public static void moveAstronaut() {
		Asteroid asteroid1 = new Asteroid();
		Asteroid asteroid2 = new Asteroid();
		Asteroid asteroid3 = new Asteroid();
		Asteroid asteroid4 = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid1);
		asteroid1.addNeighbour(asteroid2);
		asteroid1.addNeighbour(asteroid3);
		asteroid1.addNeighbour(asteroid4);
		astronaut.move();
	}
	
	public static void portal() {  //TODOinventory feltoltese
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		try{
		astronaut.craftGate();}catch(Exception e){e.printStackTrace();}
	}
	
	public static void buildGate() {  //TODOinventory feltoltese
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		try{
			astronaut.craftGate();}catch(Exception e){e.printStackTrace();}
		try{
			astronaut.deployGate();}catch(Exception e){e.printStackTrace();}
	}
	
	public static void robot() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid); //TODOinventory feltoltese
		astronaut.craftRobot();
	}

	public static void drillWithRobot() {
		Asteroid asteroid = new Asteroid();
		Robot robot = new Robot(asteroid);
		robot.drill();
	}
	
	public static void drill() {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		astronaut.drill();
	}
	
	public static void getThatNyersanyag(){
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		try{astronaut.mine();}catch(Exception e){e.printStackTrace();}
		
	}

	public static void moveRobot() {
		Asteroid asteroid1 = new Asteroid();
		Asteroid asteroid2 = new Asteroid();
		Asteroid asteroid3 = new Asteroid();
		Asteroid asteroid4 = new Asteroid();
		Robot robot = new Robot(asteroid1);
		asteroid1.addNeighbour(asteroid2);
		asteroid1.addNeighbour(asteroid3);
		asteroid1.addNeighbour(asteroid4);
		robot.move();
	}

	public static void ice(){
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		Ice ice = new Ice();
		Robot robot = new Robot(asteroid);
		Gate gate = new Gate();
		asteroid.setCore(ice);
		asteroid.setNearSun(true);
	}

	public static void putCore(){
		Asteroid asteroid = new Asteroid();
		Carbon carbon = new Carbon();
		asteroid.setCore(carbon);
		Astronaut astronaut = new Astronaut(asteroid);
		try{astronaut.mine();
			System.out.println("hallo");
			astronaut.putMaterialInAsteroid();
		}catch(Exception e){e.printStackTrace();}
		

	}

	public static void teleport(){       //TODOinventory feltoltese
		Asteroid asteroid1 = new Asteroid();
		Asteroid asteroid2 = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid1);
		try{
			astronaut.craftGate();
			astronaut.deployGate();
			asteroid1.removeEntity(astronaut);
			asteroid2.addEntity(astronaut);
			astronaut.deployGate();
			astronaut.move();
		}catch(Exception e){e.printStackTrace();}
	
			

	}

	public static void serialKilling(){
		Game game = new Game();
		//game.start();
		//game.nearSun();
	}

	public static void purge(){     //Game osztaly meg nincs kesz
		Game game = new Game();
		//game.start();
		//game.solarStorm();
	}


	public static void main(String args[]) {
		System.setIn(new UnClosableDecorator(System.in));
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Welcome to the Szoftverprojektlaboratóriumprojekt!\nWanna try? (type yes/no) ");
		String s = "";
		s = input.next();
		chosen(s);
				
		while(true){
			
			s = input.next();
			System.out.print("\033[H\033[2J");  //ez clear-eli a terminalt (lehet h windowson nem mukodik)
			chosen(s);
			
			try{randomAnswer();}catch(Exception e){e.printStackTrace();}
			
			
			
		}	

	}
	
	
}
