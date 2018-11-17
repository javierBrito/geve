package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvCajaDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.service.GvCajaService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvCajaServiceImpl implements GvCajaService {
    @EJB
    private GvCajaDAO gvCajaDAO;

    @Override
    public GvCaja buscarPorCodigo(long idCaja) {
        return gvCajaDAO.buscarPorCodigo(idCaja);
    }

	@Override
	public List<GvCaja> buscarListaPorParametros(GvCaja gvCaja) {
		return gvCajaDAO.buscarListaPorParametros(gvCaja);
	}

	@Override
	public List<GvCaja> buscarUltimoRegistro(GvCaja gvCaja) {
		return gvCajaDAO.buscarUltimoRegistro(gvCaja);
	}

	@Override
	public List<GvCaja> buscarGvCajas(GvCaja gvCaja) {
		return gvCajaDAO.buscarGvCajas(gvCaja);
	}

	@Override
	public void crearGvCaja(GvCaja gvCaja) throws Exception {
		try {
			gvCajaDAO.persist(gvCaja);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		}
	}

	@Override
	public GvCaja actualizarGvCaja(GvCaja gvCaja) throws Exception {
		try {
			return gvCajaDAO.update(gvCaja);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}
}
