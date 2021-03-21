import java.util.ArrayList;
import java.util.Arrays;

public class BillCreator {
	public BillOfMaterial createRobotBill() {
		Logger.call("BillCreator.createRobotBill", "");
		BillOfMaterial r=
				new BillOfMaterial(
						new ArrayList<Material>(
								Arrays.asList(
										new Iron(),
										new Carbon(),
										new Uranium()
								)
						)
				);
		Logger.ret("Iron,Carbon,Uranium ("+r.toString()+")");
		return r;

	}
	
	public BillOfMaterial createGateBill() {
		Logger.call("BillCreator.createGategBill", "");
		BillOfMaterial r=
				new BillOfMaterial(
						new ArrayList<Material>(
								Arrays.asList(
										new Iron(),
										new Iron(),
										new Ice(),
										new Uranium()
								)
						)
				);
		Logger.ret("Iron,Carbon,Uranium ("+r.toString()+")");
		return r;
	}
	
	public BillOfMaterial createGameWinningBill() {
		Logger.call("BillCreator.createGameWinningBill", "");
		BillOfMaterial r=
				new BillOfMaterial(
						new ArrayList<Material>(
								Arrays.asList(
										new Iron(),
										new Ice(),
										new Carbon(),
										new Uranium()
								)
						)
				);
		Logger.ret("Iron,Carbon,Uranium ("+r.toString()+")");
		return r;
	}
}
