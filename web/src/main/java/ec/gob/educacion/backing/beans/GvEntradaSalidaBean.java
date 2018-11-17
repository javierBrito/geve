package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvEntradaSalida;
import ec.gob.educacion.model.geve.GvProveedor;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.service.GvEntradaSalidaService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvProveedorService;
import ec.gob.educacion.service.GvCajaService;
import ec.gob.educacion.service.GvTipoMovimientoService;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class GvEntradaSalidaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvCajaService gvCajaService;
	@EJB
	private GvEntradaSalidaService gvEntradaSalidaService;
	@EJB
	private GvProveedorService gvProveedorService;
	@EJB
	private GvKardexService gvKardexService;
	@EJB
	private GvTipoMovimientoService gvTipoMovimientoService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvCaja gvCaja;
	private GvCaja gvCajaCrear;
	private GvCaja gvCajaActualizar;
	private GvCaja gvCajaBuscar;
	private List<GvCaja> listaGvCaja;
	private List<GvCaja> listaUltimoRegistroCaja;
	private GvCaja gvCajaUltimoRegistro;

	private GvEntradaSalida gvEntradaSalida;
	private List<GvEntradaSalida> listaGvEntradaSalidaCrear;
	private List<GvEntradaSalida> listaGvEntradaSalidaActualizar;
	private List<GvEntradaSalida> listaGvEntradaSalidaEliminar;

	private GvProveedor gvProveedor;
	private List<GvProveedor> listaGvProveedor;
	private List<GvProveedor> listaGvProveedorPuntoVenta;
	
	private String mensaje;
	private String mensajeCaja;
	private String codProceso;
	private String fechaRegistraString;
	private String nombreEmpresa;

	private Integer idProveedor;
	private Date fechaRegistra;

	private int idEmpresa;

	@PostConstruct
	public void init() {
		codProceso = "N";
		mensaje = "";
		mensajeCaja = "";

		gvCajaCrear = new GvCaja();
		gvCajaActualizar = new GvCaja();
		gvCajaBuscar = new GvCaja();
		listaGvCaja = new ArrayList<GvCaja>();
		
		gvEntradaSalida = new GvEntradaSalida();
		listaGvEntradaSalidaCrear = new ArrayList<GvEntradaSalida>();
		listaGvEntradaSalidaActualizar = new ArrayList<GvEntradaSalida>();
		
		gvProveedor = new GvProveedor();
		idEmpresa = sesionControlador.getUsuario().getSede().getCodigo().intValue();
		gvProveedor.setIdEmpresa(idEmpresa);
		gvProveedor.setIdProveedor(0);
		listaGvProveedor = new ArrayList<GvProveedor>();
		listaGvProveedor = gvProveedorService.buscarGvProveedores(gvProveedor);

		fechaRegistra = new Date();
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre().trim();
	}
	
	public void buscarListaPorParametros() {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaRegistra); // Configuramos la fecha que se recibe
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	setFechaRegistraString(sdf.format(calendar.getTime()));

		gvCajaBuscar = new GvCaja();
		gvCajaBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvCaja = new ArrayList<GvCaja>();
		if (fechaRegistra == null) {
			listaGvCaja = gvCajaService.buscarGvCajas(gvCajaBuscar);
		} else {
			gvCajaBuscar.setFechaRegistra(fechaRegistra);
			gvCajaBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			listaGvCaja = gvCajaService.buscarListaPorParametros(gvCajaBuscar);
		}
	}

	public void nuevo() {
		codProceso = "N";
		gvCajaCrear = new GvCaja();
		listaGvEntradaSalidaCrear = new ArrayList<GvEntradaSalida>();
		
		gvCajaUltimoRegistro = new GvCaja();
		gvCajaBuscar.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
		listaUltimoRegistroCaja = new  ArrayList<GvCaja>();
		listaUltimoRegistroCaja = gvCajaService.buscarUltimoRegistro(gvCajaBuscar);
		if (listaUltimoRegistroCaja.size() > 0) {
			gvCajaUltimoRegistro = listaUltimoRegistroCaja.get(0);
			if (gvCajaUltimoRegistro != null) {
				gvCajaCrear.setMntSaldo(gvCajaUltimoRegistro.getMntLibro());
			}
		}
		
		//cargarNominaciones();
		//Adiciona un item a la lista de gvEntradaSalida
		gvEntradaSalida = new GvEntradaSalida();
		gvProveedor.setIdProveedor(0);
		gvEntradaSalida.setGvProveedor(gvProveedor);
		listaGvEntradaSalidaCrear.add(gvEntradaSalida);
		
		mensaje = "";
		mensajeCaja = "";
	}

	//Cagar datos del proveedor seleccionado.
	public void cargarDatosProveedor(Integer index, int idProveedor) {
		GvProveedor gvProveedorSeleccionado = new GvProveedor();
		gvProveedorSeleccionado = gvProveedorService.buscarPorCodigo(idEmpresa, idProveedor);
		
		GvEntradaSalida gvEntradaSalidaSeleccionado = new GvEntradaSalida();
		gvEntradaSalidaSeleccionado.setGvProveedor(gvProveedorSeleccionado);
		
		if (codProceso.equals("N")) {
			listaGvEntradaSalidaCrear.set(index, gvEntradaSalidaSeleccionado);
		}
		if (codProceso.equals("E")) {
			listaGvEntradaSalidaActualizar.set(index, gvEntradaSalidaSeleccionado);
		}
	}
	
	//Cargar todas las nominaciones desde la tabla.
	public void cargarNominaciones() {
		if (listaGvProveedor.size() > 0) {
			for (GvProveedor gvProveedorAux : listaGvProveedor) {
				if (gvProveedorAux.getIdProveedor() != 0) {
					gvEntradaSalida = new GvEntradaSalida();
					gvEntradaSalida.setGvProveedor(gvProveedorAux);
					listaGvEntradaSalidaCrear.add(gvEntradaSalida);
				}
			}
		}
	}

	//Guardar datos del Proveedor de la ocurrencia actual.
	public void guardarEntradaSalida(GvEntradaSalida gvEntradaSalidaAux, Integer index) {
		mensaje = "";

		GvCaja gvCajaAux = new GvCaja();
		List<GvEntradaSalida> listaGvEntradaSalidaAux = new ArrayList<GvEntradaSalida>();
		if (codProceso == "N") {
			gvCajaAux = gvCajaCrear;
			listaGvEntradaSalidaAux = listaGvEntradaSalidaCrear;
		} else {
			gvCajaAux = gvCajaActualizar;
			listaGvEntradaSalidaAux = listaGvEntradaSalidaActualizar;
		}

		int indiceLista =  index + 1;
		if (gvEntradaSalidaAux.getGvProveedor().getIdProveedor() == 0) {
			mensaje = "Error: Ingresar proveedor... indice="+indiceLista;
			return;
		}
		/*if (gvEntradaSalidaAux.getNumCantidad() == 0) {
			mensaje = "Error: Ingresar cantidad de la nominación... indice="+indiceLista;
			return;
		}*/
		
		if (gvCajaAux.getIdCaja() != 0) {
			gvEntradaSalidaAux.setGvCaja(gvCajaAux);
		}
		//Asignar estado activo al registro.
		gvEntradaSalidaAux.setEstado(1);
		//Actualizar datos en la lista según indice escogido.
		//gvEntradaSalidaAux.setMntImporte(redondearDecimales((gvEntradaSalidaAux.getNumCantidad()  * gvEntradaSalidaAux.getGvProveedor().getValor()), 2));
		listaGvEntradaSalidaAux.set(index, gvEntradaSalidaAux);
		
		//Inicializar datos de la caja para luego actualizarlos.
		gvCajaAux.setMntCaja(0);
		
		//Actualizar datos de l tipo nominación desde la lista de caja detalle.
		for (GvEntradaSalida gvEntradaSalidaAux1 : listaGvEntradaSalidaAux) {
			if (gvEntradaSalidaAux1.getGvProveedor().getIdProveedor() != 0 &&
			   (gvEntradaSalidaAux1.getMntEntrada() != 0 || gvEntradaSalidaAux1.getMntSalida() != 0)) {
				gvCajaAux.setMntCaja(gvCajaAux.getMntCaja() + gvEntradaSalidaAux1.getMntEntrada() - gvEntradaSalidaAux1.getMntSalida());
			}
		}

		//Realizar operación para obtener la diferencia.
		gvCajaAux.setMntDiferencia(redondearDecimales((gvCajaAux.getMntCaja() - gvCajaAux.getMntLibro()), 2));
		
		//Verificar que el último elemento del arreglo contenga un producto.
		int indice = listaGvEntradaSalidaAux.size() - 1;
		if (listaGvEntradaSalidaAux.get(indice).getGvProveedor().getIdProveedor() != 0) {
			//Adicionar una fila en el detalle del documento.
			gvEntradaSalidaAux = new GvEntradaSalida();
			//gvProveedor = new GvProveedor();
			gvProveedor.setIdProveedor(0);
			gvEntradaSalidaAux.setGvProveedor(gvProveedor);
			listaGvEntradaSalidaAux.add(gvEntradaSalidaAux);
		}

		if (codProceso == "N") {
			gvCajaCrear = gvCajaAux;
			listaGvEntradaSalidaCrear = listaGvEntradaSalidaAux;
			mensaje = "Se ha guardado la transacción "+indiceLista+" ...";
		} else {
			gvCajaActualizar = gvCajaAux;
			listaGvEntradaSalidaActualizar = listaGvEntradaSalidaAux;
			mensaje = "Se ha actualizado la transaccción "+indiceLista+" ...";
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

	//Eliminar un Proveedor u ocurrencia.
	public void eliminarEntradaSalida(GvEntradaSalida gvEntradaSalidaAux, Integer index) {
		GvCaja gvCajaAux = new GvCaja();
		List<GvEntradaSalida> listaGvEntradaSalidaAux = new ArrayList<GvEntradaSalida>();
		if (codProceso == "N") {
			gvCajaAux = gvCajaCrear;
			listaGvEntradaSalidaAux = listaGvEntradaSalidaCrear;
		} else {
			gvCajaAux = gvCajaActualizar;
			listaGvEntradaSalidaAux = listaGvEntradaSalidaActualizar;
		}

		// Restar el importe del registro a la caja.
		gvCajaAux.setMntCaja(gvCajaAux.getMntCaja() - gvEntradaSalidaAux.getMntEntrada() + gvEntradaSalidaAux.getMntSalida());
		
		// Actualizar en cero los datos del registro.
		/*gvEntradaSalidaAux.setNumCantidad(0);
		gvEntradaSalidaAux.setMntEntrada(0);;
		gvEntradaSalidaAux.setMntSalida(0);;
		listaGvEntradaSalidaAux.set(index, gvEntradaSalidaAux);*/

		listaGvEntradaSalidaAux.remove(gvEntradaSalidaAux);
		
		if (gvEntradaSalidaAux.getGvProveedor() != null &&
			gvEntradaSalidaAux.getGvProveedor().getIdProveedor() != 0 &&
		   (gvEntradaSalidaAux.getMntEntrada() != 0 || gvEntradaSalidaAux.getMntSalida() != 0)) {
			listaGvEntradaSalidaEliminar.add(gvEntradaSalidaAux);
		}
		
		if (codProceso == "N") {
			gvCajaCrear = gvCajaAux;
			listaGvEntradaSalidaCrear = listaGvEntradaSalidaAux;
		} else {
			gvCajaActualizar = gvCajaAux;
			listaGvEntradaSalidaActualizar = listaGvEntradaSalidaAux;
		}
		
		index = index + 1;
		mensaje = "Se ha eliminado el producto "+index+" ...";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void crearCaja() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvCaja.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				crearCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			gvCajaCrear.setMntDiferencia(redondearDecimales((gvCajaCrear.getMntCaja() - gvCajaCrear.getMntLibro()), 2));
			//Guardar/Actualizar Caja
			if (codProceso == "N") {
				//Estado tipo nominación "GENERADO".
				//gvEstadoCaja = gvEstadoCajaService.buscarPorCodigo(4);
				//gvCajaCrear.setGvEstadoCaja(gvEstadoCaja);
				
				//Verificar existencia de ocurrencias de productos para generar o no el tipo nominación.
				if (listaGvEntradaSalidaCrear.size() > 1) {
					//Crear tipo nominación con persist.
					gvCajaService.crearGvCaja(gvCajaCrear);
					
					//Grabar GvEntradaSalida desde listaGvEntradaSalidaCrear.
					for (GvEntradaSalida gvEntradaSalidaAux : listaGvEntradaSalidaCrear) {
						//Asignar estado activo al registro.
						gvEntradaSalidaAux.setEstado(1);
						//Actualizar Caja Detalles
						gvEntradaSalidaAux.setGvCaja(gvCajaCrear);
						gvEntradaSalidaService.crearGvEntradaSalida(gvEntradaSalidaAux);
					}
					//nuevo();
					
					mensajeCaja = "Se ha guardado los datos de la caja...";
					buscarListaPorParametros();
				} else {
					mensajeCaja = "No se guardó los datos de la caja...";
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeCaja = "Error: No se actualizaron los datos...";
			else
				mensajeCaja = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	public void editar(GvCaja gvCajaAux) {
		codProceso = "E";
		gvCajaActualizar = gvCajaAux;
		gvCajaActualizar.setMntDiferencia(redondearDecimales((gvCajaActualizar.getMntCaja() - gvCajaActualizar.getMntLibro()), 2));
		listaGvEntradaSalidaActualizar = new ArrayList<GvEntradaSalida>();
		listaGvEntradaSalidaActualizar = gvEntradaSalidaService.buscarListaPorCaja(gvCajaActualizar);

		mensaje = "";
		mensajeCaja = "";
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
				//Verificar existencia de ocurrencias de productos para generar o no el tipo nominación.
				if (listaGvEntradaSalidaActualizar.size() > 1) {
					//Grabar GvEntradaSalida desde listaGvEntradaSalidaActualizar.
					for (GvEntradaSalida gvEntradaSalidaAux : listaGvEntradaSalidaActualizar) {
						//Actualizar Caja Detalles
						gvEntradaSalidaAux.setGvCaja(gvCajaActualizar);
						gvEntradaSalidaService.actualizarGvEntradaSalida(gvEntradaSalidaAux);
					}

					//Actualizar caja con merge.
					gvCajaService.actualizarGvCaja(gvCajaActualizar);
					
					mensajeCaja = "Se ha actualizado los datos de la caja...";
					buscarListaPorParametros();
				} else {
					mensajeCaja = "No se actualizó los datos de la caja...";
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensajeCaja = "Error: No se actualizaron los datos...";
			else
				mensajeCaja = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void crearCamposAuditables() {
		if (codProceso == "N") {
			gvCajaCrear.setEstado(1);
			//gvCajaCrear.setFechaRegistra(new Date());
			gvCajaCrear.setFechaRegistra(fechaRegistra);
			gvCajaCrear.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvCajaCrear.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			gvCajaCrear.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
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

	//Ejecutar procesos de acuerdo a lo solicitado en el OneMenu.
	public void procesoAplicaIva(ValueChangeEvent evt){
		/*socializadasRutas = (Integer)evt.getNewValue();
		if (socializadasRutas == 1) {
		} else {
		}*/
	}

	public List<GvCaja> getListaGvCaja() {
		return listaGvCaja;
	}

	public void setListaGvCajas(List<GvCaja> listaGvCaja) {
		this.listaGvCaja = listaGvCaja;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public GvProveedor getGvProveedor() {
		return gvProveedor;
	}

	public void setGvProveedor(GvProveedor gvProveedor) {
		this.gvProveedor = gvProveedor;
	}

	public List<GvProveedor> getListaGvProveedor() {
		return listaGvProveedor;
	}

	public void setListaGvProveedor(List<GvProveedor> listaGvProveedor) {
		this.listaGvProveedor = listaGvProveedor;
	}

	public GvCaja getGvCajaBuscar() {
		return gvCajaBuscar;
	}

	public void setGvCajaBuscar(GvCaja gvCajaBuscar) {
		this.gvCajaBuscar = gvCajaBuscar;
	}

	public String getMensajeCaja() {
		return mensajeCaja;
	}

	public void setMensajeCaja(String mensajeCaja) {
		this.mensajeCaja = mensajeCaja;
	}

	public GvCaja getGvCajaCrear() {
		return gvCajaCrear;
	}

	public void setGvCajaCrear(GvCaja gvCajaCrear) {
		this.gvCajaCrear = gvCajaCrear;
	}

	public GvCaja getGvCajaActualizar() {
		return gvCajaActualizar;
	}

	public void setGvCajaActualizar(GvCaja gvCajaActualizar) {
		this.gvCajaActualizar = gvCajaActualizar;
	}

	public List<GvEntradaSalida> getListaGvEntradaSalidaCrear() {
		return listaGvEntradaSalidaCrear;
	}

	public void setListaGvEntradaSalidaCrear(
			List<GvEntradaSalida> listaGvEntradaSalidaCrear) {
		this.listaGvEntradaSalidaCrear = listaGvEntradaSalidaCrear;
	}

	public List<GvEntradaSalida> getListaGvEntradaSalidaActualizar() {
		return listaGvEntradaSalidaActualizar;
	}

	public void setListaGvEntradaSalidaActualizar(
			List<GvEntradaSalida> listaGvEntradaSalidaActualizar) {
		this.listaGvEntradaSalidaActualizar = listaGvEntradaSalidaActualizar;
	}

	public GvCaja getGvCaja() {
		return gvCaja;
	}

	public void setGvCaja(GvCaja gvCaja) {
		this.gvCaja = gvCaja;
	}

	public GvEntradaSalida getGvEntradaSalida() {
		return gvEntradaSalida;
	}

	public void setGvEntradaSalida(GvEntradaSalida gvEntradaSalida) {
		this.gvEntradaSalida = gvEntradaSalida;
	}

	public List<GvProveedor> getListaGvProveedorPuntoVenta() {
		return listaGvProveedorPuntoVenta;
	}

	public void setListaGvProveedorPuntoVenta(
			List<GvProveedor> listaGvProveedorPuntoVenta) {
		this.listaGvProveedorPuntoVenta = listaGvProveedorPuntoVenta;
	}

	public Date getFechaRegistra() {
		return fechaRegistra;
	}

	public void setFechaRegistra(Date fechaRegistra) {
		this.fechaRegistra = fechaRegistra;
	}

	public GvCaja getGvCajaUltimoRegistro() {
		return gvCajaUltimoRegistro;
	}

	public void setGvCajaUltimoRegistro(GvCaja gvCajaUltimoRegistro) {
		this.gvCajaUltimoRegistro = gvCajaUltimoRegistro;
	}

	public String getFechaRegistraString() {
		return fechaRegistraString;
	}

	public void setFechaRegistraString(String fechaRegistraString) {
		this.fechaRegistraString = fechaRegistraString;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Integer getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public List<GvEntradaSalida> getListaGvEntradaSalidaEliminar() {
		return listaGvEntradaSalidaEliminar;
	}

	public void setListaGvEntradaSalidaEliminar(
			List<GvEntradaSalida> listaGvEntradaSalidaEliminar) {
		this.listaGvEntradaSalidaEliminar = listaGvEntradaSalidaEliminar;
	}
}