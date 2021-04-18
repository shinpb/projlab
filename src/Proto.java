import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Proto {

	/*
	* Interpreters internal state
	*/
	public enum State {
		/*
		* Input / Initialisation
		* allocate ararys for astroanuts, gates, etc.
		*/
		S_IN_INIT,

		/*
		* Input / Linking
		* make asteroids neighboars, put a gate to
		* asteroid or astronauts inventory
		*/
		S_IN_LINK,

		/*
		* Input / State
		* Change static states of objects
		* ex.: expositions of uranium, core material of
		* asteroid, layers of asteroid
		*/
		S_IN_STATE,

		/*
		* Run
		* In this state we are able to run commands that cause activicy
		* ex.: mine, put material, explosion
		*/
		S_RUN,

		/*
		* Out / Selective
		* Partialy implemented
		*/
		S_OUT,


		/*
		* Out / Full
		* Export full state of game
		* asteroids, links, states, astroinauts, inventory
		* print all data
		*/
		S_OUT_FULL
	}

	/*
	* Exit signal
	* false indicate program should exit
	*/
	private static boolean   run = true;

	/*
	* State of Interpreter
	* switch logicaly different working modes
	*/
	private static State   state = State.S_IN_INIT;

	/*
	* examined game instance
	*/
	private static Game        g = new Game();


	/*
	* global store for Astronaut s
	* syncronized with game entities
	* required for commands and modifications
	*/
	private static ArrayList<Astronaut> astronauts	= new ArrayList<>();

	/*
	* global store for Asteroid s
	* syncronized with game AsteroidField
	* required for commands and modifications
	*/
	private static ArrayList<Asteroid>	 asteroids	= new ArrayList<>();

	/*
	* global store for Robot s
	* syncronized with game entities
	* required for commands and modifications
	*/
	private static ArrayList<Robot>         robots	= new ArrayList<>();

	/*
	* global store for Ufo s
	* syncronized with game entities
	* required for commands and modifications
	*/
	private static ArrayList<Ufo> 	        	ufos	= new ArrayList<>();

	/*
	* global store for Gate s
	* syncronized with game AsteroidFields registred gates
	* required for commands and modifications
	*/
	private static ArrayList<Gate> 	         gates	= new ArrayList<>();


	/**
	* Create Material by lowercase name
	*
	* @param	s		name of material type (ex.: iron, uranium)
	* @return    	a new instance of material with specific type, or null
	*/
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

	/**
	* Determinate name of material by its type
	*
	* @param	m		Material object
	* @return    	a lowercase string by type, or 'null' (ex: "iron", "ice")
	*/
	private static String materialToString(Material m) {
		return (""+m).split("@")[0].toLowerCase();
	}

	/*
	* override AsteroidField of Game object g
	*/
	private static void gameSetAsteroidArray() {
		ArrayList<Asteroid> ori=g.getAsteroids();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeAsteroid(ori.get(i));
		for(Asteroid a: asteroids) g.addAsteroid(a);
	}

	/*
	* override astronaut collection of Game object g
	*/
	private static void gameSetAstronautArray() {
		ArrayList<Astronaut> ori=g.getAstronauts();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeAstronaut(ori.get(i));
		for(Astronaut a: astronauts) g.addAstronaut(a);
	}

	/*
	* override robot collection of Game object g
	*/
	private static void gameSetRobotArray() {
		ArrayList<Robot> ori=g.getRobots();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeRobot(ori.get(i));
		for(Robot a: robots) g.addRobot(a);
	}

	/*
	* override ufo collection of Game object g
	*/
	private static void gameSetUfoArray() {
		ArrayList<Ufo> ori=g.getUfos();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeUfo(ori.get(i));
		for(Ufo a: ufos) g.addUfo(a);
	}

	/*
	* Object array sync - magic
	* remove died and exploded asteroids from local collection
	* based on games data
	*/
	private static void syncArrays() {
		ArrayList<Entity> entlist=g.getEntities();
//		ArrayList<Astronaut> astrolist=g.getAstronauts();
		for(int i=0; i<astronauts.size(); i++)
			if(!entlist.contains(astronauts.get(i)))
				astronauts.set(i, null);
//		ArrayList<Robot> robolist=g.getRobots();
		for(int i=0; i<robots.size(); i++)
			if(!entlist.contains(robots.get(i)))
				robots.set(i, null);
//		ArrayList<Ufo> ufolist=g.getUfos();
		for(int i=0; i<ufos.size(); i++)
			if(!entlist.contains(ufos.get(i)))
				ufos.set(i, null);
//some magic
		for(Entity e: entlist) {
//			if((""+e).split("@")[0].equals("Robot"))
			if(!astronauts.contains(e) && !ufos.contains(e))
			robots.add((Robot)e);
		}

		ArrayList<Asteroid> asterlist=g.getAsteroids();
		for(int i=0; i<asteroids.size(); i++)
			if(!asterlist.contains(asteroids.get(i)))
				asteroids.set(i, null);

		for(int i=0; i<gates.size(); i++) {
			Gate g=gates.get(i);
			if((g.getOtherEnd() == null) && (g.getPosition()==null))
				gates.set(i, null);
		}

	}

	/*
	* file reader
	* for support text file based test
	* not implemented
	* @param f filename to load
	*/
	public static void loadState(String f){
		try{
			File file = new File(f);
			Scanner filecsn = new Scanner(file);

		}catch(FileNotFoundException fe){System.err.println("File does not found\n");}
	}

	/*
	* First state
	* number of entities, places, can be given
	* allocate arrays and make new object for tests
	* @param cmd command string splited to string array word by word
	*/
	public static void init(String[] cmd){
		try{
			int n = Integer.parseInt(cmd[1]);
			switch(cmd[0]) {
				case "astronaut":
					for(int i = astronauts.size(); i < n; i++) astronauts.add(new Astronaut());
					gameSetAstronautArray();
					break;
				case "asteroid":
					for(int i = asteroids.size(); i < n; i++) asteroids.add(new Asteroid());
					gameSetAsteroidArray();
					break;
				case "robot":
					for(int i = robots.size(); i < n; i++) robots.add(new Robot());
					gameSetRobotArray();
					break;
				case "gate":
					for(int i = gates.size(); i < n; i++) gates.add(new Gate());
					break;
				case "ufo":
					for(int i = ufos.size(); i < n; i++) ufos.add(new Ufo());
					gameSetUfoArray();
					break;
				default:
					System.err.println("Syntax error: unknown type: \""+cmd[0]+"\" \""+cmd[1]+"\"\n");
				}
		} catch (Exception e) {
			System.err.println("Syntax error: int expected \""+cmd[0]+"\" \""+cmd[1]+"\"\n");
			e.printStackTrace();
		}
	}

	/*
	* Switching between states
	* change main working mode for interpreter
	* init, link, state, run, export
	* @param cmd command string splited to string array word by word
	*/
	public static void setState(String[] cmd){
		if(cmd.length==0) return;
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
				state = State.S_RUN;
				break;
			case "export":
				if(cmd.length == 2 && cmd[1].equals("all")) {
					export_all();
	//				state = State.S_OUT_FULL;
					state = State.S_RUN;
				}
				else {
					exportSwitch(cmd);
					//state = State.S_OUT
					state = State.S_RUN;
				}
				break;
			default:
				System.err.println("Syntax error: unknown command (state change): \""+cmd[0]+"\"\n"+
														"Allowed: init, link, state, run, export, export all\n");
		}
	}

	/*
	* link asteroid to asteroid
	* make them neighbours
	* @param aster  first  asteroid to make neighbour
	* @param aster2 second asteroid to make neighbour
	*/
	public static void link_asteroid_asteroid(Asteroid aster, Asteroid aster2) {
		aster.addNeighbour(aster2);
		aster2.addNeighbour(aster);
	}

	/*
	* link gate and
	* @param aster  asteroid make gates position
	* @param ga     gate add to asters asteroid list
	*/
	public static void link_asteroid_gate(Asteroid aster, Gate ga) {
		aster.addGate(ga);
		ga.setPosition(aster);
	}

	/*
	* link asteroid to astronaut
	* put astronaut to asteroid
	* @param aster  asteroid that will be set as position of astronaut
	* @param astro astronaut that will be added to asteroids entity list
	*/
	public static void link_asteroid_astronaut(Asteroid aster, Astronaut astro) {
		aster.addEntity(astro);
	}

	/*
	* link asteroid to robot
	* put robot to asteroid
	* @param aster  asteroid that will be set as position of robot
	* @param robo   robot that will be added to asteroids entity list
	*/
	public static void link_asteroid_robot(Asteroid aster, Robot robo) {
		aster.addEntity(robo);
	}

	/*
	* link asteroid to ufo
	* put ufo to asteroid
	* @param aster  asteroid that will be set as position of ufo
	* @param ufo 		ufo that will be added to asteroids entity list
	*/
	public static void link_asteroid_ufo(Asteroid aster, Ufo ufo) {
		aster.addEntity(ufo);
	}

	/*
	* link gate to gate
	* make them neighbours
	* @param ga  first  gate to make neighbour
	* @param ga2 second gate to make neighbour
	*/
	public static void link_gate_gate(Gate ga, Gate ga2) {
		 ga.setOtherEnd(ga2);
		ga2.setOtherEnd(ga);
	}

	/*
	* link astronaut to gate
	* add gate to astronauts gate storage
	* @param astro  astronaut who will store the gate
	* @param ga gate that will be store
	*/
	public static void link_astronaut_gate(Astronaut astro, Gate ga) {
		try {
			astro.addGate(ga);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/*
	* Second state
	* relations between entites and places can be given
	* linker switch for recognize linking commands
	* @param cmd command string to splited to array bword by word
	*/
	public static void linker(String[] cmd){
			int a,b;
			try{
				a = Integer.parseInt(cmd[1]);
				b = Integer.parseInt(cmd[3]);
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
									} else System.err.println("Invalid astronaut id\n");
									break;
								case "robot":
									if(b<robots.size()) {
											Robot robo=robots.get(b);
											link_asteroid_robot(aster, robo);
									} else System.err.println("Invalid robor id\n");
									break;
								case "ufo":
									if(b<ufos.size()) {
											Ufo ufo=ufos.get(b);
											link_asteroid_ufo(aster, ufo);
									} else System.err.println("Invalid ufo id\n");
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
									case "gate":
										if(b < gates.size()) {
											Gate ga=gates.get(b);
											link_astronaut_gate(astro, ga);
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
					case "ufo":
						if(a < ufos.size()) {
							Ufo ufo=ufos.get(a);
							switch(cmd[2]) {
								case "asteroid":
									if(b < asteroids.size()) {
										Asteroid aster=asteroids.get(b);
										link_asteroid_ufo(aster, ufo);
									}
									else System.err.println("Invalid asteroid id\n");
									break;
								default:
									System.err.println("ufo cant link to \""+cmd[2]+"\"\n");
							}
						} else System.err.println("Invalid robot id\n");
						break;
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
								case "astronaut":
									if(b < astronauts.size()) {
										Astronaut astro=astronauts.get(b);
										link_astronaut_gate(astro, ga);
									 }
									else System.err.println("Invalid astronaut id\n");
									break;
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

	/*
	* switch case for recognite sate commands
	* STATE states command ar here:
	* core material, layer, inventory
	* @param cmd command string splited to array word by word
	*/
	public static void gyereIdeInState(String[] cmd){
		try{
			switch(cmd[0]){
				case "asteroid":
					switch(cmd[2]){
						case "layer": asteroids.get(Integer.parseInt(cmd[1])).setLayer(Integer.parseInt(cmd[3]));
						break;
						case "core":
							if(cmd.length>=5 && cmd[3].equals("uranium")) {
								Uranium u=new Uranium();
								u.setExp(Integer.parseInt(cmd[4]));
								asteroids.get(Integer.parseInt(cmd[1])).setCore(u);
							}
							else
								asteroids.get(Integer.parseInt(cmd[1])).setCore(materialEnum(cmd[3]));
						break;
						default: System.err.println("Syntax error: state change: asteroid: invalid action\n");
					}
				break;
				case "astronaut":
					switch(cmd[2]){
						case "inventory":
						astronauts.get(Integer.parseInt(cmd[1])).addMaterial(materialEnum(cmd[3]));
						break;
						case "position":
						astronauts.get(Integer.parseInt(cmd[1])).setPosition(asteroids.get(Integer.parseInt(cmd[3])));
						break;
						default: System.err.println("Syntax error: state change: astonaut: invalid action\n");
					}
				break;
				case "gate":
					switch(cmd[2]){
						case "bolond":
						if(Integer.parseInt(cmd[3]) == 0)	gates.get(Integer.parseInt(cmd[1])).setBolond(false);
						else gates.get(Integer.parseInt(cmd[1])).setBolond(true);
						break;
						case "enable":
						gates.get(Integer.parseInt(cmd[1])).enable();
						break;
						case "disable":
						gates.get(Integer.parseInt(cmd[1])).disable();
						break;
						default: System.err.println("Syntax error: state change: gate: invalid action\n");
					}
				break;
				//case "ufo" case "inventory", ha lenne
				default: System.err.println("Syntax error: state change: invalid object\n");
				}
		} catch(Exception e){System.err.println("Syntax error\n"); e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	astronaut-action command
	* at RUN state commands sart with astronaut
	* @param cmd command string splited to array word by word
	*/
	public static void astronautAction(String[] cmd){
			try{
				switch(cmd[2]){
					case "move":
						if(cmd.length == 3 ) astronauts.get(Integer.parseInt(cmd[1])).move();
						else {
							switch(cmd[3]){
								case "asteroid": astronauts.get(Integer.parseInt(cmd[1])).moveTo(asteroids.get(Integer.parseInt(cmd[4])));
								break;
								case "gate": astronauts.get(Integer.parseInt(cmd[1])).moveTo(gates.get(Integer.parseInt(cmd[4])));
								break;
							}
						}
					break;
					case "drill": astronauts.get(Integer.parseInt(cmd[1])).drill();
					break;
					case "mine": astronauts.get(Integer.parseInt(cmd[1])).mine();
					break;
					case "deploy": astronauts.get(Integer.parseInt(cmd[1])).deployGate();
					break;
					case "putMaterialInAsteroid":;
					if(cmd.length>=4) astronauts.get(Integer.parseInt(cmd[1])).putMaterialInAsteroid(Integer.parseInt(cmd[3]));
					else astronauts.get(Integer.parseInt(cmd[1])).putMaterialInAsteroid();
					break;
					case "build":
						switch(cmd[3]){
							case "robot":
								astronauts.get(Integer.parseInt(cmd[1])).craftRobot();
							break;
							case "gate":
								astronauts.get(Integer.parseInt(cmd[1])).craftGate();
							break;
							default: System.err.println("Syntax error: cant built: "+cmd[1]);
						}
					break;
					case "die": astronauts.get(Integer.parseInt(cmd[1])).die();
					break;
					default: System.err.println("Syntax error: invalid operation: "+cmd[0]);
				}
			}catch(Exception e){e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	asteroid-action command
	* at RUN state commands sart with asteroid
	* @param cmd command string splited to array word by word
	*/
	public static void asteroidAction(String[] cmd){
		try{
			switch(cmd[2]){
				case "explode": asteroids.get(Integer.parseInt(cmd[1])).explode();
				break;
				case "setNearSun":
				if(Integer.parseInt(cmd[3]) ==0 )	asteroids.get(Integer.parseInt(cmd[1])).setNearSun(false);
				else	asteroids.get(Integer.parseInt(cmd[1])).setNearSun(true);
				break;
				case "solarStorm": asteroids.get(Integer.parseInt(cmd[1])).solarStorm();
				break;
				case "replaceCore": asteroids.get(Integer.parseInt(cmd[1])).replaceCore(materialEnum(cmd[3]));
				break;
				default: System.err.println("Syntax error: invalid operation (asteroid): "+cmd[2]);
			}
		}catch(Exception e){e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	robot-action command
	* at RUN state commands sart with robot
	* @param cmd command string splited to array word by word
	*/
	public static void robotAction(String[] cmd){
		try{
			switch(cmd[2]){
				case "move":
					if(cmd.length == 3 ) robots.get(Integer.parseInt(cmd[1])).move();
					else {
						switch(cmd[3]){
							case "asteroid": robots.get(Integer.parseInt(cmd[1])).moveTo(asteroids.get(Integer.parseInt(cmd[4])));
							break;
							case "gate": robots.get(Integer.parseInt(cmd[1])).moveTo(gates.get(Integer.parseInt(cmd[4])));
							break;
						}
					}
				break;
				case "drill":
				robots.get(Integer.parseInt(cmd[1])).drill();
				break;
				case "die":
				robots.get(Integer.parseInt(cmd[1])).die();
				break;
			}
		}catch(Exception e){e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	gate-action command
	* at RUN state commands sart with gate
	* @param cmd command string splited to array word by word
	*/
	public static void gateAction(String[] cmd){
		try{
			switch(cmd[2]){
				case "bolond":
				if(Integer.parseInt(cmd[3]) ==0 )	gates.get(Integer.parseInt(cmd[1])).setBolond(false);
				else	gates.get(Integer.parseInt(cmd[1])).setBolond(true);
				break;
				default: System.err.println("Syntax error: invalid operation (gate): "+cmd[2]);
			}
		}catch(Exception e){e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	ufo-action command
	* at RUN state commands sart with ufo
	* @param cmd command string splited to array word by word
	*/
	public static void ufoAction(String[] cmd){
				try{
					switch(cmd[2]){
						case "move":
							if(cmd.length == 3 ) ufos.get(Integer.parseInt(cmd[1])).move();
							switch(cmd[3]){
								case "asteroid": ufos.get(Integer.parseInt(cmd[1])).moveTo(asteroids.get(Integer.parseInt(cmd[4])));
								break;
								case "gate": ufos.get(Integer.parseInt(cmd[1])).moveTo(gates.get(Integer.parseInt(cmd[4])));
								break;
							}
						break;
						case "mine": ufos.get(Integer.parseInt(cmd[1])).mine();
						break;
						case "die": ufos.get(Integer.parseInt(cmd[1])).die();
						break;
						default: System.err.println("Syntax error: invalid operation: "+cmd[0]);
					}
				}catch(Exception e){ e.printStackTrace();}
	}

	/*
	*	switch case for recognize
	*	action commands
	* call approproate function base string command starts with
	* @param cmd command string splited to array word by word
	*/
	public static void inGame(String[] cmd){
		switch(cmd[0]){
			case "astronaut": astronautAction(cmd);
			syncArrays();
			break;
			case "asteroid": asteroidAction(cmd);
			syncArrays();
			break;
			case "robot": robotAction(cmd);
			syncArrays();
			break;
			case "ufo": ufoAction(cmd);
			syncArrays();
			break;
			case "gate": gateAction(cmd);
			syncArrays();
			break;
			default:
			setState(cmd);
			}
	}

	/*
	* find index an asteroid in asteroids array or return 1
	* @param aster Object to check is asteroid
	* @return >0 if exist, -1 if not
	*/
	private static int asteroidID(Object aster) {
		for(int i=0; i<asteroids.size(); i++)
			if(asteroids.get(i) == aster) //reference check, szandekos
				return i;
		return -1;
	}

	/*
	* find index an astronaut in astronauts array or return 1
	* @param astro Object to check is astronaut
	* @return >0 if exist, -1 if not
	*/
	private static int astronautID(Object astro) {
		for(int i=0; i<astronauts.size(); i++)
			if(astronauts.get(i) == astro) //reference check, szandekos
				return i;
		return -1;
	}

	/*
	* find index an robot in robots array or return 1
	* @param robo Object to check is robot
	* @return >0 if exist, -1 if not
	*/
	private static int robotID(Object robo) {
		for(int i=0; i<robots.size(); i++)
			if(robots.get(i) == robo) //reference check, szandekos
				return i;
		return -1;
	}

	/*
	* find index an ufo in ufos array or return 1
	* @param aster Object to check is ufo
	* @return >0 if exist, -1 if not
	*/
	private static int ufoID(Object ufo) {
		for(int i=0; i<ufos.size(); i++)
			if(ufos.get(i) == ufo) //reference check, szandekos
				return i;
		return -1;
	}

	/*
	* find index an gate in gates array or return 1
	* @param aster Object to check is gate
	* @return >0 if exist, -1 if not
	*/
	private static int gateID(Object ga) {
		for(int i=0; i<gates.size(); i++)
			if(gates.get(i) == ga) //reference check, szandekos
				return i;
		return -1;
	}

	/*
	* export specific asteroids state
	* @param aster Asteroid to export (req not-null)
	*/
	private static void export_asteroid(Asteroid aster) {
		int n=asteroidID(aster);
		if(n<0) {
			System.err.println("invalid asteroid reference at export (no id)");
		}
		System.out.println("asteroid: "+n); 										//id
		System.out.println("layer "+aster.getLayer());	//core
		System.out.println("core "+materialToString(aster.getCore()));	//core
		int cnt=0, id;
		for(Place p: aster.getNeighbours())
			if((id=asteroidID(p)) >=0)
				cnt++;
		System.out.println("asteroids "+cnt); 									//neighbours
		for(Place p: aster.getNeighbours())
			if((id=asteroidID(p)) >=0)
				System.out.print(""+id+" ");												//neighbour list
		System.out.print("\n");
		cnt=0;
		for(Place p: aster.getNeighbours())
			if((id=gateID(p)) >=0)
				cnt++;
		System.out.println("gates "+cnt); 									//gates
		for(Place p: aster.getNeighbours())
			if((id=gateID(p)) >=0)
				System.out.print(""+id+" ");												//gate list
		System.out.print("\n");
		cnt=0;
		for(Entity e: aster.getEntities())
			if((id=astronautID(e)) >=0)
				cnt++;
		System.out.println("astronauts "+cnt); 									//astronauts
		for(Entity e: aster.getEntities())
			if((id=astronautID(e)) >=0)
				System.out.print(""+id+" ");												//astronaut list
		System.out.print("\n");
		cnt=0;
		for(Entity e: aster.getEntities())
			if((id=robotID(e)) >=0)
				cnt++;
		System.out.println("robots "+cnt); 											//robots
		for(Entity e: aster.getEntities())
			if((id=robotID(e)) >=0)
				System.out.print(""+id+" ");												//robot list
		System.out.print("\n");
		cnt=0;
		for(Entity e: aster.getEntities())
			if((id=ufoID(e)) >=0)
				cnt++;
		System.out.println("ufos "+cnt); 											//ufos
		for(Entity e: aster.getEntities())
		if((id=ufoID(e)) >=0)
			System.out.print(""+id+" ");												//ufos list
		System.out.print("\n");
		cnt=0;
		System.out.println("end");															//end
	}

	/*
	* exports all asteroids state and count
	*/
	private static void export_asteroid_all() {
		int c=0;
		for(Asteroid aster: asteroids)
			if(aster != null)
				c++;
		if(c==0) return;
		System.out.println("asteroids: "+c);
		for(Asteroid aster: asteroids)
			if(aster != null)
				export_asteroid(aster);
	}

	/*
	* exports specific atronauts state
	* @param astro Astronaut to export (req not-null)
	*/
	private static void export_astronaut(Astronaut astro) {
		System.out.println("astronaut: "+astronautID(astro));
		System.out.println("position "+asteroidID(astro.getPosition()));
		System.out.println("inventory "+astro.getInventory().size());
		for(Material m: astro.getInventory())
			System.out.print(materialToString(m)+" ");
		System.out.print("\n");
		System.out.println("gates "+astro.getGates().size());
			for(Gate ga: astro.getGates())
				System.out.print(gateID(ga)+" ");
		System.out.print("\nend\n");
	}

	/*
	* exports all astronauts state
	*/
	private static void export_astronaut_all() {
		int c=0;
		for(Astronaut astro: astronauts)
			if(astro != null)
				c++;
		if(c==0) return;
		System.out.println("astronauts: "+c);
		for(Astronaut astro: astronauts)
			if(astro != null)
				export_astronaut(astro);
	}

	/*
	* exports specific gates state
	* @param ga Gate to export (req not-null)
	*/
	private static void export_gate(Gate ga) {
		int n=gateID(ga);
		if(n<0) {
			System.err.println("invalid gate reference at export (no id)");
		}
		System.out.println("gate: "+n); 										//id
		System.out.println("isActive: "+ga.getIsActive());
		System.out.println("position "+asteroidID(ga.getPosition()));
		System.out.println("otherEnd " +gateID(ga.getOtherEnd()));
		System.out.println("end");															//end
	}

	/*
	* exports all gates state and count
	*/
	private static void export_gate_all() {
		int c=0;
		for(Gate ga: gates)
			if(ga != null)
				c++;
		if(c==0) return;
		System.out.println("gates: "+c);
		for(Gate ga: gates)
			if(ga != null)
				export_gate(ga);
	}

	/*
	* exports specific robots state
	* @param robo Robot to export (req not-null)
	*/
	private static void export_robot(Robot robo) {
		System.out.println("robot: "+robotID(robo));
		System.out.println("position "+asteroidID(robo.getPosition()));
		System.out.println("end");
	}

	/*
	* exports all robots state and count
	*/
	private static void export_robot_all() {
		int c=0;
		for(Robot robo: robots)
			if(robo != null)
				c++;
		if(c==0) return;
		System.out.println("robots: "+c);
		for(Robot robo: robots)
			if(robo != null)
				export_robot(robo);
	}

	/*
	* exports specific ufos state
	* @param ufo Ufo to export (req not-null)
	*/
	private static void export_ufo(Ufo ufo) {
		System.out.println("ufo: "+ufoID(ufo));
		System.out.println("position "+asteroidID(ufo.getPosition()));
		System.out.println("inventory "+ufo.getInventory().size());
		for(Material m: ufo.getInventory())
			System.out.print(materialToString(m)+" ");
		System.out.print("\nend\n");
	}

	/*
	* exports all ufos state and count
	*/
	private static void export_ufo_all() {
		int c=0;
		for(Ufo ufo: ufos)
			if(ufo != null)
				c++;
		if(c==0) return;
		System.out.println("ufos: "+c);
		for(Ufo ufo: ufos)
			if(ufo != null)
				export_ufo(ufo);
	}

	/*
	* exports all entities and places states
	*/
	private static void export_all() {
		export_asteroid_all();
		export_gate_all();
		export_astronaut_all();
		export_robot_all();
		export_ufo_all();
	}

	/*
	* find switch-case for commands
	* satrs with "export"
	* @param cmd commandstring splited to array word by word
	*/
	private static void exportSwitch(String[] cmd){
		if(cmd.length == 1) System.err.println("Syntax error: export requires an argument");
		else {
			int id = -1;
			if(cmd.length >= 3) {
				try{
					id=Integer.parseInt(cmd[2]);
				} catch(Exception e) {e.printStackTrace();}
			}
			switch(cmd[1]) {
				case "astronaut":
				if(id<0)	export_astronaut_all();
				else	export_astronaut(astronauts.get(id));
				break;
				case "robot":
				if(id<0)	export_robot_all();
				else	export_robot(robots.get(id));
				break;
				case "ufo":
				if(id<0)	export_ufo_all();
				else	export_ufo(ufos.get(id));
				break;
				case "asteroid":
				if(id<0)	export_asteroid_all();
				else	export_asteroid(asteroids.get(id));
				break;
				case "gate":
				if(id<0)	export_gate_all();
				else	export_gate(gates.get(id));
				break;
				case "all":
				export_all();
				break;
				default: System.err.println("Syntax error: cant export " + cmd[1]);
			}
		}
	}

	//MAIN -------------------------------------------------------------------------------------------------------------------------------------
	/*
	* Main program
	*	enrty point for Proto class
	* @param args command line argiumens will be ignored
	*/
	public static void main(String args[]) {
		System.setIn(new UnClosableDecorator(System.in));
		Scanner input = new Scanner(System.in);
		String[] cmd;

				while(run) {
					try{
					cmd = input.nextLine().split("\\s"); // " "
					if(
						(cmd.length==0) ||
						(cmd.length == 1 && cmd[0].equals("")) ||
						(cmd[0].charAt(0)=='#') ||
						((cmd[0].length() >= 2) && (cmd[0].charAt(0)=='/') && (cmd[0].charAt(1)=='/'))
					) continue;
					switch(state)
					{
						case S_IN_INIT:
							if(cmd.length == 2 && !cmd[0].equals("export")) {
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
							if(cmd.length > 2) {
									gyereIdeInState(cmd);
								} else {
									setState(cmd);
								}
							break;
						case S_RUN:
							inGame(cmd);
							break;
		//				case S_OUT:
		//					break;
		//				case S_OUT_FULL:
		//					break;
					}
				} catch(java.util.NoSuchElementException e) {run=false;}
			}
		}
	}
