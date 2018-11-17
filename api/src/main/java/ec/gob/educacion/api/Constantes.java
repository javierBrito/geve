package ec.gob.educacion.api;

import java.math.BigDecimal;

/**
 * Created by Marco on 22/12/2014.
 */
public interface Constantes {
    public static final String FECHA_INICIO_CLASES = "2015-05-05";
    public static final int INS_GRADO_ESTADO = 1;
    public static final int COD_INC_REG_ANI_LEC =40;
    public static final String TIPOS_PARENTESCOS = "PARE";
    public static final Integer CODIGO_PARENTESCO_PADRE = 19;
    public static final Integer CODIGO_PARENTESCO_MADRE = 20;
    public static final String ESTADOS_CIVILES = "ESCI";
    public static final String REPRESENTANTELEGAL_REFERENCIALCONVENCIONAL_POSI = "S";
    public static final String REPRESENTANTELEGAL_REFERENCIALCONVENCIONAL_NEG = "N";
    public static final Integer CODIGO_PARENTESCO_AUTOREPRESENTACION = 60;
    public static final Integer CODIGO_PARENTESCO_OTRO = 86;
    public static final int REGISTRO_ACTIVO_ASIGCEN = 1;
    public static final int REGISTRO_INACTIVO_ASIGCEN = 0;
    public static final long CODIGO_JORNADA_NOCTURNA = 2;
    public static final String INS_INC_ANIO_LECTIVO_ESTADO = "A";
    public static final Long TIT_REFRENDACION_VALIDADO = 1L;
    //CONSTANTE ESTADO ACIVO
    public static final long ESTADO_ACTIVO = 1;
    public static final long PARTICIPACION_CIUDADANA_NO_CUMPLE = 15;
    public static final int LONGITUD_CODIGO_REFRENDACION = 8;
    public static final BigDecimal CODIGO_REFRENDACION_INICIAL = new BigDecimal(4473983);
    //CÓDIGOS DE REGISTRO ACTIVO E INACTIVO
    public static final boolean REGISTRO_ACTIVO_ESQUEMA_NUEVO = true;
    public static final long INS_INC_ANIO_LECTIVO_CODIGO_ACTUAL=22;
}
