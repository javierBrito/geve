package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvProducto;

/**
 * Created by javier.brito
 */
public interface GvProductoDAO extends GenericOracleAsignacionesDAO<GvProducto, Long>{
    public GvProducto buscarPorCodigo(long idEmpresa, long idProducto);
    public GvProducto buscarPorDescripcion(String descripcion);
    public List<GvProducto> buscarListaPorDescripcion(GvProducto gvProducto);
    public GvProducto buscarPorCodigo(long idEmpresa, String codigo);
	public List<GvProducto> buscarGvProductos(GvProducto gvProducto);
	public void guardarGvProducto(GvProducto gvProducto) throws EducacionDAOException;
}
