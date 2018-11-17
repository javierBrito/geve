package ec.gob.educacion.service.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import ec.gob.educacion.dao.GvKardexDAO;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvKardex;
import ec.gob.educacion.service.GvKardexService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvKardexServiceImpl implements GvKardexService{
    @EJB
    private GvKardexDAO gvKardexDAO;

    @Override
    public GvKardex buscarPorCodigo(long idKardex) {
        return gvKardexDAO.buscarPorCodigo(idKardex);
    }

	@Override
	public List<GvKardex> buscarListaPorParametros(GvKardex gvKardex) {
		return gvKardexDAO.buscarListaPorParametros(gvKardex);
	}

	@Override
	public List<GvKardexFechaDTO> buscarListaPorFechaSaldos(GvKardex gvKardex) {
		return gvKardexDAO.buscarListaPorFechaSaldos(gvKardex);
	}

	@Override
	public List<GvKardexFechaDTO> buscarListaStockPorFecha(GvKardex gvKardex) {
		return gvKardexDAO.buscarListaStockPorFecha(gvKardex);
	}

	@Override
	public List<GvKardexFechaDTO> buscarListaStockPorProducto(GvKardex gvKardex) {
		return gvKardexDAO.buscarListaStockPorProducto(gvKardex);
	}

	@Override
	public List<GvKardex> buscarGvKardexs(GvKardex gvKardex) {
		return gvKardexDAO.buscarGvKardexs(gvKardex);
	}

	@Override
	public GvKardex guardarGvKardex(GvKardex gvKardex) throws Exception {
		try {
			gvKardexDAO.guardarGvKardex(gvKardex);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvKardex;
	}
}
