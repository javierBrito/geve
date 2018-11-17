package ec.gob.educacion.backing.beans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvParametro;
import ec.gob.educacion.seguridades.exception.RolAplicacionException;
import ec.gob.educacion.seguridades.model.RolAplicacion;
import ec.gob.educacion.seguridades.model.Sede;
import ec.gob.educacion.service.GvParametroService;

@Named
@ViewScoped
public class CasosBean extends BaseController implements Serializable {
	
	private static final long serialVersionUID = 3463016173694116536L;
    @Inject
    private SessionController sesionControlador;
    @Inject
    private GvParametroService gvParametroService;
	
    private int opcionBusqueda;
    private int cargasDisponibles = 1;
    private boolean cargaAutomatica = false;
    private boolean usarFlash = false;
    private boolean archivoSinErrores;
    private String tiposAceptados;
    private String mensaje;
    private String nombreArchivo;
    private String nombreBuscar;
    private String apellidoBuscar;
    private String identificacionBuscar;
    
    private GvParametro gvParametro = new GvParametro();
    private List<GvParametro> parametros;
    
	@PostConstruct
	public void init() {
		opcionBusqueda=0;
        mensaje = "";
        archivoSinErrores = false;
        nombreArchivo = "";
        parametros = new ArrayList<GvParametro>();
        parametros = gvParametroService.buscarGvParametros();
	}

	//Carga masiva Parametros (jbrito-20151029)
	public void mostrarSedeSeleccionada() {
	}

    //Grabar datos desde archivo excel. (jbrito-20151106)
    public void accionGuardar(){
    	if (!archivoSinErrores || nombreArchivo.equals("")) {
            mensaje = "Archivo con errores: ("+nombreArchivo+")\n";
            return;
    	}
        mensaje = "Archivo sin errores: ("+nombreArchivo+")\n";

    }
	
	public void buscar() {
		//listaUsuarios=new ArrayList<Usuario>();				
		//if(codigosSedes.isEmpty()) {
			if((nombreBuscar != null && !nombreBuscar.equals("")) ||
			   (apellidoBuscar != null && !apellidoBuscar.equals(""))){				
				//listaUsuarios=usuarioService.buscarUsuarioNombre(nombreBuscar, apellidoBuscar);
			}
			
		//} else {
			if(nombreBuscar != null && !nombreBuscar.equals("")){	
				//listaUsuarios = usuarioService.obtenerPorNombreYSedes(nombreBuscar, codigosSedes, false);
			}
		//}
	}
	
	public void buscarPorId(){
		//listaUsuarios=new ArrayList<Usuario>();	
		if(identificacionBuscar != null && !identificacionBuscar.equals("")) {
			/*if(codigosSedes.isEmpty()) {
				usuarioSeleccionado = usuarioService.buscarUsuarioId(identificacionBuscar);
				if(usuarioSeleccionado != null){
					listaUsuarios.add(usuarioSeleccionado);
				}
			}*/
		}		
	}
	
	public void nuevoParametro() {
		//nuevoUsuario = new Usuario();
	}
	
	public void seleccionarParametro(GvParametro usuario) {
		//this.usuarioSeleccionado = usuario;
	}
	
	public void actualizar() {
        try {
        	//log.info("Esta ingresando ha actualizar");
        	//usuarioService.actualizar(usuarioSeleccionado);
            agregarMensajeInformacion("Registro actualizado exitosamente", "");
            init();
        } catch (Exception e) {
           agregarMensajeError("Error al actualizar registro", e.getMessage());
        }
    }
	
	public void editarParametro() {
		System.out.println("editarUsuario() I");
		//Grabar archivo temporal
		//pathImagen = grabarArchivoTemporal(usuarioSeleccionado.getImagen(), usuarioSeleccionado.getNombreImagen());
		System.out.println("editarUsuario() F");
	}
	
	public void eliminar() {
        try {
        	//log.info("Esta ingresando a eliminar");
        	//usuarioService.desactivar(usuarioSeleccionado);
            agregarMensajeInformacion("Registro desactivado exitosamente", "");
            init();
        } catch (Exception e) {
           agregarMensajeError("Error al eliminar registro", e.getMessage());
        }
    }
	
	public void activar() {
        try {
        	//log.info("Esta ingresando a activar");
        	//usuarioService.activar(usuarioSeleccionado);
            agregarMensajeInformacion("Registro activado exitosamente", "");
            init();
        } catch (Exception e) {
           agregarMensajeError("Error al eliminar registro", e.getMessage());
        }
    }
	
	public void seleccionarAplicacion(AjaxBehaviorEvent evt) throws RolAplicacionException {
		//roles = rolAplicacionService.obtenerConAsignacionPorAplicacion(aplicacion, usuarioSeleccionado);
	}
	
	public void inicializarRoles() {
		//aplicacion =  new Aplicacion();
		//roles =  new ArrayList<RolAplicacion>();
	}
	
	public void asignarRol(RolAplicacion rol) {
		try {
			//rolAplicacionService.asignar(usuarioSeleccionado, rol);
		} catch (Exception e) {
			agregarMensajeError(e);
		}
	}

	public void seleccionarSede(Sede sede) {
		try {
			//usuarioService.asignarSede(usuarioSeleccionado, sede);
			agregarMensajeInformacion("El usuario ha sido asignado correctamente a la sede", "");
			init();
		} catch (Exception e) {
			agregarMensajeError(e.getMessage(), "");
		}
	}
	
	public void crearParametro() {
		
	}

	public void listarAplicaciones() {
		
	}
	
	
	public void listarSedes() {
		
	}
	
	public void limpiarVariablesBusqueda(){
		nombreBuscar="";
	    apellidoBuscar="";
	    identificacionBuscar="";
	}

	public String getNombreBuscar() {
		return nombreBuscar;
	}

	public void setNombreBuscar(String nombreBuscar) {
		this.nombreBuscar = nombreBuscar;
	}

	public String getApellidoBuscar() {
		return apellidoBuscar;
	}

	public void setApellidoBuscar(String apellidoBuscar) {
		this.apellidoBuscar = apellidoBuscar;
	}

	public String getIdentificacionBuscar() {
		return identificacionBuscar;
	}

	public void setIdentificacionBuscar(String identificacionBuscar) {
		this.identificacionBuscar = identificacionBuscar;
	}

	public int getOpcionBusqueda() {
		return opcionBusqueda;
	}

	public void setOpcionBusqueda(int opcionBusqueda) {
		this.opcionBusqueda = opcionBusqueda;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public int getCargasDisponibles() {
		return cargasDisponibles;
	}

	public void setCargasDisponibles(int cargasDisponibles) {
		this.cargasDisponibles = cargasDisponibles;
	}

	public boolean isCargaAutomatica() {
		return cargaAutomatica;
	}

	public void setCargaAutomatica(boolean cargaAutomatica) {
		this.cargaAutomatica = cargaAutomatica;
	}

	public String getTiposAceptados() {
		return tiposAceptados;
	}

	public void setTiposAceptados(String tiposAceptados) {
		this.tiposAceptados = tiposAceptados;
	}

	public boolean isUsarFlash() {
		return usarFlash;
	}

	public void setUsarFlash(boolean usarFlash) {
		this.usarFlash = usarFlash;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isArchivoSinErrores() {
		return archivoSinErrores;
	}

	public void setArchivoSinErrores(boolean archivoSinErrores) {
		this.archivoSinErrores = archivoSinErrores;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public List<GvParametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<GvParametro> parametros) {
		this.parametros = parametros;
	}

	public GvParametro getGvParametro() {
		return gvParametro;
	}

	public void setGvParametro(GvParametro gvParametro) {
		this.gvParametro = gvParametro;
	}

}