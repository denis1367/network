package com.docsis.dao;



import java.util.List;

import com.docsis.beans.AreaCmts;
import com.docsis.exception.DaoException;

public class AreaCmtsDAOTest {
	
	public static void main(String[] args) throws DaoException {
		
		AreaCmtsDAO aCmtsDAOimpl = new AreaCmtsDAOImpl();
		AreaCmts acmts = new AreaCmts();
		//###############criar###################
//		acmts.setNome("CISCO_3");
//		acmts.setAtivo((byte)0);
//		
//		aCmtsDAOimpl.criar(acmts);
//		System.out.println(" criado ");
		
//		###############LISTAR###################		
//		List<AreaCmts> area =	aCmtsDAOimpl.buscarTodos();
//		
//		for (AreaCmts a : area) {
//			
//			System.out.println(a);
//			
//		}
//
		//###############ALTERAR###################
//		
//		acmts.setCodareacmts(2);
//		acmts.setNome("CISCO_5");
//		acmts.setAtivo((byte)0);
//		aCmtsDAOimpl.alterar(acmts);
//		
//
// ###############apagar###########################
		
		
		
		//		###############LISTAR###################		
		List<AreaCmts> area =	aCmtsDAOimpl.buscarTodos();
		
		for (AreaCmts a : area) {
			
			System.out.println(a);
			
		}
		
		//	aCmtsDAOimpl.apagar(1);
		
		
	}

}
