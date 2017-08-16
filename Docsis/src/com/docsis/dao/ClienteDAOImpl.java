package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Cliente;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class ClienteDAOImpl implements ClienteDAO {
	
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE =" select ifnull(max(cod_cliente),0)+1 NOVO_COD from cliente;";
	private String QUERY_CRIAR =" insert into cliente ("
			+" cod_cliente, "
			+" nome_cliente, "
			+" cpf_cliente, "
			+" contrato_cliente,"
			+" caddata_cliente)"
			+" values( "
			+" ?,?,?,?,?)";
	private String QUERY_APAGAR_POR_COD= " delete from cliente where cod_cliente = ?";
	
	private String QUERY_ALTERAR = " update cliente set "
			+" nome_cliente = ?, "
			+" cpf_cliente = ?, "
			+" contrato_cliente = ?, "
			+" bloqdata_cliente = ? "
			+" where cod_cliente = ? ";
	
	private String QUERY_BUSCAR_POR_NOME = " select "
			+" cod_cliente as cod,"
			+" nome_cliente as nome,"
			+" cpf_cliente as cpf,"
			+" contrato_cliente as contrato, "
			+" caddata_cliente as cdata, "
			+" bloqdata_cliente as bdata "
			+" from cliente "
			+" where nome_cliente like ? ";
	
	private String QUERY_BUSCAR_POR_CONTRATO = " select "
			+" cod_cliente as cod,"
			+" nome_cliente as nome,"
			+" cpf_cliente as cpf,"
			+" contrato_cliente as contrato, "
			+" caddata_cliente as cdata, "
			+" bloqdata_cliente as bdata "
			+" from cliente "
			+" where contrato_cliente = ? ";
	
	private int getProximoValorSequence() throws DaoException{
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement=null;
		ResultSet result = null;
		int novoCodCli = 0;
		try{
			
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if(result.next()){
				novoCodCli = result.getInt("NOVO_COD");
				
			}
		}catch (SQLException e) {
			throw new DaoException(e);
			
			
		}finally{
			DBUtil.close(conn, statement, result);
		}
		return novoCodCli;
	}
	
	
	@Override
	public void Criar(Cliente cliente) throws DaoException {
	int novoCodCli = getProximoValorSequence();
	Connection conn =DBUtil.getConnection();
	PreparedStatement statement = null;
	ResultSet result = null;
	
	try{
		
		statement = conn.prepareStatement(QUERY_CRIAR);
		statement.setInt(1, novoCodCli);
		statement.setString(2, cliente.getNome());
		statement.setString(3, cliente.getCpf());
		statement.setLong(4, cliente.getContrato());
		statement.setDate(5, DBUtil.getSqlDate(cliente.getCaddata()));
		statement.execute();
		cliente.setCodCliente(novoCodCli);
	}catch (SQLException e) {
		e.printStackTrace();
		throw new DaoException();
		
	}finally{
		DBUtil.close(conn, statement, result);
		}
	}

	@Override
	public void Alterar(Cliente cliente) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.prepareStatement(QUERY_ALTERAR);
			
			statement.setString(1, cliente.getNome());
			statement.setString(2, cliente.getCpf());
			statement.setLong(3, cliente.getContrato());
			statement.setDate(4,DBUtil.getSqlDate(cliente.getBloqdata()));
			statement.setInt(5, cliente.getCodCliente());
			statement.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
			}
		}

	@Override
	public void Apagar(int codCli) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.prepareStatement(QUERY_APAGAR_POR_COD);
			statement.setInt(1, codCli);
			statement.execute();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
			}

	}

	@Override
	public List<Cliente> BuscarPorNome(String nome) throws DaoException {
		nome = "%"+nome+"%";
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		List<Cliente> cliPorNome = new ArrayList<Cliente>();
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_NOME);
			statement.setString(1,nome);
			result = statement.executeQuery();
			
			while(result.next()){
				
				Cliente retcli = getBeanFronStatement(result);
				
				cliPorNome.add(retcli);
			}
						
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
			}
				
		return cliPorNome;
	}

	private Cliente getBeanFronStatement(ResultSet retResult) throws SQLException {
		
		Cliente retCliResltSet = new Cliente();
		try {
			retCliResltSet.setCodCliente(retResult.getInt("cod"));
			
			retCliResltSet.setNome(retResult.getString("nome"));
			retCliResltSet.setCpf(retResult.getString("cpf"));
			retCliResltSet.setContrato(retResult.getLong("contrato"));
			retCliResltSet.setCaddata(retResult.getDate("cdata"));
			retCliResltSet.setBloqdata(retResult.getDate("bdata"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
			
		
		
		return retCliResltSet;
		
	}


	@Override
	public Cliente BuscarPorContrato(int contrato) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Cliente retCli = null;
		try{
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_CONTRATO);
			statement.setInt(1, contrato);
			result = statement.executeQuery();
			if(result.next()){
				retCli = getBeanFronStatement(result);	
			}
			
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
			}
		
		
		
		return retCli;
	}

}
