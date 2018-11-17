package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvEstadoDocumentoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvEstadoDocumento;
import ec.gob.educacion.service.GvEstadoDocumentoService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvEstadoDocumentoServiceImpl implements GvEstadoDocumentoService{
    @EJB
    private GvEstadoDocumentoDAO gvEstadoDocumentoDAO;

    @Override
    public GvEstadoDocumento buscarPorCodigo(long idEstadoDocumento) {
        return gvEstadoDocumentoDAO.buscarPorCodigo(idEstadoDocumento);
    }

    @Override
    public GvEstadoDocumento buscarPorNombre(String descripcion) {
        return gvEstadoDocumentoDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvEstadoDocumento> buscarGvEstadoDocumentos() {
		return gvEstadoDocumentoDAO.buscarGvEstadoDocumentos();
	}

	@Override
	public GvEstadoDocumento guardarGvEstadoDocumento(GvEstadoDocumento gvEstadoDocumento) throws Exception {
		try {
			gvEstadoDocumentoDAO.guardarGvEstadoDocumento(gvEstadoDocumento);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvEstadoDocumento;
	}
}
