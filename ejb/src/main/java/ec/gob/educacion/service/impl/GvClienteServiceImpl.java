package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvClienteDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvCliente;
import ec.gob.educacion.service.GvClienteService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvClienteServiceImpl implements GvClienteService{
    @EJB
    private GvClienteDAO gvClienteDAO;

    @Override
    public GvCliente buscarPorCodigo(long idCliente) {
        return gvClienteDAO.buscarPorCodigo(idCliente);
    }

    @Override
    public GvCliente buscarPorNombres(String nombres, String apellidos) {
        return gvClienteDAO.buscarPorNombres(nombres, apellidos);
    }

	@Override
	public List<GvCliente> buscarListaPorNombres(GvCliente gvCliente) {
		return gvClienteDAO.buscarListaPorNombres(gvCliente);
	}

    @Override
    public GvCliente buscarPorDni(String dni) {
        return gvClienteDAO.buscarPorDni(dni);
    }

	@Override
	public List<GvCliente> buscarGvClientes(GvCliente gvCliente) {
		return gvClienteDAO.buscarGvClientes(gvCliente);
	}

	@Override
	public GvCliente guardarGvCliente(GvCliente gvCliente) throws Exception {
		try {
			gvClienteDAO.guardarGvCliente(gvCliente);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvCliente;
	}
}
