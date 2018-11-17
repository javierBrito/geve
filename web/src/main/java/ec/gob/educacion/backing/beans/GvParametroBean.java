package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;
import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvParametro;
import ec.gob.educacion.service.GvParametroService;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@ViewScoped
public class GvParametroBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvParametroService gvParametroService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvParametro gvParametro;
	private List<GvParametro> listaGvParametro;
	
	private String mensaje;
	private String codProceso;

	@PostConstruct
	public void init() {
		codProceso = "";
		gvParametro = new GvParametro();
		listaGvParametro = new ArrayList<GvParametro>();
		mensaje = "";
	}
	
	public void buscarListaParametros() {
		listaGvParametro = new ArrayList<GvParametro>();
		listaGvParametro = gvParametroService.buscarGvParametros();
	}

	public void nuevo() {
		gvParametro = new GvParametro();
		codProceso = "N";
		mensaje = "";
	}

	public void editar(GvParametro gvParametroAux) {
		codProceso = "E";
		gvParametro = gvParametroAux;
		mensaje = "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void guardar() {
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvParametro.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			gvParametro.setNombre(gvParametro.getNombre().toUpperCase());
			gvParametro.setDescripcion(gvParametro.getDescripcion().toUpperCase());
			gvParametroService.guardarGvParametro(gvParametro);
			if (codProceso == "N") {
				mensaje = "Se creo el registro...";
			} else {
				mensaje = "Se actualizó el registro...";
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "Error: No se actualizaron los datos...";
			else
				mensaje = (new StringBuilder()).append("Error: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "N") {
			gvParametro.setEstado(1);
			gvParametro.setFechaRegistra(new Date());
			gvParametro.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvParametro.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvParametro.setFechaActualiza(new Date());
			gvParametro.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvParametro.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}

	//Ejecutar procesos de acuerdo a lo solicitado en el OneMenu.
	public void procesoAplicaIva(ValueChangeEvent evt){
		/*socializadasRutas = (Integer)evt.getNewValue();
		if (socializadasRutas == 1) {
		} else {
		}*/
	}

	public List<GvParametro> getListaGvParametro() {
		return listaGvParametro;
	}

	public void setListaGvParametro(List<GvParametro> listaGvParametro) {
		this.listaGvParametro = listaGvParametro;
	}

	public GvParametro getGvParametro() {
		return gvParametro;
	}

	public void setGvParametro(GvParametro gvParametro) {
		this.gvParametro = gvParametro;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}
}