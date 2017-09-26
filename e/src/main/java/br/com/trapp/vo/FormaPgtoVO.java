package br.com.trapp.vo;

import java.io.Serializable;

public class FormaPgtoVO implements Serializable {

	private static final long serialVersionUID = 1673876650026743953L;
	
	private Long idFormaPgto;
	private String formaPgto;
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
	
}
