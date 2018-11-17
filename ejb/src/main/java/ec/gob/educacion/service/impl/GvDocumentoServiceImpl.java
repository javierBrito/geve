package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvDocumentoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvDocumento;
import ec.gob.educacion.service.GvDocumentoService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvDocumentoServiceImpl implements GvDocumentoService {
    @EJB
    private GvDocumentoDAO gvDocumentoDAO;

    @Override
    public GvDocumento buscarPorCodigo(long idDocumento) {
        return gvDocumentoDAO.buscarPorCodigo(idDocumento);
    }

	@Override
	public List<GvDocumento> buscarListaPorParametros(GvDocumento gvDocumento) {
		return gvDocumentoDAO.buscarListaPorParametros(gvDocumento);
	}

	@Override
	public List<GvDocumento> buscarGvDocumentos(GvDocumento gvDocumento) {
		return gvDocumentoDAO.buscarGvDocumentos(gvDocumento);
	}

	@Override
	public void crearGvDocumento(GvDocumento gvDocumento) throws Exception {
		try {
			gvDocumentoDAO.persist(gvDocumento);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		}
	}

	@Override
	public GvDocumento actualizarGvDocumento(GvDocumento gvDocumento) throws Exception {
		try {
			return gvDocumentoDAO.update(gvDocumento);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
	}
}
