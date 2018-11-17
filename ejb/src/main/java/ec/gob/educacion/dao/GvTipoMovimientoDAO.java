package ec.gob.educacion.dao;

import java.util.List;

import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvTipoMovimiento;

/**
 * Created by javier.brito on 18/06/2015.
 */
public interface GvTipoMovimientoDAO extends GenericOracleAsignacionesDAO<GvTipoMovimiento, Long>{
    public GvTipoMovimiento buscarPorCodigo(long idTipoMovimiento);
    public GvTipoMovimiento buscarPorNombre(String descripcion);
	public List<GvTipoMovimiento> buscarGvTipoMovimientos();
	public void guardarGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) throws EducacionDAOException;
}
