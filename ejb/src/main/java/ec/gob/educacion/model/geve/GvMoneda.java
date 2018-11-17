package ec.gob.educacion.model.geve;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gv_moneda database table.
 * 
 */
@Entity
@Table(name="gv_moneda")
@NamedQuery(name="GvMoneda.findAll", query="SELECT g FROM GvMoneda g")
public class GvMoneda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idmoneda;

	private int activo;

	private String descripcion;

	private String simbolo;

	public GvMoneda() {
	}

	public int getIdmoneda() {
		return this.idmoneda;
	}

	public void setIdmoneda(int idmoneda) {
		this.idmoneda = idmoneda;
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

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

}