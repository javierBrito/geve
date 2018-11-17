package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.dto.GvKardexFechaSalidaDTO;
import ec.gob.educacion.dto.GvKardexStockDiferenciasDTO;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvCajaDetalle;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.service.GvCajaDetalleService;
import ec.gob.educacion.service.GvCajaService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvProductoService;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
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
public class GvKardexFechaCuadreCajaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvProductoService gvProductoService;
	@EJB
	private GvCajaService gvCajaService;
	@EJB
	private GvCajaDetalleService gvCajaDetalleService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvKardex gvKardex;
	private GvKardex gvKardexBuscar;
	private GvKardexFechaDTO gvKardexFechaDTO;
	private GvKardexFechaSalidaDTO gvKardexFechaSalidaDTO;
	private List<GvKardexFechaDTO> listaGvKardexFechaRegistra;
	private List<GvKardexFechaDTO> listaGvKardexFechaSaldos;
	private List<GvKardexFechaSalidaDTO> listaGvKardexFechaSaldosSalida;
	private List<GvKardexStockDiferenciasDTO> listaGvKardexStockDiferencias;
	private GvProducto gvProducto;
	private List<GvProducto> listaGvProducto;
	
	private GvCaja gvCajaActualizar;
	private GvCaja gvCajaBuscar;
	private GvCaja gvCajaUltimoRegistro;
	private List<GvCajaDetalle> listaGvCajaDetalleActualizar;
	private List<GvCajaDetalle> listaGvCajaDetalleActualizarAux;
	private List<GvCaja> listaUltimoRegistroCaja;
	private List<GvCaja> listaCajaPorFecha;
	
	private String codProceso;
	private String mensaje;
	private Integer idProductoBuscar;
	private Date fechaRegistra;
	private String fechaRegistraString;
	private float mntSaldo;
	private float mntImporte;
	private float mntImporteTotal;
	private boolean visibleCbActualizarCaja;

	@PostConstruct
	public void init() {
		gvKardex = new GvKardex();
		codProceso = "N";
		gvKardexBuscar = new GvKardex();
		listaGvKardexFechaRegistra = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexStockDiferencias = new ArrayList<GvKardexStockDiferenciasDTO>();
		mensaje = "";
		
		gvProducto = new GvProducto();
		gvProducto.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProducto = gvProductoService.buscarGvProductos(gvProducto);
		fechaRegistra = new Date();

		//Calendar calendar = Calendar.getInstance();
        //calendar.setTime(fechaRegistra); // Configuramos la fecha que se recibe
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DAY_OF_YEAR, -1);  // numero de días a añadir, o restar en caso de días<0
        //fechaAnterior = calendar.getTime();
	}
	
	public void buscarListaPorParametros() {
		mntSaldo = 0;
		mensaje = "";
		mntImporteTotal = 0;
		
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaRegistra); // Configuramos la fecha que se recibe
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	fechaRegistraString = sdf.format(calendar.getTime());
		
		gvKardexBuscar = new GvKardex();
		listaGvKardexFechaRegistra = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexStockDiferencias = new ArrayList<GvKardexStockDiferenciasDTO>();
		if (idProductoBuscar != 0) {
			gvProducto.setIdProducto(idProductoBuscar);
			gvKardexBuscar.setGvProducto(gvProducto);
		}
		if (fechaRegistra == null) {
			mensaje = "Ingrese fecha correcta...";
			return;
		}
		
		// Resetear objetos.
		listaGvKardexFechaSaldos = new ArrayList<GvKardexFechaDTO>();
		listaGvKardexFechaSaldosSalida = new ArrayList<GvKardexFechaSalidaDTO>();
		gvCajaActualizar = new GvCaja();
		gvCajaActualizar.setFechaRegistra(fechaRegistra);
		listaCajaPorFecha = new ArrayList<GvCaja>();
		listaGvCajaDetalleActualizar = new ArrayList<GvCajaDetalle>();
		listaGvCajaDetalleActualizarAux = new ArrayList<GvCajaDetalle>();
		visibleCbActualizarCaja = false;

		// Obtener lista del stock fecha Registra.
		gvKardexBuscar.setFechaRegistra(fechaRegistra);
		gvKardexBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		gvKardexBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvKardexFechaRegistra = gvKardexService.buscarListaPorFechaSaldos(gvKardexBuscar);

		// Armar la lista para mostrar.
		if (listaGvKardexFechaRegistra.size() > 0) {
			listaGvKardexFechaSaldosSalida = new ArrayList<GvKardexFechaSalidaDTO>();
			GvKardexFechaSalidaDTO gvKardexFechaDTOInicial = new GvKardexFechaSalidaDTO();
			gvKardexFechaDTOInicial.setDescripcion("SALDO ANTERIOR");
			
			// Mostrar el saldo inicial del registro de la caja de la fecha a cuadrar
			gvCajaBuscar = new GvCaja();
			gvCajaBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvCajaBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			listaUltimoRegistroCaja = new ArrayList<GvCaja>();
			listaUltimoRegistroCaja = gvCajaService.buscarUltimoRegistro(gvCajaBuscar);
			if (listaUltimoRegistroCaja.size() > 0) {
				gvCajaUltimoRegistro = new GvCaja();
				gvCajaUltimoRegistro = listaUltimoRegistroCaja.get(0);
				if (gvCajaUltimoRegistro != null) {
					mntSaldo = gvCajaUltimoRegistro.getMntSaldo();
					gvKardexFechaDTOInicial.setMntSaldo(Float.toString(mntSaldo));
					listaGvKardexFechaSaldosSalida.add(gvKardexFechaDTOInicial);
				}
			}

			// Mover los registros del kardex con los movimientos.
			for (GvKardexFechaDTO gvKardexFechaDTOAux : listaGvKardexFechaRegistra) {
				gvKardexFechaSalidaDTO = new GvKardexFechaSalidaDTO();
				
				mntSaldo = mntSaldo + gvKardexFechaDTOAux.getMntValorEgreso() - gvKardexFechaDTOAux.getMntValorIngreso();
				gvKardexFechaDTOAux.setMntSaldo(redondearDecimales(mntSaldo, 2));
				
				//Mover valores consultados de la vista de saldos hacia el registro de salida.
				gvKardexFechaSalidaDTO.setCodigo(gvKardexFechaDTOAux.getCodigo());
				gvKardexFechaSalidaDTO.setDescripcion(gvKardexFechaDTOAux.getDescripcion());
				gvKardexFechaSalidaDTO.setMntSaldo(Float.toString(mntSaldo));
				gvKardexFechaSalidaDTO.setNumEgreso(Integer.toString(gvKardexFechaDTOAux.getNumEgreso()));
				gvKardexFechaSalidaDTO.setNumIngreso(Integer.toString(gvKardexFechaDTOAux.getNumIngreso()));
				gvKardexFechaSalidaDTO.setMntValorEgreso(Float.toString(gvKardexFechaDTOAux.getMntValorEgreso()));
				gvKardexFechaSalidaDTO.setMntValorIngreso(Float.toString(gvKardexFechaDTOAux.getMntValorIngreso()));
				
				
				//Calcular Importe (items * precioDeCosto - items * precioDeCompra) 20161028
				if (gvKardexFechaDTOAux.getNumEgreso() > 0) {
					mntImporte = gvKardexFechaDTOAux.getMntValorEgreso() -
							    (gvKardexFechaDTOAux.getNumEgreso() * gvKardexFechaDTOAux.getPrecioDeCompra());
					mntImporteTotal = mntImporteTotal + mntImporte;
					//gvKardexFechaDTOAux.setMntImporte(redondearDecimales(mntImporte, 2));
					gvKardexFechaSalidaDTO.setMntImporte(Float.toString(redondearDecimales(mntImporte, 2)));
				}
				
				listaGvKardexFechaSaldosSalida.add(gvKardexFechaSalidaDTO);
			}

			// Mostrar el saldo final del registro de la caja de la fecha a cuadrar
			GvKardexFechaSalidaDTO gvKardexFechaDTOFinal = new GvKardexFechaSalidaDTO();
			gvKardexFechaDTOFinal.setDescripcion("SALDO FINAL");
			gvKardexFechaDTOFinal.setMntSaldo(Float.toString(redondearDecimales(mntSaldo, 2)));
			listaGvKardexFechaSaldosSalida.add(gvKardexFechaDTOFinal);

			// Mostrar el importe final del registro de la caja de la fecha a cuadrar
			GvKardexFechaSalidaDTO gvKardexFechaDTOFinalImporte = new GvKardexFechaSalidaDTO();
			gvKardexFechaDTOFinalImporte.setDescripcion("IMPORTE TOTAL");
			gvKardexFechaDTOFinalImporte.setMntImporte(Float.toString(redondearDecimales(mntImporteTotal, 2)));
			listaGvKardexFechaSaldosSalida.add(gvKardexFechaDTOFinalImporte);

			// Obtener datos de la caja.
			editarCaja();
		} else {
			mensaje = "No existen movimientos de la fecha...";
		}
	}

	//Redondear valor Double a 2 decimales.
    public static float redondearDecimales(float valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado= (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return (float) resultado;
    }	

	public void verificar(GvKardexFechaDTO gvKardexFechaDTO, Integer index) {
		if (gvKardexFechaDTO.isRegistroVerificado()) {
			gvKardexFechaDTO.setRegistroVerificado(false);
			mensaje = "Registro en proceso...";
		} else {
			gvKardexFechaDTO.setRegistroVerificado(true);
			mensaje = "Registro verificado...";
		}
	}

	public void editarCaja() {
		codProceso = "E";
		mensaje = "";

		//Obtener datos de la caja. Mostrar registros que tengan su valor > 0. (20161109)
		listaCajaPorFecha = new ArrayList<GvCaja>();
		gvCajaBuscar.setFechaRegistra(fechaRegistra);
		listaCajaPorFecha = gvCajaService.buscarListaPorParametros(gvCajaBuscar);
		if (listaCajaPorFecha.size() > 0) {
			gvCajaActualizar = listaCajaPorFecha.get(0);
			gvCajaActualizar.setMntLibro(redondearDecimales(mntSaldo,2));
			gvCajaActualizar.setMntDiferencia(redondearDecimales((gvCajaActualizar.getMntCaja() - gvCajaActualizar.getMntLibro()), 2));
			listaGvCajaDetalleActualizarAux = new ArrayList<GvCajaDetalle>();
			listaGvCajaDetalleActualizarAux = gvCajaDetalleService.buscarListaPorCaja(gvCajaActualizar);
			if (listaGvCajaDetalleActualizarAux.size() > 0) {
				listaGvCajaDetalleActualizar = new ArrayList<GvCajaDetalle>();
				for (GvCajaDetalle gvCajaDetalleAux : listaGvCajaDetalleActualizarAux) {
					if (gvCajaDetalleAux.getMntImporte() > 0) {
						listaGvCajaDetalleActualizar.add(gvCajaDetalleAux);
					}
				}
			}
			
			visibleCbActualizarCaja = true;
		} else {
			mensaje = "Registro de caja de esa fecha no existe..."; 
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actualizarCaja() {
		
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvCaja.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			gvCajaActualizar.setMntDiferencia(redondearDecimales((gvCajaActualizar.getMntCaja() - gvCajaActualizar.getMntLibro()), 2));
			//Guardar/Actualizar Caja
			if (codProceso == "E") {
				//Actualizar caja con merge.
				gvCajaService.actualizarGvCaja(gvCajaActualizar);
					
				mensaje = "Se ha actualizado los datos de la caja...";
				visibleCbActualizarCaja = false;
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "Error: No se actualizaron los datos...";
			else
				mensaje = (new StringBuilder()).append("Error: ").append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "E") {
			gvCajaActualizar.setFechaActualiza(new Date());
			gvCajaActualizar.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvCajaActualizar.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}
	
	public void exportarExcel() {
		String nombreArchivoExcel = "D:/reporteSaldos_"+fechaRegistraString.replace("/", "-")+".csv";
        try{
        	//Crear un libro
            XSSFWorkbook workbook = new XSSFWorkbook();
        	//Crear una hoja
            XSSFSheet sheet = workbook.createSheet("StockDiferencias");

        	//Crear la cabecera
            Row fila = sheet.createRow(0);
        	fila.createCell(0).setCellValue("No");
        	fila.createCell(1).setCellValue("Descripción");
        	fila.createCell(2).setCellValue("Código");
        	fila.createCell(3).setCellValue("Items");
        	fila.createCell(4).setCellValue("D e b e");
        	fila.createCell(5).setCellValue("Items");
        	fila.createCell(6).setCellValue("H a b e r");
        	fila.createCell(7).setCellValue("S a l d o");
        	fila.createCell(8).setCellValue("Importe");
        	//Crear c/registro de la lista.
			int rowIndex = 1;
            for (GvKardexFechaSalidaDTO gvKardexFechaSalidaDTOAux : listaGvKardexFechaSaldosSalida) {
            	fila = sheet.createRow(rowIndex);
                int columnIndex = 0;
                fila.createCell(columnIndex++).setCellValue(rowIndex);
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getDescripcion());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getCodigo());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getNumEgreso());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getMntValorEgreso());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getNumIngreso());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getMntValorIngreso());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getMntSaldo());
                fila.createCell(columnIndex++).setCellValue(gvKardexFechaSalidaDTOAux.getMntImporte());
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

	public Date getFechaRegistra() {
		return fechaRegistra;
	}

	public void setFechaRegistra(Date fechaRegistra) {
		this.fechaRegistra = fechaRegistra;
	}

	public List<GvKardexFechaDTO> getListaGvKardexFechaRegistra() {
		return listaGvKardexFechaRegistra;
	}

	public void setListaGvKardexFechaRegistra(
			List<GvKardexFechaDTO> listaGvKardexFechaRegistra) {
		this.listaGvKardexFechaRegistra = listaGvKardexFechaRegistra;
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

	public List<GvKardexFechaDTO> getListaGvKardexFechaSaldos() {
		return listaGvKardexFechaSaldos;
	}

	public void setListaGvKardexFechaSaldos(List<GvKardexFechaDTO> listaGvKardexFechaSaldos) {
		this.listaGvKardexFechaSaldos = listaGvKardexFechaSaldos;
	}

	public GvCaja getGvCajaActualizar() {
		return gvCajaActualizar;
	}

	public void setGvCajaActualizar(GvCaja gvCajaActualizar) {
		this.gvCajaActualizar = gvCajaActualizar;
	}

	public List<GvCajaDetalle> getListaGvCajaDetalleActualizar() {
		return listaGvCajaDetalleActualizar;
	}

	public void setListaGvCajaDetalleActualizar(
			List<GvCajaDetalle> listaGvCajaDetalleActualizar) {
		this.listaGvCajaDetalleActualizar = listaGvCajaDetalleActualizar;
	}

	public boolean isVisibleCbActualizarCaja() {
		return visibleCbActualizarCaja;
	}

	public void setVisibleCbActualizarCaja(boolean visibleCbActualizarCaja) {
		this.visibleCbActualizarCaja = visibleCbActualizarCaja;
	}

	public String getFechaRegistraString() {
		return fechaRegistraString;
	}

	public void setFechaRegistraString(String fechaRegistraString) {
		this.fechaRegistraString = fechaRegistraString;
	}

	public List<GvKardexFechaSalidaDTO> getListaGvKardexFechaSaldosSalida() {
		return listaGvKardexFechaSaldosSalida;
	}

	public void setListaGvKardexFechaSaldosSalida(
			List<GvKardexFechaSalidaDTO> listaGvKardexFechaSaldosSalida) {
		this.listaGvKardexFechaSaldosSalida = listaGvKardexFechaSaldosSalida;
	}
}