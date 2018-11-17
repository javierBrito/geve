package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_tipo_nominacion database table.
 * 
 */
@Entity
@Table(name="gv_tipo_nominacion")
@NamedQuery(name="GvTipoNominacion.findAll", query="SELECT tn FROM GvTipoNominacion tn")
public class GvTipoNominacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDTIPONOMINACION", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTipoNominacion;

	private String descripcion;
	private float valor;
	private int estado;

	public GvTipoNominacion() {
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

	public int getIdTipoNominacion() {
		return idTipoNominacion;
	}

	public void setIdTipoNominacion(int idTipoNominacion) {
		this.idTipoNominacion = idTipoNominacion;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

}