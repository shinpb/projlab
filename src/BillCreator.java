import java.util.ArrayList;
import java.util.Arrays;

public class BillCreator {
	public BillOfMaterial createRobotBill() {
		return 
				new BillOfMaterial(
						new ArrayList<Material>(
								Arrays.asList(
										new Iron(),
										new Carbon(),
										new Uranium()
								)
						)
				);
	}
	
	public BillOfMaterial createGateBill() {
		return 
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
	}
	
	public BillOfMaterial createGameWinningBill() {
		return 
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
	}
}
