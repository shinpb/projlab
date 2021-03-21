public static class Logger {

	private static int d=0;
	
	public static void call(String name, String param) {
		for(int i=0; i<d; i++)
			System.out.print("\t");
		System.out.print(name + "( "+param+" ) {\n");
		d++;
	}
	
	public static void ret(String val) {
		d--;
		for(int i=0; i<d; i++)
			System.out.print("\t");
		System.out.print("} :"+val+"\n");
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

