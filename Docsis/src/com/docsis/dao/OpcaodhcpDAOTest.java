package com.docsis.dao;

import java.util.List;
import java.util.Scanner;

import com.docsis.beans.OpcaoDhcp;
import com.docsis.exception.DaoException;

public class OpcaodhcpDAOTest {

	private static Scanner sc;
	public static void main(String[] args) throws DaoException {
		OpcaodhcpDAO opcdao = new OpcaodhcpDAOImpl();
		
		sc = new Scanner(System.in);
		
		OpcaoDhcp opc = new OpcaoDhcp();
		/*
		opc.setOption("option tereza");
		opc.setValor("172.17.17.9");
		opc.setAtivo((byte) 0);
		
		opcdao.criar(opc);
		System.out.println("ok criado");*/
		
		buscarTodos(opcdao);
		opc.setOption("option denis atualizou");
		opc.setValor("200.200.200.224");
		opc.setAtivo((byte) 1);
		opc.setCod(3);
		opcdao.alterar(opc);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		buscarTodos(opcdao);
			
	}
	private static void buscarTodos(OpcaodhcpDAO opcdao) throws DaoException {
		List<OpcaoDhcp> pr = opcdao.buscarTodos();
		for (OpcaoDhcp p : pr) {
			System.out.println(p);
		}
	}
	
}
