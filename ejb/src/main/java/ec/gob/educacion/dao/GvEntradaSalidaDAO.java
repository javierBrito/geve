package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvEntradaSalida;

/**
 * Created by javier.brito.
 */
public interface GvEntradaSalidaDAO extends GenericDAO<GvEntradaSalida, Long> {
    public GvEntradaSalida buscarPorCodigo(long idCajaDetalle);
    public List<GvEntradaSalida> buscarListaPorParametros(GvEntradaSalida gvEntradaSalida);
	public List<GvEntradaSalida> buscarGvEntradaSalidas();
    public List<GvEntradaSalida> buscarListaPorCaja(GvCaja gvCaja);
}
