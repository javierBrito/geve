package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvTipoCliente;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvTipoClienteService {
    public GvTipoCliente buscarPorCodigo(long idTipoCliente);
    public GvTipoCliente buscarPorNombre(String descripcion);
	public List<GvTipoCliente> buscarGvTipoClientes();
	public GvTipoCliente guardarGvTipoCliente(GvTipoCliente gvTipoCliente) throws Exception;
}
