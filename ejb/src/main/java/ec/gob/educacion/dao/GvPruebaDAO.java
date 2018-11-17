package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvPrueba;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvPruebaDAO extends GenericOracleAsignacionesDAO<GvPrueba, Long>{
    public GvPrueba buscarPorCodigo(long idPrueba);
    public GvPrueba buscarPorNombre(String descripcion);
	public List<GvPrueba> buscarGvPruebas();
}
