package ec.gob.educacion.dto;


public class GvKardexFechaSalidaDTO {

	private String idEmpresa;
	private String idProducto;
	private String codigo;
	private String descripcion;
	private String precioDeCompra;
	private String numExistencia;
	private String numExistenciaMinima;
	
	private String numIngreso;
	private String mntValorIngreso;
	private String numEgreso;
	private String mntValorEgreso;
	private String mntSaldo;
	private String mntImporte;
	private boolean registroVerificado;

	public GvKardexFechaSalidaDTO(
			String idEmpresa,
			String idProducto,
			String descripcion,
			String codigo,
			String numExistencia,
			String numExistenciaMinima,
			String numIngreso,
			String mntValorIngreso,
			String numEgreso,
			String mntValorEgreso,
			String mntSaldo,
			boolean registroVerificado,
			String precioDeCompra
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

	public GvKardexFechaSalidaDTO() {
	}

	public String getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPrecioDeCompra() {
		return precioDeCompra;
	}

	public void setPrecioDeCompra(String precioDeCompra) {
		this.precioDeCompra = precioDeCompra;
	}

	public String getNumExistencia() {
		return numExistencia;
	}

	public void setNumExistencia(String numExistencia) {
		this.numExistencia = numExistencia;
	}

	public String getNumExistenciaMinima() {
		return numExistenciaMinima;
	}

	public void setNumExistenciaMinima(String numExistenciaMinima) {
		this.numExistenciaMinima = numExistenciaMinima;
	}

	public String getNumIngreso() {
		return numIngreso;
	}

	public void setNumIngreso(String numIngreso) {
		this.numIngreso = numIngreso;
	}

	public String getMntValorIngreso() {
		return mntValorIngreso;
	}

	public void setMntValorIngreso(String mntValorIngreso) {
		this.mntValorIngreso = mntValorIngreso;
	}

	public String getNumEgreso() {
		return numEgreso;
	}

	public void setNumEgreso(String numEgreso) {
		this.numEgreso = numEgreso;
	}

	public String getMntValorEgreso() {
		return mntValorEgreso;
	}

	public void setMntValorEgreso(String mntValorEgreso) {
		this.mntValorEgreso = mntValorEgreso;
	}

	public String getMntSaldo() {
		return mntSaldo;
	}

	public void setMntSaldo(String mntSaldo) {
		this.mntSaldo = mntSaldo;
	}

	public String getMntImporte() {
		return mntImporte;
	}

	public void setMntImporte(String mntImporte) {
		this.mntImporte = mntImporte;
	}

	public boolean isRegistroVerificado() {
		return registroVerificado;
	}

	public void setRegistroVerificado(boolean registroVerificado) {
		this.registroVerificado = registroVerificado;
	}

}