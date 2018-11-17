package ec.gob.educacion.model.geve;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import java.util.Date;

/**
 * The persistent class for the gv_kardex database table.
 */
@Entity
@Table(name="gv_kardex")
@NamedQuery(name="GvKardex.findAll", query="SELECT k FROM GvKardex k")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvKardex implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idEmpresa;

	@Id
	@Column(name = "IDKARDEX", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idKardex;

	private int estado;
	private String observaciones;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_actualiza")
	private Date fechaActualiza;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registra")
	private Date fechaRegistra;

	// bi-directional many-to-one association to Clase
	@ManyToOne
	@JoinColumn(name = "IDDOCUMENTO")
	private GvDocumento gvDocumento;

	// bi-directional many-to-one association to Clase
	@ManyToOne
	@JoinColumn(name = "IDPRODUCTO")
	private GvProducto gvProducto;

	// bi-directional many-to-one association to Clase
	@ManyToOne
	@JoinColumn(name = "IDTIPOMOVIMIENTO")
	private GvTipoMovimiento gvTipoMovimiento;

	@Column(name="mnt_precio")
	private float mntPrecio;

	@Column(name="mnt_valor")
	private float mntValor;

	@Column(name="mnt_existencia_actual")
	private float mntExistenciaActual;

	@Column(name="nom_aplicativo_actualiza")
	private String nomAplicativoActualiza;

	@Column(name="nom_aplicativo_registra")
	private String nomAplicativoRegistra;

	@Column(name="nom_usuario_actualiza")
	private String nomUsuarioActualiza;

	@Column(name="nom_usuario_registra")
	private String nomUsuarioRegistra;

	@Column(name="num_existencia_actual")
	private int numExistenciaActual;

	@Column(name="num_cantidad")
	private int numCantidad;

	public GvKardex() {
	}

	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Date getFechaActualiza() {
		return this.fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public Date getFechaRegistra() {
		return this.fechaRegistra;
	}

	public void setFechaRegistra(Date fechaRegistra) {
		this.fechaRegistra = fechaRegistra;
	}

	public float getMntPrecio() {
		return this.mntPrecio;
	}

	public void setMntPrecio(float mntPrecio) {
		this.mntPrecio = mntPrecio;
	}

	public float getMntValor() {
		return this.mntValor;
	}

	public void setMntValor(float mntValor) {
		this.mntValor = mntValor;
	}

	public String getNomAplicativoActualiza() {
		return this.nomAplicativoActualiza;
	}

	public void setNomAplicativoActualiza(String nomAplicativoActualiza) {
		this.nomAplicativoActualiza = nomAplicativoActualiza;
	}

	public String getNomAplicativoRegistra() {
		return this.nomAplicativoRegistra;
	}

	public void setNomAplicativoRegistra(String nomAplicativoRegistra) {
		this.nomAplicativoRegistra = nomAplicativoRegistra;
	}

	public String getNomUsuarioActualiza() {
		return this.nomUsuarioActualiza;
	}

	public void setNomUsuarioActualiza(String nomUsuarioActualiza) {
		this.nomUsuarioActualiza = nomUsuarioActualiza;
	}

	public String getNomUsuarioRegistra() {
		return this.nomUsuarioRegistra;
	}

	public void setNomUsuarioRegistra(String nomUsuarioRegistra) {
		this.nomUsuarioRegistra = nomUsuarioRegistra;
	}

	public int getNumExistenciaActual() {
		return this.numExistenciaActual;
	}

	public void setNumExistenciaActual(int numExistenciaActual) {
		this.numExistenciaActual = numExistenciaActual;
	}

	public int getIdKardex() {
		return idKardex;
	}

	public void setIdKardex(int idKardex) {
		this.idKardex = idKardex;
	}

	public GvProducto getGvProducto() {
		return gvProducto;
	}

	public void setGvProducto(GvProducto gvProducto) {
		this.gvProducto = gvProducto;
	}

	public GvDocumento getGvDocumento() {
		return gvDocumento;
	}

	public void setGvDocumento(GvDocumento gvDocumento) {
		this.gvDocumento = gvDocumento;
	}

	public GvTipoMovimiento getGvTipoMovimiento() {
		return gvTipoMovimiento;
	}

	public void setGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) {
		this.gvTipoMovimiento = gvTipoMovimiento;
	}

	public float getMntExistenciaActual() {
		return mntExistenciaActual;
	}

	public void setMntExistenciaActual(float mntExistenciaActual) {
		this.mntExistenciaActual = mntExistenciaActual;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getNumCantidad() {
		return numCantidad;
	}

	public void setNumCantidad(int numCantidad) {
		this.numCantidad = numCantidad;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}