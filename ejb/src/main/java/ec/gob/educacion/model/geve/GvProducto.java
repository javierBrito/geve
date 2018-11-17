package ec.gob.educacion.model.geve;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.Date;

/**
 * The persistent class for the gv_producto database table.
 */
@Entity
@Table(name="gv_producto")
@NamedQuery(name="GvProducto.findAll", query="SELECT p FROM GvProducto p")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class GvProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idEmpresa;

	@Id
	@Column(name = "IDPRODUCTO", unique = true, nullable = false, precision = 10, scale = 0)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProducto;

	private int estado;

	@Column(name="aplica_iva")
	private int aplicaIva;
	
	private transient String desAplicaIva;

	private String codigo;

	private String descripcion;

	@Column(name="num_existencia_actual")
	private int numExistenciaActual;

	@Column(name="num_existencia_minima")
	private int numExistenciaMinima;

	// bi-directional many-to-one association to Clase
	@ManyToOne
	@JoinColumn(name = "IDCLASE")
	private GvClase gvClase;

	// bi-directional many-to-one association to Marca
	@ManyToOne
	@JoinColumn(name = "IDMARCA")
	private GvMarca gvMarca;

	// bi-directional many-to-one association to Unidad Medida
	@ManyToOne
	@JoinColumn(name = "IDUNIDAD")
	private GvUnidad gvUnidad;
	
	@Lob
	private byte[] imagen;

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

	@Column(name="nombre_imagen")
	private String nombreImagen;

	@Column(name="path_imagen")
	private String pathImagen;

	@Column(name="precio_de_compra")
	private float precioDeCompra;

	@Column(name="precio_de_costo")
	private float precioDeCosto;

	@Column(name="precio_por_mayor")
	private float precioPorMayor;

	@Column(name="precio_por_menor")
	private float precioPorMenor;

	public GvProducto() {
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getAplicaIva() {
		return this.aplicaIva;
	}

	public void setAplicaIva(int aplicaIva) {
		this.aplicaIva = aplicaIva;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getNumExistenciaMinima() {
		return this.numExistenciaMinima;
	}

	public void setNumExistenciaMinima(int numExistenciaMinima) {
		this.numExistenciaMinima = numExistenciaMinima;
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

	public byte[] getImagen() {
		return this.imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
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

	public String getNombreImagen() {
		return this.nombreImagen;
	}

	public void setNombreImagen(String nombreImagen) {
		this.nombreImagen = nombreImagen;
	}

	public String getPathImagen() {
		return this.pathImagen;
	}

	public void setPathImagen(String pathImagen) {
		this.pathImagen = pathImagen;
	}

	public float getPrecioDeCosto() {
		return this.precioDeCosto;
	}

	public void setPrecioDeCosto(float precioDeCosto) {
		this.precioDeCosto = precioDeCosto;
	}

	public float getPrecioPorMayor() {
		return this.precioPorMayor;
	}

	public void setPrecioPorMayor(float precioPorMayor) {
		this.precioPorMayor = precioPorMayor;
	}

	public float getPrecioPorMenor() {
		return this.precioPorMenor;
	}

	public void setPrecioPorMenor(float precioPorMenor) {
		this.precioPorMenor = precioPorMenor;
	}

	public GvClase getGvClase() {
		return gvClase;
	}

	public void setGvClase(GvClase gvClase) {
		this.gvClase = gvClase;
	}

	public GvMarca getGvMarca() {
		return gvMarca;
	}

	public void setGvMarca(GvMarca gvMarca) {
		this.gvMarca = gvMarca;
	}

	public GvUnidad getGvUnidad() {
		return gvUnidad;
	}

	public void setGvUnidad(GvUnidad gvUnidad) {
		this.gvUnidad = gvUnidad;
	}

	public String getDesAplicaIva() {
    	if(this.aplicaIva == 1){
    		setDesAplicaIva("Si");
    	}
    	else{
    		setDesAplicaIva("No");
    	}
		return desAplicaIva;
	}

	public void setDesAplicaIva(String desAplicaIva) {
		this.desAplicaIva = desAplicaIva;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getNumExistenciaActual() {
		return numExistenciaActual;
	}

	public void setNumExistenciaActual(int numExistenciaActual) {
		this.numExistenciaActual = numExistenciaActual;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public float getPrecioDeCompra() {
		return precioDeCompra;
	}

	public void setPrecioDeCompra(float precioDeCompra) {
		this.precioDeCompra = precioDeCompra;
	}
}