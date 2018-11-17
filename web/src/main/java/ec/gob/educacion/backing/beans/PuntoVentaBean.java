package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.model.geve.GvClase;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvMarca;
import ec.gob.educacion.model.geve.GvParametro;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.model.geve.GvTipoDocumento;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvCliente;
import ec.gob.educacion.model.geve.GvEstadoDocumento;
import ec.gob.educacion.model.geve.GvUnidad;
import ec.gob.educacion.service.GvDocumentoDetalleService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvParametroService;
import ec.gob.educacion.service.GvProductoService;
import ec.gob.educacion.service.GvTipoDocumentoService;
import ec.gob.educacion.service.GvDocumentoService;
import ec.gob.educacion.service.GvClienteService;
import ec.gob.educacion.service.GvEstadoDocumentoService;
import ec.gob.educacion.service.GvTipoMovimientoService;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.richfaces.application.ServiceTracker;
import org.richfaces.focus.FocusManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

@ManagedBean
@ViewScoped
public class PuntoVentaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private GvDocumentoService gvDocumentoService;
	@EJB
	private GvDocumentoDetalleService gvDocumentoDetalleService;
	@EJB
	private GvClienteService gvClienteService;
	@EJB
	private GvTipoDocumentoService gvTipoDocumentoService;
	@EJB
	private GvTipoMovimientoService gvTipoMovimientoService;
	@EJB
	private GvEstadoDocumentoService gvEstadoDocumentoService;
	@EJB
	private GvProductoService gvProductoService;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvParametroService gvParametroService;

	//para manipular el SessionController
    //@ManagedProperty(value = "#{sessionController}")
	@Inject
    private SessionController sesionControlador;
	
	private GvDocumento gvDocumento;
	private GvDocumento gvDocumentoBuscar;
	private List<GvDocumento> listaGvDocumento;

	private GvCliente gvCliente;
	private List<GvCliente> listaGvCliente;
	
	private GvTipoDocumento gvTipoDocumento;
	private List<GvTipoDocumento> listaGvTipoDocumento;
	
	private GvEstadoDocumento gvEstadoDocumento;
	private List<GvEstadoDocumento> listaGvEstadoDocumento;

	private GvDocumentoDetalle gvDocumentoDetalleSeleccionado;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalle;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleAux;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleEliminar;

	private GvProducto gvProducto;
	private GvProducto gvProductoNew;
	private GvProducto gvProductoSeleccionado;
	private List<GvProducto> listaGvProducto;
	private List<GvProducto> listaGvProductoPuntoVenta;
	private GvKardex gvKardexBuscar;
	private GvKardexFechaDTO gvKardexFechaDTO;
	private List<GvKardexFechaDTO> listaGvKardexStockMinimos;
	private List<GvKardexFechaDTO> listaGvKardexStockMinimosAux;
	
	private String mensaje;
	private String mensajeDocumento;
	private String codProceso;
	private String fechaRegistraString;
	private String codigoProducto;
	private String creaScript;
	private String reportName;
	private String nombreEmpresa;
	private String descripcionProducto;
	private String nombreImagen;
	private String observacionesKardex;

	private Integer idClienteBuscar;
	private Integer idTipoDocumentoBuscar;
	private Integer idEstadoDocumentoBuscar;
	private Integer cantidadPrecioDeCosto;
	private Integer cantidadPrecioPorMayor;
	private Integer cantidadPrecioPorMenor;
	private Integer numCantidad;
	private Integer numExistenciaActual;
	private Integer numExistenciaMinima;
	
	private int idEmpresa;
	private int posicionInicio;
	private int posicionFin;
	
	private boolean procesoPrecioDeCosto;
	private boolean procesoPrecioPorMayor;
	private boolean procesoPrecioPorMenor;
	private boolean visiblePgStockMinimos;
	private boolean visibleDgProductosExistencia;
	private boolean visibleCbGuardarDocumento;
	
	private float mntEntrega;
	private float mntCambio;
	private float porcentajeAumento;
	private Date fechaRegistra;

	@PostConstruct
	public void init() {
		idEmpresa = sesionControlador.getUsuario().getSede().getCodigo().intValue();
		nuevo();
		
		gvProductoNew = new GvProducto();
		codProceso = "N";
		mensaje = "";
		mensajeDocumento = "";

		gvDocumento = new GvDocumento();
		gvDocumentoBuscar = new GvDocumento();
		listaGvDocumento = new ArrayList<GvDocumento>();
		listaGvDocumentoDetalle = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		gvProductoSeleccionado = new GvProducto();
		gvProducto = new GvProducto();

		listaGvProducto = new ArrayList<GvProducto>();
		gvProductoSeleccionado.setIdEmpresa(idEmpresa);
		listaGvProducto = gvProductoService.buscarGvProductos(gvProductoSeleccionado);
		galeriaProductos();

		cantidadPrecioDeCosto = 0;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = 0;
		porcentajeAumento = 0;
		numCantidad = 0;
		codigoProducto = "";
		visibleCbGuardarDocumento = true;

		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
		grabarArchivoTemporalEnImg(sesionControlador.getUsuario().getSede().getImagen(), sesionControlador.getUsuario().getSede().getNombreImagen());
		
		// Obtener, parámetros de hora de atención y % de recargo.
		obtenerParametroHoraRecargo();

		// Obtener, stock mínimo.
		obtenerListaStockMinimo();

		buscarDocumentosPorParametros();
		
		descripcionProducto = "";
		numExistenciaActual = 0;
		numExistenciaMinima = 0;
		nombreImagen = "";
		visibleDgProductosExistencia = false;
		observacionesKardex = "";
	}
	
	public void inicializaAlCambioDescripcion() {
		for (GvProducto producto : listaGvProducto) {
			if (producto.getDescripcion().equals(descripcionProducto)) {
				codigoProducto = producto.getCodigo();
				numExistenciaActual = producto.getNumExistenciaActual();
				numExistenciaMinima = producto.getNumExistenciaMinima();
				nombreImagen = producto.getNombreImagen();
				break;
			}
		}
		if (!descripcionProducto.equals("")) {
			gvProducto.setCodigo(codigoProducto);
			gvProducto.setDescripcion(descripcionProducto);

			//Actualizar lista listaStockMinimo
			obtenerListaStockMinimo();
			//Actualizar lista listaGaleriaProductos
			galeriaProductos();

			posicionInicio = 0;
			posicionFin = codigoProducto.length();
			
			ingresarProductoPrecioDeCosto();

			//Para que luego se ponga el foco en el campo Cantidad
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
	        focusManager.focus("itNumCantidad");		
        }
	}
	
	public void inicializaAlCambioCodigo() {
		for (GvProducto producto : listaGvProducto) {
			if (producto.getCodigo().equals(codigoProducto)) {
				descripcionProducto = producto.getDescripcion();
				numExistenciaActual = producto.getNumExistenciaActual();
				numExistenciaMinima = producto.getNumExistenciaMinima();
				nombreImagen = producto.getNombreImagen();
				break;
			}
		}
		if (!descripcionProducto.equals("")) {
			gvProducto.setCodigo(codigoProducto);
			gvProducto.setDescripcion(descripcionProducto);

			//Actualizar lista listaStockMinimo
			obtenerListaStockMinimo();
			//Actualizar lista listaGaleriaProductos
			galeriaProductos();
			
			posicionInicio = 0;
			posicionFin = codigoProducto.length();
			
			ingresarProductoPrecioDeCosto();

			//Para que luego se ponga el foco en el campo Cantidad
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
	        focusManager.focus("itNumCantidad");		
		}
	}

	public void valueChangeListener(ValueChangeEvent e) {
		//visiblePgStockMinimos = false;
		posicionInicio = 0;
		posicionFin = e.getOldValue().toString().length();
		
		gvProducto.setCodigo(codigoProducto);
		gvProducto.setDescripcion(descripcionProducto);
	}
	
	//Guardar imagen de la Empresa en la ruta: /img/nombreArchivo 
	public void grabarArchivoTemporalEnImg(byte[] imagen, String nombreArchivo) {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pathImagen = ec.getRealPath(String.format("/img/%s", nombreArchivo));
        
		try {
			OutputStream out = new FileOutputStream(pathImagen);
			out.write(imagen);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ingresarProductoPrecioDeCosto() {
		if (numCantidad.equals(0)) {
			numCantidad = 1;
		}
		cantidadPrecioDeCosto = numCantidad;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = 0;
		ingresarProductoPrecio();
	}
	
	public void ingresarProductoPrecioPorMayor() {
		if (numCantidad.equals(0)) {
			numCantidad = 1;
		}
		cantidadPrecioDeCosto = 0;
		cantidadPrecioPorMayor = numCantidad;
		cantidadPrecioPorMenor = 0;

		ingresarProductoPrecio();
	}

	public void ingresarProductoPrecioPorMenor() {
		if (numCantidad.equals(0)) {
			numCantidad = 1;
		}
		cantidadPrecioDeCosto = 0;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = numCantidad;

		ingresarProductoPrecio();
	}
	
	public void ingresarProductoPrecio() {
		mensaje = "";
		visiblePgStockMinimos = false;
		
		if (codigoProducto.equals("")) {
			mensaje = "Ingrese código de Producto...";
			numCantidad = 0;
			return;
		}
		if (posicionFin > 0 && posicionInicio < posicionFin) {
			codigoProducto = codigoProducto.substring(posicionInicio, posicionFin);
			posicionInicio = 0;
			posicionFin = 0;
		}
		gvProductoSeleccionado = new GvProducto();
		gvProductoSeleccionado = gvProductoService.buscarPorCodigo(idEmpresa, codigoProducto);
		if (gvProductoSeleccionado == null) {
			mensaje = "No existe Producto...";
			numCantidad = 0;
			return;
		}
		//Mostrar datos del producto. (jbrito-20170713)
		descripcionProducto = gvProductoSeleccionado.getDescripcion();
		numExistenciaActual = gvProductoSeleccionado.getNumExistenciaActual();
		numExistenciaMinima = gvProductoSeleccionado.getNumExistenciaMinima();

		ingresarCantidadPrecio();
	}
	
	public void generarCodigoBarras() {
		try {
			Document document = new Document(PageSize.A4, 36, 36, 10, 10);
			
			PdfWriter pw = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/javier.brito/Downloads/prueba.pdf"));
			document.open();
			PdfContentByte pcb = pw.getDirectContent();
			
			Barcode128 code128 = new Barcode128();
			code128.setCode("1234567");
			code128.setCodeType(Barcode.CODE128);
			code128.setTextAlignment(Element.ALIGN_CENTER);
			
			Image image = code128.createImageWithBarcode(pcb, null, null);
			float percent = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - 0) / image.getWidth() * 20);
			image.scalePercent(percent);
			image.setAlignment(Element.ALIGN_CENTER);
			document.add(image);
			document.close();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// Obtener, parámetros de hora de atención y % de recargo.
	@SuppressWarnings({ "deprecation" })
	public void obtenerParametroHoraRecargo() {
        GvParametro gvParametro = new GvParametro();
        gvParametro = gvParametroService.buscarPorNombre("PARAMETRO01");
        if (gvParametro != null) {
        	// Obtener Hora Actual del sistema.
    		fechaRegistra = new Date();
            int horaActual = fechaRegistra.getHours();
            if (gvParametro.getHoraRecargo() != 0 && 
        		gvParametro.getPorcentajeRecargo() != 0) {
        		if (horaActual >= gvParametro.getHoraRecargo()) {
        			double porcentajeAumentoDouble = gvParametro.getPorcentajeRecargo();
        			porcentajeAumentoDouble = porcentajeAumentoDouble / 100.0;
        			porcentajeAumento = (float) (gvParametro.getPorcentajeRecargo() / 100.0);
				}
			}
		}
		
	}
	
	public void ocultarPgStockMinimos() {
		visiblePgStockMinimos = !visiblePgStockMinimos;
	}
	
	public void ocultarDgProductosExistencia() {
		visibleDgProductosExistencia = !visibleDgProductosExistencia;
	}
	
	@SuppressWarnings("deprecation")
	public void obtenerListaStockMinimo() {
		visiblePgStockMinimos = false;
		// Encerar tiempo en fechaRegistra
		fechaRegistra.setHours(0);
		fechaRegistra.setMinutes(0);
		fechaRegistra.setSeconds(0);
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaRegistra); // Configuramos la fecha que se recibe
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	setFechaRegistraString(sdf.format(calendar.getTime()));
    	
		gvKardexBuscar = new GvKardex();
		listaGvKardexStockMinimos = new ArrayList<GvKardexFechaDTO>();
    	
		// Obtener lista del stock general por producto.
		gvKardexBuscar.setFechaRegistra(fechaRegistra);
		gvKardexBuscar.setIdEmpresa(idEmpresa);
		GvProducto gvProductoBuscar = new GvProducto();
		if (descripcionProducto.equals("")) {
			gvProductoBuscar.setDescripcion("A");
		} else {
			gvProductoBuscar.setDescripcion(descripcionProducto);
		}
		gvKardexBuscar.setGvProducto(gvProductoBuscar);
		listaGvKardexStockMinimos = gvKardexService.buscarListaStockPorProducto(gvKardexBuscar);
		if (listaGvKardexStockMinimos.size() > 0) {
			listaGvKardexStockMinimosAux = new ArrayList<GvKardexFechaDTO>();
			for (GvKardexFechaDTO gvKardexFechaDTOAux : listaGvKardexStockMinimos) {
				if (gvKardexFechaDTOAux.getNumExistenciaMinima() >= gvKardexFechaDTOAux.getNumExistencia() &&
				    gvKardexFechaDTOAux.getIdEmpresa() != gvKardexFechaDTOAux.getIdProducto()) {
					listaGvKardexStockMinimosAux.add(gvKardexFechaDTOAux);
				}
			}
			if (listaGvKardexStockMinimosAux.size() > 0) {
				visiblePgStockMinimos = true;
			}
		}
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

	public void autocompleteDescripcion() {
		System.out.println("autocompleteDescripcion() I");
		System.out.println("descripcionProducto = "+descripcionProducto);
		System.out.println("autocompleteDescripcion() F");
	}
	
	public void galeriaProductos() {
		//nuevo();
		List<GvProducto> listaGvProductoTmp = new ArrayList<GvProducto>();
		GvProducto gvProductoTmp = new GvProducto();
		gvProductoTmp.setIdEmpresa(idEmpresa);
		if (descripcionProducto.equals("")) {
			gvProductoTmp.setDescripcion("A");
		} else {
			gvProductoTmp.setDescripcion(descripcionProducto);
		}
		listaGvProductoTmp = gvProductoService.buscarGvProductos(gvProductoTmp);

		listaGvProductoPuntoVenta = new ArrayList<GvProducto>();
        for (GvProducto gvProductoAux : listaGvProductoTmp) {
        	if (gvProductoAux.getImagen() != null) {
            	grabarArchivoTemporal(gvProductoAux.getImagen(), gvProductoAux.getNombreImagen());

        		// Obtener lista del stock general por producto.
            	gvKardexBuscar = new GvKardex();
        		listaGvKardexStockMinimos = new ArrayList<GvKardexFechaDTO>();
        		gvKardexBuscar.setGvProducto(gvProductoAux);
        		listaGvKardexStockMinimos = gvKardexService.buscarListaStockPorProducto(gvKardexBuscar);
        		if (listaGvKardexStockMinimos.size() > 0) {
        			gvProductoAux.setNumExistenciaActual(listaGvKardexStockMinimos.get(0).getNumExistencia());
				}

        		listaGvProductoPuntoVenta.add(gvProductoAux);
        	}
        }
    }	

	public String grabarArchivoTemporal(byte[] imagen, String nombreArchivo) {
        //Grabar archivo temporal  en el server: en la ruta: filePath 
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pathImagen = ec.getRealPath(String.format("/resources/%s", nombreArchivo));
        
		try {
			OutputStream out = new FileOutputStream(pathImagen);
			out.write(imagen);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathImagen;
	}
	
	@SuppressWarnings("deprecation")
	public void buscarDocumentosPorParametros() {
		gvDocumentoBuscar = new GvDocumento();
		gvDocumentoBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		fechaRegistra.setHours(0);
		fechaRegistra.setMinutes(0);
		fechaRegistra.setSeconds(0);
		gvDocumentoBuscar.setFechaRegistra(fechaRegistra);
		gvTipoDocumento = new GvTipoDocumento();
		gvTipoDocumento.setIdTipoDocumento(1);
		gvDocumentoBuscar.setGvTipoDocumento(gvTipoDocumento);
		gvDocumentoBuscar.setIdEmpresa(idEmpresa);
		listaGvDocumento = new ArrayList<GvDocumento>();
		listaGvDocumento = gvDocumentoService.buscarListaPorParametros(gvDocumentoBuscar);
	}

	public void nuevoProducto() {
		gvProductoNew = new GvProducto();
		nombreImagen = "";
	}

	public void nuevo() {
		visiblePgStockMinimos = true;
		//visibleDgProductosExistencia = false;
		gvProductoNew = new GvProducto();
		codProceso = "N";
		codigoProducto = "";
		numCantidad = 0;
		mntEntrega = 0;
		mntCambio = 0;
		visibleCbGuardarDocumento = true;
		gvDocumento = new GvDocumento();
		
		gvCliente = new GvCliente();
		gvCliente.setIdEmpresa(idEmpresa);
		gvCliente.setIdCliente(idEmpresa);
		listaGvCliente = gvClienteService.buscarGvClientes(gvCliente);
		
		gvTipoDocumento = new GvTipoDocumento();
		gvTipoDocumento.setIdTipoDocumento(1);
		listaGvTipoDocumento = gvTipoDocumentoService.buscarGvTipoDocumentos();

		gvEstadoDocumento = new GvEstadoDocumento();
		gvEstadoDocumento = gvEstadoDocumentoService.buscarPorCodigo(4);

		gvDocumentoDetalleSeleccionado = new GvDocumentoDetalle();
		gvProducto = new GvProducto();
		gvProductoSeleccionado = new GvProducto();
		
		listaGvDocumentoDetalle = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleEliminar = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		
		mensaje = "";
		mensajeDocumento = "";
		descripcionProducto = "";
		numExistenciaActual = 0;
		numExistenciaMinima = 0;
		nombreImagen = "";
	}
	
	public void resetearCantidades() {
		cantidadPrecioDeCosto = 1;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = 0;
		mensaje = "";
		mensajeDocumento = "";
		visiblePgStockMinimos = false;
	}
	
	public void ingresarCantidadPrecio() {
		Integer cantidadProductos = 0;
		float mntPrecio = 0;
		procesoPrecioDeCosto = false;
		procesoPrecioPorMayor = false;
		procesoPrecioPorMenor = false;
		if (cantidadPrecioDeCosto != 0) {
			procesoPrecioDeCosto = true;
			cantidadProductos = cantidadPrecioDeCosto;
			mntPrecio = redondearDecimales((gvProductoSeleccionado.getPrecioDeCosto() +
					   					   (gvProductoSeleccionado.getPrecioDeCosto() * porcentajeAumento)), 2);
		} else {
			if (cantidadPrecioPorMayor != 0) {
				procesoPrecioPorMayor = true;
				cantidadProductos = cantidadPrecioPorMayor;
				mntPrecio = redondearDecimales((gvProductoSeleccionado.getPrecioPorMayor() +
	   					   					   (gvProductoSeleccionado.getPrecioPorMayor() * porcentajeAumento)), 2);
			} else {
				if (cantidadPrecioPorMenor != 0) {
					procesoPrecioPorMenor = true;
					cantidadProductos = cantidadPrecioPorMenor;
					mntPrecio = redondearDecimales((gvProductoSeleccionado.getPrecioPorMenor() +
		   					   					   (gvProductoSeleccionado.getPrecioPorMenor() * porcentajeAumento)), 2);
				} else {
					mensaje = "Error: Ingresar cantidad del producto...";
					return;
				}
			}
		}

		if (cantidadProductos > gvProductoSeleccionado.getNumExistenciaActual()) {
			mensaje = "Error: cantidad ingresada excede el stock("+gvProductoSeleccionado.getNumExistenciaActual()+") del producto";
			return;
		}
		
		//Tratar datos de producto y documentodetalle.
		gvDocumentoDetalleSeleccionado = new GvDocumentoDetalle();
		gvDocumentoDetalleSeleccionado.setPrecio(redondearDecimales(mntPrecio, 2));
		gvDocumentoDetalleSeleccionado.setNumCantidad(cantidadProductos);
		gvProductoSeleccionado.setNumExistenciaActual(gvProductoSeleccionado.getNumExistenciaActual() - cantidadProductos);
		
		gvDocumentoDetalleSeleccionado.setMntImporte(redondearDecimales((gvDocumentoDetalleSeleccionado.getNumCantidad() * mntPrecio), 2));

		gvDocumentoDetalleSeleccionado.setGvProducto(gvProductoSeleccionado);
		cargarProducto(gvDocumentoDetalleSeleccionado, listaGvDocumentoDetalle.size());

		resetearCantidades();
	}

	//Guardar datos del producto de la ocurrencia actual.
	public void cargarProducto(GvDocumentoDetalle gvDocumentoDetalleAux, Integer index) {
		mensaje = "";

		if (gvDocumentoDetalleAux.getIdProductoTransient() == 0) {
			mensaje = "Error: Ingresar producto...";
			return;
		}

		int indiceLista =  index + 1; 
		if (gvDocumentoDetalleAux.getNumCantidad() == 0) {
			mensaje = "Error: Ingresar cantidad del producto "+indiceLista+" ...";
			return;
		}

		if (gvDocumentoDetalleAux.getNumCantidad() > gvProductoSeleccionado.getNumExistenciaActual()) {
			mensaje = "Error: cantidad ingresada excede el stock("+gvProductoSeleccionado.getNumExistenciaActual()+") del producto... indice="+indiceLista;
			return;
		}

		if (gvDocumento.getIdDocumento() != 0) {
			gvDocumentoDetalleAux.setGvDocumento(gvDocumento);
		}
		
		boolean existeProducto = false;
		if (listaGvDocumentoDetalle.size() > 0) {
			int indice = 0;
			for (GvDocumentoDetalle gvDocumentoDetalleAux1 : listaGvDocumentoDetalle) {
				if (gvDocumentoDetalleAux1.getGvProducto().getIdProducto() == gvProductoSeleccionado.getIdProducto()) {
					listaGvDocumentoDetalle.set(indice, gvDocumentoDetalleAux);
					existeProducto = true;
				}
				indice = indice + 1;
			}
		}
		
		if (!existeProducto) {
			//Adicionar item en la lista.
			listaGvDocumentoDetalle.add(gvDocumentoDetalleAux);
		}
		
		//Inicializar datos del documento para luego actualizarlos desde la lista de productos.
		gvDocumento.setMntSubtotal(0);
		gvDocumento.setMntIva(0);
		gvDocumento.setMntTotal(0);
		
		//Actualizar datos del documento desde la lista de productos.
		int numSize = 0;
		for (GvDocumentoDetalle gvDocumentoDetalleAux1 : listaGvDocumentoDetalle) {
			if (gvDocumentoDetalleAux1.getGvProducto().getIdProducto() != 0 &&
				gvDocumentoDetalleAux1.getNumCantidad() != 0) {
				numSize = numSize + 1;
				gvDocumento.setMntSubtotal(redondearDecimales((gvDocumento.getMntSubtotal() + gvDocumentoDetalleAux1.getMntImporte()), 2));
				GvProducto gvProductoAux = gvDocumentoDetalleAux1.getGvProducto();
				if (gvProductoAux.getAplicaIva() == 1) {
					gvDocumento.setMntIva(gvDocumento.getMntIva() + ((float) redondearDecimales((gvDocumentoDetalleAux1.getMntImporte() * Float.parseFloat("0.14")), 2)));
				}
				gvDocumento.setMntTotal(redondearDecimales((gvDocumento.getMntSubtotal() + gvDocumento.getMntIva()), 2));
			}
		}
		gvDocumento.setNumItems(numSize);
		mensaje = "Se ha guardado el producto "+indiceLista+" ...";
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

	//Redondear valor Double a 2 decimales.
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado= (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }	

	public void editar(GvDocumento gvDocumentoAux) {
		codProceso = "E";
		codigoProducto = "";
		numCantidad = 0;
		visibleCbGuardarDocumento = true;
		gvDocumento = gvDocumentoAux;
		
		if (gvDocumento.getGvCliente() != null) {
			gvCliente = gvDocumento.getGvCliente();
			gvCliente.setIdEmpresa(idEmpresa);
			listaGvCliente = gvClienteService.buscarGvClientes(gvCliente);
		}
		if (gvDocumento.getGvTipoDocumento() != null) {
			gvTipoDocumento = gvDocumento.getGvTipoDocumento();
			listaGvTipoDocumento = gvTipoDocumentoService.buscarGvTipoDocumentos();
		}
		if (gvDocumento.getGvEstadoDocumento() != null) {
			gvEstadoDocumento = gvDocumento.getGvEstadoDocumento();
		}
		
		listaGvDocumentoDetalle = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalle = gvDocumentoDetalleService.buscarListaPorDocumento(gvDocumento);
		listaGvDocumentoDetalleAux = gvDocumentoDetalleService.buscarListaPorDocumento(gvDocumento);
		listaGvDocumentoDetalleEliminar = new ArrayList<GvDocumentoDetalle>();

		mensaje = "";
		mensajeDocumento = "";
	}

	//Eliminar un producto u ocurrencia.
	public void eliminarProducto(GvDocumentoDetalle gvDocumentoDetalleAux, Integer index) {
		gvDocumento.setMntSubtotal(gvDocumento.getMntSubtotal() - gvDocumentoDetalleAux.getMntImporte());
		GvProducto gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
		//Adicionar la cantidad al stock del producto
		gvProductoAux.setNumExistenciaActual(gvProductoAux.getNumExistenciaActual() + gvDocumentoDetalleAux.getNumCantidad());
		gvDocumentoDetalleAux.setGvProducto(gvProductoAux);
		if (gvProductoAux.getAplicaIva() == 1) {
			gvDocumento.setMntIva(gvDocumento.getMntIva() - ((float) redondearDecimales((gvDocumentoDetalleAux.getMntImporte() * Float.parseFloat("0.12")), 2)));
		}
		gvDocumento.setMntTotal(gvDocumento.getMntSubtotal() + gvDocumento.getMntIva());
		
		listaGvDocumentoDetalle.remove(gvDocumentoDetalleAux);
		gvDocumento.setNumItems(listaGvDocumentoDetalle.size() - 1);
		
		if (gvDocumentoDetalleAux.getGvDocumento() != null &&
			gvDocumentoDetalleAux.getGvDocumento().getIdDocumento() != 0 &&
			gvDocumentoDetalleAux.getNumCantidad() != 0) {
			listaGvDocumentoDetalleEliminar.add(gvDocumentoDetalleAux);
		}
		
		index = index + 1;
		mensaje = "Se ha eliminado el producto "+index+" ...";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void actualizarDocumento() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvDocumento.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables(gvDocumento);
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		try {
			gvDocumento.setGvCliente(gvCliente);
			gvDocumento.setGvTipoDocumento(gvTipoDocumento);

			//Verificar existencia de ocurrencias de productos para Guardar/Actualizar el documento.
			if (listaGvDocumentoDetalle.size() > 0) {
				//Actualizar el numero de productos del documento.
				gvDocumento.setNumItems(listaGvDocumentoDetalle.size());
				
				//Guardar Documento
				if (codProceso == "N") {
					//Estado documento "GENERADO".
					gvEstadoDocumento = gvEstadoDocumentoService.buscarPorCodigo(4);
					gvDocumento.setGvEstadoDocumento(gvEstadoDocumento);

					//Crear documento con persist.
					gvDocumentoService.crearGvDocumento(gvDocumento);
					
					//Grabar GvDocumentoDetalle desde listaGvDocumentoDetalleCrear.
					for (GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalle) {
						//Actualizar datos del producto.
						GvProducto gvProductoAux = new GvProducto();
						gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
						gvProductoService.guardarGvProducto(gvProductoAux);

						//Crear Documento Detalles.
						gvDocumentoDetalleAux.setGvDocumento(gvDocumento);
						gvDocumentoDetalleAux.setEstado(1);
						gvDocumentoDetalleService.crearGvDocumentoDetalle(gvDocumentoDetalleAux);

						//Crear movimiento en el kardex.
						crearMovimientoKardex(gvDocumentoDetalleAux, "E", 1);
					}
					visibleCbGuardarDocumento = false;
					mensajeDocumento = "Se ha guardado el documento...";
				} else {
					//Actualizar Documento
					if (codProceso == "E") {
						//Eliminar registros desde listaGvDocumentoDetalleEliminar.
						if (listaGvDocumentoDetalleEliminar.size() != 0) {
							for(GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalleEliminar) {
								//Actualizar datos del producto.
								GvProducto gvProductoAux = new GvProducto();
								gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
								gvProductoService.guardarGvProducto(gvProductoAux);

								gvDocumentoDetalleService.eliminarGvDocumentoDetalle(gvDocumentoDetalleAux.getIdDocumentoDetalle());

								//Crear movimiento en el kardex.
								crearMovimientoKardex(gvDocumentoDetalleAux, "I", 5);
							}
						}
						listaGvDocumentoDetalleEliminar = new ArrayList<GvDocumentoDetalle>();

						//gvDocumento.setGvDocumentoDetalles(listaGvDocumentoDetalle);
						//Grabar GvDocumentoDetalle desde listaGvDocumentoDetalleCrear.
						for (GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalle) {
							//Verificar si ya existe el item en el arreglo para actualizar
							boolean noExisteItem = true;
							for (GvDocumentoDetalle gvDocumentoDetalleAux1 : listaGvDocumentoDetalleAux) {
								if (gvDocumentoDetalleAux1.getIdDocumentoDetalle() ==
									gvDocumentoDetalleAux.getIdDocumentoDetalle()) {
									noExisteItem = false;
								}
							}
							if (noExisteItem) {
								//Actualizar datos del producto.
								GvProducto gvProductoAux = new GvProducto();
								gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
								gvProductoService.guardarGvProducto(gvProductoAux);
							
								//Actualizar Documento Detalles
								gvDocumentoDetalleAux.setGvDocumento(gvDocumento);
								gvDocumentoDetalleService.actualizarGvDocumentoDetalle(gvDocumentoDetalleAux);

								//Crear movimiento en el kardex.
								crearMovimientoKardex(gvDocumentoDetalleAux, "E", 5);
							}
						}

						//Actualizar documento con merge.
						gvDocumentoService.actualizarGvDocumento(gvDocumento);
						
						visibleCbGuardarDocumento = false;
						mensajeDocumento = "Se ha actualizado el documento...";
					} else {
						mensajeDocumento = "No se guardó el documento...";
					}
				}
			} else {
				mensajeDocumento = "No se guardó el documento...";
			}
			buscarDocumentosPorParametros();
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeDocumento = "Error: No se actualizaron los datos...";
			else
				mensajeDocumento = (new StringBuilder()).append("Error: ").append(exc.getMessage()).toString();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void crearDocumentoNew() {
		GvDocumento gvDocumentoNew = new GvDocumento();
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvDocumento.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				codProceso = "N";
				actualizarCamposAuditables(gvDocumentoNew);
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		try {
			//Crear documento con persist.
			gvDocumentoNew.setGvCliente(gvCliente);
			GvTipoDocumento gvTipoDocumentoAux = new GvTipoDocumento();
			gvTipoDocumentoAux.setIdTipoDocumento(4);
			gvDocumentoNew.setGvTipoDocumento(gvTipoDocumentoAux);
			gvDocumentoNew.setNumItems(1);
			//Estado documento "GENERADO".
			gvDocumentoNew.setGvEstadoDocumento(gvEstadoDocumento);
			//Montos del docuemnto
			gvDocumentoNew.setMntSubtotal(gvProductoNew.getPrecioDeCompra() * gvProductoNew.getNumExistenciaActual());
			gvDocumentoNew.setMntTotal(gvProductoNew.getPrecioDeCompra() * gvProductoNew.getNumExistenciaActual());
			
			gvDocumentoNew.setObservaciones("REGISTRADO DESDE PUNTO DE VENTAS");
			gvDocumentoService.crearGvDocumento(gvDocumentoNew);

			//Grabar GvDocumentoDetalle desde gvProductoNew.
			GvDocumentoDetalle gvDocumentoDetalleNew = new GvDocumentoDetalle();
			gvDocumentoDetalleNew.setGvDocumento(gvDocumentoNew);
			gvDocumentoDetalleNew.setGvProducto(gvProductoNew);
			gvDocumentoDetalleNew.setNumCantidad(gvProductoNew.getNumExistenciaActual());
			gvDocumentoDetalleNew.setMntImporte(gvProductoNew.getPrecioDeCompra() * gvProductoNew.getNumExistenciaActual());
			gvDocumentoDetalleNew.setPrecio(gvProductoNew.getPrecioDeCompra());
			gvDocumentoDetalleNew.setEstado(1);
			gvDocumentoDetalleService.crearGvDocumentoDetalle(gvDocumentoDetalleNew);
			//Crear movimiento en el kardex.
			observacionesKardex = "REGISTRADO DESDE PUNTO DE VENTAS";
			crearMovimientoKardex(gvDocumentoDetalleNew, "I", 4);

			mensaje = "Se ha actualizado el documento...";
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeDocumento = "Error: No se actualizaron los datos...";
			else
				mensajeDocumento = (new StringBuilder()).append("Error: ").append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables(GvDocumento gvDocumentoTmp) {
		if (codProceso == "N") {
			gvDocumentoTmp.setIdEmpresa(idEmpresa);
			gvDocumentoTmp.setEstado(1);
			gvDocumentoTmp.setFechaRegistra(new Date());
			gvDocumentoTmp.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvDocumentoTmp.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvDocumentoTmp.setFechaActualiza(new Date());
			gvDocumentoTmp.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvDocumentoTmp.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	public void crearMovimientoKardex(GvDocumentoDetalle gvDocumentoDetalleAux, String ingresoEgreso, int tipoMovimiento) throws Exception {
		//Crear movimiento en el kardex.
		GvKardex gvKardex = new GvKardex();
		gvKardex.setIdEmpresa(idEmpresa);
		gvKardex.setGvProducto(gvDocumentoDetalleAux.getGvProducto());
		gvKardex.setGvDocumento(gvDocumentoDetalleAux.getGvDocumento());
		gvKardex.setNumCantidad(gvDocumentoDetalleAux.getNumCantidad());
		gvKardex.setMntPrecio(gvDocumentoDetalleAux.getPrecio());
		gvKardex.setMntValor(gvDocumentoDetalleAux.getMntImporte());
		gvKardex.setNumExistenciaActual(gvDocumentoDetalleAux.getGvProducto().getNumExistenciaActual());
		gvKardex.setMntExistenciaActual(gvKardex.getNumExistenciaActual() * gvKardex.getMntPrecio());
		gvKardex.setGvTipoMovimiento(gvTipoMovimientoService.buscarPorCodigo(tipoMovimiento));
		gvKardex.setObservaciones(observacionesKardex);
		//Datos auditables.
		gvKardex.setEstado(1);
		gvKardex.setFechaRegistra(new Date());
		gvKardex.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		gvKardex.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		
		gvKardexService.guardarGvKardex(gvKardex);
	}
	
	//Invocar el reporte del Documento
	public void crearDocumentoPDF() {
		reportName = "Documento.jasper";
		creaScript = "";

		try {
			gvDocumento = gvDocumentoService.buscarPorCodigo(gvDocumento.getIdDocumento());
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(gvDocumento.getFechaRegistra()); // Configuramos la fecha que se recibe
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        String fechaDocumento = sdf.format(calendar.getTime());
			
			creaScript =  "javascript:window.open('/geve-web/reportDocumento?"
						+ "REPORT_NAME=" + reportName
						// Datos de la Empresa
						+ "&IDEMPRESA=" + gvDocumento.getIdEmpresa()
						+ "&NOMBREEMPRESA=" + nombreEmpresa
						+ "&IDENTIFICACIONEMPRESA=" + sesionControlador.getUsuario().getSede().getIdentificacion()
						// Datos del Documento
						+ "&IDDOCUMENTO=" + gvDocumento.getIdDocumento()
						+ "&TIPODOCUMENTO=" + gvDocumento.getGvTipoDocumento().getDescripcion() // Datos del Tipo de Documento
						+ "&FECHADOCUMENTO=" + fechaDocumento
						+ "&MNTSUBTOTAL=" + gvDocumento.getMntSubtotal()
						+ "&MNTIVA=" + gvDocumento.getMntIva()
						+ "&MNTTOTAL=" + gvDocumento.getMntTotal()
						// Datos del Cliente
						+ "&NOMBRES=" + gvDocumento.getGvCliente().getNombres() + gvDocumento.getGvCliente().getApellidos()
						+ "&DNI=" + gvDocumento.getGvCliente().getDni()
						+ "&DIRECCION=" + gvDocumento.getGvCliente().getDireccion()
						+ "&MOVIL=" + gvDocumento.getGvCliente().getMovil()
						+ "&TELEFONO=" + gvDocumento.getGvCliente().getTelefono()
						+ "&PATH_IMAGEN=" + "/img/"+sesionControlador.getUsuario().getSede().getNombreImagen()
						+ "', 'window', 'params');return false";

						//FacesContext context = FacesContext.getCurrentInstance();
						//ExternalContext exc = context.getExternalContext();
						//HttpSession tt = (HttpSession) exc.getSession(true);
						//tt.setAttribute("CADENA", new String[] { refCedula });
		} catch (Exception e) {
			e.printStackTrace();
			//utils.agregarMensajeError("ERROR Al generar el PDF. "+ e.getMessage(), "");
		} finally {
			// context.responseComplete();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void guardarProductoNew() {
		if (gvProductoNew.getCodigo() == null) {
			mensaje = "ERROR: Ingresar código de barras";
			return;
		}

		if (gvProductoNew.getDescripcion().isEmpty()) {
			mensaje = "ERROR: Ingresar descripción";
			return;
		}

		if (gvProductoNew.getPrecioDeCompra() == 0.0) {
			mensaje = "ERROR: Precio de compra...";
			return;
		}
		// Actualizar campos auditables.
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvProducto.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				actualizarCamposAuditables1();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			GvClase gvClase = new GvClase();
			gvClase.setIdClase(1);
			gvProductoNew.setGvClase(gvClase);

			GvMarca gvMarca = new GvMarca();
			gvMarca.setIdMarca(1);
			gvProductoNew.setGvMarca(gvMarca);

			GvUnidad gvUnidad = new GvUnidad();
			gvUnidad.setIdUnidad(3);
			gvProductoNew.setGvUnidad(gvUnidad);
			
			gvProductoNew.setPrecioDeCosto(gvProductoNew.getPrecioDeCompra());
			gvProductoNew.setPrecioPorMayor(gvProductoNew.getPrecioDeCompra());
			gvProductoNew.setPrecioPorMenor(gvProductoNew.getPrecioDeCompra());
			
			gvProductoNew.setNumExistenciaMinima(5);
			gvProductoNew.setNumExistenciaActual(10);
			
			gvProductoService.guardarGvProducto(gvProductoNew);
			gvProductoNew = gvProductoService.buscarPorCodigo(gvProductoNew.getIdEmpresa(), gvProductoNew.getCodigo());
			
			//Generar Documento e Invanterios
			crearDocumentoNew();
			
			mensaje = "DATOS ACTUALIZADOS CORRECTAMENTE";
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "ERROR: NO SE ACTUALIZARON LOS DATOS";
			else
				mensaje = (new StringBuilder()).append("ERROR: ").append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables1() {
		if (codProceso == "N") {
			gvProductoNew.setIdEmpresa(idEmpresa);
			gvProductoNew.setEstado(1);
			gvProductoNew.setFechaRegistra(new Date());
			gvProductoNew.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvProductoNew.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvProductoNew.setFechaActualiza(new Date());
			gvProductoNew.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvProductoNew.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}
	
	public List<GvDocumento> getListaGvDocumento() {
		return listaGvDocumento;
	}

	public void setListaGvDocumentos(List<GvDocumento> listaGvDocumento) {
		this.listaGvDocumento = listaGvDocumento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<GvCliente> getListaGvCliente() {
		return listaGvCliente;
	}

	public void setListaGvCliente(List<GvCliente> listaGvCliente) {
		this.listaGvCliente = listaGvCliente;
	}

	public GvCliente getGvCliente() {
		return gvCliente;
	}

	public void setGvCliente(GvCliente gvCliente) {
		this.gvCliente = gvCliente;
	}

	public GvTipoDocumento getGvTipoDocumento() {
		return gvTipoDocumento;
	}

	public void setGvTipoDocumento(GvTipoDocumento gvTipoDocumento) {
		this.gvTipoDocumento = gvTipoDocumento;
	}

	public List<GvTipoDocumento> getListaGvTipoDocumento() {
		return listaGvTipoDocumento;
	}

	public void setListaGvTipoDocumento(List<GvTipoDocumento> listaGvTipoDocumento) {
		this.listaGvTipoDocumento = listaGvTipoDocumento;
	}

	public GvEstadoDocumento getGvEstadoDocumento() {
		return gvEstadoDocumento;
	}

	public void setGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) {
		this.gvEstadoDocumento = gvEstadoDocumento;
	}

	public List<GvEstadoDocumento> getListaGvEstadoDocumento() {
		return listaGvEstadoDocumento;
	}

	public void setListaGvEstadoDocumento(List<GvEstadoDocumento> listaGvEstadoDocumento) {
		this.listaGvEstadoDocumento = listaGvEstadoDocumento;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public Integer getIdClienteBuscar() {
		return idClienteBuscar;
	}

	public void setIdClienteBuscar(Integer idClienteBuscar) {
		this.idClienteBuscar = idClienteBuscar;
	}

	public Integer getIdTipoDocumentoBuscar() {
		return idTipoDocumentoBuscar;
	}

	public void setIdTipoDocumentoBuscar(Integer idTipoDocumentoBuscar) {
		this.idTipoDocumentoBuscar = idTipoDocumentoBuscar;
	}

	public Integer getIdEstadoDocumentoBuscar() {
		return idEstadoDocumentoBuscar;
	}

	public void setIdEstadoDocumentoBuscar(Integer idEstadoDocumentoBuscar) {
		this.idEstadoDocumentoBuscar = idEstadoDocumentoBuscar;
	}

	public GvProducto getGvProducto() {
		return gvProducto;
	}

	public void setGvProducto(GvProducto gvProducto) {
		this.gvProducto = gvProducto;
	}

	public List<GvProducto> getListaGvProducto() {
		return listaGvProducto;
	}

	public void setListaGvProducto(List<GvProducto> listaGvProducto) {
		this.listaGvProducto = listaGvProducto;
	}

	public GvDocumento getGvDocumentoBuscar() {
		return gvDocumentoBuscar;
	}

	public void setGvDocumentoBuscar(GvDocumento gvDocumentoBuscar) {
		this.gvDocumentoBuscar = gvDocumentoBuscar;
	}

	public String getMensajeDocumento() {
		return mensajeDocumento;
	}

	public void setMensajeDocumento(String mensajeDocumento) {
		this.mensajeDocumento = mensajeDocumento;
	}

	public List<GvDocumentoDetalle> getListaGvDocumentoDetalle() {
		return listaGvDocumentoDetalle;
	}

	public void setListaGvDocumentoDetalleCrear(
			List<GvDocumentoDetalle> listaGvDocumentoDetalleCrear) {
		this.listaGvDocumentoDetalle = listaGvDocumentoDetalleCrear;
	}

	public GvDocumento getGvDocumento() {
		return gvDocumento;
	}

	public void setGvDocumento(GvDocumento gvDocumento) {
		this.gvDocumento = gvDocumento;
	}

	public List<GvDocumentoDetalle> getListaGvDocumentoDetalleEliminar() {
		return listaGvDocumentoDetalleEliminar;
	}

	public void setListaGvDocumentoDetalleEliminar(
			List<GvDocumentoDetalle> listaGvDocumentoDetalleEliminar) {
		this.listaGvDocumentoDetalleEliminar = listaGvDocumentoDetalleEliminar;
	}

	public GvProducto getGvProductoSeleccionado() {
		return gvProductoSeleccionado;
	}

	public void setGvProductoSeleccionado(GvProducto gvProductoSeleccionado) {
		this.gvProductoSeleccionado = gvProductoSeleccionado;
	}

	public List<GvProducto> getListaGvProductoPuntoVenta() {
		return listaGvProductoPuntoVenta;
	}

	public void setListaGvProductoPuntoVenta(
			List<GvProducto> listaGvProductoPuntoVenta) {
		this.listaGvProductoPuntoVenta = listaGvProductoPuntoVenta;
	}

	public Integer getCantidadPrecioDeCosto() {
		return cantidadPrecioDeCosto;
	}

	public void setCantidadPrecioDeCosto(Integer cantidadPrecioDeCosto) {
		this.cantidadPrecioDeCosto = cantidadPrecioDeCosto;
	}

	public Integer getCantidadPrecioPorMayor() {
		return cantidadPrecioPorMayor;
	}

	public void setCantidadPrecioPorMayor(Integer cantidadPrecioPorMayor) {
		this.cantidadPrecioPorMayor = cantidadPrecioPorMayor;
	}

	public Integer getCantidadPrecioPorMenor() {
		return cantidadPrecioPorMenor;
	}

	public void setCantidadPrecioPorMenor(Integer cantidadPrecioPorMenor) {
		this.cantidadPrecioPorMenor = cantidadPrecioPorMenor;
	}

	public GvDocumentoDetalle getGvDocumentoDetalleSeleccionado() {
		return gvDocumentoDetalleSeleccionado;
	}

	public void setGvDocumentoDetalleSeleccionado(
			GvDocumentoDetalle gvDocumentoDetalleSeleccionado) {
		this.gvDocumentoDetalleSeleccionado = gvDocumentoDetalleSeleccionado;
	}

	public boolean isProcesoPrecioDeCosto() {
		return procesoPrecioDeCosto;
	}

	public void setProcesoPrecioDeCosto(boolean procesoPrecioDeCosto) {
		this.procesoPrecioDeCosto = procesoPrecioDeCosto;
	}

	public boolean isProcesoPrecioPorMayor() {
		return procesoPrecioPorMayor;
	}

	public void setProcesoPrecioPorMayor(boolean procesoPrecioPorMayor) {
		this.procesoPrecioPorMayor = procesoPrecioPorMayor;
	}

	public boolean isProcesoPrecioPorMenor() {
		return procesoPrecioPorMenor;
	}

	public void setProcesoPrecioPorMenor(boolean procesoPrecioPorMenor) {
		this.procesoPrecioPorMenor = procesoPrecioPorMenor;
	}

	public float getMntEntrega() {
		return mntEntrega;
	}

	public void setMntEntrega(float mntEntrega) {
		this.mntEntrega = mntEntrega;
	}

	public float getMntCambio() {
		return mntCambio;
	}

	public void setMntCambio(float mntCambio) {
		this.mntCambio = mntCambio;
	}

	public GvParametroService getGvParametroService() {
		return gvParametroService;
	}

	public void setGvParametroService(GvParametroService gvParametroService) {
		this.gvParametroService = gvParametroService;
	}

	public String getFechaRegistraString() {
		return fechaRegistraString;
	}

	public void setFechaRegistraString(String fechaRegistraString) {
		this.fechaRegistraString = fechaRegistraString;
	}

	public GvKardexFechaDTO getGvKardexFechaDTO() {
		return gvKardexFechaDTO;
	}

	public void setGvKardexFechaDTO(GvKardexFechaDTO gvKardexFechaDTO) {
		this.gvKardexFechaDTO = gvKardexFechaDTO;
	}

	public List<GvKardexFechaDTO> getListaGvKardexStockMinimos() {
		return listaGvKardexStockMinimos;
	}

	public void setListaGvKardexStockMinimos(
			List<GvKardexFechaDTO> listaGvKardexStockMinimos) {
		this.listaGvKardexStockMinimos = listaGvKardexStockMinimos;
	}

	public boolean isVisiblePgStockMinimos() {
		return visiblePgStockMinimos;
	}

	public void setVisiblePgStockMinimos(boolean visiblePgStockMinimos) {
		this.visiblePgStockMinimos = visiblePgStockMinimos;
	}

	public List<GvKardexFechaDTO> getListaGvKardexStockMinimosAux() {
		return listaGvKardexStockMinimosAux;
	}

	public void setListaGvKardexStockMinimosAux(
			List<GvKardexFechaDTO> listaGvKardexStockMinimosAux) {
		this.listaGvKardexStockMinimosAux = listaGvKardexStockMinimosAux;
	}

	public String getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public Integer getNumCantidad() {
		return numCantidad;
	}

	public void setNumCantidad(Integer numCantidad) {
		this.numCantidad = numCantidad;
	}

	public String getCreaScript() {
		return creaScript;
	}

	public void setCreaScript(String creaScript) {
		this.creaScript = creaScript;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public boolean isVisibleCbGuardarDocumento() {
		return visibleCbGuardarDocumento;
	}

	public void setVisibleCbGuardarDocumento(boolean visibleCbGuardarDocumento) {
		this.visibleCbGuardarDocumento = visibleCbGuardarDocumento;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public Integer getNumExistenciaActual() {
		return numExistenciaActual;
	}

	public void setNumExistenciaActual(Integer numExistenciaActual) {
		this.numExistenciaActual = numExistenciaActual;
	}

	public Integer getNumExistenciaMinima() {
		return numExistenciaMinima;
	}

	public void setNumExistenciaMinima(Integer numExistenciaMinima) {
		this.numExistenciaMinima = numExistenciaMinima;
	}

	public GvProducto getGvProductoNew() {
		return gvProductoNew;
	}

	public void setGvProductoNew(GvProducto gvProductoNew) {
		this.gvProductoNew = gvProductoNew;
	}

	public String getNombreImagen() {
		return nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public boolean isVisibleDgProductosExistencia() {
		return visibleDgProductosExistencia;
	}

	public void setVisibleDgProductosExistencia(boolean visibleDgProductosExistencia) {
		this.visibleDgProductosExistencia = visibleDgProductosExistencia;
	}
}