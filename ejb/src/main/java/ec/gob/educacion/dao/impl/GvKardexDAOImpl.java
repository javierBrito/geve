package ec.gob.educacion.dao.impl;

import java.util.ArrayList;
import java.util.List;
import ec.gob.educacion.dao.GvKardexDAO;
import ec.gob.educacion.dto.GvKardexFechaDTO;
import ec.gob.educacion.exception.EducacionDAOException;
import ec.gob.educacion.model.geve.GvKardex;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by javier.brito.
 */
@Stateless
public class GvKardexDAOImpl extends GenericOracleAsignacionesDAOImpl<GvKardex, Long> implements GvKardexDAO {
    @Override
    public GvKardex buscarPorCodigo(long idKardex) {
    	try{
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<GvKardex> criteria = cb.createQuery(GvKardex.class);
            Root<GvKardex> gvKardex = criteria.from(GvKardex.class);
            criteria.select(gvKardex)
                    .where(
                            cb.equal(gvKardex.get("idKardex"), idKardex)
                    	  )
                    .orderBy(cb.asc(gvKardex.get("descripcion")));
            return em.createQuery(criteria).getSingleResult();
		}catch(Exception exc){
			exc.printStackTrace();
			return null;
		}
    }

    @SuppressWarnings("unchecked")
    public List<GvKardex> buscarListaPorParametros(GvKardex gvKardex) {
		try{
			String sql =  "select k from GvKardex k "
						+ "  join k.gvProducto p "
						+ "  join k.gvTipoMovimiento tm "
						+ "  join k.gvDocumento d "
						+ " where k.idEmpresa = :idEmpresa "
						+ "   and k.estado = 1 ";

			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				sql = sql + " and p.idProducto = :idProducto";
			}
			if (gvKardex.getGvTipoMovimiento() != null && !gvKardex.getGvTipoMovimiento().equals("")) {
				sql = sql + " and tm.idTipoMovimiento = :idTipoMovimiento";
			}
			if (gvKardex.getGvDocumento() != null && !gvKardex.getGvDocumento().equals("")) {
				sql = sql + " and d.idDocumento = :idDocumento";
			}
			//sql = sql + " order by d.observaciones asc";

			Query query = getEntityManager().createQuery(sql);
			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				query.setParameter("idProducto", gvKardex.getGvProducto().getIdProducto());
			}
			if (gvKardex.getGvTipoMovimiento() != null && !gvKardex.getGvTipoMovimiento().equals("")) {
				query.setParameter("idTipoMovimiento", gvKardex.getGvTipoMovimiento().getIdTipoMovimiento());
			}
			if (gvKardex.getGvDocumento() != null && !gvKardex.getGvDocumento().equals("")) {
				query.setParameter("idDocumento", gvKardex.getGvDocumento().getIdDocumento());
			}
			query.setParameter("idEmpresa", gvKardex.getIdEmpresa());

			return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings({ "unchecked"})
    public List<GvKardexFechaDTO> buscarListaPorFechaSaldos(GvKardex gvKardex) {
        List<Object[]> objects = new ArrayList<Object[]>();
		List<GvKardexFechaDTO> listaGvKardexFechaDTO = new ArrayList<GvKardexFechaDTO>();
        try {
            String sql =
                    " select idEmpresa, idProducto, codigo, descripcion, precioDeCompra, fechaRegistra, nomUsuarioRegistra,  " +
                    "        SUM(numEgreso) as numEgreso, SUM(round(mntValorEgreso, 2)) as mntValorEgreso, " +
                    "        max(numExistenciaActualEgreso) as numExistenciaActualEgreso, " +
                    "	  	 SUM(numIngreso) as numIngreso, SUM(round(mntValorIngreso, 2)) as mntValorIngreso, " +
                    "	 	 max(numExistenciaActualIngreso) as numExistenciaActualIngreso " +
                    "   from gventas.view_gv_saldos " +
                    "  group by idEmpresa, idProducto, codigo, descripcion, precioDeCompra, fechaRegistra, nomUsuarioRegistra " +
                    " having idEmpresa = :idEmpresa " + 
            		"    and (fechaRegistra between :fechaRegistra and :fechaRegistra) "; 
   			if (gvKardex != null && !gvKardex.getNomUsuarioRegistra().equals("")) {
   				sql = sql + " and nomUsuarioRegistra = :nomUsuarioRegistra ";
   			}
   			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
   				sql = sql + " and idProducto = :idProducto";
   			}
			sql = sql + "  order by idProducto ";

            Query query = em.createNativeQuery(sql);
			query.setParameter("fechaRegistra", gvKardex.getFechaRegistra());
            
			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				query.setParameter("idProducto", gvKardex.getGvProducto().getIdProducto());
   			}
   			if (gvKardex != null && !gvKardex.getNomUsuarioRegistra().equals("")) {
				query.setParameter("nomUsuarioRegistra", gvKardex.getNomUsuarioRegistra());
   			}
			query.setParameter("idEmpresa", gvKardex.getIdEmpresa());
   	        
   	        objects = query.getResultList();
   	        
            if(objects !=null){
                for (Object[] object : objects) {
                	GvKardexFechaDTO gvKardexFechaDTO  = new GvKardexFechaDTO();
                    if(object[0] == null || object[0] == ""){
                    }else{
                    	gvKardexFechaDTO.setIdEmpresa(Integer.parseInt(object[0].toString()));
                    }
                    if(object[1] == null || object[1] == ""){
                    }else{
                    	gvKardexFechaDTO.setIdProducto(Integer.parseInt(object[1].toString()));
                    }
                    if(object[2] == null || object[2] == ""){
                    	gvKardexFechaDTO.setCodigo("");
                    }else{
                    	gvKardexFechaDTO.setCodigo(String.valueOf(object[2]));
                    }
                    if(object[3] == null || object[3] == ""){
                    	gvKardexFechaDTO.setDescripcion("");
                    }else{
                    	gvKardexFechaDTO.setDescripcion(String.valueOf(object[3]));
                    }
                    if(object[4] == null || object[4] == ""){
                    }else{
                    	gvKardexFechaDTO.setPrecioDeCompra(Float.parseFloat(object[4].toString()));
                    }
                    if(object[7] == null || object[7] == ""){
                    }else{
                    	gvKardexFechaDTO.setNumEgreso(Integer.parseInt(object[7].toString()));
                    }
                    if(object[8] == null || object[8] == ""){
                    }else{
                    	gvKardexFechaDTO.setMntValorEgreso(Float.parseFloat(object[8].toString()));
                    }
                    if(object[9] == null || object[9] == ""){
                    }else{
                    	gvKardexFechaDTO.setNumExistencia(Integer.parseInt(object[9].toString()));
                    }
                    if(object[10] == null || object[10] == ""){
                    }else{
                    	gvKardexFechaDTO.setNumIngreso(Integer.parseInt(object[10].toString()));
                    }
                    if(object[11] == null || object[11] == ""){
                    }else{
                    	gvKardexFechaDTO.setMntValorIngreso(Float.parseFloat(object[11].toString()));
                    }
                    if(object[12] == null || object[12] == ""){
                    }else{
                    	gvKardexFechaDTO.setNumExistencia(Integer.parseInt(object[12].toString()));
                    }
                    listaGvKardexFechaDTO.add(gvKardexFechaDTO);
                }
            }        
        } catch (Exception exc) {
            exc.printStackTrace();
        }
		
		return listaGvKardexFechaDTO;
    }

    @SuppressWarnings({ "unchecked"})
    public List<GvKardexFechaDTO> buscarListaStockPorFecha(GvKardex gvKardex) {
    	try{
			String sql =  " select p.idEmpresa, p.idProducto, p.descripcion, p.codigo, "
						+ " 	  (select k.num_existencia_actual "
						+ " 		 from gventas.gv_kardex k "
						+ "    		where k.idKardex = ( "
						+ " 					 		select max(k1.idKardex) "
						+ " 					   		  from gventas.gv_Kardex k1 "
						+ "                       		 where k1.idProducto = k.idProducto "
						+ "                         	   and k1.fecha_Registra = k.fecha_Registra "
						+ "                       		 group by k1.idProducto, k1.fecha_Registra "
						+ " 					 		) "
						+ " 		  and k.idproducto = p.idproducto "
						+ " 		  and k.fecha_Registra BETWEEN :fechaRegistra AND :fechaRegistra "
						+ " 	  ) as numExistenciaActual, "
						+ "        p.num_existencia_minima as numExistenciaMinima "
						+ "  from gventas.gv_producto p "
						+ " where p.idEmpresa = :idEmpresa "
						+ "   and p.idproducto not in (0, 1) ";

			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				sql = sql + " and p.idProducto = :idProducto ";
			}
			sql = sql + " order by p.descripcion ";
			
			Query query = em.createNativeQuery(sql);
			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				query.setParameter("idProducto", gvKardex.getGvProducto().getIdProducto());
			}
			query.setParameter("fechaRegistra", gvKardex.getFechaRegistra());
			query.setParameter("idEmpresa", gvKardex.getIdEmpresa());

			List<GvKardexFechaDTO> listaGvKardexFechaDTO = new ArrayList<GvKardexFechaDTO>();

            List<Object[]> objects = new ArrayList<Object[]>();
            objects = query.getResultList();
            if(objects !=null){
                for (Object[] object : objects) {
                	GvKardexFechaDTO gvKardexFechaDTO  = new GvKardexFechaDTO();
                    if(object[0] == null || object[0] == ""){
                    }else{
                    	gvKardexFechaDTO.setIdEmpresa(Integer.parseInt(object[0].toString()));
                    }
                	if(object[1] == null || object[1] == ""){
                    }else{
                    	gvKardexFechaDTO.setIdProducto(Integer.parseInt(object[1].toString()));
                    }
                    if(object[2] == null || object[2] == ""){
                    	gvKardexFechaDTO.setDescripcion("");
                    }else{
                    	gvKardexFechaDTO.setDescripcion(String.valueOf(object[2]));
                    }
                    if(object[3] == null || object[3] == ""){
                    	gvKardexFechaDTO.setCodigo("");
                    }else{
                    	gvKardexFechaDTO.setCodigo(String.valueOf(object[3]));
                    }
                    if(object[4] == null || object[4] == ""){

                    }else{
                    	gvKardexFechaDTO.setNumExistencia(Integer.parseInt(object[4].toString()));
                    }
                    if(object[5] == null || object[5] == ""){

                    }else{
                    	gvKardexFechaDTO.setNumExistenciaMinima(Integer.parseInt(object[5].toString()));
                    }
                    listaGvKardexFechaDTO.add(gvKardexFechaDTO);
                }
            }        
			
			return listaGvKardexFechaDTO;

		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings({ "unchecked"})
    public List<GvKardexFechaDTO> buscarListaStockPorProducto(GvKardex gvKardex) {
    	try{
			String sql =  " select p.idEmpresa, p.idProducto, p.descripcion, p.codigo, "
						+ " 	  (select k.num_existencia_actual "
						+ " 		 from gventas.gv_kardex k "
						+ "    		where k.idKardex = ( "
						+ " 					 		select max(k1.idKardex) "
						+ " 					   		  from gventas.gv_Kardex k1 "
						+ "                       		 where k1.idProducto = k.idProducto "
						+ "                       		 group by k1.idProducto "
						+ " 					 		) "
						+ " 		  and k.idProducto = p.idProducto "
						+ " 	  ) as numExistenciaActual, "
						+ "        p.num_existencia_minima as numExistenciaMinima "
						+ "  from gventas.gv_producto p "
						+ " where p.idEmpresa = :idEmpresa "
						+ "   and p.idproducto not in (0, 1) ";

			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				if (gvKardex.getGvProducto().getIdProducto() != 0) {
					sql = sql + " and p.idProducto = :idProducto ";
				}
				if (!gvKardex.getGvProducto().getDescripcion().equals("")) {
					sql = sql + " and p.descripcion >= :descripcion ";
				}
			}
			sql = sql + " order by p.descripcion ";
			Query query = em.createNativeQuery(sql);
			
			if (gvKardex.getGvProducto() != null && !gvKardex.getGvProducto().equals("")) {
				if (gvKardex.getGvProducto().getIdProducto() != 0) {
					query.setParameter("idProducto", gvKardex.getGvProducto().getIdProducto());
				}
				if (!gvKardex.getGvProducto().getDescripcion().equals("")) {
					query.setParameter("descripcion", gvKardex.getGvProducto().getDescripcion());
				}
			}
			query.setParameter("idEmpresa", gvKardex.getIdEmpresa());

			List<GvKardexFechaDTO> listaGvKardexFechaDTO = new ArrayList<GvKardexFechaDTO>();

            List<Object[]> objects = new ArrayList<Object[]>();
            objects = query.getResultList();
            if(objects !=null){
                for (Object[] object : objects) {
                	GvKardexFechaDTO gvKardexFechaDTO  = new GvKardexFechaDTO();
                    if(object[0] == null || object[0] == ""){

                    }else{
                    	gvKardexFechaDTO.setIdEmpresa(Integer.parseInt(object[0].toString()));
                    }
                    if(object[1] == null || object[1] == ""){

                    }else{
                    	gvKardexFechaDTO.setIdProducto(Integer.parseInt(object[1].toString()));
                    }
                    if(object[2] == null || object[2] == ""){
                    	gvKardexFechaDTO.setDescripcion("");
                    }else{
                    	gvKardexFechaDTO.setDescripcion(String.valueOf(object[2]));
                    }
                    if(object[3] == null || object[3] == ""){
                    	gvKardexFechaDTO.setCodigo("");
                    }else{
                    	gvKardexFechaDTO.setCodigo(String.valueOf(object[3]));
                    }
                    if(object[4] == null || object[4] == ""){

                    }else{
                    	gvKardexFechaDTO.setNumExistencia(Integer.parseInt(object[4].toString()));
                    }
                    if(object[5] == null || object[5] == ""){

                    }else{
                    	gvKardexFechaDTO.setNumExistenciaMinima(Integer.parseInt(object[5].toString()));
                    }
                    listaGvKardexFechaDTO.add(gvKardexFechaDTO);
                }
            }        
			
			return listaGvKardexFechaDTO;

		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<GvKardex> buscarGvKardexs(GvKardex gvKardex) {
		try{
	        Query query = em.createQuery("select k from GvKardex k where k.idEmpresa = :idEmpresa order by k.gvProducto.idProducto ");
	        query.setParameter("idEmpresa", gvKardex.getIdEmpresa());
	        return query.getResultList();
		}catch(Exception exc){
            exc.printStackTrace();
            return null;
		}
    }
    
    @Override
	public void guardarGvKardex(GvKardex gvKardex) throws EducacionDAOException {
		try{
			getEntityManager().merge(gvKardex);
		}catch(Exception exc){
			throw new EducacionDAOException(exc);
		}
	}
}
