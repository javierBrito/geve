package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;

/**
 * Created by javier.brito.
 */
public interface GvDocumentoDetalleDAO extends GenericDAO<GvDocumentoDetalle, Long> {
    public GvDocumentoDetalle buscarPorCodigo(long idDocumentoDetalle);
    public List<GvDocumentoDetalle> buscarListaPorParametros(GvDocumentoDetalle gvDocumentoDetalle);
	public List<GvDocumentoDetalle> buscarGvDocumentoDetalles();
    public List<GvDocumentoDetalle> buscarListaPorDocumento(GvDocumento gvDocumento);
}
