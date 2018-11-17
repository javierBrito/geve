package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_estado_documento database table.
 * 
 */
@Entity
@Table(name="gv_estado_documento")
@NamedQuery(name="GvEstadoDocumento.findAll", query="SELECT ed FROM GvEstadoDocumento ed")
public class GvEstadoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDESTADODOCUMENTO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEstadoDocumento;

	private int activo;

	private String descripcion;

	public GvEstadoDocumento() {
	}

	public int getIdEstadoDocumento() {
		return idEstadoDocumento;
	}

	public void setIdEstadoDocumento(int idEstadoDocumento) {
		this.idEstadoDocumento = idEstadoDocumento;
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