package com.docsis.dao;

import java.util.List;

import com.docsis.beans.PoolSubrede;
import com.docsis.exception.DaoException;

public interface PoolSubredeDAO {
	
	public void Criar(PoolSubrede poolSubrede) throws DaoException;
	public void Alterar(PoolSubrede poolSubrede) throws DaoException;
	public void Apagar(PoolSubrede poolSubrede) throws DaoException;
	public List<PoolSubrede> buscarPorStatus(PoolSubrede poolSubrede) throws DaoException;
	
}
