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
	private static ArrayList<Ufo> 	        	ufos	= new ArrayList<>();
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
	private static String materialToString(Material m) {
		return (""+m).split("@")[0].toLowerCase();
	}

	private static void gameSetAsteroidArray() {
		ArrayList<Asteroid> ori=g.getAsteroids();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeAsteroid(ori.get(i));
		for(Asteroid a: asteroids) g.addAsteroid(a);
	}
	private static void gameSetAstronautArray() {
		ArrayList<Astronaut> ori=g.getAstronauts();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeAstronaut(ori.get(i));
		for(Astronaut a: astronauts) g.addAstronaut(a);
	}
	private static void gameSetRobotArray() {
		ArrayList<Robot> ori=g.getRobots();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeRobot(ori.get(i));
		for(Robot a: robots) g.addRobot(a);
	}
	private static void gameSetUfoArray() {
		ArrayList<Ufo> ori=g.getUfos();
		for(int i=ori.size()-1; i>=0; i--)
			g.removeUfo(ori.get(i));
		for(Ufo a: ufos) g.addUfo(a);
	}
	private static void syncArrays() {
		ArrayList<Astronaut> astrolist=g.getAstronauts();
		for(int i=0; i<astronauts.size(); i++)
			if(!astrolist.contains(astronauts.get(i)))
				astronauts.set(i, null);
		ArrayList<Asteroid> asterlist=g.getAsteroids();
		for(int i=0; i<asteroids.size(); i++)
			if(!asterlist.contains(asteroids.get(i)))
				asteroids.set(i, null);
		ArrayList<Robot> robolist=g.getRobots();
		for(int i=0; i<robots.size(); i++)
			if(!robolist.contains(robots.get(i)))
				robots.set(i, null);
		ArrayList<Ufo> ufolist=g.getUfos();
		for(int i=0; i<ufos.size(); i++)
			if(!ufolist.contains(ufos.get(i)))
				ufos.set(i, null);

		for(int i=0; i<gates.size(); i++) {
			Gate g=gates.get(i);
			if((g.getOtherEnd() == null) && (g.getPosition()==null))
				gates.set(i, null);
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
public static void link_asteroid_ufo(Asteroid aster, Ufo ufo) {
	aster.addEntity(ufo);
}
public static void link_gate_gate(Gate ga, Gate ga2) {
	 ga.setOtherEnd(ga2);
	ga2.setOtherEnd(ga);
}
public static void link_astronaut_gate(Astronaut astro, Gate ga) {
	try {
		astro.addGate(ga);
	} catch(Exception e) {
		e.printStackTrace();
	}
}

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
				case "die": astronauts.get(Integer.parseInt(cmd[1])).mine();
				break;
				default: System.err.println("Syntax error: invalid operation: "+cmd[0]);
			}
		}catch(Exception e){}
}
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
	}catch(Exception e){}
}
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
	}catch(Exception e){}
}
public static void gateAction(String[] cmd){
	try{
		switch(cmd[2]){
			case "bolond":
			if(Integer.parseInt(cmd[3]) ==0 )	gates.get(Integer.parseInt(cmd[1])).setBolond(false);
			else	gates.get(Integer.parseInt(cmd[1])).setBolond(true);
			break;
			default: System.err.println("Syntax error: invalid operation (gate): "+cmd[2]);
		}
	}catch(Exception e){}
}
public static void ufoAction(String[] cmd){
			try{
				switch(cmd[2]){
					case "move":
						if(cmd.length == 3 ) ufos.get(Integer.parseInt(cmd[1])).move();
						switch(cmd[3]){
							case "asteroid": ufos.get(Integer.parseInt(cmd[1])).moveTo(asteroids.get(Integer.parseInt(cmd[3])));
							break;
							case "gate": ufos.get(Integer.parseInt(cmd[1])).moveTo(gates.get(Integer.parseInt(cmd[3])));
							break;
						}
					break;
					case "mine": ufos.get(Integer.parseInt(cmd[1])).mine();
					break;
					case "die": ufos.get(Integer.parseInt(cmd[1])).die();
					break;
					default: System.err.println("Syntax error: invalid operation: "+cmd[0]);
				}
			}catch(Exception e){}
}
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

private static int asteroidID(Object aster) {
	for(int i=0; i<asteroids.size(); i++)
		if(asteroids.get(i) == aster) //reference check, szandekos
			return i;
	return -1;
}
private static int astronautID(Object astro) {
	for(int i=0; i<astronauts.size(); i++)
		if(astronauts.get(i) == astro) //reference check, szandekos
			return i;
	return -1;
}
private static int robotID(Object robo) {
	for(int i=0; i<robots.size(); i++)
		if(robots.get(i) == robo) //reference check, szandekos
			return i;
	return -1;
}
private static int ufoID(Object ufo) {
	for(int i=0; i<ufos.size(); i++)
		if(robots.get(i) == ufo) //reference check, szandekos
			return i;
	return -1;
}
private static int gateID(Object ga) {
	for(int i=0; i<gates.size(); i++)
		if(gates.get(i) == ga) //reference check, szandekos
			return i;
	return -1;
}
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
	System.out.println("ufos "+cnt); 											//robots
	for(Entity e: aster.getEntities())
	if((id=ufoID(e)) >=0)
		System.out.print(""+id+" ");												//robot list
	System.out.print("\n");
	cnt=0;
	System.out.println("end");															//end
}
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
private static void export_robot(Robot robo) {
	System.out.println("robot: "+robotID(robo));
	System.out.println("position "+asteroidID(robo.getPosition()));
	System.out.println("end");
}
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
private static void export_ufo(Ufo ufo) {
	System.out.println("ufo: "+ufoID(ufo));
	System.out.println("position "+ufoID(ufo.getPosition()));
	System.out.println("inventory "+ufo.getInventory().size());
	for(Material m: ufo.getInventory())
		System.out.print(materialToString(m)+" ");
	System.out.print("\nend\n");
}
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
private static void export_all() {
	export_asteroid_all();
	export_gate_all();
	export_astronaut_all();
	export_robot_all();
	export_ufo_all();
}
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
			case "test":
				//do something
				//TODO
				export_all();
				break;
			case "all":
			export_all();
			break;
			default: System.err.println("Syntax error: cant export " + cmd[1]);
		}
	}
}

	//MAIN -------------------------------------------------------------------------------------------------------------------------------------
	public static void main(String args[]) {
		System.setIn(new UnClosableDecorator(System.in));
		Scanner input = new Scanner(System.in);
		String[] cmd;

				while(run) {
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
						if(cmd.length == 2 && !cmd[1].equals("all")) {
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
//					case S_OUT:
//						break;
//					case S_OUT_FULL:
//						break;
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

asteroids: 8
{asteroid: 13
//core ENUM
asteroids 3
1 3 2
gates 4
3 2 4 5
astronauts 6
1 2 3 4 5 6
robots 1
10 12
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
