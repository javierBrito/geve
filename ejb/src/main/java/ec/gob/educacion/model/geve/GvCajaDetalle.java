package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 */
@Entity
@Table(name="gv_caja_detalle")
@NamedQuery(name="GvCajaDetalle.findAll", query="SELECT cd FROM GvCajaDetalle cd")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class GvCajaDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDCAJADETALLE", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCajaDetalle;

	private String descripcion;

	private int estado;

	// bi-directional many-to-one association to Documento
	@ManyToOne
	@JoinColumn(name = "IDCAJA")
	private GvCaja gvCaja;

	// bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name = "IDTIPONOMINACION")
	private GvTipoNominacion gvTipoNominacion;

	@Column(name="mnt_importe")
	private float mntImporte;

	@Column(name="num_cantidad")
	private int numCantidad;

	public GvCajaDetalle() {
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

	public float getMntImporte() {
		return this.mntImporte;
	}

	public void setMntImporte(float mntImporte) {
		this.mntImporte = mntImporte;
	}

	public int getNumCantidad() {
		return this.numCantidad;
	}

	public void setNumCantidad(int numCantidad) {
		this.numCantidad = numCantidad;
	}

	public int getIdCajaDetalle() {
		return idCajaDetalle;
	}

	public void setIdCajaDetalle(int idCajaDetalle) {
		this.idCajaDetalle = idCajaDetalle;
	}

	public GvCaja getGvCaja() {
		return gvCaja;
	}

	public void setGvCaja(GvCaja gvCaja) {
		this.gvCaja = gvCaja;
	}

	public GvTipoNominacion getGvTipoNominacion() {
		return gvTipoNominacion;
	}

	public void setGvTipoNominacion(GvTipoNominacion gvTipoNominacion) {
		this.gvTipoNominacion = gvTipoNominacion;
	}
}