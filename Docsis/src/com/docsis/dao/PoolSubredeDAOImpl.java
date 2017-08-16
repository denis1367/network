package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.PoolSubrede;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class PoolSubredeDAOImpl implements PoolSubredeDAO {
private static final Logger LOG = Logger.getLogger(DBUtil.class);
private String QUERY_SEQUENCE = " select ifnull(max(cod_poolsubrede),0)+1 NOVO_COD from poolsubrede";
private String QUERY_CRIAR =" insert into poolsubrede ("
	+" cod_poolsubrede,"
	+" nome_poolsubrede,"
	+" ativo_poolsubrede,"
	+" cod_subrede )"
	+" values ("
	+" ?,?,?,?)";
private String QUERY_ATUALIZAR = " update poolsubrede set"
		+" nome_poolsubrede = ?,"
		+" ativo_poolsubrede = ?,"
		+" cod_subrede = ? "
		+" where "
		+" cod_poolsubrede = ? ";
private String QUERY_APAGAR = " delete from poolsubrede where cod_poolsubrede = ? ";
private String QUERY_BUSCAR_POR_STATUS = " select "
		+" cod_poolsubrede as cod,"
		+" nome_poolsubrede as nome,"
		+" ativo_poolsubrede as ativo,"
		+" cod_subrede as codsubrede "
		+" from poolsubrede"
		+" where "
		+" ativo_poolsubrede = ? ";
		
private int getProximoValorSequence() throws DaoException{
	Connection conn =DBUtil.getConnection();
	PreparedStatement statement = null;
	ResultSet result = null;
	int novoCodPoolSubrede=0;
	try {
		statement = conn.prepareStatement(QUERY_SEQUENCE);
		result = statement.executeQuery();
		if(result.next()){
			novoCodPoolSubrede = result.getInt("NOVO_COD");
		}
		
	} catch (SQLException e) {
		throw new DaoException(e);
		
		
	}finally{
		DBUtil.close(conn, statement, result);
	}
	
	
	return novoCodPoolSubrede;
	
	
}
	
	
	
	
	@Override
	public void Criar(PoolSubrede poolSubrede) throws DaoException {
		
		int novoCodPoolSubrede  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1,novoCodPoolSubrede); 
			statement.setString(2, poolSubrede.getNome());
			statement.setByte(3, poolSubrede.getAtivo());
			statement.setInt(4, poolSubrede.getSubRede().getCodSubrede());
			statement.execute();
			poolSubrede.setCod(novoCodPoolSubrede);
								
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}

		
		
	}

	@Override
	public void Alterar(PoolSubrede poolSubrede) throws DaoException {

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_ATUALIZAR);
			statement.setString(1, poolSubrede.getNome());
			statement.setByte(2, poolSubrede.getAtivo());
			statement.setInt(3, poolSubrede.getSubRede().getCodSubrede());
			statement.setInt(4, poolSubrede.getCod());
			statement.executeUpdate();
			
				
		} catch (SQLException e) {
			throw new DaoException(e);
	
	
		}finally{
			DBUtil.close(conn, statement, result);
		}

		
		


	}

	@Override
	public void Apagar(PoolSubrede poolSubrede) throws DaoException {
		
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, poolSubrede.getCod());
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
	}

	@Override
	public List<PoolSubrede> buscarPorStatus(PoolSubrede poolSubrede)throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PoolSubrede> listaTodos = new ArrayList<PoolSubrede>();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_STATUS);
			statement.setInt(1, poolSubrede.getAtivo());
			result = statement.executeQuery();
			while (result.next()){
				
				PoolSubrede retPsubrede = getBeanFromStartament(result);
				listaTodos.add(retPsubrede);
			}
					
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listaTodos;
		}

	private PoolSubrede getBeanFromStartament(ResultSet result) throws SQLException {

		PoolSubrede pSubrede = new PoolSubrede();
		Subrede subrede = new Subrede();
		pSubrede.setCod(result.getInt("cod"));
		pSubrede.setNome(result.getString("nome"));
		pSubrede.setAtivo(result.getByte("ativo"));
		subrede.setCodSubrede(result.getInt("codsubrede"));
		
		pSubrede.setSubRede(subrede);
		
		

		return pSubrede;
	}

}
