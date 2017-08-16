package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Grupo;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;


public class GrupoDAOImpl implements GrupoDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENE = " select ifnull(max(cod_grupo),0)+1 NOVO_COD from grupo";
	private String QUERY_CRIAR = " insert into grupo ("
			+" cod_grupo, "
			+" nome_grupo ,"
			+" cadcmts_grupo ) "
			+" values ( "
			+" ?,?,?)";
	private String QUERY_ALTERAR = " update grupo set "
			+" nome_grupo = ?,"
			+" cadcmts_grupo = ? "
			+" where cod_grupo = ? ";
	private String QUERY_APAGAR = " delete from grupo where cod_grupo = ? ";
	private String QUERY_BUSCAR_TODOS = " select "
			+" cod_grupo as cod, "
			+" nome_grupo as nome, "
			+" cadcmts_grupo as cad "
			+" from grupo "
			+" order by cod_grupo ";
	private int getProximoValorSequence() throws DaoException{
		int novoCodGrupo=0;
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = conn.prepareStatement(QUERY_SEQUENE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodGrupo = result.getInt("NOVO_COD");
			}			
		}catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);
		}
		
		
		return novoCodGrupo;
	}
	
	
	public void Criar(Grupo grupo) throws DaoException {
		int novoCodGrupo = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1, novoCodGrupo);
			statement.setString(2, grupo.getNome());
			statement.setByte(3, grupo.getCadcmts());
			statement.execute();
			
			grupo.setCod(novoCodGrupo);
			
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Alterar(Grupo grupo) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.prepareStatement(QUERY_ALTERAR);
			statement.setString(1, grupo.getNome());
			statement.setByte(2, grupo.getCadcmts());
			statement.setInt(3, grupo.getCod());
			statement.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
	}

	
	public void Apagar(Grupo grupo) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, grupo.getCod());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public List<Grupo> BuscarTodos() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Grupo> todosGrupos = new ArrayList<>();
		
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
			
			while(result.next()){
				Grupo retGru = getBeanFromStartament(result);
				todosGrupos.add(retGru);
				
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}	
		
		
		
		
		return todosGrupos;
	}


	private Grupo getBeanFromStartament(ResultSet result) throws SQLException {
		Grupo grupo = new Grupo();
		grupo.setCod(result.getInt("cod"));
		grupo.setNome(result.getString("nome"));
		grupo.setCadcmts(result.getByte("cad"));
		
		return grupo;
	}

}
