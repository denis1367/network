package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.PoolOpc;
import com.docsis.beans.PoolSubrede;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class PoolOpcDAOImpl implements PoolOpcDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
private String QUERY_SEQUENCE = " select ifnull (max(cod_poolopc),0)+1 NOVO_COD from poolopc";
private String QUERY_CRIAR = " insert into poolopc ("
+" cod_poolopc,"
+" nome_poolopc,"
+" valor_poolopc,"
+" ativo_poolopc,"
+" cod_poolsubrede,"
+" cable_poolopc )"
+" values ("
+" ?,?,?,?,?,?)";
private String QUERY_ALTERAR = " update poolopc set "
		+" nome_poolopc = ?,"
		+" valor_poolopc = ?,"
		+" ativo_poolopc = ?,"
		+" cod_poolsubrede = ?,"
		+" cable_poolopc = ? "
		+" where "
		+" cod_poolopc = ?";
private String QUERY_APAGAR = " delete from poolopc where cod_poolopc = ? ";
private String QUERY_BUSCAR_ATIVO_CABLE = " select "
		+" cod_poolopc as cod,"
		+" nome_poolopc as nome,"
		+" valor_poolopc as valor,"
		+" ativo_poolopc as ativo,"
		+" cod_poolsubrede as codpoolsubred,"
		+" cable_poolopc as cable "
		+" from poolopc "
		+" where "
		+" ativo_poolopc = ? and cable_poolopc = ? ";

	
	
	
	
	private int getProximoValorSequence() throws DaoException{
		
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodPoolOpc =0;
		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodPoolOpc = result.getInt("NOVO_COD");
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
		return novoCodPoolOpc;
		
	}

	@Override
	public void Criar(PoolOpc poolOpc) throws DaoException {
		int novoCodPoolOpc  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1, novoCodPoolOpc);
			statement.setString(2, poolOpc.getNome());
			statement.setString(3, poolOpc.getValor());
			statement.setByte(4, poolOpc.getAtivo());
			statement.setInt(5, poolOpc.getPoolsubrede().getCod());
			statement.setByte(6, poolOpc.getCable());
			statement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
	}

	@Override
	public void Alterar(PoolOpc poolOpc) throws DaoException {

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_ALTERAR);
			statement.setString(1, poolOpc.getNome());
			statement.setString(2, poolOpc.getValor());
			statement.setByte(3, poolOpc.getAtivo());
			statement.setInt(4, poolOpc.getPoolsubrede().getCod());
			statement.setByte(5, poolOpc.getCable());
			statement.setInt(6, poolOpc.getCod());
			statement.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Apagar(PoolOpc poolOpc) throws DaoException {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;

		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, poolOpc.getCod());
			statement.execute();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
	}

	@Override
	public List<PoolOpc> BuscarStatus(PoolOpc poolOpc ) throws DaoException {
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PoolOpc> listTodos = new ArrayList<PoolOpc>();
		
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_ATIVO_CABLE);
			statement.setByte(1, poolOpc.getAtivo());
			statement.setByte(2, poolOpc.getCable());
			result = statement.executeQuery();
				while (result.next()){
				PoolOpc poolopc = getBeanFromStatement(result);
				listTodos.add(poolopc);
				}
			} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listTodos;
	}

	private PoolOpc getBeanFromStatement(ResultSet result) throws SQLException {
		PoolOpc poolOpc = new PoolOpc();
		PoolSubrede poolSubRede = new PoolSubrede();
		poolOpc.setCod(result.getInt("cod"));
		poolOpc.setNome(result.getString("nome"));
		poolOpc.setValor(result.getString("valor"));
		poolOpc.setAtivo(result.getByte("ativo"));
		poolSubRede.setCod(result.getInt("codpoolsubred"));
		poolOpc.setPoolsubrede(poolSubRede);
		poolOpc.setCable(result.getByte("cable"));
				
		return poolOpc;
	}

}
