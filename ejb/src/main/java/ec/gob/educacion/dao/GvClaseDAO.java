package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvClase;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvClaseDAO extends GenericOracleAsignacionesDAO<GvClase, Long>{
    public GvClase buscarPorCodigo(long idClase);
    public GvClase buscarPorNombre(String descripcion);
	public List<GvClase> buscarGvClases();
	public void guardarGvClase(GvClase gvClase) throws EducacionDAOException;
}
