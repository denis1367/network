package com.docsis.beans;

public class Subrede {

		
	private int codSubrede;// bigint PRIMARY KEY,
	private String rede;// varchar(15),
	private String netmask;// varchar(15),
	private AreaCmts areacmts; //bigint,
	private byte ativo;// BIT,
	private byte gerencia;// BIT,
	private byte tipo;
	
	public byte getTipo() {
		return tipo;
	}
	public void setTipo(byte tipo) {
		this.tipo = tipo;
	}
	public AreaCmts getAreacmts() {
		return areacmts;
	}
	public void setAreacmts(AreaCmts areacmts) {
		this.areacmts = areacmts;
	}
	public int getCodSubrede() {
		return codSubrede;
	}
	public void setCodSubrede(int codSubrede) {
		this.codSubrede = codSubrede;
	}
	public String getRede() {
		return rede;
	}
	public void setRede(String rede) {
		this.rede = rede;
	}
	public String getNetmask() {
		return netmask;
	}
	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}
	
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	
	public byte getGerencia() {
		return gerencia;
	}
	public void setGerencia(byte gerencia) {
		this.gerencia = gerencia;
	}
	@Override
	public String toString() {
		return "Subrede [codSubrede=" + codSubrede + ", rede=" + rede
				+ ", netmask=" + netmask + ", areacmts=" + areacmts
				+ ", ativo=" + ativo + ", poolSubrede=" + ", gerencia=" + gerencia + ", tipo=" + tipo + "]";
	}

	
	
	
}
