package com.docsis.dao;

import java.util.List;

import com.docsis.beans.OpcSubRede;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;

public class OpcSubRedeDAOTest {
	
	public static void main(String[] args) throws DaoException {
		OpcSubRede opcsub = new OpcSubRede();
		OpcSubRedeDAO opcdaoimpl = new OpcSubRedeDAOImpl();
		Subrede subrede = new Subrede();
		
//		opcsub.setNome("option giard");
//		opcsub.setValor("deucerto!");
//		subrede.setCodSubrede(1);
//		opcsub.setSubrede(subrede);
//		opcsub.setAtivo((byte)1);
//		opcdaoimpl.Criar(opcsub);
		
		//###################alterar######################
//		opcsub.setNome("option opaaa hooo");
//		opcsub.setValor("papapap!");
//		subrede.setCodSubrede(1);
//		opcsub.setSubrede(subrede);
//		opcsub.setAtivo((byte)1);
//		opcsub.setCod(2);
//		opcdaoimpl.Alterar(opcsub);
		
		List<OpcSubRede> opc = opcdaoimpl.BuscarTodos();
		
		for(OpcSubRede o : opc){
			System.out.println(o);
		}
//			#########apagar
		//opcdaoimpl.Apagar(2);
		
		// ###################### testado
	}

}
