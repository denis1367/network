package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Plano;
import com.docsis.exception.DaoException;

public class PlanoDAOTest {

	/**
	 * @param args
	 * @throws DaoException 
	 */
	public static void main(String[] args) throws DaoException {
		Plano plano = new Plano();
		PlanoDAO planoDAOImpl= new PlanoDAOImpl();
		// 
//		+" cod_plano ,"
//		+" nome_plano ,"
//		+" nserver_plano ,"
//		+" arqbinario_plano,"
//		+" ativo_plano )"

		//##########criar
//			plano.setNome("8S MEGAS");
//		plano.setNserver("172.17.17.100");
//		plano.setArqbinario("2048-512");
//		plano.setAtivo((byte)1);
//		
//		planoDAOImpl.Criar(plano);
//		
//		
		
//####################alterar####################
//		plano.setCodPlano(2);
//		plano.setNome("6 megas");
//		plano.setNserver("172.17.17.3");
//		plano.setArqbinario("6-415");
//		plano.setAtivo((byte)0);
//		
//		planoDAOImpl.Alterar(plano);

		
		
		
		
		//###apagar
		//planoDAOImpl.Apagar(1);
		
		
//###############buscar##################
		
		//List<Plano> list = planoDAOImpl.BuscarTodos();
		List<Plano> list = planoDAOImpl.BuscarTodosAtivo();
		for( Plano a : list){
			System.out.println(a);
		}
		
	}
	// ############### TESTATDO ################

}
