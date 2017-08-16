package com.docsis.dao;

import java.util.List;

import com.docsis.beans.OpcaoDhcp;
import com.docsis.exception.DaoException;


public interface OpcaodhcpDAO {
	
	
	public void criar(OpcaoDhcp opcaoDhcp) throws DaoException;
	public List<OpcaoDhcp> buscarTodos() throws DaoException;
	public void apagar(int codOpcaodhcp) throws DaoException;
	public void alterar(OpcaoDhcp opcaodhcp) throws DaoException;
	public OpcaoDhcp buscarPorCodigo(int cod) throws DaoException;
}