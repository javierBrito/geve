package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.Date;


/**
 * The persistent class for the gv_caja database table.
 * 
 */
@Entity
@Table(name="gv_caja")
@NamedQuery(name="GvCaja.findAll", query="SELECT c FROM GvCaja c")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvCaja implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idEmpresa;
	
	@Id
	@Column(name = "IDCAJA", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCaja;

	private String descripcion;
	private int estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_actualiza")
	private Date fechaActualiza;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registra")
	private Date fechaRegistra;

	@Column(name="mnt_caja")
	private float mntCaja;

	@Column(name="mnt_saldo")
	private float mntSaldo;

	@Column(name="mnt_diferencia")
	private float mntDiferencia;

	@Column(name="mnt_libro")
	private float mntLibro;

	@Column(name="mnt_recarga")
	private float mntRecarga;

	@Column(name="mnt_recarga_total")
	private float mntRecargaTotal;

	@Column(name="mnt_reingreso")
	private float mntReingreso;

	@Column(name="nom_aplicativo_actualiza")
	private String nomAplicativoActualiza;

	@Column(name="nom_aplicativo_registra")
	private String nomAplicativoRegistra;

	@Column(name="nom_usuario_actualiza")
	private String nomUsuarioActualiza;

	@Column(name="nom_usuario_registra")
	private String nomUsuarioRegistra;

	public GvCaja() {
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

	public float getMntCaja() {
		return this.mntCaja;
	}

	public void setMntCaja(float mntCaja) {
		this.mntCaja = mntCaja;
	}

	public float getMntDiferencia() {
		return this.mntDiferencia;
	}

	public void setMntDiferencia(float mntDiferencia) {
		this.mntDiferencia = mntDiferencia;
	}

	public float getMntLibro() {
		return this.mntLibro;
	}

	public void setMntLibro(float mntLibro) {
		this.mntLibro = mntLibro;
	}

	public float getMntRecarga() {
		return this.mntRecarga;
	}

	public void setMntRecarga(float mntRecarga) {
		this.mntRecarga = mntRecarga;
	}

	public float getMntRecargaTotal() {
		return this.mntRecargaTotal;
	}

	public void setMntRecargaTotal(float mntRecargaTotal) {
		this.mntRecargaTotal = mntRecargaTotal;
	}

	public float getMntReingreso() {
		return this.mntReingreso;
	}

	public void setMntReingreso(float mntReingreso) {
		this.mntReingreso = mntReingreso;
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

	public int getIdCaja() {
		return idCaja;
	}

	public void setIdCaja(int idCaja) {
		this.idCaja = idCaja;
	}

	public float getMntSaldo() {
		return mntSaldo;
	}

	public void setMntSaldo(float mntSaldo) {
		this.mntSaldo = mntSaldo;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}