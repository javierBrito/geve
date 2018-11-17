package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvProveedor;

/**
 * Created by javier.brito on 25/10/2016.
 */
public interface GvProveedorDAO extends GenericOracleAsignacionesDAO<GvProveedor, Long>{
    public GvProveedor buscarPorCodigo(long idEmpresa, long idProveedor);
    public GvProveedor buscarPorNombres(String razonSocial, String nomContacto);
    public List<GvProveedor> buscarListaPorNombres(GvProveedor gvProveedor);
    public GvProveedor buscarPorDni(String dni);
	public List<GvProveedor> buscarGvProveedores(GvProveedor gvProveedor);
	public void guardarGvProveedor(GvProveedor gvProveedor) throws EducacionDAOException;
}
