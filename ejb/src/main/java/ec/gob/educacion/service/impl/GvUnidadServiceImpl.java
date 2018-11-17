package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvUnidadDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvUnidad;
import ec.gob.educacion.service.GvUnidadService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvUnidadServiceImpl implements GvUnidadService{
    @EJB
    private GvUnidadDAO gvUnidadDAO;

    @Override
    public GvUnidad buscarPorCodigo(long idUnidad) {
        return gvUnidadDAO.buscarPorCodigo(idUnidad);
    }

    @Override
    public GvUnidad buscarPorNombre(String descripcion) {
        return gvUnidadDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvUnidad> buscarGvUnidades() {
		return gvUnidadDAO.buscarGvUnidades();
	}

	@Override
	public GvUnidad guardarGvUnidad(GvUnidad gvUnidad) throws Exception {
		try {
			gvUnidadDAO.guardarGvUnidad(gvUnidad);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvUnidad;
	}
}
