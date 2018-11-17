package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoNominacion;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvTipoNominacionDAO extends GenericOracleAsignacionesDAO<GvTipoNominacion, Long>{
    public GvTipoNominacion buscarPorCodigo(long idTipoNominacion);
    public GvTipoNominacion buscarPorNombre(String descripcion);
	public List<GvTipoNominacion> buscarGvTipoNominaciones();
	public void guardarGvTipoNominacion(GvTipoNominacion gvTipoNominacion) throws EducacionDAOException;
}
