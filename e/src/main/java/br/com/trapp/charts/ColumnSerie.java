package br.com.trapp.charts;

import java.util.List;

public class ColumnSerie extends Serie{

	private List<Double> data;

	public ColumnSerie() {

	}

	public ColumnSerie(String name, String color,String type ,Boolean colorByPoint, List<Double> data) {
		setType(type);
		setName(name); 
		setColor(color);
		setColorByPoint(colorByPoint);
		this.data = data;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
}
