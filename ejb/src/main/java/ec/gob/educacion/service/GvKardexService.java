package ec.gob.educacion.service;

import java.util.List;
import javax.ejb.Local;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.model.geve.GvKardex;

/**
 * Created by javier.brito.
 */
@Local
public interface GvKardexService {
    public GvKardex buscarPorCodigo(long idKardex);
    public List<GvKardex> buscarListaPorParametros(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaStockPorFecha(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaStockPorProducto(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaPorFechaSaldos(GvKardex gvKardex);
	public List<GvKardex> buscarGvKardexs(GvKardex gvKardex);
	public GvKardex guardarGvKardex(GvKardex gvKardex) throws Exception;
}
