package com.docsis.dao;

import java.util.List;

import com.docsis.beans.PoolSubrede;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;

public class PoolSubredeDAOTest {

	/**
	 * @param args
	 * @throws DaoException 
	 */
	public static void main(String[] args) throws DaoException {
		PoolSubredeDAO psdaoImpl = new PoolSubredeDAOImpl();
		PoolSubrede ps = new PoolSubrede();
		Subrede sr = new Subrede();
	
				
//		//#######CRIAR
	
//		ps.setSubRede(sr);
//		sr.setCodSubrede(1);
//		ps.setSubRede(sr);
//		ps.setNome("test");
//		ps.setAtivo((byte)1);
// 		psdaoImpl.Criar(ps);
		
		///####alterar######
//		ps.setCod(1);
//		ps.setNome("test2");
//		ps.setAtivo((byte)1);
//		sr.setCodSubrede(1);
//		ps.setSubRede(sr);
//		psdaoImpl.Alterar(ps);
		
		//###apagar########
		
//		ps.setCod(2);
//		psdaoImpl.Apagar(ps);
		
		///##############
	
		List<PoolSubrede> sub = psdaoImpl.buscarPorStatus(ps);
	
		for(PoolSubrede a : sub){
			System.out.println(a);
		}
		
		
		
		
	}

}
