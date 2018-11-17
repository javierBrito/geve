package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvParametroDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvParametro;
import ec.gob.educacion.service.GvParametroService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvParametroServiceImpl implements GvParametroService{
    @EJB
    private GvParametroDAO gvParametroDAO;

    @Override
    public GvParametro buscarPorCodigo(long idParametro) {
        return gvParametroDAO.buscarPorCodigo(idParametro);
    }

	@Override
	public List<GvParametro> buscarListaPorParametros(GvParametro gvParametro) {
		return gvParametroDAO.buscarListaPorParametros(gvParametro);
	}

    @Override
    public GvParametro buscarPorDescripcion(String descripcion) {
        return gvParametroDAO.buscarPorDescripcion(descripcion);
    }

	@Override
	public List<GvParametro> buscarListaPorDescripcion(String descripcion) {
		return gvParametroDAO.buscarListaPorDescripcion(descripcion);
	}

    @Override
    public GvParametro buscarPorNombre(String nombre) {
        return gvParametroDAO.buscarPorNombre(nombre);
    }

	@Override
	public List<GvParametro> buscarGvParametros() {
		return gvParametroDAO.buscarGvParametros();
	}

	@Override
	public GvParametro guardarGvParametro(GvParametro gvParametro) throws Exception {
		try {
			gvParametroDAO.guardarGvParametro(gvParametro);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvParametro;
	}
}
