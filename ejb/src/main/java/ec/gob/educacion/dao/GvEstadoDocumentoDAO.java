package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvEstadoDocumento;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvEstadoDocumentoDAO extends GenericOracleAsignacionesDAO<GvEstadoDocumento, Long>{
    public GvEstadoDocumento buscarPorCodigo(long idEstadoDocumento);
    public GvEstadoDocumento buscarPorNombre(String descripcion);
	public List<GvEstadoDocumento> buscarGvEstadoDocumentos();
	public void guardarGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) throws EducacionDAOException;
}
