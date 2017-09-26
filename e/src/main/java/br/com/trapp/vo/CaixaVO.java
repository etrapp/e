package br.com.trapp.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaixaVO implements Serializable {

	private static final long serialVersionUID = 3417065591646413172L;
	private Long id;
	private Date data;
	private Long idConta;
	private Long idFormaPgto;
	private String formaPgto;
	private String conta;
	private Double valor;
	private String responsavel;
	private String descricao;
	private Long idOrganizacao;
	private String dc;

	public Date getData() {
		return data;
	}

	/*
	 * Utilizado para agrupamento de resultado por mes na pagina caixa.xhtml
	 */
	public String getMes() {
		SimpleDateFormat formatoData = new SimpleDateFormat("yyyyMM");
		String resultado = formatoData.format(data);
		return resultado;
	}
	
	public void setData(Date data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdFormaPgto() {
		return idFormaPgto;
	}

	public void setIdFormaPgto(Long idFormaPgto) {
		this.idFormaPgto = idFormaPgto;
	}

	public String getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(String formaPgto) {
		this.formaPgto = formaPgto;
	}

	public Long getIdOrganizacao() {
		return idOrganizacao;
	}

	public void setIdOrganizacao(Long idOrganizacao) {
		this.idOrganizacao = idOrganizacao;
	}

	public String getDc() {
		return dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}



}
