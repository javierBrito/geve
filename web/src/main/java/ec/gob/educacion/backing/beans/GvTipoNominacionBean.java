package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvTipoNominacion;
import ec.gob.educacion.service.GvTipoNominacionService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvTipoNominacionBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvTipoNominacion gvTipoNominacion;
	private String mensaje;
	private String codProceso;
	private List<GvTipoNominacion> listaGvTipoNominacion;
	@EJB
	private GvTipoNominacionService gvTipoNominacionService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvTipoNominacion = new GvTipoNominacion();
		setListaGvTipoNominacion(new ArrayList<GvTipoNominacion>());
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		setListaGvTipoNominacion(new ArrayList<GvTipoNominacion>());
		setListaGvTipoNominacion(gvTipoNominacionService.buscarGvTipoNominaciones());
	}

	public void nuevo() {
		gvTipoNominacion = new GvTipoNominacion();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvTipoNominacion gvTipoNominacion) {
		codProceso = "E";
		this.gvTipoNominacion = gvTipoNominacion;
		mensaje = "";
	}

	public void guardar() {
		try {
			if (codProceso == "N") {
				gvTipoNominacion.setEstado(1);
			}
			if (codProceso == "E") {
			}
			gvTipoNominacionService.guardarGvTipoNominacion(gvTipoNominacion);
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

	public GvTipoNominacion getGvTipoNominacion() {
		return gvTipoNominacion;
	}

	public void setGvTipoNominacion(GvTipoNominacion gvTipoNominacion) {
		this.gvTipoNominacion = gvTipoNominacion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<GvTipoNominacion> getListaGvTipoNominacion() {
		return listaGvTipoNominacion;
	}

	public void setListaGvTipoNominacion(List<GvTipoNominacion> listaGvTipoNominacion) {
		this.listaGvTipoNominacion = listaGvTipoNominacion;
	}
}