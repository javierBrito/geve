package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvDocumento;

/**
 * Created by javier.brito.
 */
public interface GvDocumentoDAO extends GenericDAO<GvDocumento, Long> {
    public GvDocumento buscarPorCodigo(long idDocumento);
    public List<GvDocumento> buscarListaPorParametros(GvDocumento gvDocumento);
	public List<GvDocumento> buscarGvDocumentos(GvDocumento gvDocumento);
}
