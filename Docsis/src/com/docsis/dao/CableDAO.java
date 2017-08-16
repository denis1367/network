package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Cable;
import com.docsis.exception.DaoException;

public interface CableDAO {
	
	public void criar(Cable cable) throws DaoException;
	public void alterar(Cable cable) throws DaoException;
	public void apagar(int codCable) throws DaoException;
	
	public List<Cable> buscarTodos() throws DaoException;
	public Cable buscarPorCodCli(int codCli) throws DaoException;
	
}
