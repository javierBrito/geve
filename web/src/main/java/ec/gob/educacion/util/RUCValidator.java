package ec.gob.educacion.util;

/**
 * The Class RUCValidator.
 */
public class RUCValidator {
	/** The ruc numero. */
	private String rucNumero;
	/** The Constant TRES_ULTIMOS_RUC. */
	static final String TRES_ULTIMOS_RUC = "001";
	private Integer[] coeficiente;

	/**
	 * Instantiates a new RUC validator.
	 * 
	 * @param rucNumero
	 *            the ruc numero
	 */
	public RUCValidator(String rucNumero) {
		super();
		this.rucNumero = rucNumero;
	}

	/**
	 * Checks if is valid.
	 * 
	 * @return true, if is valid
	 */
	public boolean isValid() {
		return (numeroCaracteresCorrecto() && rucEsCorrecto());
	}

	private boolean rucEsCorrecto() {

		if (rucNumero.charAt(2) == '9') {
			coeficiente = new Integer[] { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
			return validarRuc(coeficiente, obtenerValorInt(rucNumero.charAt(9)))
					&& tresUltimosNumerosCorrectos(obtenerTresUltimosNumeros());
		} else if (rucNumero.charAt(2) == '6') {
			coeficiente = new Integer[] { 3, 2, 7, 6, 5, 4, 3, 2 };
			return validarRuc(coeficiente, obtenerValorInt(rucNumero.charAt(8)))
					&& tresUltimosNumerosCorrectos(obtenerTresUltimosNumeros());
		} else {
			return validarCedula(obtenerCedula())
					&& tresUltimosNumerosCorrectos(obtenerTresUltimosNumeros());
		}

	}

	private int obtenerValorInt(char charAt) {
		return Character.getNumericValue(charAt);
	}

	private boolean validarRuc(Integer[] coeficiente2, int i) {
		int resultado = 0;
		int valor = 0;
		int residuo = 0;
		for (int j = 0; j < coeficiente2.length; j++) {
			valor = coeficiente2[j] * obtenerValorInt(rucNumero.charAt(j));
			resultado = resultado + valor;
		}
		residuo = resultado % 11;
		if (residuo == 0) {
			return true;
		}
		return (11 - residuo) == i;

	}

	private String obtenerTresUltimosNumeros() {
		return rucNumero.substring(10, 13);
	}

	private String obtenerCedula() {
		return rucNumero.substring(0, 10);
	}

	/**
	 * Numero caracteres correcto.
	 * 
	 * @return true, if successful
	 */
	private boolean numeroCaracteresCorrecto() {
		return rucNumero != null && rucNumero.length() == 13;
	}

	private boolean validarCedula(String cedula) {
		CedulaValidator validadorCedula = new CedulaValidator(cedula);
		return validadorCedula.validate();
	}

	private boolean tresUltimosNumerosCorrectos(String tresUltimos) {
		return tresUltimos.equals(TRES_ULTIMOS_RUC);
	}
}