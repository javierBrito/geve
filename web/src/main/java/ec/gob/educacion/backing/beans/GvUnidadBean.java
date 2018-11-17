package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvUnidad;
import ec.gob.educacion.service.GvUnidadService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvUnidadBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvUnidad gvUnidad;
	private String mensaje;
	private String codProceso;
	private List<GvUnidad> listaGvUnidades;
	@EJB
	private GvUnidadService gvUnidadService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvUnidad = new GvUnidad();
		listaGvUnidades = new ArrayList<GvUnidad>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvUnidades = new ArrayList<GvUnidad>();
		listaGvUnidades = gvUnidadService.buscarGvUnidades();
	}

	public void nuevo() {
		gvUnidad = new GvUnidad();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvUnidad gvUnidad) {
		codProceso = "E";
		this.gvUnidad = gvUnidad;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvUnidad.setDescripcion(gvUnidad.getDescripcion());
			if (codProceso == "N") {
				gvUnidad.setActivo(1);
			}
			if (codProceso == "E") {
			}
			gvUnidadService.guardarGvUnidad(gvUnidad);
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

	public List<GvUnidad> getListaUnidades() {
		return listaGvUnidades;
	}

	public void setListaGvUnidades(List<GvUnidad> listaGvUnidades) {
		this.listaGvUnidades = listaGvUnidades;
	}

	public GvUnidad getGvUnidad() {
		return gvUnidad;
	}

	public void setGvUnidad(GvUnidad gvUnidad) {
		this.gvUnidad = gvUnidad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}