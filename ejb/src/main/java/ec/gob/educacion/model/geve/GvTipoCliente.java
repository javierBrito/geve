package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_tipo_cliente database table.
 * 
 */
@Entity
@Table(name="gv_tipo_cliente")
@NamedQuery(name="GvTipoCliente.findAll", query="SELECT tc FROM GvTipoCliente tc")
public class GvTipoCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTIPOCLIENTE", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTipoCliente;

	private String descripcion;
	private int activo;

	public GvTipoCliente() {
	}

	public int getIdTipoCliente() {
		return this.idTipoCliente;
	}

	public void setIdTipoCliente(int idTipoCliente) {
		this.idTipoCliente = idTipoCliente;
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