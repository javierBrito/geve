package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvDocumentoDetalleDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.model.geve.GvDocumentoDetalle;
import ec.gob.educacion.service.GvDocumentoDetalleService;

/**
 * Created by javierr.brito.
 */
@Stateless
public class GvDocumentoDetalleServiceImpl implements GvDocumentoDetalleService{
    @EJB
    private GvDocumentoDetalleDAO gvDocumentoDetalleDAO;

    @Override
    public GvDocumentoDetalle buscarPorCodigo(long idDocumentoDetalle) {
        return gvDocumentoDetalleDAO.buscarPorCodigo(idDocumentoDetalle);
    }

	@Override
	public List<GvDocumentoDetalle> buscarListaPorParametros(GvDocumentoDetalle gvDocumentoDetalle) {
		return gvDocumentoDetalleDAO.buscarListaPorParametros(gvDocumentoDetalle);
	}

	@Override
	public List<GvDocumentoDetalle> buscarGvDocumentoDetalles() {
		return gvDocumentoDetalleDAO.buscarGvDocumentoDetalles();
	}

	@Override
	public List<GvDocumentoDetalle> buscarListaPorDocumento(GvDocumento gvDocumento) {
		return gvDocumentoDetalleDAO.buscarListaPorDocumento(gvDocumento);
	}

	@Override
	public void crearGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception {
		try {
			gvDocumentoDetalleDAO.persist(gvDocumentoDetalle);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		}
	}

	@Override
	public GvDocumentoDetalle actualizarGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception {
		try {
			return gvDocumentoDetalleDAO.update(gvDocumentoDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvDocumentoDetalle(GvDocumentoDetalle gvDocumentoDetalle) throws Exception {
		try {
			gvDocumentoDetalleDAO.delete(gvDocumentoDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvDocumentoDetalle(long idDocumentoDetalle) throws Exception {
		try {
			gvDocumentoDetalleDAO.delete(idDocumentoDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}
}
