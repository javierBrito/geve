package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvClaseDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvClase;
import ec.gob.educacion.service.GvClaseService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvClaseServiceImpl implements GvClaseService{
    @EJB
    private GvClaseDAO gvClaseDAO;

    @Override
    public GvClase buscarPorCodigo(long idClase) {
        return gvClaseDAO.buscarPorCodigo(idClase);
    }

    @Override
    public GvClase buscarPorNombre(String descripcion) {
        return gvClaseDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvClase> buscarGvClases() {
		return gvClaseDAO.buscarGvClases();
	}

	@Override
	public GvClase guardarGvClase(GvClase gvClase) throws Exception {
		try {
			gvClaseDAO.guardarGvClase(gvClase);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvClase;
	}
}
