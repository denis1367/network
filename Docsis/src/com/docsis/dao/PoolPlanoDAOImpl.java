package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Plano;
import com.docsis.beans.PoolPlano;
import com.docsis.beans.PoolSubrede;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class PoolPlanoDAOImpl implements PoolPlanoDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull(max(cod_poolplano),0)+1 NOVO_COD from poolplano ";
	private String QUERY_CRIAR ="insert into poolplano ("
			+" cod_poolplano ,"
			+" nome_poolplano,"
			+" ativo_poolplano,"
			+" cod_plano,"
			+" cod_poolsubrede )"
			+" values ("
			+" ?,?,?,?,? )" ;
	private String QUERY_ATUALIZAR =" update poolplano set "
			+" nome_poolplano = ?,"
			+" ativo_poolplano = ?,"
			+" cod_plano = ?,"
			+" cod_poolsubrede = ? "
			+" where "
			+" cod_poolplano = ? ";
	private String QUERY_APAGAR = " delete from poolplano where cod_poolplano = ? ";
	
	private String QUERY_BUSCAR_POR_STATUS = " select "
			+" cod_poolplano as cod,"
			+" nome_poolplano as nome,"
			+" ativo_poolplano as ativo,"
			+" cod_plano as codplano,"
			+" cod_poolsubrede as codpsubrede "
			+" from poolplano "
			+" where "
			+" ativo_poolplano = ? ";
	
	private int getProximoValorSequence() throws DaoException{
		Connection conn =DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodPoolPlano=0;
		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodPoolPlano = result.getInt("NOVO_COD");
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
		return novoCodPoolPlano;
		
	}
	
	
	@Override
	public void Criar(PoolPlano poolPlano) throws DaoException {
		int novoCodPoolPlano  = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1,novoCodPoolPlano); 
			statement.setString(2, poolPlano.getNome());
			statement.setByte(3, poolPlano.getAtivo());
			statement.setInt(4,poolPlano.getPlano().getCodPlano());
			statement.setInt(5, poolPlano.getPoolSubRede().getCod());
			statement.execute();
			poolPlano.setCodpoolplano(novoCodPoolPlano);
								
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}

	}

	
	public void Alterar(PoolPlano poolPlano) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			
			statement = conn.prepareStatement(QUERY_ATUALIZAR);
			statement.setString(1,poolPlano.getNome());
			statement.setByte(2, poolPlano.getAtivo());
			statement.setInt(3, poolPlano.getPlano().getCodPlano());
			statement.setInt(4, poolPlano.getPoolSubRede().getCod());
			statement.setInt(5, poolPlano.getCodpoolplano());
			statement.executeUpdate();
				
		} catch (SQLException e) {
			throw new DaoException(e);
	
	
		}finally{
			DBUtil.close(conn, statement, result);
		}

		

	}

	@Override
	public void Apagar(PoolPlano codPoolPlano) throws DaoException {

		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			statement = conn.prepareStatement(QUERY_APAGAR);
			statement.setInt(1, codPoolPlano.getCodpoolplano());
			statement.execute();
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		
		
	}

	@Override
	public List<PoolPlano> BuscarTodos(int ativo) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<PoolPlano> listaTodos = new ArrayList<PoolPlano>();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_STATUS);
			statement.setInt(1, ativo);
			result = statement.executeQuery();
			while (result.next()){
				
				PoolPlano retPplano = getBeanFromStartament(result);
				listaTodos.add(retPplano);
				
				
			}
			
			
		}catch(SQLException e){
			e.printStackTrace();
			throw new DaoException();
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return listaTodos;
		
	}


	private PoolPlano getBeanFromStartament(ResultSet result) throws SQLException {
		
		PoolPlano pplano = new PoolPlano();
		Plano plano = new Plano();
		PoolSubrede pSubRed = new PoolSubrede();
	pplano.setCodpoolplano(result.getInt("cod"));
	pplano.setNome(result.getString("nome"));
	pplano.setAtivo(result.getByte("ativo"));
	plano.setCodPlano(result.getInt("codplano"));
	pplano.setPlano(plano);
	pSubRed.setCod(result.getInt("codpsubrede"));
	pplano.setPoolSubRede(pSubRed);
	
	return pplano;
	}

}
