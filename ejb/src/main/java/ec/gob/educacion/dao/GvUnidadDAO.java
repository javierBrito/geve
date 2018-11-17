package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvUnidad;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvUnidadDAO extends GenericOracleAsignacionesDAO<GvUnidad, Long>{
    public GvUnidad buscarPorCodigo(long idUnidad);
    public GvUnidad buscarPorNombre(String descripcion);
	public List<GvUnidad> buscarGvUnidades();
	public void guardarGvUnidad(GvUnidad gvUnidad) throws EducacionDAOException;
}
