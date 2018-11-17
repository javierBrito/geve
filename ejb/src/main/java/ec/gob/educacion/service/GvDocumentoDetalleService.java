package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;

/**
 * Created by javier.brito.
 */
@Local
public interface GvDocumentoDetalleService {
    public GvDocumentoDetalle buscarPorCodigo(long idDocumentoDetalle);
    public List<GvDocumentoDetalle> buscarListaPorParametros(GvDocumentoDetalle gvDocumentoDetalle);
	public List<GvDocumentoDetalle> buscarGvDocumentoDetalles();
    public List<GvDocumentoDetalle> buscarListaPorDocumento(GvDocumento gvDocumento);
	public void crearGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception;
	public GvDocumentoDetalle actualizarGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception;
	public void eliminarGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception;
	public void eliminarGvDocumentoDetalle(long idDocumentoDetalle) throws Exception;
}
