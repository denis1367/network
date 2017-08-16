package com.docsis.beans;

public class AreaCmts {

	private int codareacmts;
	private String 	nome; //varchar(15)
	private byte ativo;// BIT
	
	public int getCodareacmts() {
		return codareacmts;
	}
	public void setCodareacmts(int codareacmts) {
		this.codareacmts = codareacmts;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	@Override
	public String toString() {
		return "arecmts [codareacmts=" + codareacmts + ", nome=" + nome
				+ ", ativo=" + ativo + "]";
	}
	
}
