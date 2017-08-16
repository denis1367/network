package com.docsis.exception;

@SuppressWarnings("serial")
public class ContaPendenteException extends Exception {

	private String nomeCompleto;
	private int numeroContasPendentes;
	
	public ContaPendenteException(String nomeCompleto, int numeroContasPendentes) {
		this.nomeCompleto = nomeCompleto;
		this.numeroContasPendentes = numeroContasPendentes;
	}
	
	@Override
	public String getMessage() {
		boolean singular = this.numeroContasPendentes == 1;
		String terminoMsg = "contas pendentes";
		if (singular) {
			terminoMsg = "conta pendente";
		}
		return this.nomeCompleto + " possui " + this.numeroContasPendentes + " " + terminoMsg;
	}

}
