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
		Logger.call("BillOfMaterial.isNeeded",m.toString());
		for(Material a: current) {
			if(m.isSameType(a)) {
				current.remove(a);
				Logger.ret("true");
				return true;
			}
		}
		Logger.ret("false");
		return false;
	}
	
	public void reset() {
		Logger.call("BillOfMaterial.reset","");
		current=new ArrayList<Material>(originalList);
		Logger.ret("");
	}
	
	public Collection<Material> checkInventory(Collection<Material> inv) {
		Logger.call("BillOfMaterial.checkInventory", "inv="+inv.toString());
		reset();
		ArrayList<Material> winv=new ArrayList<Material>(inv);
		for(Material m: winv) {
			if(isNeeded(m)) {
				winv.remove(m);
			}
		}
		
		if(current.isEmpty()) {
			Logger.ret(winv.toString());
			return winv;
		}
		else {
			Logger.ret("null");
			return null;
		}
	}
}
