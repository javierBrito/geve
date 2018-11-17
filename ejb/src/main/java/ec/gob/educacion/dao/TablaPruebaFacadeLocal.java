package ec.gob.educacion.dao;

import java.util.List;
import javax.ejb.Local;

import ec.gob.educacion.model.geve.TablaPrueba;

@Local
public interface TablaPruebaFacadeLocal {

    void create(TablaPrueba tablaPrueba);

    void edit(TablaPrueba tablaPrueba);

    void remove(TablaPrueba tablaPrueba);

    TablaPrueba find(Object id);

    TablaPrueba update(TablaPrueba tablaPrueba);

    TablaPrueba read(int codigo);

    List<TablaPrueba> findAll();

    List<TablaPrueba> findRange(int[] range);

    int count();
    
}
