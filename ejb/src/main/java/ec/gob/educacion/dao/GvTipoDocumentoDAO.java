package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoDocumento;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvTipoDocumentoDAO extends GenericOracleAsignacionesDAO<GvTipoDocumento, Long>{
    public GvTipoDocumento buscarPorCodigo(long idTipoDocumento);
    public GvTipoDocumento buscarPorNombre(String descripcion);
	public List<GvTipoDocumento> buscarGvTipoDocumentos();
	public void guardarGvTipoDocumento(GvTipoDocumento gvTipoDocumento) throws EducacionDAOException;
}
