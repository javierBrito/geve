package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvTipoNominacion;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvTipoNominacionService {
    public GvTipoNominacion buscarPorCodigo(long idTipoNominacion);
    public GvTipoNominacion buscarPorNombre(String descripcion);
	public List<GvTipoNominacion> buscarGvTipoNominaciones();
	public GvTipoNominacion guardarGvTipoNominacion(GvTipoNominacion gvTipoNominacion) throws Exception;
}
