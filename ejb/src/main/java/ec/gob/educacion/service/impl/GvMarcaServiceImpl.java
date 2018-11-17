package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvMarcaDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvMarca;
import ec.gob.educacion.service.GvMarcaService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvMarcaServiceImpl implements GvMarcaService{
    @EJB
    private GvMarcaDAO gvMarcaDAO;

    @Override
    public GvMarca buscarPorCodigo(long idMarca) {
        return gvMarcaDAO.buscarPorCodigo(idMarca);
    }

    @Override
    public GvMarca buscarPorNombre(String descripcion) {
        return gvMarcaDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvMarca> buscarGvMarcas() {
		return gvMarcaDAO.buscarGvMarcas();
	}

	@Override
	public GvMarca guardarGvMarca(GvMarca gvMarca) throws Exception {
		try {
			gvMarcaDAO.guardarGvMarca(gvMarca);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvMarca;
	}
}
