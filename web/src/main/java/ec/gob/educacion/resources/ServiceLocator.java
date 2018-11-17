package ec.gob.educacion.resources;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ServiceLocator {
	
	private static Hashtable<String, Object> properties;
	
	private static Hashtable<String, Object> getProperties() {
		if(properties == null) {
			properties = new Hashtable<String, Object>();
			properties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			properties.put("jboss.naming.client.ejb.context", true);
		}
		return properties;
	}

	@SuppressWarnings("unchecked")
	public static <T> T buscarInstancia(String modulo, Class<T> clase, boolean remoto) {
		try {
			String nombreClase = clase.getSimpleName();
			if(remoto) {
				nombreClase = quitarRemote(nombreClase);
			}
			Context ctx = new InitialContext(getProperties());
			return (T) ctx.lookup("ejb:" + modulo + "/"
					+ nombreClase + "Impl!"
					+ clase.getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T buscarInstancia(String global) {
		try {
			Context ctx = new InitialContext();
			return (T) ctx.lookup(global);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T buscarInstanciaModelo(String modulo, Class<T> clase) {
		try {
			Context ctx = new InitialContext(getProperties());
			return (T) ctx.lookup("ejb:" + modulo + "/" + clase.getSimpleName() + "!" + clase.getCanonicalName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String quitarRemote(String nombre) {
		int index = nombre.lastIndexOf("Remote");
		return nombre.substring(0, index);
	}
}
