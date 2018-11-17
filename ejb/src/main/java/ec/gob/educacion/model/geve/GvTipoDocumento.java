package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_tipo_documento database table.
 * 
 */
@Entity
@Table(name="gv_tipo_documento")
@NamedQuery(name="GvTipoDocumento.findAll", query="SELECT td FROM GvTipoDocumento td")
public class GvTipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTIPODOCUMENTO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTipoDocumento;

	private int estado;

	private String descripcion;

	public GvTipoDocumento() {
	}

	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

}