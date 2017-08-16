package com.docsis.dao;

import java.util.List;

import com.docsis.beans.PoolPlano;
import com.docsis.exception.DaoException;

public interface PoolPlanoDAO {

	public void Criar(PoolPlano poolPlano) throws DaoException;
	public void Alterar(PoolPlano poolPlano) throws DaoException;
	public void Apagar(PoolPlano codPoolPlano) throws DaoException;
	
	public List<PoolPlano> BuscarTodos(int ativo) throws DaoException;
	
}
