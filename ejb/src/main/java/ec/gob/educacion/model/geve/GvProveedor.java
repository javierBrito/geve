package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.Date;

/**
 * The persistent class for the gv_proveedor database table.
 */
@Entity
@Table(name="gv_proveedor")
@NamedQuery(name="GvProveedor.findAll", query="SELECT p FROM GvProveedor p")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvProveedor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int idEmpresa;

	@Id
	@Column(name = "IDPROVEEDOR", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProveedor;

	private String dni;
	@Column(name="razon_social")
	private String razonSocial;
	private String direccion;
	private String ciudad;
	private String telefono;
	private String movil;
	private String email;
	@Column(name="cta_bancaria")
	private String ctaBancaria;
	@Column(name="nom_contacto")
	private String nomContacto;
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

	public GvProveedor() {
	}

	public int getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public int getActivo() {
		return this.activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getApellidos() {
		return this.razonSocial;
	}

	public void setApellidos(String razonSocial) {
		this.razonSocial = razonSocial;
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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
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

	public String getCtaBancaria() {
		return ctaBancaria;
	}

	public void setCtaBancaria(String ctaBancaria) {
		this.ctaBancaria = ctaBancaria;
	}

	public String getNomContacto() {
		return nomContacto;
	}

	public void setNomContacto(String nomContacto) {
		this.nomContacto = nomContacto;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
}