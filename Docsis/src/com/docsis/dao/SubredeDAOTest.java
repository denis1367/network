package com.docsis.dao;

import java.util.List;

import com.docsis.beans.AreaCmts;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;

public class SubredeDAOTest {

	/**
	 * @param args
	 * @throws DaoException 
	 */
	public static void main(String[] args) throws DaoException {

		Subrede sr = new Subrede();
		SubredeDAO srDImpl = new SubredeDAOImpl();
		AreaCmts ac = new AreaCmts();
		
		////##### criar #########
		
		sr.setRede("192.168.55.102");
		sr.setNetmask("255.255.255.224");
		ac.setCodareacmts(2);
		sr.setAreacmts(ac);
		sr.setAtivo((byte)1);
		sr.setGerencia((byte)0);
		sr.setTipo((byte)1);
		
		srDImpl.Criar(sr);
		
///////###### alterar ##############
//		sr.setCodSubrede(1);
//		srDImpl.Alterar(sr);
///////###### apagar
//		sr.setCodSubrede(2);
//		srDImpl.Apagar(sr);
		
		
		List<Subrede> all = srDImpl.buscarPorStatusEtipo(sr);
		for (Subrede a : all){
			System.out.println(a);
		}
		}
	///############# ok criado ##################
}
