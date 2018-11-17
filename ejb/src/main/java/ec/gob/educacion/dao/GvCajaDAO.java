package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvCaja;

/**
 * Created by javier.brito.
 */
public interface GvCajaDAO extends GenericDAO<GvCaja, Long> {
    public GvCaja buscarPorCodigo(long idCaja);
    public List<GvCaja> buscarListaPorParametros(GvCaja gvCaja);
    public List<GvCaja> buscarUltimoRegistro(GvCaja gvCaja);
	public List<GvCaja> buscarGvCajas(GvCaja gvCaja);
}
