package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_marca database table.
 * 
 */
@Entity
@Table(name="gv_marca")
@NamedQuery(name="GvMarca.findAll", query="SELECT m FROM GvMarca m")
public class GvMarca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDMARCA", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMarca;

	private int activo;

	private String descripcion;

	public GvMarca() {
	}

	public int getIdMarca() {
		return this.idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
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