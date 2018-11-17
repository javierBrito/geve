package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 */
@Entity
@Table(name="gv_entrada_salida")
@NamedQuery(name="GvEntradaSalida.findAll", query="SELECT es FROM GvEntradaSalida es")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class GvEntradaSalida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDENTRADASALIDA", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idEntradaSalida;

	private String descripcion;

	private int estado;

	// bi-directional many-to-one association to Documento
	@ManyToOne
	@JoinColumn(name = "IDCAJA")
	private GvCaja gvCaja;

	// bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name = "IDPROVEEDOR")
	private GvProveedor gvProveedor;

	@Column(name="mnt_entrada")
	private float mntEntrada;

	@Column(name="mnt_salida")
	private float mntSalida;

	@Column(name="mnt_precio")
	private float mntPrecio;

	@Column(name="num_cantidad")
	private int numCantidad;

	public GvEntradaSalida() {
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

	public int getNumCantidad() {
		return this.numCantidad;
	}

	public void setNumCantidad(int numCantidad) {
		this.numCantidad = numCantidad;
	}

	public GvCaja getGvCaja() {
		return gvCaja;
	}

	public void setGvCaja(GvCaja gvCaja) {
		this.gvCaja = gvCaja;
	}

	public int getIdEntradaSalida() {
		return idEntradaSalida;
	}

	public void setIdEntradaSalida(int idEntradaSalida) {
		this.idEntradaSalida = idEntradaSalida;
	}

	public GvProveedor getGvProveedor() {
		return gvProveedor;
	}

	public void setGvProveedor(GvProveedor gvProveedor) {
		this.gvProveedor = gvProveedor;
	}

	public float getMntEntrada() {
		return mntEntrada;
	}

	public void setMntEntrada(float mntEntrada) {
		this.mntEntrada = mntEntrada;
	}

	public float getMntSalida() {
		return mntSalida;
	}

	public void setMntSalida(float mntSalida) {
		this.mntSalida = mntSalida;
	}

	public float getMntPrecio() {
		return mntPrecio;
	}

	public void setMntPrecio(float mntPrecio) {
		this.mntPrecio = mntPrecio;
	}
}