package ec.gob.educacion.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.educacion.model.geve.GvTipoMovimiento;

/**
 * Created by javier.brito on 20160321.
 */
@Local
public interface GvTipoMovimientoService {
    public GvTipoMovimiento buscarPorCodigo(long idTipoMovimiento);
    public GvTipoMovimiento buscarPorNombre(String descripcion);
	public List<GvTipoMovimiento> buscarGvTipoMovimientos();
	public GvTipoMovimiento guardarGvTipoMovimiento(GvTipoMovimiento gvTipoMovimiento) throws Exception;
}
