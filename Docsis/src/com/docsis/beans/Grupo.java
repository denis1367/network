package com.docsis.beans;

public class Grupo {
	
	private int cod;//_grupo bigint PRIMARY KEY,
	private String nome;//_grupo varchar(30),
	private byte cadcmts;// BIT
	
	private boolean cadCliente;
	private boolean cadRede;
	private boolean cadOptionDhcp;
	private boolean cadPlano;
	private boolean cadCmts;
	private boolean cadUsuario;
	private boolean cadGrupo;
	
	private boolean altCliente;
	private boolean altRede;
	private boolean altOptionDhcp;
	private boolean altPlano;
	private boolean altCmts;
	private boolean altUsuario;
	private boolean altGrupo;
	
	private boolean delCliente;
	private boolean delRede;
	private boolean delOptionDhcp;
	private boolean delPlano;
	private boolean delCmts;
	private boolean delUsuario;
	private boolean delGrupo;
	
	
	
	
	
	
	
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
	public byte getCadcmts() {
		return cadcmts;
	}
	public void setCadcmts(byte cadcmts) {
		this.cadcmts = cadcmts;
	}
	@Override
	public String toString() {
		return "Grupo [cod=" + cod + ", nome=" + nome + ", cadcmts=" + cadcmts
				+ "]";
	}

}
