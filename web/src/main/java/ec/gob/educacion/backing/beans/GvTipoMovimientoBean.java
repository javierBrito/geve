package ec.gob.educacion.backing.beans;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.model.geve.GvTipoMovimiento;
import ec.gob.educacion.service.GvTipoMovimientoService;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class GvTipoMovimientoBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;
	private GvTipoMovimiento gvTipoMovimiento;
	private String mensaje;
	private String codProceso;
	private List<GvTipoMovimiento> listaGvTipoMovimientos;
	@EJB
	private GvTipoMovimientoService gvTipoMovimientoService;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvTipoMovimiento = new GvTipoMovimiento();
		listaGvTipoMovimientos = new ArrayList<GvTipoMovimiento>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	}

	public void buscar() {
		listaGvTipoMovimientos = new ArrayList<GvTipoMovimiento>();
		listaGvTipoMovimientos = gvTipoMovimientoService.buscarGvTipoMovimientos();
	}

	public void nuevo() {
		gvTipoMovimiento = new GvTipoMovimiento();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvTipoMovimiento gvTipoMovimiento) {
		codProceso = "E";
		this.gvTipoMovimiento = gvTipoMovimiento;
		mensaje = "";
	}

	public void guardar() {
		try {
			gvTipoMovimiento.setDescripcion(gvTipoMovimiento.getDescripcion());
			if (codProceso == "N") {
				gvTipoMovimiento.setEstado(1);
			}
			if (codProceso == "E") {
			}
			gvTipoMovimientoService.guardarGvTipoMovimiento(gvTipoMovimiento);
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

	public List<GvTipoMovimiento> getListaTipoMovimientos() {
		return listaGvTipoMovimientos;
	}

	public void setListaGvTipoMovimientos(List<GvTipoMovimiento> listaGvTipoMovimientos) {
		this.listaGvTipoMovimientos = listaGvTipoMovimientos;
	}

	public GvTipoMovimiento getGvTipoMovimiento() {
		return gvTipoMovimiento;
	}

	public void setGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) {
		this.gvTipoMovimiento = gvTipoMovimiento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}