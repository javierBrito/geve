package ec.gob.educacion.controller;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
	
	/** 
     * M&eacute;todo que agrega un mensaje de error en pantalla 
     * 
     * @param resumen Mensaje a mostrar
     * @param detalle Detalle del mensaje
     */
    protected void agregarMensajeError(String resumen, String detalle) {
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
    protected void agregarMensajeError(String id, String resumen, String detalle, boolean validacionFallida) {
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
     protected void agregarMensajeError(String id, String resumen, String detalle) {
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
     protected void agregarMensajeError(Exception ex) {
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
    protected void agregarMensajeInformacion(String resumen, String detalle) {
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
    protected void agregarMensajeInformacion(String id, String resumen, String detalle) {
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
    protected void agregarMensajeAdvertencia(String resumen, String detalle) {
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
    protected void agregarMensajeAdvertencia(String resumen, String detalle, boolean enModal) {
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
    protected void agregarMensajeAdvertencia(String id, String resumen, String detalle) {
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
    protected String getHttpRequestParameter(final String parameter) {
        return FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(parameter);
    }

    /**
     * Retorna la sesi&oacute;n web del usuario
     * 
     * @return Objeto HttpSession con la sesi&oacute;n
     */
    protected HttpSession getHttpSession() {
        return (HttpSession) getExternalContext().getSession(false);
    }

    /**
     * Devuelve el contexto externo de la aplicaci&oacute;n web
     * 
     * @return Objeto ExternalContext con el contexto
     */
    protected ExternalContext getExternalContext() {
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
    protected void redireccionarPagina(String pagina) {
        try {
            getExternalContext().redirect(getHttpRequest().getContextPath().concat(pagina));         
        } catch (IOException e) {
            agregarMensajeError("No se puede redireccionar a " + pagina, e.getLocalizedMessage());
        }
    }

}
