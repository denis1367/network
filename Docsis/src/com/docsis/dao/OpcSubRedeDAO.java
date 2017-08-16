package com.docsis.dao;

import java.util.List;

import com.docsis.beans.OpcSubRede;
import com.docsis.exception.DaoException;

public interface OpcSubRedeDAO {

	
	public void Criar(OpcSubRede opcsub) throws DaoException;
	public void Alterar(OpcSubRede opcsub) throws DaoException;
	public void Apagar(int codOpcSub) throws DaoException;
	
	public List<OpcSubRede> BuscarAtivo(int opcao) throws DaoException;
	public List<OpcSubRede> BuscarTodos() throws DaoException;
	
}
