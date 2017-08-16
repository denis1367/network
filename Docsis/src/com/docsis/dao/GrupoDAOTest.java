package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Grupo;
import com.docsis.exception.DaoException;

public class GrupoDAOTest {
	public static void main(String[] args) throws DaoException {
		Grupo grupo = new Grupo();
		GrupoDAO grupDAOImpl = new GrupoDAOImpl();
		
		
		//##############CRIAR#################
//		for(int i =0; i<10; i++){
//		grupo.setNome("USER"+i);
//		grupo.setCadcmts((byte)1);
//		grupDAOImpl.Criar(grupo);
//		System.out.println(" usario "+i+" criado com sucesso !!!");
//		}
//		
		
		
		//###########alterar
//		grupo.setNome("TEST");
//		grupo.setCadcmts((byte)0);
//		grupo.setCod(3);
//		grupDAOImpl.Alterar(grupo);
		
//		//### apagar
//		grupo.setCod(13);
//		grupDAOImpl.Apagar(grupo);
//	
		
		List<Grupo> listaTodos = grupDAOImpl.BuscarTodos();
		
		for(Grupo a : listaTodos){
			System.out.println(a);
		}
		
		
		
	}
}
