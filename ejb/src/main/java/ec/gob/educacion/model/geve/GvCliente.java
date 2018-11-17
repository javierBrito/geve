package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.Date;

/**
 * The persistent class for the gv_cliente database table.
 */
@Entity
@Table(name="gv_cliente")
@NamedQuery(name="GvCliente.findAll", query="SELECT c FROM GvCliente c")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvCliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idEmpresa;

	@Id
	@Column(name = "IDCLIENTE", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCliente;

	private String apellidos;
	private String dni;
	private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false, length = 1, columnDefinition="char(1)")
    private Genero genero;
    
	private String movil;
	private String nombres;
	private String telefono;
	private String direccion;
	private int activo;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_actualiza")
	private Date fechaActualiza;

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

	// bi-directional many-to-one association to TipoCliente
	@ManyToOne
	@JoinColumn(name = "IDTIPOCLIENTE")
	private GvTipoCliente gvTipoCliente;

	public GvCliente() {
	}

	public int getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMovil() {
		return this.movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public GvTipoCliente getGvTipoCliente() {
		return gvTipoCliente;
	}

	public void setGvTipoCliente(GvTipoCliente gvTipoCliente) {
		this.gvTipoCliente = gvTipoCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Date getFechaActualiza() {
		return fechaActualiza;
	}

	public void setFechaActualiza(Date fechaActualiza) {
		this.fechaActualiza = fechaActualiza;
	}

	public Date getFechaRegistra() {
		return fechaRegistra;
	}

	public void setFechaRegistra(Date fechaRegistra) {
		this.fechaRegistra = fechaRegistra;
	}

	public String getNomAplicativoActualiza() {
		return nomAplicativoActualiza;
	}

	public void setNomAplicativoActualiza(String nomAplicativoActualiza) {
		this.nomAplicativoActualiza = nomAplicativoActualiza;
	}

	public String getNomAplicativoRegistra() {
		return nomAplicativoRegistra;
	}

	public void setNomAplicativoRegistra(String nomAplicativoRegistra) {
		this.nomAplicativoRegistra = nomAplicativoRegistra;
	}

	public String getNomUsuarioActualiza() {
		return nomUsuarioActualiza;
	}

	public void setNomUsuarioActualiza(String nomUsuarioActualiza) {
		this.nomUsuarioActualiza = nomUsuarioActualiza;
	}

	public String getNomUsuarioRegistra() {
		return nomUsuarioRegistra;
	}

	public void setNomUsuarioRegistra(String nomUsuarioRegistra) {
		this.nomUsuarioRegistra = nomUsuarioRegistra;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

}