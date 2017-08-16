package com.docsis.dao;

import java.util.List;

import com.docsis.beans.PoolOpc;
import com.docsis.exception.DaoException;

public interface PoolOpcDAO {

	
	public void Criar(PoolOpc poolOpc)throws DaoException;
	public void Alterar(PoolOpc poolOpc) throws DaoException;
	public void Apagar(PoolOpc poolOpc) throws DaoException;
	
	public List<PoolOpc> BuscarStatus(PoolOpc poolOpc) throws DaoException;
	
}
