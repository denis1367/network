package com.docsis.beans;

public class Usuario {
	
	private int cod;// bigint PRIMARY KEY,
	private String nome;// varchar(30) ,
	private String login;//_usuario varchar(15),
	private Grupo grupo;// bigint,
	private byte ativo;//_usuario BIT
	private String senha;
	
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	public byte getAtivo() {
		return ativo;
	}
	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}
	@Override
	public String toString() {
		return "Usuario [cod=" + cod + ", nome=" + nome + ", login=" + login
				+ ", grupo=" + grupo + ", ativo=" + ativo + ", senha=" + senha
				+ "]";
	}
	

	
	
}
