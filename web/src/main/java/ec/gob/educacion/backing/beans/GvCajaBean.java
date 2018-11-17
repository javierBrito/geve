package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvCajaDetalle;
import ec.gob.educacion.model.geve.GvTipoNominacion;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.service.GvCajaDetalleService;
import ec.gob.educacion.service.GvKardexService;
import ec.gob.educacion.service.GvTipoNominacionService;
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
public class GvCajaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvCajaService gvCajaService;
	@EJB
	private GvCajaDetalleService gvCajaDetalleService;
	@EJB
	private GvTipoNominacionService gvTipoNominacionService;
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

	private GvCajaDetalle gvCajaDetalle;
	private List<GvCajaDetalle> listaGvCajaDetalleCrear;
	private List<GvCajaDetalle> listaGvCajaDetalleActualizar;

	private GvTipoNominacion gvTipoNominacion;
	private List<GvTipoNominacion> listaGvTipoNominacion;
	private List<GvTipoNominacion> listaGvTipoNominacionPuntoVenta;
	
	private String mensaje;
	private String mensajeCaja;
	private String codProceso;
	private String fechaRegistraString;
	private String nombreEmpresa;

	private Date fechaRegistra;

	@PostConstruct
	public void init() {
		codProceso = "N";
		mensaje = "";
		mensajeCaja = "";

		gvCajaCrear = new GvCaja();
		gvCajaActualizar = new GvCaja();
		gvCajaBuscar = new GvCaja();
		listaGvCaja = new ArrayList<GvCaja>();
		
		gvCajaDetalle = new GvCajaDetalle();
		listaGvCajaDetalleCrear = new ArrayList<GvCajaDetalle>();
		listaGvCajaDetalleActualizar = new ArrayList<GvCajaDetalle>();
		
		gvTipoNominacion = new GvTipoNominacion();
		gvTipoNominacion.setIdTipoNominacion(0);
		listaGvTipoNominacion = new ArrayList<GvTipoNominacion>();
		listaGvTipoNominacion = gvTipoNominacionService.buscarGvTipoNominaciones();

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
		listaGvCajaDetalleCrear = new ArrayList<GvCajaDetalle>();
		
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
		
		cargarNominaciones();
		
		mensaje = "";
		mensajeCaja = "";
	}
	
	//Cargar todas las nominaciones desde la tabla.
	public void cargarNominaciones() {
		if (listaGvTipoNominacion.size() > 0) {
			for (GvTipoNominacion gvTipoNominacionAux : listaGvTipoNominacion) {
				if (gvTipoNominacionAux.getIdTipoNominacion() != 0) {
					gvCajaDetalle = new GvCajaDetalle();
					gvCajaDetalle.setGvTipoNominacion(gvTipoNominacionAux);
					listaGvCajaDetalleCrear.add(gvCajaDetalle);
				}
			}
		}
	}

	//Guardar datos del TipoNominacion de la ocurrencia actual.
	public void guardarTipoNominacion(GvCajaDetalle gvCajaDetalleAux, Integer index) {
		mensaje = "";

		GvCaja gvCajaAux = new GvCaja();
		List<GvCajaDetalle> listaGvCajaDetalleAux = new ArrayList<GvCajaDetalle>();
		if (codProceso == "N") {
			gvCajaAux = gvCajaCrear;
			listaGvCajaDetalleAux = listaGvCajaDetalleCrear;
		} else {
			gvCajaAux = gvCajaActualizar;
			listaGvCajaDetalleAux = listaGvCajaDetalleActualizar;
		}

		int indiceLista =  index + 1; 
		if (gvCajaDetalleAux.getGvTipoNominacion().getIdTipoNominacion() == 0) {
			mensaje = "Error: Ingresar tipo nominación... indice="+indiceLista;
			return;
		}
		if (gvCajaDetalleAux.getNumCantidad() == 0) {
			mensaje = "Error: Ingresar cantidad de la nominación... indice="+indiceLista;
			return;
		}
		
		if (gvCajaAux.getIdCaja() != 0) {
			gvCajaDetalleAux.setGvCaja(gvCajaAux);
		}
		//Asignar estado activo al registro.
		gvCajaDetalleAux.setEstado(1);
		//Actualizar datos en la lista según indice escogido.
		gvCajaDetalleAux.setMntImporte(redondearDecimales((gvCajaDetalleAux.getNumCantidad()  * gvCajaDetalleAux.getGvTipoNominacion().getValor()), 2));
		listaGvCajaDetalleAux.set(index, gvCajaDetalleAux);
		
		//Inicializar datos de la caja para luego actualizarlos.
		gvCajaAux.setMntCaja(0);
		
		//Actualizar datos del tipo nominación desde la lista de caja detalle.
		for (GvCajaDetalle gvCajaDetalleAux1 : listaGvCajaDetalleAux) {
			if (gvCajaDetalleAux1.getGvTipoNominacion().getIdTipoNominacion() != 0 &&
				gvCajaDetalleAux1.getNumCantidad() != 0) {
				gvCajaAux.setMntCaja(gvCajaAux.getMntCaja() + gvCajaDetalleAux1.getMntImporte());
			}
		}

		//Realizar operación para obtener la diferencia.
		gvCajaAux.setMntDiferencia(redondearDecimales((gvCajaAux.getMntCaja() - gvCajaAux.getMntLibro()), 2));

		if (codProceso == "N") {
			gvCajaCrear = gvCajaAux;
			listaGvCajaDetalleCrear = listaGvCajaDetalleAux;
			mensaje = "Se ha guardado el tipo de nominación "+indiceLista+" ...";
		} else {
			gvCajaActualizar = gvCajaAux;
			listaGvCajaDetalleActualizar = listaGvCajaDetalleAux;
			mensaje = "Se ha actualizado el tipo de nominación "+indiceLista+" ...";
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

	//Eliminar un TipoNominacion u ocurrencia.
	public void eliminarTipoNominacion(GvCajaDetalle gvCajaDetalleAux, Integer index) {

		GvCaja gvCajaAux = new GvCaja();
		List<GvCajaDetalle> listaGvCajaDetalleAux = new ArrayList<GvCajaDetalle>();
		if (codProceso == "N") {
			gvCajaAux = gvCajaCrear;
			listaGvCajaDetalleAux = listaGvCajaDetalleCrear;
		} else {
			gvCajaAux = gvCajaActualizar;
			listaGvCajaDetalleAux = listaGvCajaDetalleActualizar;
		}

		// Restar el importe del registro a la caja.
		gvCajaAux.setMntCaja(gvCajaAux.getMntCaja() - gvCajaDetalleAux.getMntImporte());
		
		// Actualizar en cero los datos del registro.
		gvCajaDetalleAux.setNumCantidad(0);
		gvCajaDetalleAux.setMntImporte(0);;
		listaGvCajaDetalleAux.set(index, gvCajaDetalleAux);
		
		if (codProceso == "N") {
			gvCajaCrear = gvCajaAux;
			listaGvCajaDetalleCrear = listaGvCajaDetalleAux;
		} else {
			gvCajaActualizar = gvCajaAux;
			listaGvCajaDetalleActualizar = listaGvCajaDetalleAux;
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
				if (listaGvCajaDetalleCrear.size() > 1) {
					//Crear tipo nominación con persist.
					gvCajaService.crearGvCaja(gvCajaCrear);
					
					//Grabar GvCajaDetalle desde listaGvCajaDetalleCrear.
					for (GvCajaDetalle gvCajaDetalleAux : listaGvCajaDetalleCrear) {
						//Asignar estado activo al registro.
						gvCajaDetalleAux.setEstado(1);
						//Actualizar Caja Detalles
						gvCajaDetalleAux.setGvCaja(gvCajaCrear);
						gvCajaDetalleService.crearGvCajaDetalle(gvCajaDetalleAux);
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
		listaGvCajaDetalleActualizar = new ArrayList<GvCajaDetalle>();
		listaGvCajaDetalleActualizar = gvCajaDetalleService.buscarListaPorCaja(gvCajaActualizar);

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
				if (listaGvCajaDetalleActualizar.size() > 1) {
					//Grabar GvCajaDetalle desde listaGvCajaDetalleActualizar.
					for (GvCajaDetalle gvCajaDetalleAux : listaGvCajaDetalleActualizar) {
						//Actualizar Caja Detalles
						gvCajaDetalleAux.setGvCaja(gvCajaActualizar);
						gvCajaDetalleService.actualizarGvCajaDetalle(gvCajaDetalleAux);
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

	public GvTipoNominacion getGvTipoNominacion() {
		return gvTipoNominacion;
	}

	public void setGvTipoNominacion(GvTipoNominacion gvTipoNominacion) {
		this.gvTipoNominacion = gvTipoNominacion;
	}

	public List<GvTipoNominacion> getListaGvTipoNominacion() {
		return listaGvTipoNominacion;
	}

	public void setListaGvTipoNominacion(List<GvTipoNominacion> listaGvTipoNominacion) {
		this.listaGvTipoNominacion = listaGvTipoNominacion;
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

	public List<GvCajaDetalle> getListaGvCajaDetalleCrear() {
		return listaGvCajaDetalleCrear;
	}

	public void setListaGvCajaDetalleCrear(
			List<GvCajaDetalle> listaGvCajaDetalleCrear) {
		this.listaGvCajaDetalleCrear = listaGvCajaDetalleCrear;
	}

	public List<GvCajaDetalle> getListaGvCajaDetalleActualizar() {
		return listaGvCajaDetalleActualizar;
	}

	public void setListaGvCajaDetalleActualizar(
			List<GvCajaDetalle> listaGvCajaDetalleActualizar) {
		this.listaGvCajaDetalleActualizar = listaGvCajaDetalleActualizar;
	}

	public GvCaja getGvCaja() {
		return gvCaja;
	}

	public void setGvCaja(GvCaja gvCaja) {
		this.gvCaja = gvCaja;
	}

	public GvCajaDetalle getGvCajaDetalle() {
		return gvCajaDetalle;
	}

	public void setGvCajaDetalle(GvCajaDetalle gvCajaDetalle) {
		this.gvCajaDetalle = gvCajaDetalle;
	}

	public List<GvTipoNominacion> getListaGvTipoNominacionPuntoVenta() {
		return listaGvTipoNominacionPuntoVenta;
	}

	public void setListaGvTipoNominacionPuntoVenta(
			List<GvTipoNominacion> listaGvTipoNominacionPuntoVenta) {
		this.listaGvTipoNominacionPuntoVenta = listaGvTipoNominacionPuntoVenta;
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
}