package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvCliente;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvClienteService {
    public GvCliente buscarPorCodigo(long idCliente);
    public GvCliente buscarPorNombres(String nombres, String apellidos);
    public List<GvCliente> buscarListaPorNombres(GvCliente gvCliente);
    public GvCliente buscarPorDni(String dni);
	public List<GvCliente> buscarGvClientes(GvCliente gvCliente);
	public GvCliente guardarGvCliente(GvCliente gvCliente) throws Exception;
}
