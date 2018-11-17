package ec.gob.educacion.model.geve;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import java.util.Date;


/**
 * The persistent class for the gv_parametro database table.
 */
@Entity
@Table(name="gv_parametro")
@NamedQuery(name="GvParametro.findAll", query="SELECT p FROM GvParametro p")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDPARAMETRO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idParametro;

	private int estado;

	private String descripcion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_actualiza")
	private Date fechaActualiza;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_registra")
	private Date fechaRegistra;

	@Column(name="nom_aplicativo_actualiza")
	private String nomAplicativoActualiza;

	@Column(name="nom_aplicativo_registra")
	private String nomAplicativoRegistra;

	@Column(name="nom_usuario_actualiza")
	private String nomUsuarioActualiza;

	@Column(name="nom_usuario_registra")
	private String nomUsuarioRegistra;

	private String nombre;

	private String valor;

	@Column(name="hora_recargo")
	private int horaRecargo;

	@Column(name="porcentaje_recargo")
	private int porcentajeRecargo;
	
	public GvParametro() {
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaActualiza() {
		return this.fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public Date getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaRegistra() {
		return this.fechaRegistra;
	}

	public void setFechaRegistra(Date fechaRegistra) {
		this.fechaRegistra = fechaRegistra;
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

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getIdParametro() {
		return idParametro;
	}

	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getPorcentajeRecargo() {
		return porcentajeRecargo;
	}

	public void setPorcentajeRecargo(int porcentajeRecargo) {
		this.porcentajeRecargo = porcentajeRecargo;
	}

	public int getHoraRecargo() {
		return horaRecargo;
	}

	public void setHoraRecargo(int horaRecargo) {
		this.horaRecargo = horaRecargo;
	}
	
}