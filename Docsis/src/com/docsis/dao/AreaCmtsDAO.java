package com.docsis.dao;

import java.util.List;


import com.docsis.beans.AreaCmts;
import com.docsis.exception.DaoException;

public interface AreaCmtsDAO {
	
	public void criar(AreaCmts areaCmts) throws DaoException;
	public List<AreaCmts> buscarTodos() throws DaoException;
	public List<AreaCmts> buscarTodosAtivo() throws DaoException;
	public AreaCmts buscarPorCod(int cod) throws DaoException;
	public void apagar(int codAreaCmts) throws DaoException;
	public void alterar(AreaCmts codAreaCmts) throws DaoException;

	
}
