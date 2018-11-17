package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvProveedor;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvProveedorService {
    public GvProveedor buscarPorCodigo(long idEmpresa, long idProveedor);
    public GvProveedor buscarPorNombres(String razonSocial, String nomContacto);
    public List<GvProveedor> buscarListaPorNombres(GvProveedor gvProveedor);
    public GvProveedor buscarPorDni(String dni);
	public List<GvProveedor> buscarGvProveedores(GvProveedor gvProveedor);
	public GvProveedor guardarGvProveedor(GvProveedor gvProveedor) throws Exception;
}
