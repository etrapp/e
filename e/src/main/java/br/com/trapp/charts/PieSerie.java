package br.com.trapp.charts;

import java.util.List;

public class PieSerie {

	private final String TYPE_PIE = "pie";
	private List<PieData> data;
	private String type;

	public PieSerie() {

	}

	public PieSerie(  List<PieData> dados) {
		this.data = dados;
		this.type=TYPE_PIE;
	}
	


	public String getType() {
		return type;
	}

	public List<PieData> getData() {
		return data;
	}

	public void setData(List<PieData> data) {
		this.data = data;
	}

	public static class PieData {

		private String name;

		private Double y;

		public PieData() {

		}

		public PieData(String name, Double total) {
			this.name = name;
			this.y = total;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getY() {
			return y;
		}

		public void setY(Double y) {
			this.y = y;
		}

	}

}
