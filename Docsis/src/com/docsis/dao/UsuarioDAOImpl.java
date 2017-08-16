package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Grupo;
import com.docsis.beans.Usuario;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class UsuarioDAOImpl implements UsuarioDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull(max(cod_usuario),0)+1 NOVO_COD from usuario ";
	private String QUERY_CRIAR ="insert into usuario ("
			+" cod_usuario,"
			+" nome_usuario,"
			+" login_usuario,"
			+" cod_grupo ,"
			+" ativo_usuario, "
			+" senha_usuario )"
			+" values ("
			+" ?,?,?,?,?,? ) ";
	
	private String QUERY_ALTERARSENHA =" update usuario set  senha_usuario = ? where cod_usuario = ?;";
	private String QUERY_ATUALIZAR =" update usuario set "
			
			+" nome_usuario = ?,"
			+" login_usuario = ?,"
			+" cod_grupo = ? ,"
			+" ativo_usuario = ?, "
			+" senha_usuario = ? "
			+" where "
			+" cod_usuario = ?";
	private String QUERY_APAGAR = " delete from usuario where cod_usuario = ? ";		
	
	private String QUERY_BUSCAR_POR_LOGINSENHA = " select "
			+" cod_usuario as cod ,"
			+" nome_usuario as nome,"
			+" login_usuario as login,"
			+" cod_grupo as codgrupo,"
			+" ativo_usuario as ativo,"
			+" senha_usuario as senha  "
			+" from usuario "
			+" where "
			+" login_usuario = ? and senha_usuario = ?;";
	private String QUERY_BUSCAR_TODOS = " select "
			+" cod_usuario as cod ,"
			+" nome_usuario as nome,"
			+" login_usuario as login,"
			+" cod_grupo as codgrupo,"
			+" ativo_usuario as ativo,"
			+" senha_usuario as senha "
			+" from usuario";
	private String QUERY_LOGAR = " select "
			+" cod_usuario as cod,"
			+" login_usuario as login, "
			+" senha_usuario as senha "
			+" from usuario "
			+" where login_usuario = ? and senha_usuario = ?";
			
			
			
	private int getProximoValorSequence() throws DaoException{
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoUsuario=0;
		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoUsuario = result.getInt("NOVO_COD");
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		return novoUsuario;
		
	}
	public void Criar(Usuario usuario) throws DaoException {
		
		int novoCodUsuario  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			
			statement.setInt(1, novoCodUsuario);
			statement.setString(2, usuario.getNome());
			statement.setString(3, usuario.getLogin());
			statement.setInt(4, usuario.getGrupo().getCod());
			statement.setByte(5, usuario.getAtivo());
			statement.setString(6, usuario.getSenha());
			
			statement.execute();
			usuario.setCod(novoCodUsuario);
								
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
	}

	@Override
	
	
	public void AlterarSeha(Usuario usuario)throws DaoException{
		
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement =null;
		ResultSet result =null;
		
		try {
			
			statement = conn.prepareStatement(QUERY_ALTERARSENHA);
			statement.setString(1, usuario.getSenha());
			statement.setInt(2, usuario.getCod());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new DaoException(e);
		
		}finally{
			
			DBUtil.close(conn, statement, result);
			
		}
		
	}
	public void Alterar(Usuario usuario) throws DaoException {

		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_ATUALIZAR);
			statement.setString(1, usuario.getNome());
			statement.setString(2, usuario.getLogin());
			statement.setInt(3, usuario.getGrupo().getCod());
			statement.setByte(4, usuario.getAtivo());
			statement.setString(5, usuario.getSenha());
			statement.setInt(6, usuario.getCod());
			
			statement.executeUpdate();
					
		} catch (SQLException e) {
			throw new DaoException(e);
	
	
		}finally{
			DBUtil.close(conn, statement, result);
		}

		
	}

	public void Apagar(Usuario usuario) throws DaoException {

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, usuario.getCod());
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	
	
	public Usuario Logar(Usuario usuario) throws DaoException{
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Usuario user = new Usuario();
		try{
			statement = conn.prepareStatement(QUERY_LOGAR);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			result = statement.executeQuery();
			if(result.next()){
			user.setLogin(result.getString("login"));
			user.setSenha(result.getString("senha"));
			}
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return user;
			
	}
	public List<Usuario> buscarTodos() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Usuario> listaTodos = new ArrayList<Usuario>();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
			while (result.next()){
				
				Usuario retUsuario = getBeanFromStartament(result);
				listaTodos.add(retUsuario);
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listaTodos;
		
	
	}
	private Usuario getBeanFromStartament(ResultSet result) throws SQLException {
		
		Usuario us = new Usuario();
		Grupo gp = new Grupo();
			
		us.setCod(result.getInt("cod"));
		us.setNome(result.getString("nome"));
		us.setLogin(result.getString("login"));
		gp.setCod(result.getInt("codgrupo"));
		us.setGrupo(gp);
		us.setAtivo(result.getByte("ativo"));
		us.setSenha(result.getString("senha"));
			
		
		return us;
	}
	@Override
	public Usuario BuscarUserLogin(Usuario usuario) throws DaoException {


		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Usuario user = new Usuario();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_LOGINSENHA);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			result = statement.executeQuery();
			
			while (result.next()){
				
			user = getBeanFromStartament(result);
			
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return user;
			
	}

}
