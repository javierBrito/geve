package ec.gob.educacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
public class ErrorController {
	
    private static Logger log = Logger.getLogger(ErrorController.class);
	
	private String mensaje;

	private String stackTrace;

	@PostConstruct
	public void init() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> map = context.getExternalContext().getRequestMap();
		Throwable throwable = (Throwable) map
				.get("javax.servlet.error.exception");
		stackTrace = joinStackTrace(throwable);
		mensaje = throwable.getMessage();
	}

	public static String joinStackTrace(Throwable e) {
		StringWriter writer = null;
		try {
			writer = new StringWriter();
			joinStackTrace(e, writer);
			return writer.toString();
		} finally {
			if (writer != null)
				try {
					writer.close();
				} catch (IOException ioe) {
					log.error(" ---> Error generado: " + ioe.getMessage());
				}
		}
	}

	public static void joinStackTrace(Throwable e, StringWriter writer) {
		PrintWriter printer = null;
		try {
			printer = new PrintWriter(writer);
			while (e != null) {
				printer.println(e);
				StackTraceElement[] trace = e.getStackTrace();
				for (int i = 0; i < trace.length; i++)
					printer.println("\tat " + trace[i]);

				e = e.getCause();
				if (e != null)
					printer.println("\r\nCausado por:");
			}
		} catch (Exception er) {
			if (printer != null) {
				printer.close();
			}
		} finally {
			if (printer != null)
				printer.close();
		}
	}

	public String getStackTrace() {
		return this.stackTrace;
	}

	public String getMensaje() {
		return mensaje;
	}
}
