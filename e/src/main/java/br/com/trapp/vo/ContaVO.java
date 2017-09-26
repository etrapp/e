package br.com.trapp.vo;

import java.io.Serializable;

public class ContaVO implements Serializable {

	private static final long serialVersionUID = 7716025025487563412L;
	private Long idConta;
	private String conta;
	private Long idOrganizacao;
	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public Long getIdOrganizacao() {
		return idOrganizacao;
	}
	public void setIdOrganizacao(Long idOrganizacao) {
		this.idOrganizacao = idOrganizacao;
	}
	
}
