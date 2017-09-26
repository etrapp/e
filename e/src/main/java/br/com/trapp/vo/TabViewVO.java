package br.com.trapp.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TabViewVO implements Serializable {

	private static final long serialVersionUID = -4080322644259713083L;
	private String categoria;
	private List<CrudVO> categorias = new ArrayList<CrudVO>();
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public List<CrudVO> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<CrudVO> categorias) {
		this.categorias = categorias;
	}

}
