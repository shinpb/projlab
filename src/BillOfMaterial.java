import java.util.ArrayList;
import java.util.Collection;

public class BillOfMaterial {
	private ArrayList<Material> current;
	//modell change
	private ArrayList<Material> originalList;
	
	/*
	Constructor, BillOfMaterial-t készít 
	egy Material Collection alapján
	és ellenőrizni képes állapotba hozza.
	NEM támogatja a naplózást
	*/
	public BillOfMaterial(Collection<Material> l) {
		originalList=new ArrayList<Material>(l);
		reset();
	}
	/*
	isNeeded(Material m) ellenőrzi, hogy 
	egy anyag rajta van e a listán, ha igen 
	lehúzza (egyszer)
	visszatérési értéke a tény, hogy szerepelt e a listán
	Támogatja a nalózást
	*/
	//modell change
	protected boolean isNeeded(Material m) {
		Logger.call("BillOfMaterial.isNeeded",""+m);
		for(int i=0; i<current.size(); i++) {
			Material a=current.get(i);
			if(m.isSameType(a)) {
				current.remove(a);
				i--;
				Logger.ret("true");
				return true;
			}
		}
		Logger.ret("false");
		return false;
	}
	
	/*
	Az ellenőrzőlistát visszaállítja alapállapotba
	Azaz, a lehúzások eltűnnek
	Támogatja a nalózást
	*/
	public void reset() {
		Logger.call("BillOfMaterial.reset","");
		current=new ArrayList<Material>(originalList);
		Logger.ret("");
	}
	
	/*
	Collection<Material> checkInventory(Collection<Material> inv) 
	egy olyan függvény, ami ellenőrzi, hogy 
	egy inventoryban szerepel e minden, ami
	az ellenőzzőlistán
	Visszatérési értéke null, ha nem szerepel
	és egy lista, ami az eredeti inventory
	és a checklist különbsége, ha minden anyag 
	bennevolt az inventoryban a litáról
	Támogatja a naplózást
	*/
	public Collection<Material> checkInventory(Collection<Material> inv) {
		Logger.call("BillOfMaterial.checkInventory", "inv="+inv);
		reset();
		ArrayList<Material> winv=new ArrayList<Material>(inv);
		for(int i=0; i<winv.size(); i++) {
			Material m=winv.get(i);
			if(isNeeded(m)) {
				winv.remove(m);
				i--;
			}
		}
		
		if(current.isEmpty()) {
			Logger.ret(""+winv);
			return winv;
		}
		else {
			Logger.ret("null");
			return null;
		}
	}
}
