package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoCliente;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvTipoClienteDAO extends GenericOracleAsignacionesDAO<GvTipoCliente, Long>{
    public GvTipoCliente buscarPorCodigo(long idTipoCliente);
    public GvTipoCliente buscarPorNombre(String descripcion);
	public List<GvTipoCliente> buscarGvTipoClientes();
	public void guardarGvTipoCliente(GvTipoCliente gvTipoCliente) throws EducacionDAOException;
}
