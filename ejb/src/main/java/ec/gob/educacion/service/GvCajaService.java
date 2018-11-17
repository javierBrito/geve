package ec.gob.educacion.service;

import java.util.List;
import javax.ejb.Local;
import ec.gob.educacion.model.geve.GvCaja;

/**
 * Created by javier.brito.
 */
@Local
public interface GvCajaService {
    public GvCaja buscarPorCodigo(long idDocumento);
    public List<GvCaja> buscarListaPorParametros(GvCaja gvCaja);
    public List<GvCaja> buscarUltimoRegistro(GvCaja gvCaja);
	public List<GvCaja> buscarGvCajas(GvCaja gvCaja);
	public void crearGvCaja(GvCaja gvCaja) throws Exception;
	public GvCaja actualizarGvCaja(GvCaja gvCaja) throws Exception;
}
