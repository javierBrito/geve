package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvTipoCliente;
import ec.gob.educacion.service.GvTipoClienteService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvTipoClienteBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvTipoCliente gvTipoCliente;
	private String mensaje;
	private String codProceso;
	private List<GvTipoCliente> listaGvTipoClientes;
	@EJB
	private GvTipoClienteService gvTipoClienteService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvTipoCliente = new GvTipoCliente();
		listaGvTipoClientes = new ArrayList<GvTipoCliente>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvTipoClientes = new ArrayList<GvTipoCliente>();
		listaGvTipoClientes = gvTipoClienteService.buscarGvTipoClientes();
	}

	public void nuevo() {
		gvTipoCliente = new GvTipoCliente();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvTipoCliente gvTipoCliente) {
		codProceso = "E";
		this.gvTipoCliente = gvTipoCliente;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvTipoCliente.setDescripcion(gvTipoCliente.getDescripcion());
			if (codProceso == "N") {
				gvTipoCliente.setActivo(1);
			}
			if (codProceso == "E") {
			}
			gvTipoClienteService.guardarGvTipoCliente(gvTipoCliente);
			mensaje = "DATOS ACTUALIZADOS CORRECTAMENTE";
			buscar();
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "ERROR: NO SE ACTUALIZARON LOS DATOS";
			else
				mensaje = (new StringBuilder()).append("ERROR: ")
						.append(exc.getMessage()).toString();
		}
	}

	public List<GvTipoCliente> getListaTipoClientes() {
		return listaGvTipoClientes;
	}

	public void setListaGvTipoClientes(List<GvTipoCliente> listaGvTipoClientes) {
		this.listaGvTipoClientes = listaGvTipoClientes;
	}

	public GvTipoCliente getGvTipoCliente() {
		return gvTipoCliente;
	}

	public void setGvTipoCliente(GvTipoCliente gvTipoCliente) {
		this.gvTipoCliente = gvTipoCliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}