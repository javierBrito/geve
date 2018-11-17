package ec.gob.educacion.util;

/**
 * The Class CedulaValidator.
 */
public class CedulaValidator {
	/** The Constant coeficientes. */
	private static final int[] coeficientes = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
	/** The cedula. */
	private String cedula;

	/**
	 * Instantiates a new cedula validator.
	 *
	 * @param cedula
	 *            the cedula
	 */
	public CedulaValidator(String cedula) {
		super();
		this.cedula = cedula;
	}

	/**
	 * Validate.
	 *
	 * @return true, if successful
	 */
	public boolean validate() {
		try {
			return cedula != null && longitudCorrecta()
					&& ultimoDigitoEsValido();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Ultimo digito es valido.
	 *
	 * @return true, if successful
	 */
	private boolean ultimoDigitoEsValido() {
		return calcularUltimoDigito().equals(extraerUltimoDigito());
	}

	/**
	 * Extraer ultimo digito.
	 *
	 * @return the string
	 */
	private String extraerUltimoDigito() {
		return cedula.substring(9, 10);
	}

	/**
	 * Calcular ultimo digito.
	 *
	 * @return the string
	 */
	private String calcularUltimoDigito() {
		int sum = 0;
		int aux;
		for (int i = 0; i < coeficientes.length; i++) {
			int digito = Integer.parseInt(cedula.substring(i, i + 1));
			aux = digito * coeficientes[i];
			if (aux > 9) {
				aux -= 9;
			}
			sum += aux;
		}
		int mod = sum % 10;
		mod = (mod == 0) ? 10 : mod;
		final int res = (10 - mod);
		return String.valueOf(res);
	}

	/**
	 * Longitud correcta.
	 *
	 * @return true, if successful
	 */
	private boolean longitudCorrecta() {
		return cedula.length() == 10;
	}
}