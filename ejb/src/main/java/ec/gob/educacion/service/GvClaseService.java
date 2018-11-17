package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvClase;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvClaseService {
    public GvClase buscarPorCodigo(long idClase);
    public GvClase buscarPorNombre(String descripcion);
	public List<GvClase> buscarGvClases();
	public GvClase guardarGvClase(GvClase gvClase) throws Exception;
}
