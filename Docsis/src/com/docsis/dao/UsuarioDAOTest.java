package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Grupo;
import com.docsis.beans.Usuario;
import com.docsis.exception.DaoException;

public class UsuarioDAOTest {

	/**
	 * @param args
	 * @throws DaoException 
	 */
	public static void main(String[] args){
		
		Usuario  us = new Usuario();
		Grupo gp = new Grupo();
		gp.setCod(1);
		UsuarioDAO usDAOImpl = new UsuarioDAOImpl();
		
		gp.setCod(1);
		us.setNome("Denis");
		us.setLogin("robson");
		us.setGrupo(gp);
		us.setAtivo((byte)1);
		us.setSenha("robson");
		
//		try {
//		//	usDAOImpl.Criar(us);
//		} catch (DaoException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		//#####alterar ###########
	us.setCod(1);
		try {
			usDAOImpl.AlterarSeha(us);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//#### 
		
		//###listar
		
		List<Usuario> u = null;
		try {
			u = usDAOImpl.buscarTodos();
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Usuario p : u){
			System.out.println(p);
		}
	

	}

}
