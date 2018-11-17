package ec.gob.educacion.dao;

import java.util.List;
import ec.gob.educacion.model.geve.TablaPrueba;

public interface TablaPruebaDao {
	List<TablaPrueba> lista();
	void create(TablaPrueba tablaPrueba);
	public TablaPrueba read(int codigo);
	TablaPrueba update(TablaPrueba tablaPrueba);
	void delete(TablaPrueba tablaPrueba);
}