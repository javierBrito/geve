package ec.gob.educacion.backing.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import ec.gob.educacion.dao.TablaPruebaFacadeLocal;
import ec.gob.educacion.model.geve.TablaPrueba;
import ec.gob.educacion.scope.ViewScoped;

@ManagedBean
@ViewScoped
public class TablaPruebasBean{
	@Inject
	private TablaPruebaFacadeLocal tablaPruebaFacadeLocal;
	
	private TablaPrueba tablaPrueba = new TablaPrueba();
	private List<TablaPrueba> listaTablaPrueba;
	private int codigo = 0;
	private String descripcion = "";
	private float valor = 0;
	private String mensaje = "";
	
	@PostConstruct
	public void init() {
		System.out.println("init() I");
		listaTablaPrueba = tablaPruebaFacadeLocal.findAll();
		System.out.println("init() F");
	} 
	
	public void crear() {
		System.out.println("crear() I");
		tablaPrueba = new TablaPrueba();
		tablaPrueba.setCodigo(this.getCodigo());
		tablaPrueba.setDescripcion(this.getDescripcion());
		tablaPrueba.setValor(getValor());
		try {
			tablaPruebaFacadeLocal.create(tablaPrueba);
			listaTablaPrueba = tablaPruebaFacadeLocal.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("crear() F");
	}
	
	public void actualizar() {
		System.out.println("actualizar() I");
		tablaPrueba = new TablaPrueba();
		tablaPrueba.setCodigo(this.getCodigo());
		tablaPrueba.setDescripcion(this.getDescripcion());
		tablaPrueba.setValor(getValor());
		System.out.println("tablaPrueba.getDescripcion() = "+tablaPrueba.getDescripcion());
		try {
			tablaPruebaFacadeLocal.update(tablaPrueba);
			listaTablaPrueba = tablaPruebaFacadeLocal.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("actualizar() F");
	}
	
	public void editar(TablaPrueba tablaPruebaAux) {
		System.out.println("editar(TablaPrueba tablaPruebaAux) I ");
		tablaPrueba = new TablaPrueba();
		tablaPrueba = tablaPruebaFacadeLocal.read(tablaPruebaAux.getCodigo());
		codigo = tablaPrueba.getCodigo();
		descripcion = tablaPrueba.getDescripcion();
		valor = tablaPrueba.getValor();
		System.out.println("editar(TablaPrueba tablaPruebaAux) F ");
	}
	
	public void eliminar(TablaPrueba tablaPruebaAux) {
		tablaPrueba = new TablaPrueba();
		tablaPrueba = tablaPruebaFacadeLocal.read(tablaPruebaAux.getCodigo());
		codigo = tablaPrueba.getCodigo();
		descripcion = tablaPrueba.getDescripcion();
		valor = tablaPrueba.getValor();
		try {
			tablaPruebaFacadeLocal.remove(tablaPrueba);
			listaTablaPrueba = tablaPruebaFacadeLocal.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TablaPrueba getTablaPrueba() {
		return tablaPrueba;
	}

	public void setTablaPrueba(TablaPrueba tablaPrueba) {
		this.tablaPrueba = tablaPrueba;
	}

	public List<TablaPrueba> getListaTablaPrueba() {
		return listaTablaPrueba;
	}

	public void setListaTablaPrueba(List<TablaPrueba> listaTablaPrueba) {
		this.listaTablaPrueba = listaTablaPrueba;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
