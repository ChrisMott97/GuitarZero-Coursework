import java.io.EOFException;
import java.io.IOException;

import server.Server;

public class Main {

	public static void main( String[] argv ) throws Exception {
		
		Server s = new Server();
		s.run();
		
		//Client c = new Client();
		//c.sendZip();
		
		StoreManagerFrame smf = new StoreManagerFrame();
		smf.create();
		
		
			      
	  }
	
}
