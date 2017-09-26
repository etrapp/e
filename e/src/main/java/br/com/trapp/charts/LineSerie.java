package br.com.trapp.charts;

import java.util.List;

public class LineSerie extends Serie{

	private List<Double> data;
	
	public LineSerie() {

	}

	public LineSerie(String name, String color,String type ,Boolean colorByPoint, List<Double> data) {
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
