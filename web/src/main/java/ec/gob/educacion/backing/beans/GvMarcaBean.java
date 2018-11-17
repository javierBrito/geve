package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvMarca;
import ec.gob.educacion.service.GvMarcaService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvMarcaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvMarca gvMarca;
	private String mensaje;
	private String codProceso;
	private List<GvMarca> listaGvMarcas;
	@EJB
	private GvMarcaService gvMarcaService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvMarca = new GvMarca();
		listaGvMarcas = new ArrayList<GvMarca>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvMarcas = new ArrayList<GvMarca>();
		listaGvMarcas = gvMarcaService.buscarGvMarcas();
	}

	public void nuevo() {
		gvMarca = new GvMarca();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvMarca gvMarca) {
		codProceso = "E";
		this.gvMarca = gvMarca;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvMarca.setDescripcion(gvMarca.getDescripcion());
			if (codProceso == "N") {
				gvMarca.setActivo(1);
			}
			if (codProceso == "E") {
			}
			gvMarcaService.guardarGvMarca(gvMarca);
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

	public List<GvMarca> getListaMarcas() {
		return listaGvMarcas;
	}

	public void setListaGvMarcas(List<GvMarca> listaGvMarcas) {
		this.listaGvMarcas = listaGvMarcas;
	}

	public GvMarca getGvMarca() {
		return gvMarca;
	}

	public void setGvMarca(GvMarca gvMarca) {
		this.gvMarca = gvMarca;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}