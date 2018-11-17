package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvUnidad;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvUnidadService {
    public GvUnidad buscarPorCodigo(long idUnidad);
    public GvUnidad buscarPorNombre(String descripcion);
	public List<GvUnidad> buscarGvUnidades();
	public GvUnidad guardarGvUnidad(GvUnidad gvUnidad) throws Exception;
}
