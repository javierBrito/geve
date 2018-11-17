package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


/**
 * The persistent class for the gv_documento_detalle database table.
 * 
 */
@Entity
@Table(name="gv_documento_detalle")
@NamedQuery(name="GvDocumentoDetalle.findAll", query="SELECT dd FROM GvDocumentoDetalle dd")
@Audited(targetAuditMode=RelationTargetAuditMode.NOT_AUDITED)
public class GvDocumentoDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDDOCUMENTODETALLE", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idDocumentoDetalle;

	private int estado;

	@Column(name="num_cantidad")
	private int numCantidad;

	@Column(name="mnt_descuento")
	private float mntDescuento;

	// bi-directional many-to-one association to Documento
	@ManyToOne
	@JoinColumn(name = "IDDOCUMENTO")
	private GvDocumento gvDocumento;

	// bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name = "IDPRODUCTO")
	private GvProducto gvProducto;

	private float precio;

	@Column(name="mnt_importe")
	private float mntImporte;
	
	@Transient
	private int idProductoTransient;

	public GvDocumentoDetalle() {
	}

	public long getIdDocumentoDetalle() {
		return this.idDocumentoDetalle;
	}

	public void setIdDocumentoDetalle(long idDocumentoDetalle) {
		this.idDocumentoDetalle = idDocumentoDetalle;
	}

	public GvDocumento getGvDocumento() {
		return gvDocumento;
	}

	public void setGvDocumento(GvDocumento gvDocumento) {
		this.gvDocumento = gvDocumento;
	}

	public GvProducto getGvProducto() {
		return gvProducto;
	}

	public void setGvProducto(GvProducto gvProducto) {
		this.gvProducto = gvProducto;
	}

	public int getIdProductoTransient() {
		if (idProductoTransient == 0) {
			if (gvProducto != null) {
				idProductoTransient = gvProducto.getIdProducto();
			} else {
				idProductoTransient = 0;
			}
		}
		
		return idProductoTransient;
	}

	public void setIdProductoTransient(int idProductoTransient) {
		this.idProductoTransient = idProductoTransient;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getNumCantidad() {
		return numCantidad;
	}

	public void setNumCantidad(int numCantidad) {
		this.numCantidad = numCantidad;
	}

	public float getMntDescuento() {
		return mntDescuento;
	}

	public void setMntDescuento(float mntDescuento) {
		this.mntDescuento = mntDescuento;
	}

	public float getMntImporte() {
		return mntImporte;
	}

	public void setMntImporte(float mntImporte) {
		this.mntImporte = mntImporte;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}
}