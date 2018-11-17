package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.dto.GvKardexStockDiferenciasDTO;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvProductoService;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@ManagedBean
@ViewScoped
public class GvKardexFechaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvProductoService gvProductoService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvKardex gvKardex;
	private GvKardex gvKardexBuscar;
	private GvKardexFechaDTO gvKardexFechaDTO;
	private List<GvKardexFechaDTO> listaGvKardexFechaAnterior;
	private List<GvKardexFechaDTO> listaGvKardexFechaActual;
	private List<GvKardexStockDiferenciasDTO> listaGvKardexStockDiferencias;
	private GvProducto gvProducto;
	private List<GvProducto> listaGvProducto;
	
	private String mensaje;
	private String nombreEmpresa;
	
	private Integer idProductoBuscar;
	
	private Date fechaAnterior;
	private Date fechaActual;

	@PostConstruct
	public void init() {
		gvKardex = new GvKardex();
		gvKardexBuscar = new GvKardex();
		listaGvKardexFechaAnterior = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexFechaActual = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexStockDiferencias = new ArrayList<GvKardexStockDiferenciasDTO>();
		mensaje = "";
		
		gvProducto = new GvProducto();
		gvProducto.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProducto = gvProductoService.buscarGvProductos(gvProducto);
		
		fechaActual = new Date();

		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, -1);  // numero de días a añadir, o restar en caso de días<0
        fechaAnterior = calendar.getTime();
        
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
	}
	
	public void buscarListaPorParametros() {
		mensaje = "";		
		gvKardexBuscar = new GvKardex();
		listaGvKardexFechaAnterior = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexFechaActual = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexStockDiferencias = new ArrayList<GvKardexStockDiferenciasDTO>();
		if (idProductoBuscar != 0) {
			gvProducto.setIdProducto(idProductoBuscar);
			gvKardexBuscar.setGvProducto(gvProducto);
		}
		if (fechaAnterior == null ||
			fechaActual == null ||
			fechaAnterior.after(fechaActual)) {
			mensaje = "Ingrese fechas correctas...";
		} else {
			//Obtener lista del stock fecha Anetrior.
			gvKardexBuscar.setFechaRegistra(fechaAnterior);
			gvKardexBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			listaGvKardexFechaAnterior = gvKardexService.buscarListaStockPorFecha(gvKardexBuscar);
			
			//Obtener lista del stock fecha Actual.
			gvKardexBuscar.setFechaRegistra(fechaActual);
			listaGvKardexFechaActual = gvKardexService.buscarListaStockPorFecha(gvKardexBuscar);
			
			int index = 0;
			for (GvKardexFechaDTO gvKardexFechaDTOAux : listaGvKardexFechaAnterior) {
				GvKardexStockDiferenciasDTO gvKardexStockDiferenciasDTO = new GvKardexStockDiferenciasDTO();
				gvKardexStockDiferenciasDTO.setIdProducto(gvKardexFechaDTOAux.getIdProducto());
				gvKardexStockDiferenciasDTO.setDescripcion(gvKardexFechaDTOAux.getDescripcion());
				gvKardexStockDiferenciasDTO.setCodigo(gvKardexFechaDTOAux.getCodigo());
				gvKardexStockDiferenciasDTO.setNumExistenciaAnterior(gvKardexFechaDTOAux.getNumExistencia());
				
				GvKardexFechaDTO gvKardexFechaDTOAux1 = new GvKardexFechaDTO();
				gvKardexFechaDTOAux1 = listaGvKardexFechaActual.get(index);
				
				//Calcular diferencias si las cantidades son <> 0.
				if (gvKardexFechaDTOAux.getNumExistencia() != 0) {
					gvKardexStockDiferenciasDTO.setNumDiferencia(gvKardexFechaDTOAux.getNumExistencia() -
							 gvKardexFechaDTOAux1.getNumExistencia());
				}
				gvKardexStockDiferenciasDTO.setNumExistenciaActual(gvKardexFechaDTOAux1.getNumExistencia());
				
				if (gvKardexFechaDTOAux.getIdEmpresa() != gvKardexFechaDTOAux.getIdProducto()) {
					listaGvKardexStockDiferencias.add(gvKardexStockDiferenciasDTO);
				}
				
				index = index + 1;
			}
		}
	}

	public void verificar(GvKardexStockDiferenciasDTO gvKardexStockDiferenciasDTO, Integer index) {
		if (gvKardexStockDiferenciasDTO.isRegistroVerificado()) {
			gvKardexStockDiferenciasDTO.setRegistroVerificado(false);
			mensaje = "Registro en proceso...";
		} else {
			gvKardexStockDiferenciasDTO.setRegistroVerificado(true);
			mensaje = "Registro verificado...";
		}
	}
	
	public void exportarExcel() {
    	String nombreArchivoExcel = "D:/reporteStockDiferencias.csv";
        try{
        	//Crear un libro
            XSSFWorkbook workbook = new XSSFWorkbook();
        	//Crear una hoja
            XSSFSheet sheet = workbook.createSheet("StockDiferencias");

        	//Crear la cabecera
            Row fila = sheet.createRow(0);
        	fila.createCell(0).setCellValue("No");
        	fila.createCell(1).setCellValue("Producto");
        	fila.createCell(2).setCellValue("Descripción");
        	fila.createCell(3).setCellValue("Código");
        	fila.createCell(4).setCellValue("Cantidad Anterior");
        	fila.createCell(5).setCellValue("Cantidad Actual");
        	fila.createCell(6).setCellValue("Diferencia");
            
        	//Crear c/registro de la lista.
			int rowIndex = 1;
            for (GvKardexStockDiferenciasDTO gvKardexStockDiferenciasDTOAux : listaGvKardexStockDiferencias) {
            	fila = sheet.createRow(rowIndex);
                int columnIndex = 0;
                fila.createCell(columnIndex++).setCellValue(rowIndex);
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getIdProducto());
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getDescripcion());
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getCodigo());
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getNumExistenciaAnterior());
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getNumExistenciaActual());
                fila.createCell(columnIndex++).setCellValue(gvKardexStockDiferenciasDTOAux.getNumDiferencia());
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

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
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

	public Date getFechaAnterior() {
		return fechaAnterior;
	}

	public void setFechaAnterior(Date fechaAnterior) {
		this.fechaAnterior = fechaAnterior;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<GvKardexFechaDTO> getListaGvKardexFechaAnterior() {
		return listaGvKardexFechaAnterior;
	}

	public void setListaGvKardexFechaAnterior(List<GvKardexFechaDTO> listaGvKardexFechaAnterior) {
		this.listaGvKardexFechaAnterior = listaGvKardexFechaAnterior;
	}

	public List<GvKardexFechaDTO> getListaGvKardexFechaActual() {
		return listaGvKardexFechaActual;
	}

	public void setListaGvKardexFechaActual(
			List<GvKardexFechaDTO> listaGvKardexFechaActual) {
		this.listaGvKardexFechaActual = listaGvKardexFechaActual;
	}

	public GvKardexFechaDTO getGvKardexFechaDTO() {
		return gvKardexFechaDTO;
	}

	public void setGvKardexFechaDTO(GvKardexFechaDTO gvKardexFechaDTO) {
		this.gvKardexFechaDTO = gvKardexFechaDTO;
	}

	public List<GvKardexStockDiferenciasDTO> getListaGvKardexStockDiferencias() {
		return listaGvKardexStockDiferencias;
	}

	public void setListaGvKardexStockDiferencias(
			List<GvKardexStockDiferenciasDTO> listaGvKardexStockDiferencias) {
		this.listaGvKardexStockDiferencias = listaGvKardexStockDiferencias;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
}