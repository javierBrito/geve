package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvCajaDetalleDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvCajaDetalle;
import ec.gob.educacion.service.GvCajaDetalleService;

/**
 * Created by javierr.brito.
 */
@Stateless
public class GvCajaDetalleServiceImpl implements GvCajaDetalleService {
    @EJB
    private GvCajaDetalleDAO gvCajaDetalleDAO;

    @Override
    public GvCajaDetalle buscarPorCodigo(long idCajaDetalle) {
        return gvCajaDetalleDAO.buscarPorCodigo(idCajaDetalle);
    }

	@Override
	public List<GvCajaDetalle> buscarListaPorParametros(GvCajaDetalle gvCajaDetalle) {
		return gvCajaDetalleDAO.buscarListaPorParametros(gvCajaDetalle);
	}

	@Override
	public List<GvCajaDetalle> buscarGvCajaDetalles() {
		return gvCajaDetalleDAO.buscarGvCajaDetalles();
	}

	@Override
	public List<GvCajaDetalle> buscarListaPorCaja(GvCaja gvCaja) {
		return gvCajaDetalleDAO.buscarListaPorCaja(gvCaja);
	}

	@Override
	public void crearGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception {
		try {
			gvCajaDetalleDAO.persist(gvCajaDetalle);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		}
	}

	@Override
	public GvCajaDetalle actualizarGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception {
		try {
			return gvCajaDetalleDAO.update(gvCajaDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception {
		try {
			gvCajaDetalleDAO.delete(gvCajaDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvCajaDetalle(long idCajaDetalle) throws Exception {
		try {
			gvCajaDetalleDAO.delete(idCajaDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}
}
