package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Grupo;
import com.docsis.exception.DaoException;

public interface GrupoDAO {

	public void Criar(Grupo grupo) throws DaoException;
	public void Alterar(Grupo grupo) throws DaoException;
	public void Apagar(Grupo grupo) throws DaoException;
	public List<Grupo> BuscarTodos() throws DaoException;
	
}
