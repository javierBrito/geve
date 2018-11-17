package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvTipoClienteDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvTipoCliente;
import ec.gob.educacion.service.GvTipoClienteService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvTipoClienteServiceImpl implements GvTipoClienteService{
    @EJB
    private GvTipoClienteDAO gvTipoClienteDAO;

    @Override
    public GvTipoCliente buscarPorCodigo(long idTipoCliente) {
        return gvTipoClienteDAO.buscarPorCodigo(idTipoCliente);
    }

    @Override
    public GvTipoCliente buscarPorNombre(String descripcion) {
        return gvTipoClienteDAO.buscarPorNombre(descripcion);
    }

	@Override
	public List<GvTipoCliente> buscarGvTipoClientes() {
		return gvTipoClienteDAO.buscarGvTipoClientes();
	}

	@Override
	public GvTipoCliente guardarGvTipoCliente(GvTipoCliente gvTipoCliente) throws Exception {
		try {
			gvTipoClienteDAO.guardarGvTipoCliente(gvTipoCliente);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvTipoCliente;
	}
}
