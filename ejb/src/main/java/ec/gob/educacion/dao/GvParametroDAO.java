package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvParametro;

/**
 * Created by javier.brito
 */
public interface GvParametroDAO extends GenericOracleAsignacionesDAO<GvParametro, Long>{
    public GvParametro buscarPorCodigo(long idParametro);
    public List<GvParametro> buscarListaPorParametros(GvParametro gvParametro);
    public GvParametro buscarPorDescripcion(String descripcion);
    public List<GvParametro> buscarListaPorDescripcion(String descripcion);
    public GvParametro buscarPorNombre(String nombre);
	public List<GvParametro> buscarGvParametros();
	public void guardarGvParametro(GvParametro gvParametro) throws EducacionDAOException;
}
