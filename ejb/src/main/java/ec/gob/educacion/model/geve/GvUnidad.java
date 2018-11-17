package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_unidad database table.
 * 
 */
@Entity
@Table(name="gv_unidad")
@NamedQuery(name="GvUnidad.findAll", query="SELECT u FROM GvUnidad u")
public class GvUnidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDUNIDAD", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUnidad;

	private String nemonico;
	private String descripcion;
	@Column(name="items_por_unidad")
	private int itemsPorUnidad;
	private int activo;

	public GvUnidad() {
	}

	public int getIdUnidad() {
		return idUnidad;
	}

	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getItemsPorUnidad() {
		return this.itemsPorUnidad;
	}

	public void setItemsPorUnidad(int itemsPorUnidad) {
		this.itemsPorUnidad = itemsPorUnidad;
	}

	public String getNemonico() {
		return this.nemonico;
	}

	public void setNemonico(String nemonico) {
		this.nemonico = nemonico;
	}
}