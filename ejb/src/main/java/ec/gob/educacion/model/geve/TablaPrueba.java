package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tabla_prueba database table.
 * 
 */
@Entity
@Table(name="tabla_prueba")
@NamedQuery(name="TablaPrueba.findAll", query="SELECT t FROM TablaPrueba t")
public class TablaPrueba implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codigo;

	private String descripcion;

	private float valor;

	public TablaPrueba() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getValor() {
		return this.valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

}