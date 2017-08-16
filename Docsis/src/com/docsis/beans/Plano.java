package com.docsis.beans;

public class Plano {
	
	private int codPlano;// bigint PRIMARY KEY,
	private String nome;// varchar(10),
	private String nserver;// varchar(15),
	private String arqbinario;// varchar(20),
	private byte ativo;// BIT
	
	
	
	public int getCodPlano() {
		return codPlano;
	}
	public void setCodPlano(int codPlano) {
		this.codPlano = codPlano;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getNserver() {
		return nserver;
	}
	public void setNserver(String nserver) {
		this.nserver = nserver;
	}
	public String getArqbinario() {
		return arqbinario;
	}
	public void setArqbinario(String arqbinario) {
		this.arqbinario = arqbinario;
	}
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	@Override
	public String toString() {
		return "Plano [codPlano=" + codPlano + ", nome=" + nome + ", nserver="
				+ nserver + ", arqbinario=" + arqbinario + ", ativo=" + ativo
				+ "]";
	}

}
