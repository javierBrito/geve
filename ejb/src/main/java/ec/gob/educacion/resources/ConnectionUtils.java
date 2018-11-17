package ec.gob.educacion.resources;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionUtils {
	
	public static boolean isConnectionAvailable(String link) {
		try{  
            URL url = new URL(link);  
            HttpURLConnection con=(HttpURLConnection) url.openConnection();  
            con.connect();
            return true;
        }catch(Exception x){  
            return false ;
        }  
	}

}
