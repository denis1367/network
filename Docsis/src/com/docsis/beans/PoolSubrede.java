package com.docsis.beans;

public class PoolSubrede {
	
	private int cod;// bigint PRIMARY KEY,
	private String nome;//_poolsubrede varchar(10),
	private byte ativo;//_poolsubrede BIT,
	private Subrede subRede;
	
	public Subrede getSubRede() {
		return subRede;
	}
	public void setSubRede(Subrede subRede) {
		this.subRede = subRede;
	}
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
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	@Override
	public String toString() {
		return "Poolsubrede [cod=" + cod + ", nome=" + nome + ", ativo="
				+ ativo + ", subRede=" + subRede + "]";
	}
	
	
}
