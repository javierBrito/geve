package ec.gob.educacion.service;

import java.util.List;
import javax.ejb.Local;
import ec.gob.educacion.model.geve.GvParametro;

/**
 * Created by javier.brito.
 */
@Local
public interface GvParametroService {
    public GvParametro buscarPorCodigo(long idParametro);
    public List<GvParametro> buscarListaPorParametros(GvParametro gvParametro);
    public GvParametro buscarPorDescripcion(String descripcion);
    public List<GvParametro> buscarListaPorDescripcion(String descripcion);
    public GvParametro buscarPorNombre(String nombre);
	public List<GvParametro> buscarGvParametros();
	public GvParametro guardarGvParametro(GvParametro gvParametro) throws Exception;
}
