package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvCliente;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvClienteDAO extends GenericOracleAsignacionesDAO<GvCliente, Long>{
    public GvCliente buscarPorCodigo(long idCliente);
    public GvCliente buscarPorNombres(String nombres, String apellidos);
    public List<GvCliente> buscarListaPorNombres(GvCliente gvCliente);
    public GvCliente buscarPorDni(String dni);
	public List<GvCliente> buscarGvClientes(GvCliente gvCliente);
	public void guardarGvCliente(GvCliente gvCliente) throws EducacionDAOException;
}
