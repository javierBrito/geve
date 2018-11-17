package ec.gob.educacion.dto;


public class GvKardexStockDiferenciasDTO {

	private int idProducto;
	private String descripcion;
	private String codigo;
	private int numExistenciaAnterior;
	private int numExistenciaActual;
	private int numDiferencia;
	private boolean registroVerificado;

	public GvKardexStockDiferenciasDTO(
			int idProducto,
			String descripcion,
			String codigo,
			int numExistenciaAnterior,
			int numExistenciaActual,
			int numDiferencia,
			boolean registroVerificado
			) {
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.codigo = codigo;
		this.numExistenciaAnterior = numExistenciaAnterior;
		this.numExistenciaActual = numExistenciaActual;
		this.numDiferencia = numDiferencia;
		this.registroVerificado = registroVerificado;
	}

	public GvKardexStockDiferenciasDTO() {
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

	public int getNumExistenciaActual() {
		return numExistenciaActual;
	}

	public void setNumExistenciaActual(int numExistenciaActual) {
		this.numExistenciaActual = numExistenciaActual;
	}

	public int getNumExistenciaAnterior() {
		return numExistenciaAnterior;
	}

	public void setNumExistenciaAnterior(int numExistenciaAnterior) {
		this.numExistenciaAnterior = numExistenciaAnterior;
	}

	public int getNumDiferencia() {
		return numDiferencia;
	}

	public void setNumDiferencia(int numDiferencia) {
		this.numDiferencia = numDiferencia;
	}

	public boolean isRegistroVerificado() {
		return registroVerificado;
	}

	public void setRegistroVerificado(boolean registroVerificado) {
		this.registroVerificado = registroVerificado;
	}
}