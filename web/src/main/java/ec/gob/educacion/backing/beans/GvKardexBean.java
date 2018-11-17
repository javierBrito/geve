package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;
import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.model.geve.GvTipoMovimiento;
import ec.gob.educacion.service.GvDocumentoService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvProductoService;
import ec.gob.educacion.service.GvTipoMovimientoService;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ManagedBean
@ViewScoped
public class GvKardexBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvProductoService gvProductoService;
	@EJB
	private GvDocumentoService gvDocumentoService;
	@EJB
	private GvTipoMovimientoService gvTipoMovimientoService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvKardex gvKardex;
	private GvKardex gvKardexBuscar;
	private List<GvKardex> listaGvKardex;
	private GvProducto gvProducto;
	private List<GvProducto> listaGvProducto;
	private GvDocumento gvDocumento;
	private List<GvDocumento> listaGvDocumento;
	private GvTipoMovimiento gvTipoMovimiento;
	private List<GvTipoMovimiento> listaGvTipoMovimiento;
	
	private String mensaje;
	private String codProceso;
    private String descripcionBuscar;
    private String codigoBuscar;
	private String pathImagen;
	private String nombreEmpresa;
	
    private int opcionBusqueda;
	private Integer idProductoBuscar;
	private Integer idTipoMovimientoBuscar;
	private Integer idDocumentoBuscar;

	@PostConstruct
	public void init() {
		opcionBusqueda = 0;
		codProceso = "";
		gvKardex = new GvKardex();
		gvKardexBuscar = new GvKardex();
		listaGvKardex = new ArrayList<GvKardex>();
		mensaje = "";
		
		gvProducto = new GvProducto();
		gvProducto.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProducto = gvProductoService.buscarGvProductos(gvProducto);
		gvTipoMovimiento = new GvTipoMovimiento();
		listaGvTipoMovimiento = gvTipoMovimientoService.buscarGvTipoMovimientos();
		gvDocumento = new GvDocumento();
		gvDocumento.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvDocumento = gvDocumentoService.buscarGvDocumentos(gvDocumento);
		
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
	}
	
	public void buscarListaPorParametros() {
		mensaje = "";		
		gvKardexBuscar = new GvKardex();
		gvKardexBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvKardex = new ArrayList<GvKardex>();
		if (idProductoBuscar == 0 && 
			idTipoMovimientoBuscar == 0 &&
			idDocumentoBuscar == 0) {
			listaGvKardex = gvKardexService.buscarGvKardexs(gvKardexBuscar);
		} else {
			if (idProductoBuscar != 0) {
				gvProducto.setIdProducto(idProductoBuscar);
				gvKardexBuscar.setGvProducto(gvProducto);
			}
			if (idTipoMovimientoBuscar != 0) {
				gvTipoMovimiento.setIdTipoMovimiento(idTipoMovimientoBuscar);
				gvKardexBuscar.setGvTipoMovimiento(gvTipoMovimiento);
			}
			if (idDocumentoBuscar != 0) {
				gvDocumento.setIdDocumento(idDocumentoBuscar);
				gvKardexBuscar.setGvDocumento(gvDocumento);
			}
			listaGvKardex = gvKardexService.buscarListaPorParametros(gvKardexBuscar);
		}
	}

	public void nuevo() {
		gvKardex = new GvKardex();
		gvProducto = new GvProducto();
		gvProducto.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProducto = gvProductoService.buscarGvProductos(gvProducto);
		
		gvTipoMovimiento = new GvTipoMovimiento();
		listaGvTipoMovimiento = gvTipoMovimientoService.buscarGvTipoMovimientos();
		
		gvDocumento = new GvDocumento();
		gvDocumento.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvDocumento = gvDocumentoService.buscarGvDocumentos(gvDocumento);

		mensaje = "";
		codProceso = "N";
	}

	public void editar(GvKardex gvKardex) {
		codProceso = "E";
		this.gvKardex = gvKardex;
		if (this.gvKardex.getGvProducto() != null) {
			gvProducto = this.gvKardex.getGvProducto();
			gvProducto.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			listaGvProducto = gvProductoService.buscarGvProductos(gvProducto);
		}
		if (this.gvKardex.getGvTipoMovimiento() != null) {
			gvTipoMovimiento = this.gvKardex.getGvTipoMovimiento();
			listaGvTipoMovimiento = gvTipoMovimientoService.buscarGvTipoMovimientos();
		}
		if (this.gvKardex.getGvDocumento() != null) {
			gvDocumento = this.gvKardex.getGvDocumento();
			idDocumentoBuscar = gvDocumento.getIdDocumento();
			gvDocumento.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			listaGvDocumento = gvDocumentoService.buscarGvDocumentos(gvDocumento);
		}
		mensaje = "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void guardar() {
		if (gvKardex.getNumCantidad() == 0) {
			mensaje = "Ingrese cantidad...";
			return;
		}
		
		if (gvKardex.getMntPrecio() == 0) {
			mensaje = "Ingrese el precio...";
			return;
		}
		
		if (gvKardex.getNumExistenciaActual() == 0) {
			mensaje = "Ingrese cantidad de existencia actual...";
			return;
		}
		
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvKardex.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			gvKardex.setGvProducto(gvProducto);
			gvKardex.setGvTipoMovimiento(gvTipoMovimiento);
			gvKardex.setGvDocumento(gvDocumento);
			
			//Calcular montos.
			gvKardex.setMntValor(gvKardex.getNumCantidad() * gvKardex.getMntPrecio());
			gvKardex.setMntValor(gvKardex.getNumExistenciaActual() * gvKardex.getMntPrecio());
			
			gvKardexService.guardarGvKardex(gvKardex);
			mensaje = "Datos actualizados correctamente...";
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "Error: No se actualizaron los datos...";
			else
				mensaje = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "N") {
			gvKardex.setEstado(1);
			gvKardex.setFechaRegistra(new Date());
			gvKardex.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvKardex.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvKardex.setFechaActualiza(new Date());
			gvKardex.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvKardex.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	//Ejecutar procesos de acuerdo a lo solicitado en el OneMenu.
	public void procesoAplicaIva(ValueChangeEvent evt){
		/*socializadasRutas = (Integer)evt.getNewValue();
		if (socializadasRutas == 1) {
		} else {
		}*/
	}
	
	public void exportarExcel() {
    	String nombreArchivoExcel = "D:/reporteInventarios.csv";
        try{
        	//Crear un libro
            XSSFWorkbook workbook = new XSSFWorkbook();
        	//Crear una hoja
            XSSFSheet sheet = workbook.createSheet("reporteInventarios");

        	//Crear la cabecera
            Row fila = sheet.createRow(0);
        	fila.createCell(0).setCellValue("No");
        	fila.createCell(1).setCellValue("Producto");
        	fila.createCell(2).setCellValue("Tipo Movimiento");
        	fila.createCell(3).setCellValue("Documento");
        	fila.createCell(4).setCellValue("Cantidad");
        	fila.createCell(5).setCellValue("Precio");
        	fila.createCell(6).setCellValue("Monto I/E");
        	fila.createCell(7).setCellValue("Cantidad Actual");
        	fila.createCell(8).setCellValue("Monto Actual");
            
        	//Crear c/registro de la lista.
			int rowIndex = 1;
            for (GvKardex gvKardexAux : listaGvKardex) {
            	fila = sheet.createRow(rowIndex);
                int columnIndex = 0;
                fila.createCell(columnIndex++).setCellValue(rowIndex);
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getGvProducto().getDescripcion());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getGvTipoMovimiento().getDescripcion()+" - "+gvKardexAux.getFechaRegistra().toString());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getGvDocumento().getIdDocumento());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getNumCantidad());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getMntPrecio());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getMntValor());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getNumExistenciaActual());
                fila.createCell(columnIndex++).setCellValue(gvKardexAux.getMntExistenciaActual());
                rowIndex++;
            }
            //Escribir resultados a un fichero Excel
            FileOutputStream archivoSalida = new FileOutputStream(nombreArchivoExcel);
            workbook.write(archivoSalida);
            archivoSalida.close();
            
            //Abrir fichero.
    		Runtime.getRuntime().exec("cmd /c start "+nombreArchivoExcel);
            
            mensaje = "Archivo excel generado: ("+nombreArchivoExcel+")\n";
        }catch (Exception e){
            mensaje = "Error al grabar Archivo Excel... ("+nombreArchivoExcel+")\n";
        }
	}

	public List<GvKardex> getListaGvKardex() {
		return listaGvKardex;
	}

	public void setListaGvKardex(List<GvKardex> listaGvKardex) {
		this.listaGvKardex = listaGvKardex;
	}

	public GvKardex getGvKardex() {
		return gvKardex;
	}

	public void setGvKardex(GvKardex gvKardex) {
		this.gvKardex = gvKardex;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<GvProducto> getListaGvProducto() {
		return listaGvProducto;
	}

	public void setListaGvProducto(List<GvProducto> listaGvProducto) {
		this.listaGvProducto = listaGvProducto;
	}

	public GvProducto getGvProducto() {
		return gvProducto;
	}

	public void setGvProducto(GvProducto gvProducto) {
		this.gvProducto = gvProducto;
	}

	public int getOpcionBusqueda() {
		return opcionBusqueda;
	}

	public void setOpcionBusqueda(int opcionBusqueda) {
		this.opcionBusqueda = opcionBusqueda;
	}

	public String getDescripcionBuscar() {
		return descripcionBuscar;
	}

	public void setDescripcionBuscar(String descripcionBuscar) {
		this.descripcionBuscar = descripcionBuscar;
	}

	public String getCodigoBuscar() {
		return codigoBuscar;
	}

	public void setCodigoBuscar(String codigoBuscar) {
		this.codigoBuscar = codigoBuscar;
	}

	public GvDocumento getGvDocumento() {
		return gvDocumento;
	}

	public void setGvDocumento(GvDocumento gvDocumento) {
		this.gvDocumento = gvDocumento;
	}

	public List<GvDocumento> getListaGvDocumento() {
		return listaGvDocumento;
	}

	public void setListaGvDocumento(List<GvDocumento> listaGvDocumento) {
		this.listaGvDocumento = listaGvDocumento;
	}

	public GvTipoMovimiento getGvTipoMovimiento() {
		return gvTipoMovimiento;
	}

	public void setGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) {
		this.gvTipoMovimiento = gvTipoMovimiento;
	}

	public List<GvTipoMovimiento> getListaGvTipoMovimiento() {
		return listaGvTipoMovimiento;
	}

	public void setListaGvTipoMovimiento(List<GvTipoMovimiento> listaGvTipoMovimiento) {
		this.listaGvTipoMovimiento = listaGvTipoMovimiento;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public Integer getIdDocumentoBuscar() {
		return idDocumentoBuscar;
	}

	public void setIdDocumentoBuscar(Integer idDocumentoBuscar) {
		this.idDocumentoBuscar = idDocumentoBuscar;
	}

	public String getPathImagen() {
		return pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public GvKardex getGvKardexBuscar() {
		return gvKardexBuscar;
	}

	public void setGvKardexBuscar(GvKardex gvKardexBuscar) {
		this.gvKardexBuscar = gvKardexBuscar;
	}

	public Integer getIdProductoBuscar() {
		return idProductoBuscar;
	}

	public void setIdProductoBuscar(Integer idProductoBuscar) {
		this.idProductoBuscar = idProductoBuscar;
	}

	public Integer getIdTipoMovimientoBuscar() {
		return idTipoMovimientoBuscar;
	}

	public void setIdTipoMovimientoBuscar(Integer idTipoMovimientoBuscar) {
		this.idTipoMovimientoBuscar = idTipoMovimientoBuscar;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
}