package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.AreaCmts;
import com.docsis.beans.PoolSubrede;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class SubredeDAOImpl implements SubredeDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull(max(cod_subrede),0)+1 NOVO_COD from subrede";
	private String QUERY_CRIAR =" insert into subrede ("
			+" cod_subrede,"
			+" rede_subrede ,"
			+" netmask_subrede ,"
			+" cod_areacmts ,"
			+" ativo_subrede ,"
			+" gerencia_subrede, "
			+" tipo_subrede )"
			+" values ( "
			+" ?,?,?,?,?,?,? )";
	private String QUERY_ATUALIZAR = " update subrede set "
			+" rede_subrede = ? ,"
			+" netmask_subrede = ? ,"
			+" cod_areacmts = ? ,"
			+" ativo_subrede = ? ,"
			+" gerencia_subrede = ? ,"
			+" tipo_subrede = ? "
			+" where "
			+" cod_subrede = ?";
	private String QUERY_APAGAR = " delete from subrede where cod_subrede = ? ";
	
	private String QUERY_BUSCAR_POR_STATUS_ETIPO = " select "
			+" cod_subrede as cod ,"
			+" rede_subrede as rede  ,"
			+" netmask_subrede as netmask,"
			+" cod_areacmts as areacmts,"
			+" ativo_subrede as ativo,"
			+" gerencia_subrede as gerencia, "
			+" tipo_subrede as tipo "
			+" from subrede"
			+" where "
			+" ativo_subrede = ? and tipo_subrede = ? and gerencia_subrede = 0";
	private String QUERY_BUSCAR_POR_GERENCIA = " select "
					+" cod_subrede as cod ,"
					+" rede_subrede as rede  ,"
					+" netmask_subrede as netmask,"
					+" cod_areacmts as areacmts,"
					+" ativo_subrede as ativo,"
					+" gerencia_subrede as gerencia, "
					+" tipo_subrede as tipo "
					+" from subrede"
					+" where "
					+" gerencia_subrede = ?";
			
			
	private int getProximoValorSequence() throws DaoException{
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodSubrede=0;
		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodSubrede = result.getInt("NOVO_COD");
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		return novoCodSubrede;
		
		
	}
	
	public void Criar(Subrede subrede) throws DaoException {
		int novoCodSubrede  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1, novoCodSubrede);
			statement.setString(2, subrede.getRede());
			statement.setString(3, subrede.getNetmask());
			statement.setInt(4, subrede.getAreacmts().getCodareacmts());
			statement.setByte(5, subrede.getAtivo());
			statement.setByte(6, subrede.getGerencia());
			statement.setByte(7, subrede.getTipo());
			
			
			
			statement.execute();
			subrede.setCodSubrede(novoCodSubrede);
								
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Alterar(Subrede subrede) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_ATUALIZAR);
			statement.setString(1, subrede.getRede());
			statement.setString(2, subrede.getNetmask());
			statement.setInt(3, subrede.getAreacmts().getCodareacmts());
			statement.setByte(4, subrede.getAtivo());
			statement.setByte(5, subrede.getGerencia());
			statement.setByte(6, subrede.getTipo());
			statement.setInt(7, subrede.getCodSubrede());
			statement.executeUpdate();
			
				
		} catch (SQLException e) {
			throw new DaoException(e);
	
	
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Apagar(Subrede subrede) throws DaoException {
		
		

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, subrede.getCodSubrede());
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public List<Subrede> buscarPorStatusEtipo(Subrede subrede) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Subrede> listaTodos = new ArrayList<Subrede>();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_STATUS_ETIPO);
			statement.setByte(1, subrede.getAtivo());
			statement.setByte(2, subrede.getTipo());
			result = statement.executeQuery();
			while (result.next()){
				
				Subrede retSubrede = getBeanFromStartament(result);
				listaTodos.add(retSubrede);
			}
					
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listaTodos;
	}

	private Subrede getBeanFromStartament(ResultSet result) throws SQLException {
				Subrede sub = new Subrede();
				AreaCmts area = new AreaCmts();
				
						
						
				sub.setCodSubrede(result.getInt("cod"));
				sub.setRede(result.getString("rede"));
				sub.setNetmask(result.getString("netmask"));
				
				area.setCodareacmts(result.getInt("cod"));
				sub.setAreacmts(area);
				
				sub.setAtivo(result.getByte("ativo"));
				
								
				sub.setGerencia(result.getByte("gerencia"));
				sub.setTipo(result.getByte("tipo"));
				
		
		
		return sub;
	}
}
