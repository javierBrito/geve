package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvCaja;
import ec.gob.educacion.model.geve.GvEntradaSalida;

/**
 * Created by javier.brito.
 */
@Local
public interface GvEntradaSalidaService {
    public GvEntradaSalida buscarPorCodigo(long idCajaDetalle);
    public List<GvEntradaSalida> buscarListaPorParametros(GvEntradaSalida gvEntradaSalida);
	public List<GvEntradaSalida> buscarGvEntradaSalidas();
    public List<GvEntradaSalida> buscarListaPorCaja(GvCaja gvCaja);
	public void crearGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception;
	public GvEntradaSalida actualizarGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception;
	public void eliminarGvEntradaSalida(GvEntradaSalida gvEntradaSalida) throws Exception;
	public void eliminarGvEntradaSalida(long idCajaDetalle) throws Exception;
}
