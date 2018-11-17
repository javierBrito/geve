package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvTipoDocumento;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvTipoDocumentoService {
    public GvTipoDocumento buscarPorCodigo(long idTipoDocumento);
    public GvTipoDocumento buscarPorNombre(String descripcion);
	public List<GvTipoDocumento> buscarGvTipoDocumentos();
	public GvTipoDocumento guardarGvTipoDocumento(GvTipoDocumento gvTipoDocumento) throws Exception;
}
