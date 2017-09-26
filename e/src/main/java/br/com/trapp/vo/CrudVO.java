package br.com.trapp.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrudVO implements Serializable {

	private static final long serialVersionUID = -5656425688241022251L;
	private Long id;
	private Date data;
	private String nome;
	private boolean concluido;
	private String categoria;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isConcluido() {
		return concluido;
	}

	public void setConcluido(boolean concluido) {
		this.concluido = concluido;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


}
