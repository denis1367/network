package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.OpcaoDhcp;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class OpcaodhcpDAOImpl implements OpcaodhcpDAO{
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = "select ifnull(max(cod_opcaodhcp),0)+1 NOVO_COD from opcaodhcp;";
	
	private String CRIAR_OPCAODHCP = 
			"insert into opcaodhcp (" 
					+" cod_opcaodhcp ,"
					+" option_opcaodhcp ,"
					+" valor_opcaodhcp ,"
					+" ativo_opcaodhcp "
					+" ) " 
					+" values("
					+" ?,?,?,?)" ;
	
	
	private String QUERY_APAGAR_POR_COD = "delete from opcaodhcp where cod_opcaodhcp = ?";
	
	private String QUERY_BUSCAR_TODOS = 
			"select "
							+" cod_opcaodhcp as cod ,"
							+" option_opcaodhcp as opcao ,"
							+" valor_opcaodhcp as valor,"
							+" ativo_opcaodhcp as ativo "
							+" from "
							+" opcaodhcp "
							+" order by cod_opcaodhcp; ";
	
	private String QUERY_BUSCAR_COD = 
			"select "
							+" cod_opcaodhcp as cod ,"
							+" option_opcaodhcp as opcao ,"
							+" valor_opcaodhcp as valor,"
							+" ativo_opcaodhcp as ativo "
							+" from "
							+" opcaodhcp "
							+" where cod_opcaodhcp = ?; ";
	
	


	private String QUERY_ATUALIZAR_OPCAODHCP = 
			"update opcaodhcp " + 
			"set " 
			+" option_opcaodhcp = ?,"
			+" valor_opcaodhcp = ?,"
			+" ativo_opcaodhcp = ? "
			+" where  " 
			+" cod_opcaodhcp = ? ";
	
	private int getProximoValorSequence() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodProduto = 0;
		try {
			
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if (result.next()) {

				novoCodProduto = result.getInt("NOVO_COD");
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);
		}
		return novoCodProduto;
	}
	
	
	
	
	public void criar(OpcaoDhcp opcaoDhcp) throws DaoException {
		
		int novoCodOpcaoDhcp = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			
			statement= conn.prepareStatement(CRIAR_OPCAODHCP);
			statement.setInt(1, novoCodOpcaoDhcp);
			statement.setString(2,opcaoDhcp.getOption()) ;
			statement.setString(3,opcaoDhcp.getValor());
			statement.setByte(4, opcaoDhcp.getAtivo());
			statement.execute();
			
			opcaoDhcp.setCod(novoCodOpcaoDhcp);
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
	}

	@Override
	public List<OpcaoDhcp> buscarTodos() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement =null;
		ResultSet result =null;
		List<OpcaoDhcp> listaTudo = new ArrayList<OpcaoDhcp>();
		
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
			while(result.next()){
				
				OpcaoDhcp opcdhcp = getBeanFromStatement(result);
				
				listaTudo.add(opcdhcp);
				
			}
		}catch (SQLException e){
			throw new DaoException(e);
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		return listaTudo;
	}

	private OpcaoDhcp getBeanFromStatement(ResultSet result) throws SQLException,DaoException {
		OpcaoDhcp opcaodhcp = new OpcaoDhcp();
		opcaodhcp.setCod(result.getInt("cod"));
		opcaodhcp.setOption(result.getString("opcao"));
		opcaodhcp.setValor(result.getString("valor"));
		opcaodhcp.setAtivo(result.getByte("ativo"));
		
		return opcaodhcp;
		
	}




	@Override
	public void apagar(int codOpcaodhcp) throws DaoException {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result =null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_APAGAR_POR_COD);
			statement.setInt(1,codOpcaodhcp);
			statement.execute();
			
		
		}catch(SQLException e){
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
	}

	@Override
	public void alterar(OpcaoDhcp opcaodhcp) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.prepareStatement(QUERY_ATUALIZAR_OPCAODHCP);
			statement.setString(1,opcaodhcp.getOption());
			statement.setString(2, opcaodhcp.getValor());
			statement.setByte(3, opcaodhcp.getAtivo());
			statement.setInt(4,opcaodhcp.getCod());
			statement.executeUpdate();
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		
	}

	@Override
	public OpcaoDhcp buscarPorCodigo(int cod) throws DaoException {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement =null;
		ResultSet result =null;
		OpcaoDhcp buscaCodigo = new OpcaoDhcp();
		
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_COD);
			statement.setInt(1, cod);
			result = statement.executeQuery();
			if(result.next()){
				
					buscaCodigo = getBeanFromStatement(result);
							
				
			}
		}catch (SQLException e){
			throw new DaoException(e);
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		
		
		return buscaCodigo;
	}
	

}
