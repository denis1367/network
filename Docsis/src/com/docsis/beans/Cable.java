package com.docsis.beans;

import java.util.Date;

public class Cable {

	private int cod;// bigint PRIMARY KEY,
	private String maccable;// varchar(17),
	private Cliente cliente;// bigint,
	private String serial;// varchar(30),
	private Plano plano;// bigint,
	private byte ativo;
	private Date caddata;// date,
	private Date bloqdata;//_cable date,
	private Date excludata;//_cable date
	private java.sql.Time hcad;
	private java.sql.Time hbloq_cable;
	
	
	public java.sql.Time getHcad() {
		return hcad;
	}
	public void setHcad(java.sql.Time hcad) {
		this.hcad = hcad;
	}
	public java.sql.Time getHbloq_cable() {
		return hbloq_cable;
	}
	public void setHbloq_cable(java.sql.Time hbloq_cable) {
		this.hbloq_cable = hbloq_cable;
	}
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getMaccable() {
		return maccable;
	}
	public void setMaccable(String maccable) {
		this.maccable = maccable;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Plano getPlano() {
		return plano;
	}
	public void setPlano(Plano plano) {
		this.plano = plano;
	}
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
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
	public Date getExcludata() {
		return excludata;
	}
	public void setExcludata(Date excludata) {
		this.excludata = excludata;
	}
	@Override
	public String toString() {
		return "Cable [cod=" + cod + ", maccable=" + maccable + ", cliente="
				+ cliente.getCodCliente() + ", serial=" + serial + ", plano=" + plano.getCodPlano()
				+ ", ativo=" + ativo + ", caddata=" + caddata + ", bloqdata="
				+ bloqdata + ", excludata=" + excludata +" hblq = "+ hbloq_cable+ "]";
	}

}
