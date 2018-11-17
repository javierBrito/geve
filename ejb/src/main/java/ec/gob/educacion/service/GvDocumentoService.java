package ec.gob.educacion.service;

import java.util.List;
import javax.ejb.Local;
import ec.gob.educacion.model.geve.GvDocumento;

/**
 * Created by javier.brito.
 */
@Local
public interface GvDocumentoService {
    public GvDocumento buscarPorCodigo(long idDocumento);
    public List<GvDocumento> buscarListaPorParametros(GvDocumento gvDocumento);
	public List<GvDocumento> buscarGvDocumentos(GvDocumento gvDocumento);
	public void crearGvDocumento(GvDocumento gvDocumento) throws Exception;
	public GvDocumento actualizarGvDocumento(GvDocumento gvDocumento) throws Exception;
}
