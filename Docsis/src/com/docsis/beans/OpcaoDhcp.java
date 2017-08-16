package com.docsis.beans;

public class OpcaoDhcp {
	private int cod; //bigint PRIMARY KEY,
	private String option ;//varchar(20),
	private String valor;//varchar(10),
	private byte ativo;//BIT
	
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
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
	@Override
	public String toString() {
		return "Opcaodhcp [cod=" + cod + ", option=" + option + ", valor="
				+ valor + ", ativo=" + ativo + "]";
	}
	

}
