package br.com.trapp.charts;

public class Serie {
	
	
	public static final String TYPE_COLUMN="column";
	public static final String TYPE_PIE="pie";
	public static final String TYPE_SPLINE ="spline";
	public static final String TYPE_BAR ="bar";
	
	
	
	private String type;
	private String name;
	private String color;
	private Boolean colorByPoint;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean getColorByPoint() {
		return colorByPoint;
	}
	public void setColorByPoint(Boolean colorByPoint) {
		this.colorByPoint = colorByPoint;
	}
	
	

}
