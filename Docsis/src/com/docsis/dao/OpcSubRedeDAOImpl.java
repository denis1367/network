package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.OpcSubRede;
import com.docsis.beans.Subrede;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class OpcSubRedeDAOImpl implements OpcSubRedeDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	
	private String QUERY_SEQUENCE =" select ifnull(max(cod_opcsubrede),0)+1 NOVO_COD from opcsubrede; ";
	private String QUERY_CRIAR = "insert into opcsubrede ("
			+" cod_opcsubrede ,"
			+" nome_opcsubrede ,"
			+" valor_opcsubrede ,"
			+" cod_subrede ,"
			+" ativo_opcsubrede )"
			+" values( "
			+" ?,?,?,?,?)";
	private String QUERY_ALTERAR = " update opcsubrede set "
			+" nome_opcsubrede = ? ,"
			+" valor_opcsubrede = ? ,"
			+" cod_subrede = ? ,"
			+" ativo_opcsubrede = ? "
			+" where "
			+" cod_opcsubrede = ? ";
	private String QUERY_APAGAR = " delete from opcsubrede where cod_opcsubrede = ?";
	private String QUERY_BUSCAR_TODOS =" select "
			+" cod_opcsubrede as cod ,"
			+" nome_opcsubrede as nome,"
			+" valor_opcsubrede as valor,"
			+" cod_subrede as codsub,"
			+" ativo_opcsubrede as ativo"
			+" from opcsubrede ";
	private String QUERY_BUSCA_ATIVO = " select "
			+" cod_opcsubrede as cod ,"
			+" nome_opcsubrede as nome,"
			+" valor_opcsubrede as valor,"
			+" cod_subrede as codsub,"
			+" ativo_opcsubrede as ativo "
			+" from opcsubrede "
			+" where ativo_opcsubrede = ? ";
		
	private int getProximoValorSequence() throws DaoException{
		int novoCodOpcSubred=0;
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		
		try {
			
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodOpcSubred = result.getInt("NOVO_COD");					
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
	return novoCodOpcSubred;
		
	}
	@Override
	public void Criar(OpcSubRede opcsub) throws DaoException {
		int novoCod = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1, novoCod);
			statement.setString(2,opcsub.getNome());
			statement.setString(3, opcsub.getValor());
			statement.setInt(4, opcsub.getSubrede().getCodSubrede());
			statement.setByte(5, opcsub.getAtivo());
			statement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		
	}

	@Override
	public void Alterar(OpcSubRede opcsub) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.prepareStatement(QUERY_ALTERAR);
			statement.setString(1, opcsub.getNome());
			statement.setString(2, opcsub.getValor());
			statement.setInt(3, opcsub.getSubrede().getCodSubrede());
			statement.setByte(4, opcsub.getAtivo());
			statement.setInt(5, opcsub.getCod());
			statement.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			}finally{
			DBUtil.getConnection();
			}
		
		
	}

	@Override
	public void Apagar(int codOpcSub) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try {
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, codOpcSub);
			statement.execute();
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			}finally{
			DBUtil.getConnection();
			}
		
		
	}

	@Override
	public List<OpcSubRede> BuscarTodos() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<OpcSubRede> listaTodos = new ArrayList<OpcSubRede>();
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
			
			while(result.next()){
				
				OpcSubRede retOpcResult = getBeanFromStantermet(result);
				listaTodos.add(retOpcResult);
			}
			
			
						
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			}finally{
			DBUtil.getConnection();
			}
		
		return listaTodos;
	}

	private OpcSubRede getBeanFromStantermet(ResultSet result) throws SQLException {
		
		OpcSubRede opcSubRedeResult = new OpcSubRede();
		Subrede subrede = new Subrede();
		opcSubRedeResult.setCod(result.getInt("cod"));
		opcSubRedeResult.setNome(result.getString("nome"));
		opcSubRedeResult.setValor(result.getString("valor"));
		
		subrede.setCodSubrede(result.getInt("codsub"));
		opcSubRedeResult.setSubrede(subrede);
		
		opcSubRedeResult.setAtivo(result.getByte("ativo"));
		
		
		
		
		return opcSubRedeResult;
	}
	@Override
	public List<OpcSubRede> BuscarAtivo(int opcao) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<OpcSubRede> listaTodosAtivo = new ArrayList<OpcSubRede>();
		try {
			statement = conn.prepareStatement(QUERY_BUSCA_ATIVO);
			statement.setInt(1, opcao);
			result = statement.executeQuery();
			
			while(result.next()){
				
				OpcSubRede retOpcResult = getBeanFromStantermet(result);
				listaTodosAtivo.add(retOpcResult);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			}finally{
			DBUtil.getConnection();
			}
		
		return listaTodosAtivo;
	}

}
