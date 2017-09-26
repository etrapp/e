package br.com.trapp.charts;

public enum Type {
	
	PIE(1L), BAR(2L), LINE(3L), COLUMN(4L), COLUMNSIDE(5L);
	
	private Long codigo;
	
	private String nome;
	
	private Type(Long codigo){
		this.codigo = codigo;
		if(codigo.equals(1L)){
			this.nome = "PIE";
		}else if(codigo.equals(2L)){
			this.nome = "BAR";
		}else if(codigo.equals(3L)){
			this.nome = "LINE";
		}else if(codigo.equals(4L)){
			this.nome = "COLUMN";
		}else if(codigo.equals(5L)){
			this.nome = "COLUMNSIDE";
		}
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
