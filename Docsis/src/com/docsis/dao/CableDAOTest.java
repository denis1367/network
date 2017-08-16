package com.docsis.dao;

import java.util.List;

import com.docsis.beans.Cable;
import com.docsis.beans.Cliente;
import com.docsis.beans.Plano;
import com.docsis.exception.DaoException;

public class CableDAOTest {
	
	public static void main(String[] args) throws DaoException {
			CableDAO cbImpl = new CableDAOImpl();
			java.util.Date date = new java.util.Date();
			long t = date.getTime();

			java.sql.Date sqlDate = new java.sql.Date(t);
			java.sql.Time sqlTime = new java.sql.Time(t);

			
			Cable cable = new Cable();
			Cliente cli = new Cliente();
			cli.setCodCliente(1);
			Plano plano = new Plano();
			plano.setCodPlano(3);
			
//		###### criar ##########			
//			cable.setMaccable("00:18:20:10:10:12");
//			cable.setCliente(cli);
//			cable.setSerial("00455465431454");
//			cable.setPlano(plano);
//			cable.setAtivo((byte)1);
//			cable.setCaddata(sqlDate);
//			cable.setHcad(sqlTime);
//			cbImpl.criar(cable);
		
			
			listar(cbImpl);
			
			// ####alterar####
			
//			cable.setCod(1);
//			cable.setPlano(plano);
//			cable.setAtivo((byte)1);
//			cable.setCaddata(sqlDate);
//			cable.setBloqdata(sqlDate);
//			cable.setHbloq_cable(sqlTime);
//			cbImpl.alterar(cable);		
	
			//cbImpl.apagar(1);
			listar(cbImpl);
			
		//################ TESTANDO #########################
	}

	private static void listar(CableDAO cbImpl) throws DaoException {
		List<Cable> cab = cbImpl.buscarTodos();
		
		for(Cable a : cab){
			System.out.println(a);
		}
	}

}
