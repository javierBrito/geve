package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvCajaDetalle;

/**
 * Created by javier.brito.
 */
public interface GvCajaDetalleDAO extends GenericDAO<GvCajaDetalle, Long> {
    public GvCajaDetalle buscarPorCodigo(long idCajaDetalle);
    public List<GvCajaDetalle> buscarListaPorParametros(GvCajaDetalle gvCajaDetalle);
	public List<GvCajaDetalle> buscarGvCajaDetalles();
    public List<GvCajaDetalle> buscarListaPorCaja(GvCaja gvCaja);
}
