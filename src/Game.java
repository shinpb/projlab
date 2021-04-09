import java.awt.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Game.java
//  @ Date : 2021. 03. 19.
//  @ Author : 
//
//




public class Game {
	private ArrayList<Asteroid> asteroidField = new ArrayList<>();
	//TODO majd kesobb
	//private WindowHandler windowHandler;
	private ArrayList<Robot> robots = new ArrayList<>();
	private ArrayList<Astronaut> astronauts = new ArrayList<>();
	private ArrayList<Ufo> ufos = new ArrayList<>();
	
	//aszteroidaovhoz aszteroida hozzadasa
	public void addAsteroid(Asteroid a) {
		asteroidField.add(a);
	}
	//robot hozzadasa
	public void addRobot(Robot r) {
		robots.add(r);
	}
	//ufo hozzadasa
	public void addUfo(Ufo u) {
		ufos.add(u);
	}
	//asztronauta hozzaadasa
	public void addAstronaut(Astronaut a) {
		astronauts.add(a);
	}
	//aszteroida levetel az aszteroidaovbol
	public void removeAsteroid(Asteroid a) {
		asteroidField.remove(asteroidField.indexOf(a));
	}
	//robotot levesz a palyarol
	public void removeRobot(Robot r) {
		robots.remove(robots.indexOf(r));
	}
	//ufo eltavolitasa
	public void removeUfo(Ufo u) {
		ufos.remove(ufos.indexOf(u));
	}
	//levesz egy asztronautat
	public void removeAstronaut(Astronaut a) {
		astronauts.remove(astronauts.indexOf(a));
	}
	//aszteroidaov lekerese
	public ArrayList<Asteroid> getAsteroids() {
		return asteroidField;
	}
	//robotok lekerese
	public ArrayList<Robot> getRobots() {
		return robots;
	}
	//ufok lekerese
	public ArrayList<Ufo> getUfos() {
		return ufos;
	}
	//asztronautak lekerese
	public ArrayList<Astronaut> getAstronauts() {
		return astronauts;
	}
	//TODO javításra szorul
	public void start() {
		Logger.call("Game.start", "");
		//seged valtozok
		//aszteroidaov merete
		int asteroidfieldsize = 50;
		//asztronauta szama
		int astronautcount = 5;
		//randomhoz
		int randomNum;
		//osszes materialbol mennyi kell a gyozelemhez
		int materialsToWin = 3;
		//mennyi material van osszesen
		int materialcount = 4;
		
		//aszteroidaov keszites:
		for(int i = 0; i < materialsToWin; i++) {
			//ice, iron, carbon, uranium mindenbol 3, osszesen 12
			asteroidField.add(new Asteroid(new Ice()));
			asteroidField.add(new Asteroid(new Iron()));
			asteroidField.add(new Asteroid(new Carbon()));
			asteroidField.add(new Asteroid(new Uranium()));
		}
		
		for(int i = 0; i < asteroidfieldsize - materialsToWin * materialcount; i++) {
			//maradek 38 random igy osszsesen 50
			//random szam 1-4 kozott
			randomNum = ThreadLocalRandom.current().nextInt(1, materialcount + 1);
			if(randomNum == 1) asteroidField.add(new Asteroid(new Ice()));
			else if(randomNum == 2) asteroidField.add(new Asteroid(new Iron()));
			else if(randomNum == 3) asteroidField.add(new Asteroid(new Carbon()));
			else if(randomNum == 4) asteroidField.add(new Asteroid(new Uranium()));
			else {System.out.println("Hiba a random szammal");}
			
		}
		System.out.println("Az aszteroidak elkeszultek");
		
		//szomszedossagok letrohazosa
		//TODO algoritmus javitasa
		
		for (Asteroid a : asteroidField) {
			//ha keves szomszedja van 3-nal kevesebb
			if(a.getNeighbours().size() < 3) {
			
				//3 szomszedsag hozzaadasa
				for(int y = 0; y < 3; y++) {
					//Random szam generalasa
					randomNum = ThreadLocalRandom.current().nextInt(0, asteroidField.size() + 1);
					boolean same = false;
					//megnezni van e mar ilyen szomszedja
					for(Place p : a.getNeighbours()) {
						if(p.equals(asteroidField.get(randomNum))) {
							same = true;
						}
					}
					//ha nincs ilyen szomszedja
					if(same == false){
						//szomszedsag  beallitasa
						a.addNeighbour(asteroidField.get(randomNum));
						asteroidField.get(randomNum).addNeighbour(a);
					}
				}
			}
		}		
		System.out.println("Az aszteroidak egymas szomszedai.");
		System.out.println("Az aszteroidov elkeszult.");
		
		//asztronautak keszitese
		//asztronautak lerakasa random aszteroidakra
		for (int i = 0; i < astronautcount; i++) { 		      
			randomNum = ThreadLocalRandom.current().nextInt(0, asteroidField.size() + 1);
			astronauts.add(new Astronaut(asteroidField.get(randomNum)));	
	      }   		
		System.out.println("Az asztronautak megerkeztek az aszteroidaovbe");
		Logger.ret("");
	}
	
	
	public void step() {
		Logger.call("Game.step", "");
		
		//Minden astronautara meghivjuk a step()
		for(Astronaut a : astronauts) {
			try{
				a.step();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		
		System.out.println("Leptek a telepesek.");
		
		//Minden robotra meghivjuk a step()
		for(Robot r : robots) {
			r.step();
		}
		System.out.println("Leptek a robotok.");
		
		//Minden ufora meghivjuk a step()
		for(Ufo u : ufos) {
			//u.step(); //TODO
			u.move();
		}		
		System.out.println("Leptek az ufok.");

		//ha entitasoknak egy aszteroidan megvan a raktarukba az osszes nyersanyag vege
		if(checkGameState() == false) end();
		
		Logger.ret("");		
	}
	
	//kiuriti az aszteroidaovet, astronautakat, robotokat
	private void end() {
		Logger.call("Game.end", "");
		
		//TODO majd kesobb
		
		asteroidField.clear();
		astronauts.clear();
		robots.clear();
		
		System.out.println("A jatek vegetert");
		Logger.ret("");		
	}
	
	//true ha megy a jatek, false ha nem
	private boolean checkGameState() {
		Logger.call("Game.checkGameState", "");
		
		//bill keszitese
		BillOfMaterial bill = new BillCreator().createGameWinningBill();
		//osszes aszeroidara
		for (Asteroid a : asteroidField) {
			ArrayList<Material> onAsteroid = new ArrayList<>();
			
			//onAsteroid = a.allMaterial
					
			//TODO osszes entitas az aszteroidan
			
		//	bill.checkInventory(inv);
		}
		//return false;
		Logger.ret("");

		return true;
			
	}
	
	public void solarStorm() {
		Logger.call("Game.solarStorm", "");
		//osszes aszteroidara solarStorm()
		for (Asteroid a : asteroidField) { 		      
	          a.solarStorm();		
	      }
		Logger.ret("");	
	}
	
	public void NearSunTest() {
		Logger.call("Game.NearSunTest", "");
		//vizjeg 0 layer
		Asteroid ice = new Asteroid(new Ice());
		ice.setLayer(0);
		//iron 0 layer
		Asteroid iron = new Asteroid(new Iron());
		iron.setLayer(0);
		//uranium 0 layer
		Asteroid uranium = new Asteroid(new Uranium());
		uranium.setLayer(0);
		//empty 0 layer
		Asteroid empty = new Asteroid(null);
		empty.setLayer(0);
		//hozzaadas az aszteroidaovhoz
		asteroidField.add(ice);
		asteroidField.add(iron);
		asteroidField.add(uranium);
		asteroidField.add(empty);
		//astronauta rarakasa az ures asteroiodra
		Astronaut a = new Astronaut(empty);
		astronauts.add(a);
		Logger.ret("");	
	}
	
	public void nearSun() {
		Logger.call("Game.nearSun", "");
		//osszes aszteroidara checknearsun()
		for (Asteroid a : asteroidField) { 		      
	          a.checkNearSun();		
	      }
		Logger.ret("");	
	}
	
}
