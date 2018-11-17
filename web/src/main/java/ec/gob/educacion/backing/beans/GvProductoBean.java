package ec.gob.educacion.backing.beans;

import java.lang.reflect.Method;

import ec.gob.educacion.controller.BaseController;
import ec.gob.educacion.controller.SessionController;
import ec.gob.educacion.model.geve.GvMarca;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.model.geve.GvClase;
import ec.gob.educacion.model.geve.GvUnidad;
import ec.gob.educacion.service.GvMarcaService;
import ec.gob.educacion.service.GvProductoService;
import ec.gob.educacion.service.GvClaseService;
import ec.gob.educacion.service.GvUnidadService;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class GvProductoBean extends BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GvProductoService gvProductoService;
	@EJB
	private GvClaseService gvClaseService;
	@EJB
	private GvMarcaService gvMarcaService;
	@EJB
	private GvUnidadService gvUnidadService;

	//para manipular el SessionController
    @ManagedProperty(value = "#{sessionController}")
    private SessionController sesionControlador;
	
	private GvProducto gvProducto;
	private GvProducto gvProductoBuscar;
	private List<GvProducto> listaGvProductos;
	private GvClase gvClase;
	private List<GvClase> listaGvClase;
	private GvMarca gvMarca;
	private List<GvMarca> listaGvMarca;
	private GvUnidad gvUnidad;
	private List<GvUnidad> listaGvUnidad;
	
	private String mensaje;
	private String codProceso;
    private String descripcionBuscar;
    private String codigoBuscar;
	private String pathImagen;
	private String nombreEmpresa;
	
	private int idEmpresa;
    private int opcionBusqueda;
    private Integer idMarcaBuscar;

	@PostConstruct
	public void init() {
		opcionBusqueda = 0;
		codProceso = "";
		gvProducto = new GvProducto();
		gvProductoBuscar = new GvProducto();
		listaGvProductos = new ArrayList<GvProducto>();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
	    setDescripcionBuscar("");
	    setCodigoBuscar("");
		
		nombreEmpresa = sesionControlador.getUsuario().getSede().getNombre();
		idEmpresa = sesionControlador.getUsuario().getSede().getCodigo().intValue();
	}
	
	public void limpiarVariablesBusqueda() {
	    setDescripcionBuscar("");
	    setCodigoBuscar("");
		gvProducto = new GvProducto();
		gvProductoBuscar = new GvProducto();
		listaGvProductos = new ArrayList<GvProducto>();
	}
	
	public void buscarPorCodigo() {
		listaGvProductos = new ArrayList<GvProducto>();
		if(codigoBuscar != null && !codigoBuscar.equals("")) {
			gvProducto = gvProductoService.buscarPorCodigo(idEmpresa, codigoBuscar);
			if (gvProducto != null) {
				listaGvProductos.add(gvProducto);
			}
		}		
		
		for (GvProducto gvProductoAux : listaGvProductos) {
			if (gvProductoAux.getImagen() != null) {
				pathImagen = grabarArchivoTemporal(gvProductoAux.getImagen(), gvProductoAux.getNombreImagen());
			}
		}
	}
	
	public void buscarPorDescripcion() {
		listaGvProductos = new ArrayList<GvProducto>();
		if (descripcionBuscar != null && !descripcionBuscar.equals("")) {
			gvProductoBuscar.setDescripcion(descripcionBuscar);
			gvProductoBuscar.setIdEmpresa(idEmpresa);
			listaGvProductos = gvProductoService.buscarListaPorDescripcion(gvProductoBuscar);
		}
		
		if (listaGvProductos.size() > 0) {
			for (GvProducto gvProductoAux : listaGvProductos) {
				if (gvProductoAux.getImagen() != null) {
					pathImagen = grabarArchivoTemporal(gvProductoAux.getImagen(), gvProductoAux.getNombreImagen());
				}
			}
		}
	}

	public void nuevo() {
		gvProducto = new GvProducto();
		
		gvClase = new GvClase();
		gvClase.setIdClase(1);
		listaGvClase = gvClaseService.buscarGvClases();
		
		gvMarca = new GvMarca();
		idMarcaBuscar = 1;
		gvMarca.setIdMarca(1);
		listaGvMarca = gvMarcaService.buscarGvMarcas();
		
		gvUnidad = new GvUnidad();
		gvUnidad.setIdUnidad(3);
		listaGvUnidad = gvUnidadService.buscarGvUnidades();
		mensaje = "ERROR: INGRESAR LOS DATOS SOLICITADOS";
		codProceso = "N";
	}

	public void editar(GvProducto gvProductoAux) {
		codProceso = "E";
		
		gvProducto = gvProductoAux;
		if (gvProducto.getGvClase() != null) {
			gvClase = gvProducto.getGvClase();
			listaGvClase = gvClaseService.buscarGvClases();
		}
		if (gvProducto.getGvMarca() != null) {
			gvMarca = gvProducto.getGvMarca();
			idMarcaBuscar = gvMarca.getIdMarca();
			listaGvMarca = gvMarcaService.buscarGvMarcas();
		}
		if (gvProducto.getGvUnidad() != null) {
			gvUnidad = gvProducto.getGvUnidad();
			listaGvUnidad = gvUnidadService.buscarGvUnidades();
		}
		if (gvProducto.getImagen() != null && !gvProducto.getNombreImagen().equals("")) {
			pathImagen = grabarArchivoTemporal(gvProducto.getImagen(), gvProducto.getNombreImagen());
		}
		mensaje = "";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void guardar() {
		if (gvProducto.getCodigo().isEmpty()) {
			mensaje = "ERROR: INGRESAR CÓDIGO DE BARRAS";
			return;
		}
		try {
			// Comprobar si existe el metodo de la clase.
			Class clase = GvProducto.class;
			Method metodo = clase.getMethod("getFechaRegistra");
			if (metodo != null) {
				// Actualizar campos auditables.
				actualizarCamposAuditables();
			}
		} catch (NoSuchMethodException e) {
			// Si no existe el método, no actualiza los campos auditables.
		}
		
		try {
			if (idMarcaBuscar != 0) {
				gvMarca = gvMarcaService.buscarPorCodigo(idMarcaBuscar);
				gvProducto.setGvMarca(gvMarca);
			}
			gvProducto.setGvClase(gvClase);
			gvProducto.setGvUnidad(gvUnidad);
			
			gvProductoService.guardarGvProducto(gvProducto);
			mensaje = "DATOS ACTUALIZADOS CORRECTAMENTE";
			if (opcionBusqueda != 0) {
				if(codigoBuscar != null && !codigoBuscar.equals("")) {
					buscarPorCodigo();
				}

				if(descripcionBuscar != null && !descripcionBuscar.equals("")) {
					buscarPorDescripcion();
				}
			}
		} catch (Exception exc) {
			if (exc.getMessage().contains("rolled back"))
				mensaje = "ERROR: NO SE ACTUALIZARON LOS DATOS";
			else
				mensaje = (new StringBuilder()).append("ERROR: ")
						.append(exc.getMessage()).toString();
		}
	}

	//Actualizar campos auditables.
	public void actualizarCamposAuditables() {
		if (codProceso == "N") {
			gvProducto.setIdEmpresa(idEmpresa);
			gvProducto.setEstado(1);
			gvProducto.setFechaRegistra(new Date());
			gvProducto.setNomUsuarioRegistra(sesionControlador.getUsuario().getIdentificacion());
			gvProducto.setNomAplicativoRegistra(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
		if (codProceso == "E") {
			gvProducto.setFechaActualiza(new Date());
			gvProducto.setNomUsuarioActualiza(sesionControlador.getUsuario().getIdentificacion());
			gvProducto.setNomAplicativoActualiza(sesionControlador.getMenu().get(0).getAplicacion().getNombre());
		}
	}
	
	//Asignar imagen seleccionada al campo de la entidad y grabar en el server la imagen.
	public void respaldarImagen(FileUploadEvent fileUploadEvent) throws Exception {
		UploadedFile uploadedFile = fileUploadEvent.getUploadedFile();
		gvProducto.setImagen(uploadedFile.getData());
		gvProducto.setNombreImagen(uploadedFile.getName());
		//Grabar archivo temporal
		setPathImagen(grabarArchivoTemporal(gvProducto.getImagen(), gvProducto.getNombreImagen()));
		gvProducto.setPathImagen("/resources/"+gvProducto.getNombreImagen());
	}

	public String grabarArchivoTemporal(byte[] imagen, String nombreArchivo) {
        //Grabar archivo temporal en el server: en la ruta: filePath 
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		String pathImagen = ec.getRealPath(String.format("/resources/%s", nombreArchivo));
        
		try {
			OutputStream out = new FileOutputStream(pathImagen);
			out.write(imagen);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pathImagen;
	}

	//Ejecutar procesos de acuerdo a lo solicitado en el OneMenu.
	public void procesoAplicaIva(ValueChangeEvent evt){
		/*socializadasRutas = (Integer)evt.getNewValue();
		if (socializadasRutas == 1) {
		} else {
		}*/
	}

	public List<GvProducto> getListaGvProductos() {
		return listaGvProductos;
	}

	public void setListaGvProductos(List<GvProducto> listaGvProductos) {
		this.listaGvProductos = listaGvProductos;
	}

	public GvProducto getGvProducto() {
		return gvProducto;
	}

	public void setGvProducto(GvProducto gvProducto) {
		this.gvProducto = gvProducto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public List<GvClase> getListaGvClase() {
		return listaGvClase;
	}

	public void setListaGvClase(List<GvClase> listaGvClase) {
		this.listaGvClase = listaGvClase;
	}

	public GvClase getGvClase() {
		return gvClase;
	}

	public void setGvClase(GvClase gvClase) {
		this.gvClase = gvClase;
	}

	public int getOpcionBusqueda() {
		return opcionBusqueda;
	}

	public void setOpcionBusqueda(int opcionBusqueda) {
		this.opcionBusqueda = opcionBusqueda;
	}

	public String getDescripcionBuscar() {
		return descripcionBuscar;
	}

	public void setDescripcionBuscar(String descripcionBuscar) {
		this.descripcionBuscar = descripcionBuscar;
	}

	public String getCodigoBuscar() {
		return codigoBuscar;
	}

	public void setCodigoBuscar(String codigoBuscar) {
		this.codigoBuscar = codigoBuscar;
	}

	public GvMarca getGvMarca() {
		return gvMarca;
	}

	public void setGvMarca(GvMarca gvMarca) {
		this.gvMarca = gvMarca;
	}

	public List<GvMarca> getListaGvMarca() {
		return listaGvMarca;
	}

	public void setListaGvMarca(List<GvMarca> listaGvMarca) {
		this.listaGvMarca = listaGvMarca;
	}

	public GvUnidad getGvUnidad() {
		return gvUnidad;
	}

	public void setGvUnidad(GvUnidad gvUnidad) {
		this.gvUnidad = gvUnidad;
	}

	public List<GvUnidad> getListaGvUnidad() {
		return listaGvUnidad;
	}

	public void setListaGvUnidad(List<GvUnidad> listaGvUnidad) {
		this.listaGvUnidad = listaGvUnidad;
	}

	public SessionController getSesionControlador() {
		return sesionControlador;
	}

	public void setSesionControlador(SessionController sesionControlador) {
		this.sesionControlador = sesionControlador;
	}

	public Integer getIdMarcaBuscar() {
		return idMarcaBuscar;
	}

	public void setIdMarcaBuscar(Integer idMarcaBuscar) {
		this.idMarcaBuscar = idMarcaBuscar;
	}

	public String getPathImagen() {
		return pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
}