package ec.gob.educacion.dto;


public class GvKardexFechaDTO {

	private int idEmpresa;
	private int idProducto;
	private String codigo;
	private String descripcion;
	private float precioDeCompra;
	private int numExistencia;
	private int numExistenciaMinima;
	
	private int numIngreso;
	private float mntValorIngreso;
	private int numEgreso;
	private float mntValorEgreso;
	private float mntSaldo;
	private float mntImporte;
	private boolean registroVerificado;

	public GvKardexFechaDTO(
			int idEmpresa,
			int idProducto,
			String descripcion,
			String codigo,
			int numExistencia,
			int numExistenciaMinima,
			int numIngreso,
			float mntValorIngreso,
			int numEgreso,
			float mntValorEgreso,
			float mntSaldo,
			boolean registroVerificado,
			float precioDeCompra
			) {
		this.idEmpresa = idEmpresa;
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.numExistencia = numExistencia;
		this.numExistenciaMinima = numExistenciaMinima;
		this.numIngreso = numIngreso;
		this.mntValorIngreso = mntValorIngreso;
		this.numEgreso = numEgreso;
		this.mntValorEgreso = mntValorEgreso;
		this.mntSaldo = mntSaldo;
		this.registroVerificado = registroVerificado;
		this.precioDeCompra = precioDeCompra;
	}

	public GvKardexFechaDTO() {
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getNumExistencia() {
		return numExistencia;
	}

	public void setNumExistencia(int numExistencia) {
		this.numExistencia = numExistencia;
	}

	public int getNumIngreso() {
		return numIngreso;
	}

	public void setNumIngreso(int numIngreso) {
		this.numIngreso = numIngreso;
	}

	public float getMntValorIngreso() {
		return mntValorIngreso;
	}

	public void setMntValorIngreso(float mntValorIngreso) {
		this.mntValorIngreso = mntValorIngreso;
	}

	public int getNumEgreso() {
		return numEgreso;
	}

	public void setNumEgreso(int numEgreso) {
		this.numEgreso = numEgreso;
	}

	public float getMntValorEgreso() {
		return mntValorEgreso;
	}

	public void setMntValorEgreso(float mntValorEgreso) {
		this.mntValorEgreso = mntValorEgreso;
	}

	public boolean isRegistroVerificado() {
		return registroVerificado;
	}

	public void setRegistroVerificado(boolean registroVerificado) {
		this.registroVerificado = registroVerificado;
	}

	public float getMntSaldo() {
		return mntSaldo;
	}

	public void setMntSaldo(float mntSaldo) {
		this.mntSaldo = mntSaldo;
	}

	public int getNumExistenciaMinima() {
		return numExistenciaMinima;
	}

	public void setNumExistenciaMinima(int numExistenciaMinima) {
		this.numExistenciaMinima = numExistenciaMinima;
	}

	public float getPrecioDeCompra() {
		return precioDeCompra;
	}

	public void setPrecioDeCompra(float precioDeCompra) {
		this.precioDeCompra = precioDeCompra;
	}

	public float getMntImporte() {
		return mntImporte;
	}

	public void setMntImporte(float mntImporte) {
		this.mntImporte = mntImporte;
	}

	public int getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
}