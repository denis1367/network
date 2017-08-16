package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Plano;
import com.docsis.exception.DaoException;

public interface PlanoDAO {

	public void Criar(Plano plano) throws DaoException;
	public void Alterar(Plano plano) throws DaoException;
	public void Apagar(int codPlano) throws DaoException;
	
	public List<Plano> BuscarTodosAtivo() throws DaoException;
	public List<Plano> BuscarTodos() throws DaoException;
	public Plano BuscarPorCodigo(int cod) throws DaoException;
	
	
}
