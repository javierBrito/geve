package ec.gob.educacion.api;


import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by marco.almeida on 15/12/2014.
 */
public class Utils {

    private NumerosLetras numerosLetras;

    public Utils () {
        this.numerosLetras = new NumerosLetras();
    }

    public Integer calcularEdad(Timestamp fechaNacimiento){
        Date date = new Date(fechaNacimiento.getTime());
        Date ahora = new Date();
        DateTime dt = new DateTime(date);
        DateTime now = new DateTime();
        Integer anioActual = now.getYear();
        Integer anioNacimiento = dt.getYear();
        Integer diferencia = anioActual - anioNacimiento;

        LocalDate dateTimeAhora = new LocalDate(now);
        LocalDate dateTimeFechaNacimiento = new LocalDate(dt);

        Period period = new Period(dateTimeFechaNacimiento, dateTimeAhora, PeriodType.yearMonthDay());

        return diferencia;
    }

    public int[] edadAFuturoArreglo(Timestamp fechaNacimiento, String fechaFuturo){
        int[] edad = new int[3];
        Date fechaNacimientoDate = new Date(fechaNacimiento.getTime());
        DateTime fechaNacimientoDateTime = new DateTime(fechaNacimientoDate);
        LocalDate fechaNacimientoLocalDate = new LocalDate(fechaNacimientoDateTime);
        LocalDate fechaAFuturo = new LocalDate(fechaFuturo);
        Period period = new Period(fechaNacimientoLocalDate, fechaAFuturo, PeriodType.yearMonthDay());
        edad[0]= period.getYears();
        edad[1]= period.getMonths();
        edad[2]= period.getDays();
        return edad;
    }

    public String calcularEdadStr(Timestamp fechaNacimiento){
        Date date = new Date(fechaNacimiento.getTime());
        DateTime dt = new DateTime(date);
        DateTime now = new DateTime();

        LocalDate dateTimeAhora = new LocalDate(now);
        LocalDate dateTimeFechaNacimiento = new LocalDate(dt);

        Period period = new Period(dateTimeFechaNacimiento, dateTimeAhora, PeriodType.yearMonthDay());

        String edad = period.getYears() + " años " + period.getMonths() + " meses";
        return edad;
    }

    public String obtenerFechaNacimientoStr(Timestamp fechaNacimiento){
        Date date = new Date(fechaNacimiento.getTime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String fechaNacimientoStr = simpleDateFormat.format(date);
        return fechaNacimientoStr;
    }

    public Integer calculoEdadFechaFuturo(Timestamp fechaNacimiento, String fechaFuturo){
        Date fechaNacimientoDate = new Date(fechaNacimiento.getTime());
        DateTime fechaNacimientoDateTime = new DateTime(fechaNacimientoDate);
        LocalDate fechaNacimientoLocalDate = new LocalDate(fechaNacimientoDateTime);
        LocalDate fechaAFuturo = new LocalDate(fechaFuturo);
        Period period = new Period(fechaNacimientoLocalDate, fechaAFuturo, PeriodType.months());
        Integer edadMeses = period.getMonths();
        return edadMeses;
    }

    /**
     * M&eacute;todo que agrega un mensaje de error en pantalla
     *
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeError(String resumen, String detalle) {
        FacesMessage errorMessage = new FacesMessage();
        errorMessage.setSummary(resumen);
        errorMessage.setDetail(detalle == null ? detalle : detalle);
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, errorMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de error en pantalla, y adicionalmente
     * indica que la validacion ha sido fallida
     *
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeError(String id, String resumen, String detalle, boolean validacionFallida) {
        agregarMensajeError(id, resumen, detalle);
        if(validacionFallida) {
            FacesContext.getCurrentInstance().validationFailed();
        }
    }

    /**
     * M&eacute;todo que agrega un mensaje de error en pantalla
     *
     * @param id Identificador del componente donde se mostrará el mensaje
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeError(String id, String resumen, String detalle) {
        FacesMessage errorMessage = new FacesMessage();
        errorMessage.setSummary(resumen);
        errorMessage.setDetail(detalle == null ? detalle : detalle);
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(id, errorMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de error en pantalla
     *
     * @param ex Excepcion que tiene la causa del error
     */
    public void agregarMensajeError(Exception ex) {
        FacesMessage errorMessage = new FacesMessage();
        errorMessage.setSummary(ex.getMessage());
        errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(null, errorMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de informaci&oacute;n en pantalla
     *
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeInformacion(String resumen, String detalle) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(resumen);
        infoMessage.setDetail(detalle == null ? detalle : detalle);
        infoMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, infoMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de informaci&oacute;n en pantalla
     *
     * @param id Identificador del componente donde se mostrará el mensaje
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeInformacion(String id, String resumen, String detalle) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(resumen);
        infoMessage.setDetail(detalle == null ? detalle : detalle);
        infoMessage.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(id, infoMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de advertencia en pantalla
     *
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeAdvertencia(String resumen, String detalle) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(resumen);
        infoMessage.setDetail(detalle == null ? detalle : detalle);
        infoMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext.getCurrentInstance().addMessage(null, infoMessage);
    }

    /**
     * M&eacute;todo que agrega un mensaje de advertencia en pantalla
     *
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     * @param enModal Especifica si es que estuvo en un modal el mensaje
     */
    public void agregarMensajeAdvertencia(String resumen, String detalle, boolean enModal) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(resumen);
        infoMessage.setDetail(detalle == null ? detalle : detalle);
        infoMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        if(enModal) {
            FacesContext.getCurrentInstance().addMessage("idMensaje", infoMessage);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, infoMessage);
        }
    }

    /**
     * M&eacute;todo que agrega un mensaje de advertencia en pantalla
     *
     * @param id Identificador del componente donde se mostrará el mensaje
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    public void agregarMensajeAdvertencia(String id, String resumen, String detalle) {
        FacesMessage infoMessage = new FacesMessage();
        infoMessage.setSummary(resumen);
        infoMessage.setDetail(detalle == null ? detalle : detalle);
        infoMessage.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext.getCurrentInstance().addMessage(id, infoMessage);
    }

    /**
     * M&eacute;todo que devuelve el objeto request de la navegaci&oacuten;

     * @return Objeto HttpServletRequest de la petici&oacute;n
     */
    public HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    /**
     * M&eacute;todo que devuelve el valor de un par&aacute;metro enviado desde
     * el request de la p&aacute;gina
     *
     * @param parameter Nombre del par&aacute;metro
     * @return String con el valor del par&aacute;metro enviado
     */
    public String getHttpRequestParameter(final String parameter) {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(parameter);
    }

    /**
     * Retorna la sesi&oacute;n web del usuario
     *
     * @return Objeto HttpSession con la sesi&oacute;n
     */
    public HttpSession getHttpSession() {
        return (HttpSession) getExternalContext().getSession(false);
    }

    /**
     * Devuelve el contexto externo de la aplicaci&oacute;n web
     *
     * @return Objeto ExternalContext con el contexto
     */
    public ExternalContext getExternalContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * Obtiene el valor de context path.
     *
     * @return valor de context path
     */
    public String getContextPath() {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getRequestContextPath();
    }

    /**
     * Devuelve el mapa de valores de la sesi&oacute;n del usuario
     *
     * @return Objeto Map con los valores de la sesi&oacute;n
     */
    public Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    /**
     * Redirecciona a una p&aacute;gina diferente de la navegaci&oacute;n normal
     * de la aplicaci&oacute;n
     *
     * @param pagina Direcci&oacute;n a redireccionar
     */
    public void redireccionarPagina(String pagina) {
        try {
            getExternalContext().redirect(getHttpRequest().getContextPath().concat(pagina));
        } catch (IOException e) {
            agregarMensajeError("No se puede redireccionar a " + pagina, e.getLocalizedMessage());
        }
    }

    public boolean isNumeric(String cadena){
        try {
            Long.parseLong(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public NumerosLetras getNumerosLetras() {
        return numerosLetras;
    }

    public void setNumerosLetras(NumerosLetras numerosLetras) {
        this.numerosLetras = numerosLetras;
    }
}
