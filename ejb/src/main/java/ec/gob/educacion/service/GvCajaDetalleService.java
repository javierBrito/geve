package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvCajaDetalle;

/**
 * Created by javier.brito.
 */
@Local
public interface GvCajaDetalleService {
    public GvCajaDetalle buscarPorCodigo(long idCajaDetalle);
    public List<GvCajaDetalle> buscarListaPorParametros(GvCajaDetalle gvCajaDetalle);
	public List<GvCajaDetalle> buscarGvCajaDetalles();
    public List<GvCajaDetalle> buscarListaPorCaja(GvCaja gvCaja);
	public void crearGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception;
	public GvCajaDetalle actualizarGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception;
	public void eliminarGvCajaDetalle(GvCajaDetalle gvCajaDetalle) throws Exception;
	public void eliminarGvCajaDetalle(long idCajaDetalle) throws Exception;
}
