package com.docsis.dao;

import java.util.List;

import com.docsis.beans.PoolOpc;
import com.docsis.beans.PoolSubrede;
import com.docsis.exception.DaoException;

public class PoolOpcDAOTest {
	
	
	public static void main(String[] args) throws DaoException {
	
		PoolOpc poolOpc = new PoolOpc();
		PoolOpcDAO poolOpcDAOImpl = new PoolOpcDAOImpl();
		PoolSubrede poolsubrede = new PoolSubrede();
	
		poolOpc.setNome("range");
		poolOpc.setValor("192.168.55.1 10.10.5.1");
		poolOpc.setAtivo((byte)1);
		poolsubrede.setCod(1);
		poolOpc.setPoolsubrede(poolsubrede);
		poolOpc.setCable((byte)1);
		
		//##criar##
//		poolOpcDAOImpl.Criar(poolOpc);
		//##alterar##
//		poolOpc.setCod(1);
//		poolOpcDAOImpl.Alterar(poolOpc);
//		   
		poolOpc.setCod(2);
		poolOpcDAOImpl.Apagar(poolOpc);
		
		List<PoolOpc> op = poolOpcDAOImpl.BuscarStatus(poolOpc);
		for(PoolOpc a : op){
			System.out.println(a);
		}
		
	}
	
	
	
	
	
	

}
