import java.util.ArrayList;
import java.util.Collection;

public class BillOfMaterial {
	private Collection<Material> current;
	//modell change
	private Collection<Material> originalList;
	
	public BillOfMaterial(Collection<Material> l) {
		originalList=l;
		reset();
	}
	
	//modell change
	protected boolean isNeeded(Material m) {
		for(Material a: current) {
			if(m.isSameType(a)) {
				current.remove(a);
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		current=new ArrayList<Material>(originalList);
	}
	
	public Collection<Material> checkInventory(Collection<Material> inv) {
		reset();
		ArrayList<Material> winv=new ArrayList<Material>(inv);
		for(Material m: winv) {
			if(isNeeded(m)) {
				winv.remove(m);
			}
		}
		
		if(current.isEmpty()) return winv;
		else return null;
	}
}
