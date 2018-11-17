package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvClase;
import ec.gob.educacion.service.GvClaseService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvClaseBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvClase gvClase;
	private String mensaje;
	private String codProceso;
	private List<GvClase> listaGvClases;
	@EJB
	private GvClaseService gvClaseService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvClase = new GvClase();
		listaGvClases = new ArrayList<GvClase>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvClases = new ArrayList<GvClase>();
		listaGvClases = gvClaseService.buscarGvClases();
	}

	public void nuevo() {
		gvClase = new GvClase();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvClase gvClase) {
		codProceso = "E";
		this.gvClase = gvClase;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvClase.setDescripcion(gvClase.getDescripcion());
			if (codProceso == "N") {
				gvClase.setActivo(1);
			}
			if (codProceso == "E") {
			}
			gvClaseService.guardarGvClase(gvClase);
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

	public List<GvClase> getListaClases() {
		return listaGvClases;
	}

	public void setListaGvClases(List<GvClase> listaGvClases) {
		this.listaGvClases = listaGvClases;
	}

	public GvClase getGvClase() {
		return gvClase;
	}

	public void setGvClase(GvClase gvClase) {
		this.gvClase = gvClase;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}