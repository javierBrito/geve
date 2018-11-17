package ec.gob.educacion.api;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by marco.almeida on 12/12/2014.
 */
public class Persona {

    private BigDecimal cedula;
    private String nombres;
    private Timestamp fechaNacimiento;
    private String genero;
    private Integer edadActual;
    private String edadActualStr;
    private String anioEscolar;
    private String fechaNacimientoStr;
    private String estadoCivil;

    public BigDecimal getCedula() {
        return cedula;
    }

    public void setCedula(BigDecimal cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdadActual() {
        return edadActual;
    }

    public void setEdadActual(Integer edadActual) {
        this.edadActual = edadActual;
    }

    public String getAnioEscolar() {
        return anioEscolar;
    }

    public void setAnioEscolar(String anioEscolar) {
        this.anioEscolar = anioEscolar;
    }

    public String getFechaNacimientoStr() {
        return fechaNacimientoStr;
    }

    public void setFechaNacimientoStr(String fechaNacimientoStr) {
        this.fechaNacimientoStr = fechaNacimientoStr;
    }

    public String getEdadActualStr() {
        return edadActualStr;
    }

    public void setEdadActualStr(String edadActualStr) {
        this.edadActualStr = edadActualStr;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }
}
