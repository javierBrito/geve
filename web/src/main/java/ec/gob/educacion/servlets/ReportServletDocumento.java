package ec.gob.educacion.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@WebServlet("/reportDocumento")
public class ReportServletDocumento extends HttpServlet {
	private static final long serialVersionUID = 8795000477609113780L;

	@Resource(mappedName = "java:jboss/datasources/geve-dsmysql")
	private DataSource dataSource;
	public static final String REPORT_PREFIX = "/reportDocumento/";
	public static final String REPORT_SUFFIX = ".jasper";
	public static final String REPORT_DEFINITION_SUFFIX = ".jrxml";
	
	@Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		try {
			String reporte = request.getServletContext().getRealPath("/reportDocumento/Documento.jasper");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros = obtenerParametros(request);
			connection = dataSource.getConnection();
			JasperPrint jasp = JasperFillManager.fillReport(reporte, parametros, connection);
			
			byte[] result = JasperExportManager.exportReportToPdf(jasp);
			response.setContentType( "application/pdf" );
			response.setContentLength(result.length);
			response.getOutputStream().write(result, 0, result.length);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new ServletException(e);
			}
		}
	}
	
	private Map<String, Object> obtenerParametros(HttpServletRequest request) {
		// Datos de la Empresa
		String idEmpresa = request.getParameter("IDEMPRESA");
		String nombreEmpresa = request.getParameter("NOMBREEMPRESA");
		String identificacionEmpresa = request.getParameter("IDENTIFICACIONEMPRESA");
		// Datos del Documento
		String idDocumento = request.getParameter("IDDOCUMENTO");
		String tipoDocumento = request.getParameter("TIPODOCUMENTO");
		String fechaDocumento = request.getParameter("FECHADOCUMENTO");
		String mntSubtotal = request.getParameter("MNTSUBTOTAL");
		String mntIva = request.getParameter("MNTIVA");
		String mntTotal = request.getParameter("MNTTOTAL");
		// Datos del Cliente
		String nombres = request.getParameter("NOMBRES");
		String dni = request.getParameter("DNI");
		String direccion = request.getParameter("DIRECCION");
		String movil = request.getParameter("MOVIL");
		String telefono = request.getParameter("TELEFONO");
		String pathImagen = request.getParameter("PATH_IMAGEN");

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("URL_LOGO", getURLWithContextPath(request) + pathImagen);
		parametros.put("URL_LOGO1", getURLWithContextPath(request) + "/img/fondoacta.png");
		// Datos de la Empresa
		parametros.put("IDEMPRESA", idEmpresa);
		parametros.put("NOMBREEMPRESA", nombreEmpresa);
		parametros.put("IDENTIFICACIONEMPRESA", identificacionEmpresa);
		// Datos del Documento
		parametros.put("IDDOCUMENTO", idDocumento);
		parametros.put("TIPODOCUMENTO", tipoDocumento);
		parametros.put("FECHADOCUMENTO", fechaDocumento);
		parametros.put("MNTSUBTOTAL", mntSubtotal);
		parametros.put("MNTIVA", mntIva);
		parametros.put("MNTTOTAL", mntTotal);
		// Datos del Cliente
		parametros.put("NOMBRES", nombres);
		parametros.put("DNI", dni);
		parametros.put("DIRECCION", direccion);
		parametros.put("MOVIL", movil);
		parametros.put("TELEFONO", telefono);
		
		return parametros;
	}
	
	private String getURLWithContextPath(HttpServletRequest request) {
		   return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}