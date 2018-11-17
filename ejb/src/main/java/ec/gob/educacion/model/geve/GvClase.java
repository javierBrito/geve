package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_clase database table.
 * 
 */
@Entity
@Table(name="gv_clase")
@NamedQuery(name="GvClase.findAll", query="SELECT c FROM GvClase c")
public class GvClase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDCLASE", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idClase;

	private int activo;

	private String descripcion;

	public GvClase() {
	}

	public int getIdClase() {
		return this.idClase;
	}

	public void setIdClase(int idClase) {
		this.idClase = idClase;
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
}