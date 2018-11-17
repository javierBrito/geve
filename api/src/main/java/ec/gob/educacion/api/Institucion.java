package ec.gob.educacion.api;

/**
 * Created by Marco on 13/01/2015.
 */
public class Institucion {
    private long codigo;
    private String amie;
    private String descripcion;
    private String provincia;
    private String canton;
    private String parroquia;
    private String tipo;
    private String especialidad;
    private Integer cantidad;

    public Institucion(){

    }

    public Institucion(long codigo ,String amie,String descripcion,String provincia,String canton,String parroquia){
        this.codigo =codigo;
        this.amie=amie;
        this.descripcion=descripcion;
        this.provincia=provincia;
        this.canton=canton;
        this.parroquia=parroquia;

    }

    public String getAmie() {
        return amie;
    }

    public void setAmie(String amie) {
        this.amie = amie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
}
