package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Usuario;
import com.docsis.exception.DaoException;

public interface UsuarioDAO {
	public void Criar(Usuario usuario) throws DaoException;
	public void Alterar(Usuario usuario) throws DaoException;
	public void Apagar(Usuario usuario) throws DaoException;
	public List<Usuario> buscarTodos() throws DaoException;
	public Usuario Logar(Usuario usuario) throws DaoException;
    public Usuario BuscarUserLogin(Usuario usuario)throws DaoException ;
	public void AlterarSeha(Usuario usuario) throws DaoException;
    
}
