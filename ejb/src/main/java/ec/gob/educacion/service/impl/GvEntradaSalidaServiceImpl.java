package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvEntradaSalidaDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvEntradaSalida;
import ec.gob.educacion.service.GvEntradaSalidaService;

/**
 * Created by javierr.brito.
 */
@Stateless
public class GvEntradaSalidaServiceImpl implements GvEntradaSalidaService {
    @EJB
    private GvEntradaSalidaDAO gvEntradaSalidaDAO;

    @Override
    public GvEntradaSalida buscarPorCodigo(long idCajaDetalle) {
        return gvEntradaSalidaDAO.buscarPorCodigo(idCajaDetalle);
    }

	@Override
	public List<GvEntradaSalida> buscarListaPorParametros(GvEntradaSalida gvEntradaSalida) {
		return gvEntradaSalidaDAO.buscarListaPorParametros(gvEntradaSalida);
	}

	@Override
	public List<GvEntradaSalida> buscarGvEntradaSalidas() {
		return gvEntradaSalidaDAO.buscarGvEntradaSalidas();
	}

	@Override
	public List<GvEntradaSalida> buscarListaPorCaja(GvCaja gvCaja) {
		return gvEntradaSalidaDAO.buscarListaPorCaja(gvCaja);
	}

	@Override
	public void crearGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception {
		try {
			gvEntradaSalidaDAO.persist(gvEntradaSalida);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		}
	}

	@Override
	public GvEntradaSalida actualizarGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception {
		try {
			return gvEntradaSalidaDAO.update(gvEntradaSalida);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception {
		try {
			gvEntradaSalidaDAO.delete(gvEntradaSalida);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}

	@Override
	public void eliminarGvEntradaSalida(long idCajaDetalle) throws Exception {
		try {
			gvEntradaSalidaDAO.delete(idCajaDetalle);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}
}
