package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the gv_prueba database table.
 * 
 */
@Entity
@Table(name="gv_prueba")
@NamedQuery(name="GvPrueba.findAll", query="SELECT g FROM GvPrueba g")
public class GvPrueba implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codigo;

	private String nombre;

	public GvPrueba() {
	}

	public int getCodigo() {
		return this.codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}