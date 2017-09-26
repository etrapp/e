package br.com.trapp.vo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CaixaFiltro implements Serializable{

	private static final long serialVersionUID = 5098932118178419954L;

	private List<ContaVO> contas;
	
	private List<FormaPgtoVO> formaPgtos;
	
	private List<String> responsaveis;
	
	private List<String> categorias;
	
	private Date dataInicio;
	
	private Date dataFim;

	public void initData() {
		Calendar aCalendar = Calendar.getInstance();
		dataFim = aCalendar.getTime();
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date primeiroDiaMes = aCalendar.getTime();
		dataInicio = primeiroDiaMes; 
	}
	
	public List<ContaVO> getContas() {
		return contas;
	}

	public void setContas(List<ContaVO> contas) {
		this.contas = contas;
	}

	public List<FormaPgtoVO> getFormaPgtos() {
		return formaPgtos;
	}

	public void setFormaPgtos(List<FormaPgtoVO> formaPgtos) {
		this.formaPgtos = formaPgtos;
	}

	public List<String> getResponsaveis() {
		return responsaveis;
	}

	public void setResponsaveis(List<String> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
}
