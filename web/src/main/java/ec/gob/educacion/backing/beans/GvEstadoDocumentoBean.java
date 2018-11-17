package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvEstadoDocumento;
import ec.gob.educacion.service.GvEstadoDocumentoService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvEstadoDocumentoBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvEstadoDocumento gvEstadoDocumento;
	private String mensaje;
	private String codProceso;
	private List<GvEstadoDocumento> listaGvEstadoDocumentos;
	@EJB
	private GvEstadoDocumentoService gvEstadoDocumentoService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvEstadoDocumento = new GvEstadoDocumento();
		listaGvEstadoDocumentos = new ArrayList<GvEstadoDocumento>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvEstadoDocumentos = new ArrayList<GvEstadoDocumento>();
		listaGvEstadoDocumentos = gvEstadoDocumentoService.buscarGvEstadoDocumentos();
	}

	public void nuevo() {
		gvEstadoDocumento = new GvEstadoDocumento();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvEstadoDocumento gvEstadoDocumento) {
		codProceso = "E";
		this.gvEstadoDocumento = gvEstadoDocumento;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvEstadoDocumento.setDescripcion(gvEstadoDocumento.getDescripcion());
			if (codProceso == "N") {
				gvEstadoDocumento.setActivo(1);
			}
			if (codProceso == "E") {
			}
			gvEstadoDocumentoService.guardarGvEstadoDocumento(gvEstadoDocumento);
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

	public List<GvEstadoDocumento> getListaEstadoDocumentos() {
		return listaGvEstadoDocumentos;
	}

	public void setListaGvEstadoDocumentos(List<GvEstadoDocumento> listaGvEstadoDocumentos) {
		this.listaGvEstadoDocumentos = listaGvEstadoDocumentos;
	}

	public GvEstadoDocumento getGvEstadoDocumento() {
		return gvEstadoDocumento;
	}

	public void setGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) {
		this.gvEstadoDocumento = gvEstadoDocumento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}