package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvCliente;
import ec.gob.educacion.model.geve.GvTipoCliente;
import ec.gob.educacion.service.GvClienteService;
import ec.gob.educacion.service.GvTipoClienteService;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvClienteBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvClienteService gvClienteService;
	@EJB
	private GvTipoClienteService gvTipoClienteService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvCliente gvCliente;
	private GvCliente gvClienteBuscar;
	private GvTipoCliente gvTipoCliente;
	private List<GvCliente> listaGvClientes;
	private List<GvTipoCliente> listaGvTipoCliente;
	
	private String mensaje;
	private String codProceso;
    private String nombresBuscar;
    private String apellidosBuscar;
    private String identificacionBuscar;
    private String nombreEmpresa;
	
    private int opcionBusqueda;

	@PostConstruct
	public void init() {
		opcionBusqueda=0;
		codProceso = "";
		gvCliente = new GvCliente();
		gvTipoCliente = new GvTipoCliente();
		listaGvClientes = new ArrayList<GvCliente>();
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
		
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		listaGvTipoCliente = gvTipoClienteService.buscarGvTipoClientes();
	}
	
	public void limpiarVariablesBusqueda(){
		setNombresBuscar("");
	    setApellidosBuscar("");
	    setIdentificacionBuscar("");
		listaGvClientes = new ArrayList<GvCliente>();
	}

	public void buscar() {
		gvClienteBuscar = new GvCliente();
		gvClienteBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvClientes = new ArrayList<GvCliente>();
		listaGvClientes = gvClienteService.buscarGvClientes(gvClienteBuscar);
	}
	
	public void buscarPorDni() {
		listaGvClientes = new ArrayList<GvCliente>();
		if(identificacionBuscar != null && !identificacionBuscar.equals("")) {
			gvCliente = gvClienteService.buscarPorDni(identificacionBuscar);
			if (gvCliente != null) {
				listaGvClientes.add(gvCliente);
			}
		}		
	}
	
	public void buscarPorNombres() {
		listaGvClientes = new ArrayList<GvCliente>();
		gvClienteBuscar = new GvCliente();
		gvClienteBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		if ((nombresBuscar != null && !nombresBuscar.equals("")) || 
			(apellidosBuscar != null && !apellidosBuscar.equals(""))) {
			gvClienteBuscar.setNombres(nombresBuscar);
			gvClienteBuscar.setApellidos(apellidosBuscar);
			listaGvClientes = gvClienteService.buscarListaPorNombres(gvClienteBuscar);
		}
	}

	public void nuevo() {
		gvCliente = new GvCliente();
		gvTipoCliente = new GvTipoCliente();
		listaGvTipoCliente = gvTipoClienteService.buscarGvTipoClientes();
		
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvCliente gvCliente) {
		codProceso = "E";
		this.gvCliente = gvCliente;
		if (this.gvCliente.getGvTipoCliente() != null) {
			gvTipoCliente = this.gvCliente.getGvTipoCliente();
			listaGvTipoCliente = gvTipoClienteService.buscarGvTipoClientes();
		}
		mensaje = "";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void guardar() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvCliente.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}

		try {
			gvCliente.setApellidos(gvCliente.getApellidos().toUpperCase());
			gvCliente.setNombres(gvCliente.getNombres().toUpperCase());
			gvCliente.setGvTipoCliente(gvTipoCliente);
			
			gvClienteService.guardarGvCliente(gvCliente);
			mensaje = "DATOS ACTUALIZADOS CORRECTAMENTE";
			if (opcionBusqueda != 0) {
				if(identificacionBuscar != null && !identificacionBuscar.equals("")) {
					buscarPorDni();
				}

				if ((nombresBuscar != null && !nombresBuscar.equals("")) || 
					(apellidosBuscar != null && !apellidosBuscar.equals(""))) {
					buscarPorNombres();
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "ERROR: NO SE ACTUALIZARON LOS DATOS";
			else
				mensaje = (new StringBuilder()).append("ERROR: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "N") {
			gvCliente.setActivo(1);
			gvCliente.setFechaRegistra(new Date());
			gvCliente.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			gvCliente.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvCliente.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvCliente.setFechaActualiza(new Date());
			gvCliente.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvCliente.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	public List<GvCliente> getListaGvClientes() {
		return listaGvClientes;
	}

	public void setListaGvClientes(List<GvCliente> listaGvClientes) {
		this.listaGvClientes = listaGvClientes;
	}

	public GvCliente getGvCliente() {
		return gvCliente;
	}

	public void setGvCliente(GvCliente gvCliente) {
		this.gvCliente = gvCliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<GvTipoCliente> getListaGvTipoCliente() {
		return listaGvTipoCliente;
	}

	public void setListaGvTipoCliente(List<GvTipoCliente> listaGvTipoCliente) {
		this.listaGvTipoCliente = listaGvTipoCliente;
	}

	public GvTipoCliente getGvTipoCliente() {
		return gvTipoCliente;
	}

	public void setGvTipoCliente(GvTipoCliente gvTipoCliente) {
		this.gvTipoCliente = gvTipoCliente;
	}

	public int getOpcionBusqueda() {
		return opcionBusqueda;
	}

	public void setOpcionBusqueda(int opcionBusqueda) {
		this.opcionBusqueda = opcionBusqueda;
	}

	public String getNombresBuscar() {
		return nombresBuscar;
	}

	public void setNombresBuscar(String nombresBuscar) {
		this.nombresBuscar = nombresBuscar;
	}

	public String getApellidosBuscar() {
		return apellidosBuscar;
	}

	public void setApellidosBuscar(String apellidosBuscar) {
		this.apellidosBuscar = apellidosBuscar;
	}

	public String getIdentificacionBuscar() {
		return identificacionBuscar;
	}

	public void setIdentificacionBuscar(String identificacionBuscar) {
		this.identificacionBuscar = identificacionBuscar;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
}