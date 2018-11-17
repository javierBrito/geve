package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvMarca;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvMarcaService {
    public GvMarca buscarPorCodigo(long idMarca);
    public GvMarca buscarPorNombre(String descripcion);
	public List<GvMarca> buscarGvMarcas();
	public GvMarca guardarGvMarca(GvMarca gvMarca) throws Exception;
}
