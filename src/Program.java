import java.util.Scanner; 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/*
A szkeleton program
Menüvezérelt, eseteket bemutató
*/

public class Program{

	/*
		Visszaad egy olyan Astronautát, aki
		a paraméterül kapott aszteroidén van
		és minden anyagból van 2 a tárolójában
		hasznos az építős tesztesetekhez
		mellékhatásaként az Asteroid magja üres lesz
		NEM támogatja a naplózást
	*/
	private static Astronaut bigBackPack(Asteroid asteroid) throws Exception {
		Astronaut astronaut = new Astronaut(asteroid);
		try{
			for(int i=0; i<2; i++){
				asteroid.setCore(new Carbon());
				astronaut.mine();
				asteroid.setCore(new Iron());
				astronaut.mine();
				asteroid.setCore(new Ice());
				astronaut.mine();
				asteroid.setCore(new Uranium());
				astronaut.mine();
			}
		}catch(Exception e){e.printStackTrace();}
		return astronaut;
	}

/*
A menürendszer lelke, az egyes esetekhez
a hozzájuk tartozó függvént rendeli
nyújt help-et, és exitre kilép
Főbb funkciói:
	exit-re, no ra
		kilép a programból
	yes-re, help-re
		kiírja az elérhető menüpontokat
	1-re
		elindítja a moveAstronaut forgatókönyvét
	2-re
		elindítja a drill forgatókönyvét
	3-re
		elindítja a mine forgatókönyvét
	4-re
		elindítja a moveRobot forgatókönyvét
	5-re
		elindítja a robotDrill forgatókönyvét
	6-re
		elindítja a craftRobot forgatókönyvét
	7-re
		elindítja a craftGate forgatókönyvét
	8-re
		elindítja a deployGate forgatókönyvét
	9-re
		elindítja a asteroidExlpodes forgatókönyvét
	10-re
		elindítja a setNearSun forgatókönyvét
	11-re
		elindítja a solarStorm forgatókönyvét
	12-re
		elindítja a iceSublimate forgatókönyvét
	13-re
		elindítja a putCoreIntoEmptyAsteroid forgatókönyvét
	14-re
		elindítja a moveAstronaut és teleportAstronmaut forgatókönyvét
*/
	public static void chosen(String s) throws Exception {
		switch (s) {
		case "exit":
			  System.exit(0);
		      break;
		case "no":
			  System.out.println("Okay than:(");
			  System.exit(0);
			  break;
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
				//TODO CHECK
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
	
	/*
	Ez a függvény menő kis üzeneteket
	dobál vissza, véletlenszerűen
	csak hogy a telsztelők se unják halálra magukat
	*/
	public static void randomAnswer()throws FileNotFoundException{
		File file = new File("randomString.txt");
		Scanner filescn = new Scanner(file);
		ArrayList<String> answers = new ArrayList<String>();
        while(filescn.hasNextLine()){
            String line = filescn.nextLine();
            answers.add(line);
        }
		System.out.println(answers.get((int) ((Math.random() * (answers.size() - 0)) + 0)));
		filescn.close();
	}

	/*
	compact File reader
	olvas, kiír, bezár
	a soronkéni olvasás és kiírás eléég gyakori 
	ahoz, hogy ez egy hasznos függvény legyen
	*/
	public static void readFile(String str)throws FileNotFoundException{
		File file = new File(str);
		Scanner filescn = new Scanner(file);
        while(filescn.hasNextLine()){
            String line = filescn.nextLine();
            System.out.println(line);
        }
		filescn.close();
	}

	/*
	scenario for Asteroid exposion
	létrehoz egy aszteroidát, rajra
	egy robottal és egy asztonautával,
	egy kapuval, ami kapcsolódik egy 
	másik aszeroidához, majd felrobbantja
	(a robor kifúrja az aszeroidát, ami urániummagú, 
	majd napközelbe kerül)
	*/
	public static void bumm() throws Exception {
		Asteroid asteroid = new Asteroid();
		Asteroid asteroid_remote = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		Uranium uranium = new Uranium();
		Robot robot = new Robot(asteroid);
		Gate gate = new Gate();
		Gate gate2 = new Gate();
		gate.setOtherEnd(gate2);
		gate2.setOtherEnd(gate);
		gate.setPosition(asteroid);
		gate2.setPosition(asteroid_remote);
		try {
		for(int i=0; i<30; i++)
			robot.drill();
		}catch(Exception e) {e.printStackTrace();}
		asteroid.setCore(uranium);
		asteroid.setNearSun(true);
	}
	
	/*
	scenario for move astronaut
	létrehoz 4 aszteroidát, ezek egymás szomszédjai
	ratesz egy asztronautat
	a user kivalaszthatja, melyik szomszedra szeretne ugrani
	*/
	public static void moveAstronaut() throws Exception {
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

	/*
	scenario for craft gate
	létrehoz egy aszteroidan allo asztronautat
	feltolti az inventory-jat a szukseges anyagokkal
	carft-ol egy gate-et
	*/
	
	public static void portal() throws Exception { 
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = bigBackPack(asteroid);
		try{
		astronaut.craftGate();}catch(Exception e){e.printStackTrace();}
	}
	/*
	scenario for deploy gate
	létrehoz egy aszteroidan allo asztronautat
	feltolti az inventory-jat a szukseges anyagokkal
	carft-ol egy gate-et, majd le is teszi azt
	*/

	
	public static void buildGate() throws Exception { 
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = bigBackPack(asteroid);
		try{
			astronaut.craftGate();}catch(Exception e){e.printStackTrace();}
		try{
			astronaut.deployGate();}catch(Exception e){e.printStackTrace();}
	}
	
	/*
	scenario for craft robot
	létrehoz egy aszteroidan allo asztronautat
	feltolti az inventory-jat a szukseges anyagokkal
	carft-ol egy robotot
	*/

	public static void robot() throws Exception {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = bigBackPack(asteroid);
		astronaut.craftRobot();
	}

	/*
	scenario for drill with robot
	létrehoz egy aszteroidan allo robotot
	robot belefur az szteroidaba
	*/

	public static void drillWithRobot() throws Exception {
		Asteroid asteroid = new Asteroid();
		Robot robot = new Robot(asteroid);
		robot.drill();
	}

	/*
	scenario for drill with astronaut
	létrehoz egy aszteroidan allo asztronautat
	asztronauta belefur az aszteroidaba
	*/
	
	public static void drill() throws Exception {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		astronaut.drill();
	}

	/*
	scenario for mine
	létrehoz egy aszteroidan allo asztronautat
	asztronauta banyaszik
	*/
	
	public static void getThatNyersanyag() throws Exception {
		Asteroid asteroid = new Asteroid(new Iron());
		Astronaut astronaut = new Astronaut(asteroid);
		asteroid.setLayer(0);
		try{astronaut.mine();}catch(Exception e){e.printStackTrace();}
		
	}

	/*
	scenario for move robot
	létrehoz 4 aszteroidát, ezek egymás szomszédjai
	ratesz egy robotot
	meghivja a robot move()-jat
	*/

	public static void moveRobot() throws Exception {
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

	/*
	scenario for ice sublimates
	létrehoz egy aszteroidan allo asztronautat, egy robotot
	kozetretege 0
	magja vizjeg
	meghivja a setNearSun() fgv-t, ami elszublimaltatja a jeget (core = null)
	*/

	public static void ice() throws Exception {
		Asteroid asteroid = new Asteroid();
		Astronaut astronaut = new Astronaut(asteroid);
		Ice ice = new Ice();
		asteroid.setLayer(0);
		asteroid.setCore(ice);
		asteroid.setNearSun(true);
	}

	/*
	scenario for put material into empty asteroid
	létrehoz egy aszteroidan allo asztronautat
	aszteroida magja szen, kozetretege 0
	asztronauta kibanyassza a nyersanyagot, majd visszateszi
	*/

	public static void putCore() throws Exception {
		Asteroid asteroid = new Asteroid();
		Carbon carbon = new Carbon();
		asteroid.setLayer(0);
		asteroid.setCore(carbon);
		Astronaut astronaut = new Astronaut(asteroid);
		try{astronaut.mine();

			astronaut.putMaterialInAsteroid();
		}catch(Exception e){e.printStackTrace();}
		

	}

	/*
	scenario for teleport
	létrehoz 2 aszteroidat, egyiknek egy rajta allo asztronautat
	feltolti az asztronauta hatizsakjat a szukseges nyersanyagokkal
	craft-ol egy kaput, leteszi ahol all
	atmegy a masik aszteoidara (ok nem szomszedosak), oda leteszi a kapu parjat
	hasznalja a kaput
	*/

	public static void teleport() throws Exception {
		Asteroid asteroid1 = new Asteroid();
		Asteroid asteroid2 = new Asteroid();
		Astronaut astronaut = bigBackPack(asteroid1);
		try{
			astronaut.craftGate();
			astronaut.deployGate();
			asteroid1.removeEntity(astronaut);
			asteroid2.addEntity(astronaut);
			astronaut.deployGate();
			astronaut.move();
		}catch(Exception e){e.printStackTrace();}
	}

	/*
	scenario for nearsun effect
	nearSunTest() letrehoz egy-egy 0 kozetretegu asteroidat kulonbozo nyersanyagokkal, es egy ureset is, hozzaadja az asteroidfield-hez
	ures asteroidara rateszi az asztronautat
	az aszteroidamezo kozel kerul a naphoz (uranium magu felrobban)
	*/

	public static void serialKilling() throws Exception {
		Game game = new Game();
		game.NearSunTest();
		game.nearSun();

	}

	/*
	scenario for solarstorm effect
	nearSunTest() letrehoz egy-egy 0 kozetretegu asteroidat kulonbozo nyersanyagokkal, es egy ureset is, hozzaadja az asteroidfield-hez
	ures asteroidara rateszi az asztronautat
	aszteroidamezon atmegy egy napvihar (asztronautra el tud bujni, az ures magban)
	*/

	public static void purge() throws Exception {     
		Game game = new Game();
		game.NearSunTest();				
		game.solarStorm();

	}


	public static void main(String args[]) {
		/*try {
			System.setIn(new UnClosableDecorator(System.in));
			
			Scanner input = new Scanner(System.in);
			
			System.out.println("Welcome to the Szoftverprojektlaboratóriumprojekt!\nWanna try? (type yes/no) ");
			String s = "";
			s = input.next();
			chosen(s);

					
			while(true){
				
				s = input.next();
				//System.out.print("\033[H\033[2J");  //ez clear-eli a terminalt (lehet h windowson nem mukodik)
			Logger.clear();
				chosen(s);
				
			Logger.print();
				try{randomAnswer();}catch(Exception e){e.printStackTrace();}
				
				
				
			}
			
		} catch(Exception e) { 
			e.printStackTrace();
		}*/
		
		MainPanel mp = new MainPanel();
		mp.setVisible(true);
	}
}
