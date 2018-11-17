package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvTipoDocumento;
import ec.gob.educacion.service.GvTipoDocumentoService;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvTipoDocumentoBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvTipoDocumento gvTipoDocumento;
	private String mensaje;
	private String codProceso;
	private List<GvTipoDocumento> listaGvTipoDocumentos;
	@EJB
	private GvTipoDocumentoService gvTipoDocumentoService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvTipoDocumento = new GvTipoDocumento();
		listaGvTipoDocumentos = new ArrayList<GvTipoDocumento>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvTipoDocumentos = new ArrayList<GvTipoDocumento>();
		listaGvTipoDocumentos = gvTipoDocumentoService.buscarGvTipoDocumentos();
	}

	public void nuevo() {
		gvTipoDocumento = new GvTipoDocumento();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvTipoDocumento gvTipoDocumento) {
		codProceso = "E";
		this.gvTipoDocumento = gvTipoDocumento;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvTipoDocumento.setDescripcion(gvTipoDocumento.getDescripcion());
			if (codProceso == "N") {
				gvTipoDocumento.setEstado(1);
			}
			if (codProceso == "E") {
			}
			gvTipoDocumentoService.guardarGvTipoDocumento(gvTipoDocumento);
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

	public List<GvTipoDocumento> getListaTipoDocumentos() {
		return listaGvTipoDocumentos;
	}

	public void setListaGvTipoDocumentos(List<GvTipoDocumento> listaGvTipoDocumentos) {
		this.listaGvTipoDocumentos = listaGvTipoDocumentos;
	}

	public GvTipoDocumento getGvTipoDocumento() {
		return gvTipoDocumento;
	}

	public void setGvTipoDocumento(GvTipoDocumento gvTipoDocumento) {
		this.gvTipoDocumento = gvTipoDocumento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}