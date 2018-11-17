package ec.gob.educacion.service.impl;

import java.util.List;

import ec.gob.educacion.dao.TablaPruebaDao;
import ec.gob.educacion.model.geve.TablaPrueba;
import ec.gob.educacion.service.TablaPruebaService;

public class TablaPruebaServiceImpl implements TablaPruebaService {
	
	private TablaPruebaDao tablaPruebaDao;

	@Override
	public List<TablaPrueba> listaTablaPrueba() {
		return tablaPruebaDao.lista();
	}

}
