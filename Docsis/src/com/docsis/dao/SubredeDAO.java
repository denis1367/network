package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;

public interface SubredeDAO {
	
	public void Criar(Subrede subrede) throws DaoException;
	public void Alterar(Subrede subrede) throws DaoException;
	public void Apagar(Subrede subrede) throws DaoException;
	public List<Subrede> buscarPorStatusEtipo(Subrede subrede) throws DaoException;
	

}
