package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvMarca;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvMarcaDAO extends GenericOracleAsignacionesDAO<GvMarca, Long>{
    public GvMarca buscarPorCodigo(long idMarca);
    public GvMarca buscarPorNombre(String descripcion);
	public List<GvMarca> buscarGvMarcas();
	public void guardarGvMarca(GvMarca gvMarca) throws EducacionDAOException;
}
