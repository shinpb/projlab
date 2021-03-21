import java.util.ArrayList;
import java.util.Collection;

public class BillOfMaterial {
	private ArrayList<Material> current;
	//modell change
	private ArrayList<Material> originalList;
	
	public BillOfMaterial(Collection<Material> l) {
		originalList=new ArrayList<Material>(l);
		reset();
	}
	
	//modell change
	protected boolean isNeeded(Material m) {
		Logger.call("BillOfMaterial.isNeeded",m.toString());
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
	
	public void reset() {
		Logger.call("BillOfMaterial.reset","");
		current=new ArrayList<Material>(originalList);
		Logger.ret("");
	}
	
	public Collection<Material> checkInventory(Collection<Material> inv) {
		Logger.call("BillOfMaterial.checkInventory", "inv="+inv.toString());
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
			Logger.ret(winv.toString());
			return winv;
		}
		else {
			Logger.ret("null");
			return null;
		}
	}
}
