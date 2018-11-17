package ec.gob.educacion.service.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ec.gob.educacion.dao.GvProveedorDAO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.exception.EducacionPersistException;
import ec.gob.educacion.model.geve.GvProveedor;
import ec.gob.educacion.service.GvProveedorService;

/**
 * Created by javierr.brito on 18/06/2015.
 */
@Stateless
public class GvProveedorServiceImpl implements GvProveedorService{
    @EJB
    private GvProveedorDAO gvProveedorDAO;

    @Override
    public GvProveedor buscarPorCodigo(long idEmpresa, long idProveedor) {
        return gvProveedorDAO.buscarPorCodigo(idEmpresa, idProveedor);
    }

    @Override
    public GvProveedor buscarPorNombres(String razonSocial, String nomContacto) {
        return gvProveedorDAO.buscarPorNombres(razonSocial, nomContacto);
    }

	@Override
	public List<GvProveedor> buscarListaPorNombres(GvProveedor gvProveedor) {
		return gvProveedorDAO.buscarListaPorNombres(gvProveedor);
	}

    @Override
    public GvProveedor buscarPorDni(String dni) {
        return gvProveedorDAO.buscarPorDni(dni);
    }

	@Override
	public List<GvProveedor> buscarGvProveedores(GvProveedor gvProveedor) {
		return gvProveedorDAO.buscarGvProveedores(gvProveedor);
	}

	@Override
	public GvProveedor guardarGvProveedor(GvProveedor gvProveedor) throws Exception {
		try {
			gvProveedorDAO.guardarGvProveedor(gvProveedor);
		} catch (EducacionPersistException e) {
			throw new Exception(e);
		} catch (EducacionDAOException exc) {
			throw new Exception(exc);
		}
		return gvProveedor;
	}
}
