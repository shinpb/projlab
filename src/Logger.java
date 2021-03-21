public class Logger {

	private static int d=0;
	private static String log = "";

	public static void call(String name, String param) {
		for(int i=0; i<d; i++)
			log +=("\t");
		log += name + "( "+param+" ) {\n";
		d++;
	}

	public static void ret(String val) {
		d--;
		for(int i=0; i<d; i++)
			log += "\t";
		log += "} :"+val+"\n";
	}

	public static void print() {
		System.out.println(log);
	}

	public static void clear() {
		if(d!=0)
			System.out.println("Warn: clear log while stack depth != 0 ("+d+")");
		d=0;
		log="";
	}
}


/*usage:

fnc(int a, asd b, String c) {
	//first call logging
	Logger.call("fnc", "int a=" +a+ ", asd b=" +b.toString()+ ", String c=" +c );

	if(a) {
		Logger.ret("" + 8);
		return 8;
	} else {
		Logger.ret("" + asd.x+Integer.parseint(c));
		return asd.x+Integer.parseint(c);
	}

	//ret for each return
	Logger.ret("" + -1);
	return -1;
}

*/

