package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvKardex;

/**
 * Created by javier.brito
 */
public interface GvKardexDAO extends GenericOracleAsignacionesDAO<GvKardex, Long>{
    public GvKardex buscarPorCodigo(long idKardex);
    public List<GvKardex> buscarListaPorParametros(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaStockPorFecha(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaStockPorProducto(GvKardex gvKardex);
    public List<GvKardexFechaDTO> buscarListaPorFechaSaldos(GvKardex gvKardex);
	public List<GvKardex> buscarGvKardexs(GvKardex gvKardex);
	public void guardarGvKardex(GvKardex gvKardex) throws EducacionDAOException;
}
