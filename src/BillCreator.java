import java.util.ArrayList;
import java.util.Arrays;

/*
A BillCreator osaztály az egyes 
szükséges anyag listák alapján 
állítja elő az adottt szituációhoz 
megfelelő BillOfMaterial-t
*/
public class BillCreator {
/*
A createRobotBill() egy BillOfMaterial-t
ad vissza, olyan anyagok alapán, ami egy 
robot elkészítéséhez szükséges:
Vas, Szén, Urán
Támogatja a naplózást (Logger)
*/
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
		Logger.ret("Iron,Carbon,Uranium ("+r+")");
		return r;

	}
	
/*
A createGateBill() egy BillOfMaterial-t
ad vissza, olyan anyagok alapán, ami egy 
kapu elkészítéséhez szükséges:
2 Vas, Jég, Urán
Támogatja a naplózást (Logger)
*/
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
		Logger.ret("Iron,Carbon,Uranium ("+r+")");
		return r;
	}
	
/*
A createRobotBill() egy BillOfMaterial-t
ad vissza, olyan anyagok alapán, ami egy 
játék megnyeréséhez szükséges:
minden anyagfajtából egy:
Vas, Jég, Szén, Urán
Támogatja a naplózást (Logger)
*/
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
		Logger.ret("Iron,Carbon,Uranium ("+r+")");
		return r;
	}
}
