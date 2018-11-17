package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.dao.GvPruebaDAO;
import ec.gob.educacion.model.geve.GvPrueba;

import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PruebaBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvPrueba gvPrueba;
	private String mensaje;
	private String codProceso;
	private List<GvPrueba> listaPruebas;
    @EJB
    private GvPruebaDAO gvPruebaDAO;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvPrueba = new GvPrueba();
		listaPruebas = new ArrayList<GvPrueba>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaPruebas = new ArrayList<GvPrueba>();
		listaPruebas = gvPruebaDAO.findAll();
	}

	public void nuevo() {
		gvPrueba = new GvPrueba();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvPrueba gvPrueba) {
		codProceso = "E";
		this.gvPrueba = gvPrueba;
		mensaje = "";
	}

	public void guardar() {
		try {
			//gvPrueba.setDescripcion(gvPrueba.getCodigon());
			if (codProceso == "N") {
				gvPruebaDAO.persist(gvPrueba);
			}
			if (codProceso == "E") {
				gvPruebaDAO.update(gvPrueba);
			}
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

	public List<GvPrueba> getListaPruebas() {
		return listaPruebas;
	}

	public void setListaPruebas(List<GvPrueba> listaPruebas) {
		this.listaPruebas = listaPruebas;
	}

	public GvPrueba getGvPrueba() {
		return gvPrueba;
	}

	public void setGvPrueba(GvPrueba gvPrueba) {
		this.gvPrueba = gvPrueba;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}