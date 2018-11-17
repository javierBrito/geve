package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvEstadoDocumento;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvEstadoDocumentoService {
    public GvEstadoDocumento buscarPorCodigo(long idEstadoDocumento);
    public GvEstadoDocumento buscarPorNombre(String descripcion);
	public List<GvEstadoDocumento> buscarGvEstadoDocumentos();
	public GvEstadoDocumento guardarGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) throws Exception;
}
