package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvTipoDocumentoDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvTipoDocumento;
import ec.gob.educacion.service.GvTipoDocumentoService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvTipoDocumentoServiceImpl implements GvTipoDocumentoService{
    @EJB
    private GvTipoDocumentoDAO gvTipoDocumentoDAO;

    @Override
    public GvTipoDocumento buscarPorCodigo(long idTipoDocumento) {
        return gvTipoDocumentoDAO.buscarPorCodigo(idTipoDocumento);
    }

    @Override
    public GvTipoDocumento buscarPorNombre(String descripcion) {
        return gvTipoDocumentoDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvTipoDocumento> buscarGvTipoDocumentos() {
		return gvTipoDocumentoDAO.buscarGvTipoDocumentos();
	}

	@Override
	public GvTipoDocumento guardarGvTipoDocumento(GvTipoDocumento gvTipoDocumento) throws Exception {
		try {
			gvTipoDocumentoDAO.guardarGvTipoDocumento(gvTipoDocumento);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvTipoDocumento;
	}
}
