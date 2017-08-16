package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.AreaCmts;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class AreaCmtsDAOImpl implements AreaCmtsDAO{
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull(max(cod_areacmts),0)+1 NOVO_COD from areacmts;";
	private String CRIAR_AREACMTS =
				" insert into areacmts ("
				+" cod_areacmts ,"
				+" nome_areacmts, "
				+" ativo_areacmts "
				+" )"
				+" values ("
				+" ?,?,?)";
	
	private String QUERY_APAGAR_POR_COD= " delete from areacmts where cod_areacmts = ? ";
	
	private String  QUERY_BUSCAR_TUDO = 
			" select "
			+" cod_areacmts as cod,"
			+" nome_areacmts as nome,"
			+" ativo_areacmts as ativo"
			+" from "
			+" areacmts "
			+" order by cod_areacmts ";
	private String QUERY_BUCAR_POR_COD= 
			"select "
			+" cod_areacmts as cod,"
			+" nome_areacmts as nome ,"
			+" ativo_areacmts as ativo "
			+" from areacmts "
			+" where cod_areacmts = ?";
	
	private String QUERY_BUCAR_POR_ATIVO= 
			"select "
			+" cod_areacmts as cod,"
			+" nome_areacmts as nome ,"
			+" ativo_areacmts as ativo "
			+" from areacmts "
			+" where ativo_cmts = ?";
	
	private String QUERY_ATUALIZAR_AREACMTS = "update areacmts "
			+" set "
			+" nome_areacmts = ?,"
			+" ativo_areacmts = ?"
			+" where "
			+" cod_areacmts = ? ";
	
	private int getProximoValorSequence() throws DaoException{
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodArea=0;
		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodArea = result.getInt("NOVO_COD");
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		return novoCodArea;
	}
	
	public void criar(AreaCmts areaCmts) throws DaoException{
		int novoCodArea  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(CRIAR_AREACMTS);
			statement.setInt(1, novoCodArea);
			statement.setString(2, areaCmts.getNome());
			statement.setByte(3, areaCmts.getAtivo());
			statement.execute();
			areaCmts.setCodareacmts(novoCodArea);
								
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
	}

	@Override
	public List<AreaCmts> buscarTodosAtivo() throws DaoException {
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<AreaCmts> listAreaAtivo = new ArrayList<AreaCmts>();
		
		try {
			statement = conn.prepareStatement(QUERY_BUCAR_POR_ATIVO);
			result = statement.executeQuery();
			
			while (result.next()){
				 
				AreaCmts areaCmts = getBeanFromStatement(result);
				
				listAreaAtivo.add(areaCmts);
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		return listAreaAtivo;
	}

	private AreaCmts getBeanFromStatement(ResultSet result) throws SQLException {
		AreaCmts areacmts = new AreaCmts();
		areacmts.setCodareacmts(result.getInt("cod"));
		areacmts.setNome(result.getString("nome"));
		areacmts.setAtivo(result.getByte("ativo"));
			
		return areacmts;
	}

	@Override
	public void apagar(int codAreaCmts) throws DaoException {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.prepareStatement(QUERY_APAGAR_POR_COD);
			statement.setInt(1, codAreaCmts);
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
	}

	@Override
	public void alterar(AreaCmts areaCmts) throws DaoException {
	
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_ATUALIZAR_AREACMTS);
			statement.setString(1,areaCmts.getNome());
			statement.setByte(2, areaCmts.getAtivo());
			statement.setInt(3, areaCmts.getCodareacmts());
			statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
	}

	@Override
	public List<AreaCmts> buscarTodos() throws DaoException {
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<AreaCmts> listAreaTodos = new ArrayList<AreaCmts>();
		
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_TUDO);
			result = statement.executeQuery();
				while (result.next()){
				AreaCmts areaCmts = getBeanFromStatement(result);
				listAreaTodos.add(areaCmts);
				}
			} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listAreaTodos;
	}

	@Override
	public AreaCmts buscarPorCod(int cod) throws DaoException {
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		AreaCmts areaPorCod = new AreaCmts();
		
		try {
			statement = conn.prepareStatement(QUERY_BUCAR_POR_COD);
			statement.setInt(1,cod);
			result = statement.executeQuery();
			
			if (result.next()){
				 
				areaPorCod = getBeanFromStatement(result);
				
						
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		return areaPorCod;
	
	}
}
