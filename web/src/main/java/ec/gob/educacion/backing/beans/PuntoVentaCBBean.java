package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvParametro;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.model.geve.GvTipoDocumento;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvCliente;
import ec.gob.educacion.model.geve.GvEstadoDocumento;
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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class PuntoVentaCBBean extends BaseController implements Serializable {

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
    @ManagedProperty(value = "#{sessionController}")
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

	private Integer idClienteBuscar;
	private Integer idTipoDocumentoBuscar;
	private Integer idEstadoDocumentoBuscar;
	private Integer cantidadPrecioDeCosto;
	private Integer cantidadPrecioPorMayor;
	private Integer cantidadPrecioPorMenor;
	
	private boolean procesoPrecioDeCosto;
	private boolean procesoPrecioPorMayor;
	private boolean procesoPrecioPorMenor;
	private boolean visibleStockMinimos;
	
	private float mntEntregado;
	private float mntCambio;
	private float porcentajeAumento;
	private Date fechaRegistra;

	@PostConstruct
	public void init() {
		codProceso = "N";
		mensaje = "";
		mensajeDocumento = "";

		gvDocumento = new GvDocumento();
		gvDocumentoBuscar = new GvDocumento();
		listaGvDocumento = new ArrayList<GvDocumento>();
		listaGvDocumentoDetalle = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		gvProductoSeleccionado = new GvProducto();
		
		listaGvProducto = new ArrayList<GvProducto>();
		gvProductoSeleccionado.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProducto = gvProductoService.buscarGvProductos(gvProductoSeleccionado);
		galeriaProductos();

		cantidadPrecioDeCosto = 0;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = 0;
		porcentajeAumento = 0;
		
		// Obtener, parámetros de hora de atención y % de recargo.
		obtenerParametroHoraRecargo();

		// Obtener, stock mínimo.
		obtenerListaStockMinimo();

		buscarDocumentosPorParametros();
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
	
	@SuppressWarnings("deprecation")
	public void obtenerListaStockMinimo() {
		visibleStockMinimos = false;
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
		listaGvKardexStockMinimos = gvKardexService.buscarListaStockPorProducto(gvKardexBuscar);
		if (listaGvKardexStockMinimos.size() > 0) {
			listaGvKardexStockMinimosAux = new ArrayList<GvKardexFechaDTO>();
			for (GvKardexFechaDTO gvKardexFechaDTOAux : listaGvKardexStockMinimos) {
				if (gvKardexFechaDTOAux.getNumExistenciaMinima() >= gvKardexFechaDTOAux.getNumExistencia()) {
					listaGvKardexStockMinimosAux.add(gvKardexFechaDTOAux);
				}
			}
			if (listaGvKardexStockMinimosAux.size() > 0) {
				visibleStockMinimos = true;
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
	
	public void galeriaProductos() {
		nuevo();

		listaGvProductoPuntoVenta = new ArrayList<GvProducto>();
        for (GvProducto gvProductoAux : listaGvProducto) {
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
		gvDocumentoBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvDocumento = new ArrayList<GvDocumento>();
		listaGvDocumento = gvDocumentoService.buscarListaPorParametros(gvDocumentoBuscar);
	}

	public void nuevo() {
		codProceso = "N";
		gvDocumento = new GvDocumento();
		
		gvCliente = new GvCliente();
		gvCliente.setIdCliente(1);
		gvCliente.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
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
	}
	
	public void resetearCantidades() {
		cantidadPrecioDeCosto = 0;
		cantidadPrecioPorMayor = 0;
		cantidadPrecioPorMenor = 0;
		mensaje = "";
		mensajeDocumento = "";
		visibleStockMinimos = false;
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
			//gvProductoSeleccionado.setPrecioDeCosto(mntPrecio);
		} else {
			if (cantidadPrecioPorMayor != 0) {
				procesoPrecioPorMayor = true;
				cantidadProductos = cantidadPrecioPorMayor;
				mntPrecio = redondearDecimales((gvProductoSeleccionado.getPrecioPorMayor() +
	   					   					   (gvProductoSeleccionado.getPrecioPorMayor() * porcentajeAumento)), 2);
				//gvProductoSeleccionado.setPrecioPorMayor(mntPrecio);
			} else {
				if (cantidadPrecioPorMenor != 0) {
					procesoPrecioPorMenor = true;
					cantidadProductos = cantidadPrecioPorMenor;
					mntPrecio = redondearDecimales((gvProductoSeleccionado.getPrecioPorMenor() +
		   					   					   (gvProductoSeleccionado.getPrecioPorMenor() * porcentajeAumento)), 2);
					//gvProductoSeleccionado.setPrecioPorMenor(mntPrecio);
				} else {
					mensaje = "Error: Ingresar cantidad del producto...";
					return;
				}
			}
		}

		if (cantidadProductos > gvProductoSeleccionado.getNumExistenciaActual()) {
			mensaje = "Error: cantidad excede el stock("+gvProductoSeleccionado.getNumExistenciaActual()+") del producto";
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
			mensaje = "Error: cantidad excede el stock("+gvProductoSeleccionado.getNumExistenciaActual()+") del producto... indice="+indiceLista;
			return;
		}

		if (gvDocumento.getIdDocumento() != 0) {
			gvDocumentoDetalleAux.setGvDocumento(gvDocumento);
		}
		
		gvDocumentoDetalleAux.setEstado(1);
		//Adicionar item en la lista.
		listaGvDocumentoDetalle.add(gvDocumentoDetalleAux);
		
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
				gvDocumento.setMntSubtotal(gvDocumento.getMntSubtotal() + gvDocumentoDetalleAux1.getMntImporte());
				GvProducto gvProductoAux = gvDocumentoDetalleAux1.getGvProducto();
				if (gvProductoAux.getAplicaIva() == 1) {
					gvDocumento.setMntIva(gvDocumento.getMntIva() + ((float) redondearDecimales((gvDocumentoDetalleAux1.getMntImporte() * Float.parseFloat("0.14")), 2)));
				}
				gvDocumento.setMntTotal(gvDocumento.getMntSubtotal() + gvDocumento.getMntIva());
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
		gvDocumento = gvDocumentoAux;
		
		if (gvDocumento.getGvCliente() != null) {
			gvCliente = gvDocumento.getGvCliente();
			gvCliente.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
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
				actualizarCamposAuditables();
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
						gvDocumentoDetalleService.crearGvDocumentoDetalle(gvDocumentoDetalleAux);

						//Crear movimiento en el kardex.
						crearMovimientoKardex(gvDocumentoDetalleAux, "E", 1);
					}
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
				mensajeDocumento = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "N") {
			gvDocumento.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			gvDocumento.setEstado(1);
			gvDocumento.setFechaRegistra(new Date());
			gvDocumento.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvDocumento.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvDocumento.setFechaActualiza(new Date());
			gvDocumento.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvDocumento.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	public void crearMovimientoKardex(GvDocumentoDetalle gvDocumentoDetalleAux, String ingresoEgreso, int tipoMovimiento) throws Exception {
		//Crear movimiento en el kardex.
		GvKardex gvKardex = new GvKardex();
		gvKardex.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		gvKardex.setGvProducto(gvDocumentoDetalleAux.getGvProducto());
		gvKardex.setGvDocumento(gvDocumentoDetalleAux.getGvDocumento());
		gvKardex.setNumCantidad(gvDocumentoDetalleAux.getNumCantidad());
		gvKardex.setMntPrecio(gvDocumentoDetalleAux.getPrecio());
		gvKardex.setMntValor(gvDocumentoDetalleAux.getMntImporte());
		gvKardex.setNumExistenciaActual(gvDocumentoDetalleAux.getGvProducto().getNumExistenciaActual());
		gvKardex.setMntExistenciaActual(gvKardex.getNumExistenciaActual() * gvKardex.getMntPrecio());
		gvKardex.setGvTipoMovimiento(gvTipoMovimientoService.buscarPorCodigo(tipoMovimiento));
		//Datos auditables.
		gvKardex.setEstado(1);
		gvKardex.setFechaRegistra(new Date());
		gvKardex.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		gvKardex.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		
		gvKardexService.guardarGvKardex(gvKardex);
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

	public float getMntEntregado() {
		return mntEntregado;
	}

	public void setMntEntregado(float mntEntregado) {
		this.mntEntregado = mntEntregado;
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

	public boolean isVisibleStockMinimos() {
		return visibleStockMinimos;
	}

	public void setVisibleStockMinimos(boolean visibleStockMinimos) {
		this.visibleStockMinimos = visibleStockMinimos;
	}

	public List<GvKardexFechaDTO> getListaGvKardexStockMinimosAux() {
		return listaGvKardexStockMinimosAux;
	}

	public void setListaGvKardexStockMinimosAux(
			List<GvKardexFechaDTO> listaGvKardexStockMinimosAux) {
		this.listaGvKardexStockMinimosAux = listaGvKardexStockMinimosAux;
	}
}