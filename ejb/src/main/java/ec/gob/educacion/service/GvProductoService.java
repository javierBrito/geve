package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvProducto;

/**
 * Created by javier.brito.
 */
@Local
public interface GvProductoService {
    public GvProducto buscarPorCodigo(long idEmpresa, long idProducto);
    public GvProducto buscarPorDescripcion(String descripcion);
    public List<GvProducto> buscarListaPorDescripcion(GvProducto gvProducto);
    public GvProducto buscarPorCodigo(long idEmpresa, String codigo);
	public List<GvProducto> buscarGvProductos(GvProducto gvProducto);
	public GvProducto guardarGvProducto(GvProducto gvProducto) throws Exception;
}
