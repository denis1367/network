package com.docsis.dao;

import java.util.ArrayList;
import java.util.List;

import com.docsis.beans.Cliente;
import com.docsis.exception.DaoException;

public class ClienteDAOTest {
	
	public static void main(String[] args) throws DaoException {
		Cliente cli = new Cliente();
		ClienteDAO cliDaoImpl = new ClienteDAOImpl();
		
		java.util.Date date = new java.util.Date();
		long t = date.getTime();
		
		java.sql.Date sqlDate = new java.sql.Date(t);
	
		//criar
//		
//		String nome = "jose";
//		cli.setNome(nome.toUpperCase());
//		cli.setCpf("220.333.444-89");
//		cli.setContrato(3);
//		cli.setCaddata(sqlDate);
//		cliDaoImpl.Criar(cli);
		
		
		
		//###busca por contrato
		//System.out.println(cliDaoImpl.BuscarPorContrato(701));
//		##########busca por nome
//		List<Cliente> listCli = new ArrayList<Cliente>();
//		listCli = cliDaoImpl.BuscarPorNome("s");
//		
//		for(Cliente a :listCli){
//			System.out.println(a);
//		}
		
		//#### alterar ####
		
//		cli.setCodCliente(1);
//		cli.setNome("pamela martins");
//		cli.setCpf("229.555.999-11");
//		cli.setContrato(559);
//		cli.setBloqdata(null);		
//		cliDaoImpl.Alterar(cli);
		
		//cliDaoImpl.Apagar(3);
		
		System.out.println(cliDaoImpl.BuscarPorContrato(3));
		
	}

}
