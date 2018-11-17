package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvProveedor;
import ec.gob.educacion.service.GvProveedorService;

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
public class GvProveedorBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvProveedorService gvProveedorService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvProveedor gvProveedor;
	private GvProveedor gvProveedorBuscar;
	private List<GvProveedor> listaGvProveedores;
	
	private String mensaje;
	private String codProceso;
    private String nomContactoBuscar;
    private String razonSocialBuscar;
    private String identificacionBuscar;
    private String nombreEmpresa;
	
    private int opcionBusqueda;

	@PostConstruct
	public void init() {
		opcionBusqueda=0;
		codProceso = "";
		gvProveedor = new GvProveedor();
		listaGvProveedores = new ArrayList<GvProveedor>();
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
		
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}
	
	public void limpiarVariablesBusqueda(){
		nomContactoBuscar = "";
	    razonSocialBuscar = "";
	    identificacionBuscar = "";
		listaGvProveedores = new ArrayList<GvProveedor>();
	}

	public void buscar() {
		gvProveedorBuscar = new GvProveedor();
		gvProveedorBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		listaGvProveedores = new ArrayList<GvProveedor>();
		listaGvProveedores = gvProveedorService.buscarGvProveedores(gvProveedorBuscar);
	}
	
	public void buscarPorDni() {
		listaGvProveedores = new ArrayList<GvProveedor>();
		if(identificacionBuscar != null && !identificacionBuscar.equals("")) {
			gvProveedor = gvProveedorService.buscarPorDni(identificacionBuscar);
			if (gvProveedor != null) {
				listaGvProveedores.add(gvProveedor);
			}
		}		
	}
	
	public void buscarPorNombres() {
		listaGvProveedores = new ArrayList<GvProveedor>();
		gvProveedorBuscar = new GvProveedor();
		gvProveedorBuscar.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
		if ((nomContactoBuscar != null && !nomContactoBuscar.equals("")) || 
			(razonSocialBuscar != null && !razonSocialBuscar.equals(""))) {
			gvProveedorBuscar.setNomContacto(nomContactoBuscar);
			gvProveedorBuscar.setRazonSocial(razonSocialBuscar);
			listaGvProveedores = gvProveedorService.buscarListaPorNombres(gvProveedorBuscar);
		}
	}

	public void nuevo() {
		gvProveedor = new GvProveedor();
		
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvProveedor gvProveedor) {
		codProceso = "E";
		this.gvProveedor = gvProveedor;

		mensaje = "";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void guardar() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvProveedor.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}

		try {
			gvProveedor.setRazonSocial(gvProveedor.getRazonSocial().toUpperCase());
			gvProveedor.setNomContacto(gvProveedor.getNomContacto().toUpperCase());
			
			gvProveedorService.guardarGvProveedor(gvProveedor);
			mensaje = "DATOS ACTUALIZADOS CORRECTAMENTE";
			if (opcionBusqueda != 0) {
				if(identificacionBuscar != null && !identificacionBuscar.equals("")) {
					buscarPorDni();
				}

				if ((nomContactoBuscar != null && !nomContactoBuscar.equals("")) || 
					(razonSocialBuscar != null && !razonSocialBuscar.equals(""))) {
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
			gvProveedor.setActivo(1);
			gvProveedor.setFechaRegistra(new Date());
			gvProveedor.setIdEmpresa(sesionControlador.getUsuario().getSede().getCodigo().intValue());
			gvProveedor.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvProveedor.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvProveedor.setFechaActualiza(new Date());
			gvProveedor.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvProveedor.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	public List<GvProveedor> getListaGvProveedores() {
		return listaGvProveedores;
	}

	public void setListaGvProveedores(List<GvProveedor> listaGvProveedores) {
		this.listaGvProveedores = listaGvProveedores;
	}

	public GvProveedor getGvProveedor() {
		return gvProveedor;
	}

	public void setGvProveedor(GvProveedor gvProveedor) {
		this.gvProveedor = gvProveedor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getOpcionBusqueda() {
		return opcionBusqueda;
	}

	public void setOpcionBusqueda(int opcionBusqueda) {
		this.opcionBusqueda = opcionBusqueda;
	}

	public String getNomContactoBuscar() {
		return nomContactoBuscar;
	}

	public void setNomContactoBuscar(String nombresBuscar) {
		this.nomContactoBuscar = nombresBuscar;
	}

	public String getRazonSocialBuscar() {
		return razonSocialBuscar;
	}

	public void setRazonSocialBuscar(String razonSocialBuscar) {
		this.razonSocialBuscar = razonSocialBuscar;
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