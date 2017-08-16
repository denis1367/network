package com.docsis.beans;

public class PoolPlano {
	private int codpoolplano;// bigint PRIMARY KEY,
	private String nome;// varchar(30),
	private byte ativo;// BIT,
	private Plano plano;// bigint
	private PoolSubrede poolSubRede;
	
	public PoolSubrede getPoolSubRede() {
		return poolSubRede;
	}
	public void setPoolSubRede(PoolSubrede poolSubRede) {
		this.poolSubRede = poolSubRede;
	}
	public int getCodpoolplano() {
		return codpoolplano;
	}
	public void setCodpoolplano(int codpoolplano) {
		this.codpoolplano = codpoolplano;
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
	public Plano getPlano() {
		return plano;
	}
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	@Override
	public String toString() {
		return "Poolplano [codpoolplano=" + codpoolplano + ", nome=" + nome
				+ ", ativo=" + ativo + ", plano=" + plano + "]";
	}

	
}
