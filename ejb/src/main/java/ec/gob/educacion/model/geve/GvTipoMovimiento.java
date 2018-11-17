package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_tipo_movimiento database table.
 * 
 */
@Entity
@Table(name="gv_tipo_movimiento")
@NamedQuery(name="GvTipoMovimiento.findAll", query="SELECT tm FROM GvTipoMovimiento tm")
public class GvTipoMovimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTIPOMOVIMIENTO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTipoMovimiento;

	private String descripcion;

	private int estado;

	public GvTipoMovimiento() {
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getIdTipoMovimiento() {
		return idTipoMovimiento;
	}

	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

}