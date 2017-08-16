package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Plano;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class PlanoDAOImpl implements PlanoDAO {
	
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull (max(cod_plano),0)+1 NOVO_COD from plano";
	private String QUERY_CRIAR = " insert into plano ("
			+" cod_plano ,"
			+" nome_plano ,"
			+" nserver_plano ,"
			+" arqbinario_plano,"
			+" ativo_plano )"
			+" values ("
			+" ?,?,?,?,?)";
	private String QUERY_ALTERAR = " update plano set "
			+" nome_plano = ? ,"
			+" nserver_plano = ? ,"
			+" arqbinario_plano = ?,"
			+" ativo_plano = ? "
			+" where "
			+" cod_plano = ?";
	private String QUERY_APAGAR = " delete from plano where cod_plano = ?";
	private String QUERY_BUSCAR_TODOS =  " select "
			+" cod_plano as cod,"
			+" nome_plano as nome,"
			+" nserver_plano as nserver,"
			+" arqbinario_plano as bin,"
			+" ativo_plano as ativo "
			+" from plano";
	private String QUERY_BUSCAR_CODIGO =  " select "
			+" cod_plano as cod,"
			+" nome_plano as nome,"
			+" nserver_plano as nserver,"
			+" arqbinario_plano as bin,"
			+" ativo_plano as ativo "
			+" from plano " 
			+" where cod_plano = ?";
	private String QUERY_BUSCAR_ATIVO = " select "
			+" cod_plano as cod,"
			+" nome_plano as nome,"
			+" nserver_plano as nserver,"
			+" arqbinario_plano as bin,"
			+" ativo_plano as ativo "
			+" from plano"
			+" where "
			+" ativo_plano = ?";
	
private int getProximoValorSequence() throws DaoException{
	Connection conn =DBUtil.getConnection();
	PreparedStatement statement = null;
	ResultSet result = null;
	int novoCodPlano=0;
	try {
		statement = conn.prepareStatement(QUERY_SEQUENCE);
		result = statement.executeQuery();
		if(result.next()){
			novoCodPlano = result.getInt("NOVO_COD");
		}
		
	} catch (SQLException e) {
		throw new DaoException(e);
		
		
	}finally{
		DBUtil.close(conn, statement, result);
	}
	
	
	return novoCodPlano;
}
	
	@Override
	public void Criar(Plano plano) throws DaoException {
		int novoCodPlano  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			
			statement.setInt(1,novoCodPlano);
			statement.setString(2, plano.getNome());
			statement.setString(3, plano.getNserver());
			statement.setString(4, plano.getArqbinario());
			statement.setByte(5, plano.getAtivo());
			statement.execute();
			plano.setCodPlano(novoCodPlano);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Alterar(Plano plano) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_ALTERAR);
			statement.setString(1, plano.getNome());
			statement.setString(2, plano.getNserver());
			statement.setString(3, plano.getArqbinario());
			statement.setByte(4, plano.getAtivo());
			statement.setInt(5, plano.getCodPlano());
			
			statement.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}

	}

	@Override
	public void Apagar(int codPlano) throws DaoException {

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, codPlano);
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		

	}

	@Override
	public List<Plano> BuscarTodos() throws DaoException {
		
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Plano> listPlanoTodos = new ArrayList<Plano>();
		
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
				while (result.next()){
				Plano plano = getBeanFromStatement(result);
				listPlanoTodos.add(plano);
				}
			} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listPlanoTodos;
	}

	private Plano getBeanFromStatement(ResultSet result) throws SQLException {
		Plano plano = new Plano();
		
		plano.setCodPlano(result.getInt("cod"));
		plano.setNome(result.getString("nome"));
		plano.setNserver(result.getString("nserver"));
		plano.setArqbinario(result.getString("bin"));
		plano.setAtivo(result.getByte("ativo"));
	
		return plano;
	}

	@Override
	public List<Plano> BuscarTodosAtivo() throws DaoException {
		byte ativo = 1;
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Plano> listPlanoAtivo = new ArrayList<Plano>();
		
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_ATIVO);
			statement.setByte(1, ativo);
			result = statement.executeQuery();
				while (result.next()){
				Plano plano = getBeanFromStatement(result);
				listPlanoAtivo.add(plano);
				}
			} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listPlanoAtivo;
	}

	@Override
	public Plano BuscarPorCodigo(int cod) throws DaoException {
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Plano planoCod = new Plano();
		
		try {
			statement = conn.prepareStatement(QUERY_BUSCAR_CODIGO);
			statement.setInt(1, cod);
			result = statement.executeQuery();
				if (result.next()){
					planoCod = getBeanFromStatement(result);
				
				}
			} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return planoCod;
	}

}
