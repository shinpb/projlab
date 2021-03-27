import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Proto {

	public enum State {
		S_IN_INIT, S_IN_LINK, S_IN_STATE,
		S_RUN,
		S_OUT, S_OUT_FULL
	}

	private static boolean   run = true;
	private static State   state = State.S_IN_INIT;
	private static Game        g = new Game();
	private static ArrayList<Astronaut> astronauts	= new ArrayList<>();
	private static ArrayList<Asteroid>	 asteroids	= new ArrayList<>();
	private static ArrayList<Robot>         robots	= new ArrayList<>();
	//private static //ArrayList<Ufo> 	     	ufos	= new ArrayList<>();
	private static ArrayList<Gate> 	         gates	= new ArrayList<>();

	private static Material materialEnum(String s) {
		switch(s) {
			case "carbon":  return new Carbon();
			case "ice":     return new Ice();
			case "iron":    return new Iron();
			case "uranium": return new Uranium();
			case "null":    return null;
			default:
				System.err.println("Invelid material: \""+s+"\"\nAllowed: carbon,ice,iron,uranium,null\n");
				return null;
		}
	}


	public static void loadState(String f){ //TODO ???
		try{
			File file = new File(f);
			Scanner filecsn = new Scanner(file);

		}catch(FileNotFoundException fe){System.err.println("File does not found\n");}
	}

public static void init(String[] cmd){
	try{
		int n = Integer.parseInt(cmd[1]);
		switch(cmd[0]) {
			case "astronaut":
				for(int i = astronauts.size(); i < n; i++) astronauts.add(new Astronaut());
				break;
			case "asteroid":
				for(int i = asteroids.size(); i < n; i++) asteroids.add(new Asteroid());
				break;
			case "robot":
				for(int i = robots.size(); i < n; i++) robots.add(new Robot());
				break;
			case "gate":
				for(int i = gates.size(); i < n; i++) gates.add(new Gate());
				break;
//			case "ufo":
//				for(int i = ufos.size(); i < n; i++) ufos.add(new Ufo());
//				break;
			default:
				System.err.println("Syntax error: unknown type: \""+cmd[0]+"\" \""+cmd[1]+"\"\n");
			}
	} catch (Exception e) {
		System.err.println("Syntax error: int expected \""+cmd[0]+"\" \""+cmd[1]+"\"\n");
		e.printStackTrace();
	}
}


public static void setState(String[] cmd){
	switch(cmd[0]) {
		case "init":
			state = State.S_IN_INIT;
			break;
		case "link":
			state = State.S_IN_LINK;
			break;
		case "state":
			state = State.S_IN_STATE;
			break;
		case "run":
			state = State.S_IN_STATE;
			break;
		case "export":
			if(cmd.length == 2 && cmd[1].equals("all"))
				state = State.S_OUT_FULL;
			else
				state = State.S_OUT;
			break;
		default:
			System.err.println("Syntax error: unknown command (state change): \""+cmd[0]+"\"\n"+
													"Allowed: init, link, state, run, export, export all\n");
	}
}


public static void link_asteroid_asteroid(Asteroid aster, Asteroid aster2) {
	aster.addNeighbour(aster2);
	aster2.addNeighbour(aster);
}
public static void link_asteroid_gate(Asteroid aster, Gate ga) {
	aster.addGate(ga);
	ga.setPosition(aster);
}
public static void link_asteroid_astronaut(Asteroid aster, Astronaut astro) {
	aster.addEntity(astro);
}
public static void link_asteroid_robot(Asteroid aster, Robot robo) {
	aster.addEntity(robo);
}
public static void link_gate_gate(Gate ga, Gate ga2) {
	 ga.setOtherEnd(ga2);
	ga2.setOtherEnd(ga);
}

public static void linker(String[] cmd){
		int a,b;
		try{
			a = Integer.parseInt(cmd[1]);
			b = Integer.parseInt(cmd[2]);
			switch(cmd[0]) {
				case "asteroid":
					if(a < asteroids.size()) {
						Asteroid aster=asteroids.get(a);
						switch(cmd[2]) {
							case "asteroid":
								if(a!=b && b<asteroids.size()) {
									Asteroid aster2=asteroids.get(b);
									link_asteroid_asteroid(aster, aster2);
								} else System.err.println("Invalid asteroid id\n");
								break;
							case "gate":
								if(b<gates.size()) {
									Gate ga=gates.get(b);
									link_asteroid_gate(aster, ga);
								} else System.err.println("Invalid gate id\n");
								break;
							case "astronaut":
								if(b<astronauts.size()) {
									Astronaut astro=astronauts.get(b);
									link_asteroid_astronaut(aster, astro);
								} else System.err.println("Invalid asteroid id\n");
								break;
							case "robot":
								if(b<robots.size()) {
										Robot robo=robots.get(b);
										link_asteroid_robot(aster, robo);
								} else System.err.println("Invalid asteroid id\n");
								break;
							default:
								System.err.println("asteroid cant link to \""+cmd[2]+"\"\n");
						}
					} else System.err.println("Invalid asteroid id\n");
					break;
				case "astronaut":
					if(a < astronauts.size()) {
						Astronaut astro=astronauts.get(a);
						switch(cmd[2]) {
							case "asteroid":
								if(b < asteroids.size()) {
									Asteroid aster=asteroids.get(b);
									link_asteroid_astronaut(aster, astro);
								}
								else System.err.println("Invalid asteroid id\n");
								break;
							//TODO case gate
							default:
								System.err.println("astronaut cant link to \""+cmd[2]+"\"\n");
						}
					} else System.err.println("Invalid astronaut id\n");
					break;
				case "robot":
					if(a < robots.size()) {
						Robot robo=robots.get(a);
						switch(cmd[2]) {
							case "asteroid":
								if(b < asteroids.size()) {
									Asteroid aster=asteroids.get(b);
									link_asteroid_robot(aster, robo);
								}
								else System.err.println("Invalid asteroid id\n");
								break;
							default:
								System.err.println("robot cant link to \""+cmd[2]+"\"\n");
						}
					} else System.err.println("Invalid robot id\n");
					break;
				//TODO case ufo
				case "gate":
					if(a < gates.size()) {
						Gate ga=gates.get(a);
						switch(cmd[2]) {
							case "asteroid":
								if(b < asteroids.size()) {
									Asteroid aster=asteroids.get(b);
									link_asteroid_gate(aster, ga);
								}
								else System.err.println("Invalid asteroid id\n");
								break;
//							case "astronaut":
//								if(b < astronauts.size()) {
//									Astronaut astro=astronauts.get(b);
//TODO astronaut gate add
//									 //A lényeg, némi kód overheaddel
//								}
//								else System.err.println("Invalid astronaut id\n");
//								break;
							case "gate":
								if( (b!=a) && (b<gates.size())) {
									Gate ga2=gates.get(b);
									link_gate_gate(ga, ga2);
								}
								else System.err.println("Invalid gate id\n");
								break;
							default:
								System.err.println("gate cant link to \""+cmd[2]+"\"\n");
						}
					} else System.err.println("Invalid gate id\n");
					break;
				default:
					System.err.println("Syntax error: unknown command: \""+cmd[0]+"\"\n");
			}

		} catch (Exception e) {
			System.err.println("Syntax error: ints expected \""+cmd[0]+"\" \""+cmd[1]+"\" \""+cmd[2]+"\" \""+cmd[3]+"\"\n");
			e.printStackTrace();
		}

}

public static void gyereIdeInState(String[] cmd){

	try{
		switch(cmd[0]){
			case "asteroid":
				switch(cmd[2]){
					case "layer": asteroids.get(Integer.parseInt(cmd[1])).setLayer(Integer.parseInt(cmd[3]));
					break;
					case "core": asteroids.get(Integer.parseInt(cmd[1])).setCore(materialEnum(cmd[3]));
					break;
					default: System.err.println("Syntax error: state change: asteroid: invalid action\n");
				}
			break;
			case "astronaut":
				switch(cmd[2]){
					case "inventory":
					//TODO astronaut add material
					break;
					default: System.err.println("Syntax error: state change: astonaut: invalid action\n");
				}
			break;
			default: System.err.println("Syntax error: state change: invalid object\n");
			}
	} catch(Exception e){System.err.println("Syntax error\n"); e.printStackTrace();}
}

public static void astronautAction(String[] cmd){
		try{
			switch(cmd[2]){
				case "move":
					if(cmd.length == 3 ) astronauts.get(Integer.parseInt(cmd[1])).move();
					switch(cmd[3]){
//						case "asteroid": astronauts.get(Integer.parseInt(cmd[1])).move(asteroids.get(Integer.parseInt(cmd[3])));
//						break;
//						case "gate": astronauts.get(Integer.parseInt(cmd[1])).move(gates.get(Integer.parseInt(cmd[3])));
//						break;
//TODO Astronaut move place
					}
				break;
				case "drill": astronauts.get(Integer.parseInt(cmd[1])).drill();
				break;
				case "mine": astronauts.get(Integer.parseInt(cmd[1])).mine();
				break;
				case "deploy":
				break;
				case "build":
					switch(cmd[3]){
						case "robot":
						break;
						case "gate":
						break;
					}
				break;
				case "die":
				break;
			}
		}catch(Exception e){}
}
public static void asteroidAction(String[] cmd){
	try{
		switch(cmd[2]){
			case "explode": asteroids.get(Integer.parseInt(cmd[1])).explode();
		}
	}catch(Exception e){}

}
public static void robotAction(String[] cmd){
	try{
		switch(cmd[2]){
			case "move":
			break;
			case "drill":
			break;
			case "die":
			break;
		}
	}catch(Exception e){}
}
public static void gateAction(String[] cmd){

}
public static void ufoAction(String[] cmd){

}
public static void inGame(String[] cmd){
	switch(cmd[0]){
		case "astronaut": astronautAction(cmd);
		break;
		case "asteroid": asteroidAction(cmd);
		break;
		case "robot": robotAction(cmd);
		break;
		case "ufo": ufoAction(cmd);
		break;
		case "gate": gateAction(cmd);
		break;
		}
}


	//MAIN -------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String args[]) {
		System.setIn(new UnClosableDecorator(System.in));
		Scanner input = new Scanner(System.in);
		String[] cmd;

				while(run) {
				cmd = input.next().split("\\s"); // " "
				switch(state)
				{
					case S_IN_INIT:
						if(cmd.length == 2) {
							init(cmd);
						} else {
							setState(cmd);
						}
						break;
					case S_IN_LINK:
					 if(cmd.length == 4) {
						 linker(cmd);
						} else {
							setState(cmd);
						}
						break;
					case S_IN_STATE:
						if(cmd.length == 3){
							gyereIdeInState(cmd);
						} else {
							setState(cmd);
						}
						break;
					case S_RUN:
						inGame(cmd);
						break;
					case S_OUT:
						break;
					case S_OUT_FULL:
						break;
				}
			}
		}
	}





/*
//init section
astronaut 8
robot 9
[ufo,gate,asteroid]

link
asteroid 8 astronaut 9
gate 2 asteroid 3
gate 2 gate 3

state
astronaut %d inventory ENUM
asteroid %d core enum
asteroid %d layer %d

run

asteroid %d
							explode

astronaut %d
							move
										gate %d
										asteroid %d
							mine
							drill
							die
							build
										robot %d
										gate %d
							deploy gate %d

robot %d
							move asteroid %d
							drill
							die
ufo %d
							move asteroid %d
							mine
gate %d
							megkerguling

make asteroid %d %d %d ... nearsun
make asteroid %d %d %d ... solarstorm




export
ááááááááááááááááááááááááááá

export full

asteroid: 8
{asteroid 3
asteroid 3
1 3 2
core ENUM
astronaut 6
1 2 3 4 5 6
gate 4
3 2 4 5
end
}

astronaut: 3
{astroneut 213
inventory: 5
ENUM ENUM....

}




//TODO
GAME setters (add ize, add bigyo)
Astronaut setter (add gate, etc)
UML update, lassan érik
Asteroid neighbours tárolja a gateeket? (addgate?)


*/
