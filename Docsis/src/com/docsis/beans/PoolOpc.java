package com.docsis.beans;

public class PoolOpc {
	
	private int cod;// bigint PRIMARY KEY,
	private String nome;// varchar(20),
	private String valor;// varchar(20),
	private byte ativo;// BIT,
	private PoolSubrede poolsubrede;// bigint,
	private byte cable;// BIT
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
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	public PoolSubrede getPoolsubrede() {
		return poolsubrede;
	}
	public void setPoolsubrede(PoolSubrede poolsubrede) {
		this.poolsubrede = poolsubrede;
	}
	public byte getCable() {
		return cable;
	}
	public void setCable(byte cable) {
		this.cable = cable;
	}
	@Override
	public String toString() {
		return "Poolopc [cod=" + cod + ", nome=" + nome + ", valor=" + valor
				+ ", ativo=" + ativo + ", poolsubrede=" + poolsubrede
				+ ", cable=" + cable + "]";
	}

}
