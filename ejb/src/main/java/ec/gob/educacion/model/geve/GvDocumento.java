package ec.gob.educacion.model.geve;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the gv_documento database table.
 */
@Entity
@Table(name="gv_documento")
@NamedQuery(name="GvDocumento.findAll", query="SELECT d FROM GvDocumento d")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idEmpresa;

	@Id
	@Column(name = "IDDOCUMENTO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDocumento;

	private int estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_actualiza")
	private Date fechaActualiza;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registra")
	private Date fechaRegistra;

	// bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name = "IDCLIENTE")
	private GvCliente gvCliente;

	// bi-directional many-to-one association to TipoDocumento
	@ManyToOne
	@JoinColumn(name = "IDTIPODOCUMENTO")
	private GvTipoDocumento gvTipoDocumento;

	// bi-directional many-to-one association to EstadoDocumento
	@ManyToOne
	@JoinColumn(name = "IDESTADODOCUMENTO")
	private GvEstadoDocumento gvEstadoDocumento;

	// bi-directional many-to-one association to GvDocumento
	@OneToMany(fetch=FetchType.LAZY, mappedBy="gvDocumento", cascade=CascadeType.ALL)
	private List<GvDocumentoDetalle> gvDocumentoDetalles;

	@Column(name="mnt_iva")
	private float mntIva;

	@Column(name="mnt_subtotal")
	private float mntSubtotal;

	@Column(name="mnt_total")
	private float mntTotal;

	@Column(name="nom_aplicativo_actualiza")
	private String nomAplicativoActualiza;

	@Column(name="nom_aplicativo_registra")
	private String nomAplicativoRegistra;

	@Column(name="nom_usuario_actualiza")
	private String nomUsuarioActualiza;

	@Column(name="nom_usuario_registra")
	private String nomUsuarioRegistra;

	@Column(name="num_items")
	private int numItems;

	@Lob
	private String observaciones;

	public GvDocumento() {
	}

	public int getIdDocumento() {
		return this.idDocumento;
	}

	public void setIdDocumento(int idDocumento) {
		this.idDocumento = idDocumento;
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

	public float getMntIva() {
		return this.mntIva;
	}

	public void setMntIva(float mntIva) {
		this.mntIva = mntIva;
	}

	public float getMntSubtotal() {
		return this.mntSubtotal;
	}

	public void setMntSubtotal(float mntSubtotal) {
		this.mntSubtotal = mntSubtotal;
	}

	public float getMntTotal() {
		return this.mntTotal;
	}

	public void setMntTotal(float mntTotal) {
		this.mntTotal = mntTotal;
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

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public GvCliente getGvCliente() {
		return gvCliente;
	}

	public void setGvCliente(GvCliente gvCliente) {
		this.gvCliente = gvCliente;
	}

	public GvEstadoDocumento getGvEstadoDocumento() {
		return gvEstadoDocumento;
	}

	public void setGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) {
		this.gvEstadoDocumento = gvEstadoDocumento;
	}

	public GvTipoDocumento getGvTipoDocumento() {
		return gvTipoDocumento;
	}

	public void setGvTipoDocumento(GvTipoDocumento gvTipoDocumento) {
		this.gvTipoDocumento = gvTipoDocumento;
	}

	public List<GvDocumentoDetalle> getGvDocumentoDetalles() {
		return gvDocumentoDetalles;
	}

	public void setGvDocumentoDetalles(List<GvDocumentoDetalle> gvDocumentoDetalles) {
		this.gvDocumentoDetalles = gvDocumentoDetalles;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getNumItems() {
		return numItems;
	}

	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}