package com.docsis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.docsis.beans.Cable;
import com.docsis.beans.Cliente;
import com.docsis.beans.Plano;
import com.docsis.exception.DaoException;
import com.docsis.util.DBUtil;

public class CableDAOImpl implements CableDAO {
	private static final Logger LOG = Logger.getLogger(DBUtil.class);
	private String QUERY_SEQUENCE = " select ifnull (max(cod_cable),0)+1 NOVO_COD from cable;";

	private String QUERY_CRIAR = " insert into cable (" 
			+ " cod_cable, "
			+ " mac_cable, " 
			+ " cod_cliente, " 
			+ " serial_cable, "
			+ " cod_plano, " 
			+ " ativo_cable, " 
			+ " caddata_cable, "
			+ " hcad_cable )"
			+ " values ("
			+ " ?,?,?,?,?,?,?,?)";


	private String QUERY_APAGAR_POR_COD = " delete from cable where cod_cable = ?";

	private String QUERY_BUSCAR_POR_COD_CLI = " select "
			+ " cod_cable as cod, " 
			+ " mac_cable as mac, "
			+ " cod_cliente as codcli, " 
			+ " serial_cable as serial, "
			+ " cod_plano as plano, " 
			+ " ativo_cable as ativo, "
			+ " caddata_cable as cadata, " 
			+ " bloqdata_cable as bdt, "
			+ " excludata_cable as edt,"
			+ " hcad_cable as hcd,"
			+ " hbloq_cable as hblq"
			+ " from cable where cod_cliente = ?";

	private String QUERY_BUSCAR_TODOS = " select "
			+ " cod_cable as cod, "
			+ " mac_cable as mac, " + " cod_cliente as codcli, "
			+ " serial_cable as serial, " + " cod_plano as plano, "
			+ " ativo_cable as ativo, " + " caddata_cable as cadata, "
			+ " bloqdata_cable as bdt, " + " excludata_cable as edt,"
			+ " hcad_cable as hcd,"+ " hbloq_cable as hblq"
			+ " from cable ";
	private String QUERY_ALTPLANO_BLOQU = " update cable  set "
			+" cod_plano = ? , "
			+ " ativo_cable = ? , "
			+ " bloqdata_cable = ? , " 
			+ " excludata_cable = ? ,"
			+ " hbloq_cable = ? "
			+ " where cod_cable = ?";
	
	private int getProximoValorSequence() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		int novoCodCalbe = 0;

		try {
			statement = conn.prepareStatement(QUERY_SEQUENCE);
			result = statement.executeQuery();
			if (result.next()) {
				novoCodCalbe = result.getInt("NOVO_COD");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);

		} finally {
			DBUtil.close(conn, statement, result);
		}

		return novoCodCalbe;

	}

	@Override
	public void criar(Cable cable) throws DaoException {
		int novoCodCable = getProximoValorSequence();
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		try{
			java.util.Date date =new java.util.Date();
			long t = date.getTime();
			
			java.sql.Date sqlDate = new java.sql.Date(t);
			java.sql.Time sqlTime = new java.sql.Time(t);
			
			cable.setCaddata(sqlDate);
			cable.setHcad(sqlTime);
			
			
			statement = conn.prepareStatement(QUERY_CRIAR);
			statement.setInt(1,novoCodCable);
			statement.setString(2, cable.getMaccable());
			statement.setLong(3, cable.getCliente().getCodCliente());
			statement.setString(4,cable.getSerial());
			statement.setInt(5,cable.getPlano().getCodPlano());
			statement.setByte(6, cable.getAtivo());
			statement.setDate(7, DBUtil.getSqlDate(cable.getCaddata()));
			statement.setTime(8, cable.getHcad());
			statement.executeUpdate();
			cable.setCod(novoCodCable);
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException();
			
		}finally{
			DBUtil.close(conn, statement, result);
		}

	}

	@Override
	public void alterar(Cable cable) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_ALTPLANO_BLOQU);
			statement.setInt(1, cable.getPlano().getCodPlano());
			statement.setByte(2, cable.getAtivo());
			statement.setDate(3, DBUtil.getSqlDate(cable.getBloqdata()));
			statement.setDate(4, DBUtil.getSqlDate(cable.getExcludata()));
			statement.setTime(5, cable.getHbloq_cable());
			statement.setInt(6, cable.getCod());
					
			statement.executeUpdate();
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);

		}
	}

	@Override
	public void apagar(int codCable) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
	try{
		statement = conn.prepareStatement(QUERY_APAGAR_POR_COD);
		statement.setInt(1, codCable);
		statement.execute();
		
	}catch (SQLException e) {
		e.printStackTrace();
		throw new DaoException(e);
	} finally {
		DBUtil.close(conn, statement, result);
	}

	}

	@Override
	public List<Cable> buscarTodos() throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		List<Cable> todosCable = new ArrayList<Cable>();
		try{
			
			statement = conn.prepareStatement(QUERY_BUSCAR_TODOS);
			result = statement.executeQuery();
			
			while(result.next()){
				Cable cableResult = getBeanCable(result);
				todosCable.add(cableResult);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);
		}
		
		
		
		
		return todosCable;
	}

	private Cable getBeanCable(ResultSet result) throws SQLException {
		Cable retCable = new Cable();
		Plano retPlano = new Plano();
		Cliente retCli = new Cliente();
		
		retCable.setCod(result.getInt("cod"));
		retCable.setMaccable(result.getString("mac"));
		retCli.setCodCliente(result.getInt("codcli"));
		retCable.setCliente(retCli);
		retCable.setSerial(result.getString("serial"));
		retPlano.setCodPlano(result.getInt("plano"));
		retCable.setPlano(retPlano);
		retCable.setAtivo(result.getByte("ativo"));
		retCable.setCaddata(result.getDate("cadata"));
		retCable.setBloqdata(result.getDate("bdt"));
		retCable.setExcludata(result.getDate("edt"));
		retCable.setHcad(result.getTime("hcd"));
		retCable.setHbloq_cable(result.getTime("hblq"));
		
	
		return retCable;
	}

	@Override
	public Cable buscarPorCodCli(int codCli) throws DaoException {
		Connection conn = DBUtil.getConnection();
		PreparedStatement statement = null;
		ResultSet result = null;
		Cable cableResult=null;
		
		try{
			
			statement = conn.prepareStatement(QUERY_BUSCAR_POR_COD_CLI);
			
			result = statement.executeQuery();
			
			while(result.next()){
				 cableResult = getBeanCable(result);
				}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(e);
		} finally {
			DBUtil.close(conn, statement, result);
		}
		
		
		
		
		return cableResult;
	}

}
