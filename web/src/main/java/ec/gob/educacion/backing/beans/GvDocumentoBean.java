package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.model.geve.GvTipoDocumento;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvCliente;
import ec.gob.educacion.model.geve.GvEstadoDocumento;
import ec.gob.educacion.service.GvDocumentoDetalleService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvProductoService;
import ec.gob.educacion.service.GvTipoDocumentoService;
import ec.gob.educacion.service.GvDocumentoService;
import ec.gob.educacion.service.GvClienteService;
import ec.gob.educacion.service.GvEstadoDocumentoService;
import ec.gob.educacion.service.GvTipoMovimientoService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class GvDocumentoBean extends BaseController implements Serializable {

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
	private GvEstadoDocumentoService gvEstadoDocumentoService;
	@EJB
	private GvProductoService gvProductoService;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvTipoMovimientoService gvTipoMovimientoService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvDocumento gvDocumento;
	private GvDocumento gvDocumentoCrear;
	private GvDocumento gvDocumentoActualizar;
	private GvDocumento gvDocumentoBuscar;
	private List<GvDocumento> listaGvDocumento;

	private GvCliente gvCliente;
	private List<GvCliente> listaGvCliente;
	
	private GvTipoDocumento gvTipoDocumento;
	private List<GvTipoDocumento> listaGvTipoDocumento;
	
	private GvEstadoDocumento gvEstadoDocumento;
	private List<GvEstadoDocumento> listaGvEstadoDocumento;

	private GvDocumentoDetalle gvDocumentoDetalle;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleCrear;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleActualizar;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleActualizarAux;
	private List<GvDocumentoDetalle> listaGvDocumentoDetalleEliminar;

	private GvProducto gvProducto;
	private GvProducto gvProductoBuscar;
	private List<GvProducto> listaGvProducto;
	private List<GvProducto> listaGvProductoPuntoVenta;
	
	private String mensaje;
	private String mensajeDocumento;
	private String codProceso;
	private String nombreEmpresa;
	
	private int idEmpresa;

	private Integer idClienteBuscar;
	private Integer idTipoDocumentoBuscar;
	private Integer idEstadoDocumentoBuscar;
	
	private boolean visibleCbEliminarDocumento;
	private boolean visibleCbActualizarDocumento;

	@PostConstruct
	public void init() {
		codProceso = "N";
		mensaje = "";
		mensajeDocumento = "";

		gvDocumentoCrear = new GvDocumento();
		gvDocumentoActualizar = new GvDocumento();
		gvDocumentoBuscar = new GvDocumento();
		listaGvDocumento = new ArrayList<GvDocumento>();
		gvDocumentoDetalle = new GvDocumentoDetalle();
		listaGvDocumentoDetalleCrear = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleActualizar = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleActualizarAux = new ArrayList<GvDocumentoDetalle>();
		idEmpresa = sesionControlador.getUsuario().getSede().getCodigo().intValue();

		gvTipoDocumento = new GvTipoDocumento();
		gvTipoDocumento.setIdTipoDocumento(4);
		listaGvTipoDocumento = gvTipoDocumentoService.buscarGvTipoDocumentos();

		gvEstadoDocumento = new GvEstadoDocumento();
		gvEstadoDocumento.setIdEstadoDocumento(1);
		listaGvEstadoDocumento = gvEstadoDocumentoService.buscarGvEstadoDocumentos();
		
		gvProductoBuscar = new GvProducto();
		gvProductoBuscar.setIdEmpresa(idEmpresa);
		listaGvProducto = new ArrayList<GvProducto>();
		listaGvProducto = gvProductoService.buscarGvProductos(gvProductoBuscar);

		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
		visibleCbEliminarDocumento = false;
		visibleCbActualizarDocumento = false;
	}
	
	public void buscarListaPorParametros() {
		gvDocumentoBuscar = new GvDocumento();
		gvDocumentoBuscar.setIdEmpresa(idEmpresa);
		listaGvDocumento = new ArrayList<GvDocumento>();
		if (idClienteBuscar == 0 && 
			idTipoDocumentoBuscar == 0 &&
			idEstadoDocumentoBuscar == 0) {
			listaGvDocumento = gvDocumentoService.buscarGvDocumentos(gvDocumentoBuscar);
		} else {
			if (idClienteBuscar != 0) {
				gvCliente.setIdCliente(idClienteBuscar);
				gvDocumentoBuscar.setGvCliente(gvCliente);
			}
			if (idTipoDocumentoBuscar != 0) {
				gvTipoDocumento.setIdTipoDocumento(idTipoDocumentoBuscar);
				gvDocumentoBuscar.setGvTipoDocumento(gvTipoDocumento);
			}
			if (idEstadoDocumentoBuscar != 0) {
				gvEstadoDocumento.setIdEstadoDocumento(idEstadoDocumentoBuscar);
				gvDocumentoBuscar.setGvEstadoDocumento(gvEstadoDocumento);
			}
			gvDocumentoBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			listaGvDocumento = gvDocumentoService.buscarListaPorParametros(gvDocumentoBuscar);
		}
	}

	public void nuevo() {
		codProceso = "N";
		gvDocumentoCrear = new GvDocumento();
		
		gvCliente = new GvCliente();
		gvCliente.setIdCliente(idEmpresa);
		gvCliente.setIdEmpresa(idEmpresa);
		listaGvCliente = gvClienteService.buscarGvClientes(gvCliente);
		
		gvTipoDocumento = new GvTipoDocumento();
		gvTipoDocumento.setIdTipoDocumento(4);
		listaGvTipoDocumento = gvTipoDocumentoService.buscarGvTipoDocumentos();

		gvEstadoDocumento = new GvEstadoDocumento();
		gvEstadoDocumento = gvEstadoDocumentoService.buscarPorCodigo(4);

		gvDocumentoDetalle = new GvDocumentoDetalle();
		gvProducto = new GvProducto();
		gvProducto = gvProductoService.buscarPorCodigo(idEmpresa, idEmpresa);
		gvDocumentoDetalle.setGvProducto(gvProducto);
		listaGvDocumentoDetalleCrear = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleCrear.add(gvDocumentoDetalle);
		
		mensaje = "";
		mensajeDocumento = "";
	}

	//Cargar datos del producto seleccionado.
	public void cargarDatosProducto(Integer index, int idProducto) {
		
		GvProducto gvProductoSeleccionado = new GvProducto();
		gvProductoSeleccionado = gvProductoService.buscarPorCodigo(idEmpresa, idProducto);
		
		GvDocumentoDetalle gvDocumentoDetalleSeleccionado = new GvDocumentoDetalle();
		gvDocumentoDetalleSeleccionado.setGvProducto(gvProductoSeleccionado);
		
		if (codProceso.equals("N")) {
			listaGvDocumentoDetalleCrear.set(index, gvDocumentoDetalleSeleccionado);
		}
		if (codProceso.equals("E")) {
			listaGvDocumentoDetalleActualizar.set(index, gvDocumentoDetalleSeleccionado);
		}
	}

	//Guardar datos del producto de la ocurrencia actual.
	public void guardarProducto(GvDocumentoDetalle gvDocumentoDetalleAux, Integer index) {
		mensaje = "";

		GvDocumento gvDocumentoAux = new GvDocumento();
		List<GvDocumentoDetalle> listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		if (codProceso == "N") {
			gvDocumentoAux = gvDocumentoCrear;
			listaGvDocumentoDetalleAux = listaGvDocumentoDetalleCrear;
		} else {
			gvDocumentoAux = gvDocumentoActualizar;
			listaGvDocumentoDetalleAux = listaGvDocumentoDetalleActualizar;
		}

		int indiceLista =  index + 1; 
		//if (gvDocumentoDetalleAux.getIdProductoTransient() == 0) {
		if (gvDocumentoDetalleAux.getGvProducto().getIdProducto() == 0 ||
		    idEmpresa == gvDocumentoDetalleAux.getGvProducto().getIdProducto()) {
			mensaje = "Error: Ingresar producto... indice="+indiceLista;
			return;
		}
		if (gvDocumentoDetalleAux.getNumCantidad() == 0) {
			mensaje = "Error: Ingresar cantidad del producto... indice="+indiceLista;
			return;
		}
		
		//Tratar producto.
		gvProducto = new GvProducto();
		gvProducto = gvProductoService.buscarPorCodigo(idEmpresa, gvDocumentoDetalleAux.getGvProducto().getIdProducto());
		//gvProducto.setIdEmpresa(idEmpresa);
		if (gvDocumentoDetalleAux.getNumCantidad() > gvProducto.getNumExistenciaActual() &&
		   (gvTipoDocumento.getIdTipoDocumento() == 1 | gvTipoDocumento.getIdTipoDocumento() == 3)) {
			mensaje = "Error: cantidad excede el stock("+gvProducto.getNumExistenciaActual()+") del producto... indice="+indiceLista;
			return;
		}
		//Sumar o Restar la cantidad al stock del producto
		if (gvTipoDocumento.getIdTipoDocumento() == 1 | gvTipoDocumento.getIdTipoDocumento() == 3) {
			gvProducto.setNumExistenciaActual(gvProducto.getNumExistenciaActual() - gvDocumentoDetalleAux.getNumCantidad());
		} else {
			gvProducto.setNumExistenciaActual(gvProducto.getNumExistenciaActual() + gvDocumentoDetalleAux.getNumCantidad());
		}

		//Calcular Importe de la Venta o Precio de Compra del Producto.
		if (gvTipoDocumento.getIdTipoDocumento() == 1) {
			gvDocumentoDetalleAux.setMntImporte(gvDocumentoDetalleAux.getNumCantidad() * gvProducto.getPrecioDeCosto());
			gvDocumentoDetalleAux.setPrecio(gvProducto.getPrecioDeCosto());
		}
		if (gvTipoDocumento.getIdTipoDocumento() == 3) {
			gvDocumentoDetalleAux.setMntImporte(gvDocumentoDetalleAux.getNumCantidad() * gvProducto.getPrecioDeCompra());
			gvDocumentoDetalleAux.setPrecio(gvProducto.getPrecioDeCompra());
		} 
		if (gvTipoDocumento.getIdTipoDocumento() == 4) {
			gvDocumentoDetalleAux.setPrecio(redondearDecimales((gvDocumentoDetalleAux.getMntImporte() / gvDocumentoDetalleAux.getNumCantidad()), 2));
			gvProducto.setPrecioDeCompra(redondearDecimales((gvDocumentoDetalleAux.getMntImporte() / gvDocumentoDetalleAux.getNumCantidad()), 2));
		}
		gvDocumentoDetalleAux.setGvProducto(gvProducto);
		
		if (gvDocumentoAux.getIdDocumento() != 0) {
			gvDocumentoDetalleAux.setGvDocumento(gvDocumentoAux);
		}
		gvDocumentoDetalleAux.setEstado(1);
		//Actualizar datos en la lista según indice escogido.
		listaGvDocumentoDetalleAux.set(index, gvDocumentoDetalleAux);
		
		//Inicializar datos del documento para luego actualizarlos desde la lista de productos.
		gvDocumentoAux.setMntSubtotal(0);
		gvDocumentoAux.setMntIva(0);
		gvDocumentoAux.setMntTotal(0);
		
		//Actualizar datos del documento desde la lista de productos.
		int numSize = 0;
		for (GvDocumentoDetalle gvDocumentoDetalleAux1 : listaGvDocumentoDetalleAux) {
			if (gvDocumentoDetalleAux1.getGvProducto().getIdProducto() != 0 &&
				gvDocumentoDetalleAux1.getNumCantidad() != 0) {
				numSize = numSize + 1;
				gvDocumentoAux.setMntSubtotal(gvDocumentoAux.getMntSubtotal() + gvDocumentoDetalleAux1.getMntImporte());
				GvProducto gvProductoAux = gvDocumentoDetalleAux1.getGvProducto();
				if (gvProductoAux.getAplicaIva() == 1) {
					gvDocumentoAux.setMntIva(gvDocumentoAux.getMntIva() + (gvDocumentoDetalleAux1.getMntImporte() * Float.parseFloat("0.12")));
				}
				gvDocumentoAux.setMntTotal(gvDocumentoAux.getMntSubtotal() + gvDocumentoAux.getMntIva());
			}
		}
		gvDocumentoAux.setNumItems(numSize);
		
		//Verificar que el último elemento del arreglo contenga un producto.
		int indice = listaGvDocumentoDetalleAux.size() - 1;
		if (listaGvDocumentoDetalleAux.get(indice).getGvProducto().getIdProducto() != 0) {
			//Adicionar una fila en el detalle del documento.
			gvDocumentoDetalleAux = new GvDocumentoDetalle();
			gvProducto = new GvProducto();
			gvProducto = gvProductoService.buscarPorCodigo(idEmpresa, 0);
			gvDocumentoDetalleAux.setGvProducto(gvProducto);
			listaGvDocumentoDetalleAux.add(gvDocumentoDetalleAux);
		}

		if (codProceso == "N") {
			gvDocumentoCrear = gvDocumentoAux;
			listaGvDocumentoDetalleCrear = listaGvDocumentoDetalleAux;
		} else {
			gvDocumentoActualizar = gvDocumentoAux;
			listaGvDocumentoDetalleActualizar = listaGvDocumentoDetalleAux;
		}
		
		//Verificar si graba o actualiza el producto
		if (index == indice) {
			mensaje = "Se ha guardado el producto "+indiceLista+" ...";
		} else {
			mensaje = "Se ha actualizado el producto "+indiceLista+" ...";
		}
	}

	//Eliminar un producto u ocurrencia.
	public void eliminarProducto(GvDocumentoDetalle gvDocumentoDetalleAux, Integer index) {
		GvDocumento gvDocumentoAux = new GvDocumento();
		List<GvDocumentoDetalle> listaGvDocumentoDetalleAux = new ArrayList<GvDocumentoDetalle>();
		if (codProceso == "N") {
			gvDocumentoAux = gvDocumentoCrear;
			listaGvDocumentoDetalleAux = listaGvDocumentoDetalleCrear;
		} else {
			gvDocumentoAux = gvDocumentoActualizar;
			listaGvDocumentoDetalleAux = listaGvDocumentoDetalleActualizar;
		}
		
		gvDocumentoAux.setMntSubtotal(gvDocumentoAux.getMntSubtotal() - gvDocumentoDetalleAux.getMntImporte());
		
		GvProducto gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
		//Adicionar la cantidad al stock del producto
		if (gvTipoDocumento.getIdTipoDocumento() == 1 | gvTipoDocumento.getIdTipoDocumento() == 3) {
			gvProductoAux.setNumExistenciaActual(gvProductoAux.getNumExistenciaActual() + gvDocumentoDetalleAux.getNumCantidad());
		} else {
			gvProductoAux.setNumExistenciaActual(gvProductoAux.getNumExistenciaActual() - gvDocumentoDetalleAux.getNumCantidad());
		}
		gvDocumentoDetalleAux.setGvProducto(gvProductoAux);
		if (gvProductoAux.getAplicaIva() == 1) {
			gvDocumentoAux.setMntIva(gvDocumentoAux.getMntIva() - (gvDocumentoDetalleAux.getMntImporte() * Float.parseFloat("0.12")));
		}
		gvDocumentoAux.setMntTotal(gvDocumentoAux.getMntSubtotal() + gvDocumentoAux.getMntIva());
		
		listaGvDocumentoDetalleAux.remove(gvDocumentoDetalleAux);
		gvDocumentoAux.setNumItems(listaGvDocumentoDetalleAux.size() - 1);
		
		if (gvDocumentoDetalleAux.getGvDocumento() != null &&
			gvDocumentoDetalleAux.getGvDocumento().getIdDocumento() != 0 &&
			gvDocumentoDetalleAux.getNumCantidad() != 0) {
			listaGvDocumentoDetalleEliminar.add(gvDocumentoDetalleAux);
		}

		if (codProceso == "N") {
			gvDocumentoCrear = gvDocumentoAux;
			listaGvDocumentoDetalleCrear = listaGvDocumentoDetalleAux;
		} else {
			gvDocumentoActualizar = gvDocumentoAux;
			listaGvDocumentoDetalleActualizar = listaGvDocumentoDetalleAux;
		}
		
		index = index + 1;
		mensaje = "Se ha eliminado el producto "+index+" ...";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void crearDocumento() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvDocumento.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				crearCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			gvDocumentoCrear.setGvCliente(gvCliente);
			gvDocumentoCrear.setGvTipoDocumento(gvTipoDocumento);
			
			//Guardar/Actualizar Documento
			if (codProceso == "N") {
				//Estado documento "GENERADO".
				gvEstadoDocumento = gvEstadoDocumentoService.buscarPorCodigo(4);
				gvDocumentoCrear.setGvEstadoDocumento(gvEstadoDocumento);
				
				//Verificar existencia de ocurrencias de productos para generar o no el documento.
				if (listaGvDocumentoDetalleCrear.size() > 0) {
					//Actualizar el numero de productos del documento.
					gvDocumentoCrear.setNumItems(listaGvDocumentoDetalleCrear.size() - 1);
					//Eliminar el último registro de la lista.
					listaGvDocumentoDetalleCrear.remove(listaGvDocumentoDetalleCrear.size() - 1);
					
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3) {
						listaGvDocumentoDetalleActualizar = new ArrayList<GvDocumentoDetalle>();
						gvDocumentoActualizar = new GvDocumento();
						gvDocumentoActualizar.setGvCliente(gvCliente);
						gvDocumentoActualizar.setGvTipoDocumento(gvTipoDocumento);
					}

					//Crear documento con persist.
					gvDocumentoService.crearGvDocumento(gvDocumentoCrear);
					
					//Grabar GvDocumentoDetalle desde listaGvDocumentoDetalleCrear.
					for (GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalleCrear) {
						//Actualizar datos del producto.
						GvProducto gvProductoAux = new GvProducto();
						gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
						gvProductoService.guardarGvProducto(gvProductoAux);
					
						//Actualizar Documento Detalles
						gvDocumentoDetalleAux.setGvDocumento(gvDocumentoCrear);
						gvDocumentoDetalleService.crearGvDocumentoDetalle(gvDocumentoDetalleAux);
						
						if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3) {
							//Crear item de DocumentoDetalle del producto a recibir la transferencia de las unidades de la caja, java o paquete
							crearItemTransferencia(gvDocumentoDetalleAux);
						}

						//Crear movimiento en el kardex.
						if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 1) {
							crearMovimientoKardex(gvDocumentoDetalleAux, "E", 1);
						} 
						if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3) {
							crearMovimientoKardex(gvDocumentoDetalleAux, "E", 3);
						}
						if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 4) {
							crearMovimientoKardex(gvDocumentoDetalleAux, "I", 2);
						}
					}

					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3) {
						gvDocumentoActualizar.setIdEmpresa(gvDocumentoCrear.getIdEmpresa());
						gvDocumentoActualizar.setEstado(gvDocumentoCrear.getEstado());
						gvDocumentoActualizar.setGvEstadoDocumento(gvDocumentoCrear.getGvEstadoDocumento());
						gvDocumentoActualizar.setGvTipoDocumento(gvDocumentoCrear.getGvTipoDocumento());
						gvDocumentoActualizar.setFechaActualiza(gvDocumentoCrear.getFechaActualiza());
						gvDocumentoActualizar.setFechaRegistra(gvDocumentoCrear.getFechaRegistra());
						gvDocumentoActualizar.setNomAplicativoActualiza(gvDocumentoCrear.getNomAplicativoActualiza());
						gvDocumentoActualizar.setNomAplicativoRegistra(gvDocumentoCrear.getNomAplicativoRegistra());
						gvDocumentoActualizar.setNomUsuarioActualiza(gvDocumentoCrear.getNomUsuarioActualiza());
						gvDocumentoActualizar.setNomUsuarioRegistra(gvDocumentoCrear.getNomUsuarioRegistra());
						crearDocumentoAjuste();
					}
					
					mensajeDocumento = "Se ha guardado el documento...";
					buscarListaPorParametros();
				} else {
					mensajeDocumento = "No se guardó el documento...";
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeDocumento = "Error: No se actualizaron los datos...";
			else
				mensajeDocumento = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}
	
	public void crearItemTransferencia(GvDocumentoDetalle gvDocumentoDetalleAux) {
		//Tratar producto anterior y actual
		String codigoBarra = gvDocumentoDetalleAux.getGvProducto().getCodigo().substring(1);
		Integer numeroUnidadesTransferir = gvDocumentoDetalleAux.getNumCantidad() * gvDocumentoDetalleAux.getGvProducto().getGvUnidad().getItemsPorUnidad();
		GvProducto gvProductoAux = new GvProducto();
		gvProductoAux = gvProductoService.buscarPorCodigo(idEmpresa, codigoBarra);

		//Tratar documento detalle anterior y actual
		GvDocumentoDetalle gvDocumentoDetalleActualizar = new GvDocumentoDetalle();
		gvDocumentoDetalleActualizar.setEstado(gvDocumentoDetalleAux.getEstado());
		gvDocumentoDetalleActualizar.setGvProducto(gvProductoAux);
		gvDocumentoDetalleActualizar.setMntDescuento(gvDocumentoDetalleAux.getMntDescuento());
		gvDocumentoDetalleActualizar.setMntImporte(numeroUnidadesTransferir * gvProductoAux.getPrecioDeCompra());
		gvDocumentoDetalleActualizar.setNumCantidad(numeroUnidadesTransferir);
		gvDocumentoDetalleActualizar.setPrecio(gvProductoAux.getPrecioDeCompra());
		
		//Adicionar un registro al documento detalle actual para crear nuevo documento
		listaGvDocumentoDetalleActualizar.add(gvDocumentoDetalleActualizar);
		
		//Tratar documento para ir actualizando sus valores
		gvDocumentoActualizar.setNumItems(gvDocumentoActualizar.getNumItems() + 1);
		gvDocumentoActualizar.setMntSubtotal(gvDocumentoActualizar.getMntSubtotal() + gvDocumentoDetalleActualizar.getMntImporte());
		if (gvProductoAux.getAplicaIva() == 1) {
			gvDocumentoActualizar.setMntIva(gvDocumentoActualizar.getMntIva() + (gvDocumentoDetalleActualizar.getMntImporte() * Float.parseFloat("0.12")));
		}
		gvDocumentoActualizar.setMntTotal(gvDocumentoActualizar.getMntSubtotal() + gvDocumentoActualizar.getMntIva());
	}

	public void crearDocumentoAjuste() {
		try {
			if (gvDocumentoActualizar.getGvTipoDocumento().getIdTipoDocumento() != 3) {
				//Eliminar el último registro de la lista.
				listaGvDocumentoDetalleActualizar.remove(listaGvDocumentoDetalleActualizar.size() - 1);
			}
			// Verificar existencia de ocurrencias de productos para generar o no el documento.
			if (listaGvDocumentoDetalleActualizar.size() > 0) {
				GvDocumento gvDocumentoCrear = new GvDocumento();
				//Mover datos del documento que se va a eliminar.
				gvDocumentoCrear.setEstado(gvDocumentoActualizar.getEstado());
				gvDocumentoCrear.setFechaActualiza(gvDocumentoActualizar.getFechaActualiza());
				gvDocumentoCrear.setFechaRegistra(gvDocumentoActualizar.getFechaRegistra());
				gvDocumentoCrear.setGvCliente(gvDocumentoActualizar.getGvCliente());
				gvDocumentoCrear.setGvEstadoDocumento(gvDocumentoActualizar.getGvEstadoDocumento());
				//Tratar tipo de documento.
				if (gvDocumentoActualizar.getGvTipoDocumento().getIdTipoDocumento() == 1) {
					gvDocumentoActualizar.setGvTipoDocumento(gvTipoDocumentoService.buscarPorCodigo(6));
				} 
				if (gvDocumentoActualizar.getGvTipoDocumento().getIdTipoDocumento() == 4) {
					gvDocumentoActualizar.setGvTipoDocumento(gvTipoDocumentoService.buscarPorCodigo(7));
				}
				gvDocumentoCrear.setGvTipoDocumento(gvDocumentoActualizar.getGvTipoDocumento());
				gvDocumentoCrear.setIdEmpresa(gvDocumentoActualizar.getIdEmpresa());
				gvDocumentoCrear.setMntIva(gvDocumentoActualizar.getMntIva());
				gvDocumentoCrear.setMntSubtotal(gvDocumentoActualizar.getMntSubtotal());
				gvDocumentoCrear.setMntTotal(gvDocumentoActualizar.getMntTotal());
				gvDocumentoCrear.setNomAplicativoActualiza(gvDocumentoActualizar.getNomAplicativoActualiza());
				gvDocumentoCrear.setNomAplicativoRegistra(gvDocumentoActualizar.getNomAplicativoRegistra());
				gvDocumentoCrear.setNomUsuarioActualiza(gvDocumentoActualizar.getNomUsuarioActualiza());
				gvDocumentoCrear.setNomUsuarioRegistra(gvDocumentoActualizar.getNomUsuarioRegistra());
				gvDocumentoCrear.setNumItems(gvDocumentoActualizar.getNumItems());
				gvDocumentoCrear.setObservaciones(gvDocumentoActualizar.getObservaciones());
				//Crear documento con persist.
				gvDocumentoService.crearGvDocumento(gvDocumentoCrear);
				
				if (gvDocumentoActualizar.getGvTipoDocumento().getIdTipoDocumento() != 3) {
					//Anular documento original.
					GvEstadoDocumento gvEstadoDocumento = new GvEstadoDocumento();
					gvEstadoDocumento = gvEstadoDocumentoService.buscarPorCodigo(3);
					gvDocumentoActualizar.setGvEstadoDocumento(gvEstadoDocumento);
					gvDocumentoService.actualizarGvDocumento(gvDocumentoActualizar);
				}

				//Inicializar Documento Detalle
				GvDocumentoDetalle gvDocumentoDetalleCrear = new GvDocumentoDetalle();

				// Grabar GvDocumentoDetalle desde listaGvDocumentoDetalleCrear.
				for (GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalleActualizar) {
					// Actualizar datos del producto.
					GvProducto gvProductoAux = new GvProducto();
					gvProductoAux = gvDocumentoDetalleAux.getGvProducto();
					//Adicionar la cantidad al stock del producto
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3 |
						gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 6) {
						gvProductoAux.setNumExistenciaActual(gvProductoAux.getNumExistenciaActual() + gvDocumentoDetalleAux.getNumCantidad());
					}
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 7) {
						gvProductoAux.setNumExistenciaActual(gvProductoAux.getNumExistenciaActual() - gvDocumentoDetalleAux.getNumCantidad());
					}
					gvProductoService.guardarGvProducto(gvProductoAux);

					//Crear Documento Detalle
					gvDocumentoDetalleCrear = new GvDocumentoDetalle();
					gvDocumentoDetalleCrear.setEstado(gvDocumentoDetalleAux.getEstado());
					gvDocumentoDetalleCrear.setGvDocumento(gvDocumentoCrear);
					gvDocumentoDetalleCrear.setGvProducto(gvProductoAux);
					gvDocumentoDetalleCrear.setIdProductoTransient(gvDocumentoDetalleAux.getIdProductoTransient());
					gvDocumentoDetalleCrear.setMntDescuento(gvDocumentoDetalleAux.getMntDescuento());
					gvDocumentoDetalleCrear.setMntImporte(gvDocumentoDetalleAux.getMntImporte());
					gvDocumentoDetalleCrear.setNumCantidad(gvDocumentoDetalleAux.getNumCantidad());
					gvDocumentoDetalleCrear.setPrecio(gvDocumentoDetalleAux.getPrecio());
					//Crear documentoDetalle con persist.
					gvDocumentoDetalleService.crearGvDocumentoDetalle(gvDocumentoDetalleCrear);
					// Crear movimiento en el kardex.
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 3) {
						crearMovimientoKardex(gvDocumentoDetalleCrear, "I", 3);
					}
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 6) {
						crearMovimientoKardex(gvDocumentoDetalleCrear, "I", 5);
					} 
					if (gvDocumentoCrear.getGvTipoDocumento().getIdTipoDocumento() == 7) {
						crearMovimientoKardex(gvDocumentoDetalleCrear, "E", 6);
					}
				}

				mensajeDocumento = "Se realizó el ajuste al documento...";
				buscarListaPorParametros();
			} else {
				mensajeDocumento = "No se realizó el ajuste al documento...";
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeDocumento = "Error: No se actualizaron los datos...";
			else
				mensajeDocumento = (new StringBuilder()).append("Error: ").append(exc.getMessage()).toString();
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
		//Datos auditables.
		gvKardex.setEstado(1);
		gvKardex.setFechaRegistra(new Date());
		gvKardex.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		gvKardex.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		
		gvKardexService.guardarGvKardex(gvKardex);
	}

	public void editar(GvDocumento gvDocumentoAux) {
		codProceso = "E";
		gvDocumentoActualizar = gvDocumentoAux;
		
		if (gvDocumentoActualizar.getGvCliente() != null) {
			gvCliente = gvDocumentoActualizar.getGvCliente();
			listaGvCliente = gvClienteService.buscarGvClientes(gvCliente);
		}
		if (gvDocumentoActualizar.getGvTipoDocumento() != null) {
			gvTipoDocumento = gvDocumentoActualizar.getGvTipoDocumento();
			listaGvTipoDocumento = gvTipoDocumentoService.buscarGvTipoDocumentos();
		}
		if (gvDocumentoActualizar.getGvEstadoDocumento() != null) {
			gvEstadoDocumento = gvDocumentoActualizar.getGvEstadoDocumento();
		}
		
		if (gvDocumentoAux.getGvTipoDocumento().getIdTipoDocumento() == 1 |
			gvDocumentoAux.getGvTipoDocumento().getIdTipoDocumento() == 4) {
			visibleCbEliminarDocumento = true;
		}
		if (!gvDocumentoAux.getGvEstadoDocumento().equals("ANULADO")) {
			visibleCbActualizarDocumento = true;
		}
		
		listaGvDocumentoDetalleActualizar = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleActualizarAux = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleEliminar = new ArrayList<GvDocumentoDetalle>();
		listaGvDocumentoDetalleActualizar = gvDocumentoDetalleService.buscarListaPorDocumento(gvDocumentoActualizar);
		listaGvDocumentoDetalleActualizarAux = gvDocumentoDetalleService.buscarListaPorDocumento(gvDocumentoActualizar);
		
		//Adicionar una fila al detalle del documento. 
		gvDocumentoDetalle = new GvDocumentoDetalle();
		gvProducto = new GvProducto();
		gvProducto = gvProductoService.buscarPorCodigo(idEmpresa, idEmpresa);
		gvDocumentoDetalle.setGvProducto(gvProducto);
		listaGvDocumentoDetalleActualizar.add(gvDocumentoDetalle);

		mensaje = "";
		mensajeDocumento = "";
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
			gvDocumentoActualizar.setGvCliente(gvCliente);
			gvDocumentoActualizar.setGvTipoDocumento(gvTipoDocumento);
			
			//Guardar/Actualizar Documento
			if (codProceso == "E") {
				//Verificar existencia de ocurrencias de productos para generar o no el documento.
				if (listaGvDocumentoDetalleActualizar.size() > 1) {
					//Actualizar el numero de productos del documento.
					gvDocumentoActualizar.setNumItems(listaGvDocumentoDetalleActualizar.size() - 1);
					
					//Eliminar el último registro de la lista.
					listaGvDocumentoDetalleActualizar.remove(listaGvDocumentoDetalleActualizar.size() - 1);
					
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
					
					//Grabar GvDocumentoDetalle desde listaGvDocumentoDetalleActualizar.
					for (GvDocumentoDetalle gvDocumentoDetalleAux : listaGvDocumentoDetalleActualizar) {
						//Verificar si ya existe el item en el arreglo para actualizar
						boolean noExisteItem = true;
						for (GvDocumentoDetalle gvDocumentoDetalleAux1 : listaGvDocumentoDetalleActualizarAux) {
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
							gvDocumentoDetalleAux.setGvDocumento(gvDocumentoActualizar);
							gvDocumentoDetalleService.actualizarGvDocumentoDetalle(gvDocumentoDetalleAux);

							//Crear movimiento en el kardex.
							crearMovimientoKardex(gvDocumentoDetalleAux, "E", 5);
						}
					}

					//Actualizar documento con merge.
					gvDocumentoService.actualizarGvDocumento(gvDocumentoActualizar);
					
					mensajeDocumento = "Se ha actualizado el documento...";
					buscarListaPorParametros();
				} else {
					mensajeDocumento = "No se actualizó el documento...";
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeDocumento = "Error: No se actualizaron los datos...";
			else
				mensajeDocumento = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void crearCamposAuditables() {
		if (codProceso == "N") {
			gvDocumentoCrear.setIdEmpresa(idEmpresa);
			gvDocumentoCrear.setEstado(1);
			gvDocumentoCrear.setFechaRegistra(new Date());
			gvDocumentoCrear.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvDocumentoCrear.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "E") {
			gvDocumentoActualizar.setFechaActualiza(new Date());
			gvDocumentoActualizar.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvDocumentoActualizar.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
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

	//Ejecutar procesos de acuerdo a lo solicitado en el OneMenu.
	public void procesoAplicaIva(ValueChangeEvent evt){
		/*socializadasRutas = (Integer)evt.getNewValue();
		if (socializadasRutas == 1) {
		} else {
		}*/
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

	public GvDocumento getGvDocumentoCrear() {
		return gvDocumentoCrear;
	}

	public void setGvDocumentoCrear(GvDocumento gvDocumentoCrear) {
		this.gvDocumentoCrear = gvDocumentoCrear;
	}

	public GvDocumento getGvDocumentoActualizar() {
		return gvDocumentoActualizar;
	}

	public void setGvDocumentoActualizar(GvDocumento gvDocumentoActualizar) {
		this.gvDocumentoActualizar = gvDocumentoActualizar;
	}

	public List<GvDocumentoDetalle> getListaGvDocumentoDetalleCrear() {
		return listaGvDocumentoDetalleCrear;
	}

	public void setListaGvDocumentoDetalleCrear(
			List<GvDocumentoDetalle> listaGvDocumentoDetalleCrear) {
		this.listaGvDocumentoDetalleCrear = listaGvDocumentoDetalleCrear;
	}

	public List<GvDocumentoDetalle> getListaGvDocumentoDetalleActualizar() {
		return listaGvDocumentoDetalleActualizar;
	}

	public void setListaGvDocumentoDetalleActualizar(
			List<GvDocumentoDetalle> listaGvDocumentoDetalleActualizar) {
		this.listaGvDocumentoDetalleActualizar = listaGvDocumentoDetalleActualizar;
	}

	public GvDocumento getGvDocumento() {
		return gvDocumento;
	}

	public void setGvDocumento(GvDocumento gvDocumento) {
		this.gvDocumento = gvDocumento;
	}

	public GvDocumentoDetalle getGvDocumentoDetalle() {
		return gvDocumentoDetalle;
	}

	public void setGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) {
		this.gvDocumentoDetalle = gvDocumentoDetalle;
	}

	public List<GvDocumentoDetalle> getListaGvDocumentoDetalleEliminar() {
		return listaGvDocumentoDetalleEliminar;
	}

	public void setListaGvDocumentoDetalleEliminar(
			List<GvDocumentoDetalle> listaGvDocumentoDetalleEliminar) {
		this.listaGvDocumentoDetalleEliminar = listaGvDocumentoDetalleEliminar;
	}

	public List<GvProducto> getListaGvProductoPuntoVenta() {
		return listaGvProductoPuntoVenta;
	}

	public void setListaGvProductoPuntoVenta(
			List<GvProducto> listaGvProductoPuntoVenta) {
		this.listaGvProductoPuntoVenta = listaGvProductoPuntoVenta;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public boolean isVisibleCbEliminarDocumento() {
		return visibleCbEliminarDocumento;
	}

	public void setVisibleCbEliminarDocumento(boolean visibleCbEliminarDocumento) {
		this.visibleCbEliminarDocumento = visibleCbEliminarDocumento;
	}

	public boolean isVisibleCbActualizarDocumento() {
		return visibleCbActualizarDocumento;
	}

	public void setVisibleCbActualizarDocumento(boolean visibleCbActualizarDocumento) {
		this.visibleCbActualizarDocumento = visibleCbActualizarDocumento;
	}
}