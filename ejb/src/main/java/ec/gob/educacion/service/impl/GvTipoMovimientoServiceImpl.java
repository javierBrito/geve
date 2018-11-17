package ec.gob.educacion.service.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ec.gob.educacion.dao.GvTipoMovimientoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvTipoMovimiento;
import ec.gob.educacion.service.GvTipoMovimientoService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvTipoMovimientoServiceImpl implements GvTipoMovimientoService{
    @EJB
    private GvTipoMovimientoDAO gvTipoMovimientoDAO;

    @Override
    public GvTipoMovimiento buscarPorCodigo(long idTipoMovimiento) {
        return gvTipoMovimientoDAO.buscarPorCodigo(idTipoMovimiento);
    }

    @Override
    public GvTipoMovimiento buscarPorNombre(String descripcion) {
        return gvTipoMovimientoDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvTipoMovimiento> buscarGvTipoMovimientos() {
		return gvTipoMovimientoDAO.buscarGvTipoMovimientos();
	}

	@Override
	public GvTipoMovimiento guardarGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) throws Exception {
		try {
			gvTipoMovimientoDAO.guardarGvTipoMovimiento(gvTipoMovimiento);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvTipoMovimiento;
	}
}
