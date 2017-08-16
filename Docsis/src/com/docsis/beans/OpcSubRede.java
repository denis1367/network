package com.docsis.beans;

public class OpcSubRede {
	
	private int cod ;//bigint PRIMARY KEY,
	private String nome;// varchar(15),
	private String valor;// varchar(15),
	private Subrede subrede;// bigint,
	private byte ativo;// BIT
	
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public Subrede getSubrede() {
		return subrede;
	}
	public void setSubrede(Subrede subrede) {
		this.subrede = subrede;
	}
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	@Override
	public String toString() {
		return "Opcsubrede [cod=" + cod + ", nome=" + nome + ", valor=" + valor
				+ ", subrede=" + subrede + ", ativo=" + ativo + "]";
	}
	
}
