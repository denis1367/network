package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Plano;
import com.docsis.beans.PoolPlano;
import com.docsis.beans.PoolSubrede;
import com.docsis.exception.DaoException;

public class PoolPlanoDAOTest {
public static void main(String[] args) throws DaoException {
	
	
	PoolPlano pplano = new PoolPlano();
	Plano plano = new Plano();
	PoolSubrede psubrede = new PoolSubrede();
	PoolPlanoDAO ppDAOimpl = new PoolPlanoDAOImpl();
	
	pplano.setNome("tesste2");
	pplano.setAtivo((byte)0);
	plano.setCodPlano(2);
	pplano.setPlano(plano);
	psubrede.setCod(1);
	pplano.setPoolSubRede(psubrede);
	//ppDAOimpl.Criar(pplano);
	
	
	pplano.setCodpoolplano(1);
	ppDAOimpl.Alterar(pplano);
	
	
	 List<PoolPlano> td = ppDAOimpl.BuscarTodos(pplano.getAtivo());
	 for(PoolPlano p : td){
		 System.out.println(p);
	 }

}
	
}
