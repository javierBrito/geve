package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvProductoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvProducto;
import ec.gob.educacion.service.GvProductoService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvProductoServiceImpl implements GvProductoService{
    @EJB
    private GvProductoDAO gvProductoDAO;

    @Override
    public GvProducto buscarPorCodigo(long idEmpresa, long idProducto) {
        return gvProductoDAO.buscarPorCodigo(idEmpresa, idProducto);
    }

    @Override
    public GvProducto buscarPorDescripcion(String descripcion) {
        return gvProductoDAO.buscarPorDescripcion(descripcion);
    }

	@Override
	public List<GvProducto> buscarListaPorDescripcion(GvProducto gvProducto) {
		return gvProductoDAO.buscarListaPorDescripcion(gvProducto);
	}

    @Override
    public GvProducto buscarPorCodigo(long idEmpresa, String codigo) {
        return gvProductoDAO.buscarPorCodigo(idEmpresa, codigo);
    }

	@Override
	public List<GvProducto> buscarGvProductos(GvProducto gvProducto) {
		return gvProductoDAO.buscarGvProductos(gvProducto);
	}

	@Override
	public GvProducto guardarGvProducto(GvProducto gvProducto) throws Exception {
		try {
			gvProductoDAO.guardarGvProducto(gvProducto);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvProducto;
	}
}
