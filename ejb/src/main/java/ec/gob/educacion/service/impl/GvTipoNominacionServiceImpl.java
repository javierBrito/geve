package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvTipoNominacionDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvTipoNominacion;
import ec.gob.educacion.service.GvTipoNominacionService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvTipoNominacionServiceImpl implements GvTipoNominacionService{
    @EJB
    private GvTipoNominacionDAO gvTipoNominacionDAO;

    @Override
    public GvTipoNominacion buscarPorCodigo(long idTipoNominacion) {
        return gvTipoNominacionDAO.buscarPorCodigo(idTipoNominacion);
    }

    @Override
    public GvTipoNominacion buscarPorNombre(String descripcion) {
        return gvTipoNominacionDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvTipoNominacion> buscarGvTipoNominaciones() {
		return gvTipoNominacionDAO.buscarGvTipoNominaciones();
	}

	@Override
	public GvTipoNominacion guardarGvTipoNominacion(GvTipoNominacion gvTipoNominacion) throws Exception {
		try {
			gvTipoNominacionDAO.guardarGvTipoNominacion(gvTipoNominacion);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvTipoNominacion;
	}
}
