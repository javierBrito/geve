package ec.gob.educacion.model.geve;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gv_modelo database table.
 * 
 */
@Entity
@Table(name="gv_modelo")
@NamedQuery(name="GvModelo.findAll", query="SELECT g FROM GvModelo g")
public class GvModelo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idmodelo;

	private int activo;

	private String descripcion;

	public GvModelo() {
	}

	public int getIdmodelo() {
		return this.idmodelo;
	}

	public void setIdmodelo(int idmodelo) {
		this.idmodelo = idmodelo;
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