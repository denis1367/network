package com.docsis.beans;

import java.util.Date;

public class Cliente {
	private int codCliente;// bigint PRIMARY KEY,
	private String nome;// varchar(70),
	private Date caddata;// date,
	private Date bloqdata;// date,
	private long contrato;//bigint
	private String cpf; //varchar
	
	
	public long getContrato() {
		return contrato;
	}
	public void setContrato(long contrato) {
		this.contrato = contrato;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getCodCliente() {
		return codCliente;
	}
	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Date getCaddata() {
		return caddata;
	}
	public void setCaddata(Date caddata) {
		this.caddata = caddata;
	}
	public Date getBloqdata() {
		return bloqdata;
	}
	public void setBloqdata(Date bloqdata) {
		this.bloqdata = bloqdata;
	}
	@Override
	public String toString() {
		return "Cliente [codCliente=" + codCliente + ", nome=" + nome
				+ ", caddata=" + caddata + ", bloqdata=" + bloqdata
				+ ", contrato=" + contrato + ", cpf=" + cpf + "]";
	}
	
	

	

}
