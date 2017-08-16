package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Cliente;
import com.docsis.exception.DaoException;

public interface ClienteDAO {
	
	public void Criar(Cliente cliente) throws DaoException;
	public void Alterar(Cliente cliente) throws DaoException;
	public void Apagar(int codCli) throws DaoException;
	
	public List<Cliente> BuscarPorNome(String nome) throws DaoException;
	public Cliente BuscarPorContrato(int contrato) throws DaoException;
	

}
