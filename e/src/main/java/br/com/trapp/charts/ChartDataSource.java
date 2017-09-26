package br.com.trapp.charts;

import java.util.List;

public class ChartDataSource {

	private List<String> categorias;
	private List<ColumnSerie> series;
	private List<PieSerie> seriesPie;
	private List<LineSerie> seriesLine;
	private List<BarSerie> seriesBar;
	private String title;
	private String subTitle;
	private Long type;
	private String idDiv;
	private String suffix;
	private String yTitle;

	public ChartDataSource() {

	}

	public ChartDataSource(String title, String subTitle, List<ColumnSerie> series, List<String> categorias, Type type, String id) {
		this.title = title;
		this.subTitle = subTitle;
		this.series = series;
		this.categorias = categorias;
		this.type = type.getCodigo();
		this.idDiv = id;
	}
	
	public ChartDataSource(String title, String subTitle, List<ColumnSerie> series, List<String> categorias, Type type, String id, String yTitle) {
		this.title = title;
		this.subTitle = subTitle;
		this.series = series;
		this.categorias = categorias;
		this.type = type.getCodigo();
		this.idDiv = id;
		this.yTitle = yTitle;
	}
	
	public ChartDataSource(String title, String subTitle, List<LineSerie> series, List<String> categorias, Type type, String id, String yTitle, String suffix) {
		this.title = title;
		this.subTitle = subTitle;
		this.seriesLine = series;
		this.categorias = categorias;
		this.type = type.getCodigo();
		this.idDiv = id;
		this.yTitle = yTitle;
		this.suffix = suffix;
	}
	
	public ChartDataSource(String title, String subTitle, List<String> categorias, Type type, String id, String yTitle, String suffix) {
		this.title = title;
		this.subTitle = subTitle;
		this.categorias = categorias;
		this.type = type.getCodigo();
		this.idDiv = id;
		this.yTitle = yTitle;
		this.suffix = suffix;
	}
	
	public ChartDataSource(String title, String subTitle, List<PieSerie> series, Type type, String id) {
		this.title = title;
		this.subTitle = subTitle;
		this.seriesPie = series;
		this.type = type.getCodigo();
		this.idDiv = id;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public List<ColumnSerie> getSeries() {
		return series;
	}

	public void setSeries(List<ColumnSerie> series) {
		this.series = series;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public List<PieSerie> getSeriesPie() {
		return seriesPie;
	}

	public void setSeriesPie(List<PieSerie> seriesPie) {
		this.seriesPie = seriesPie;
	}

	public String getIdDiv() {
		return idDiv;
	}

	public void setIdDiv(String idDiv) {
		this.idDiv = idDiv;
	}

	public List<LineSerie> getSeriesLine() {
		return seriesLine;
	}

	public void setSeriesLine(List<LineSerie> seriesLine) {
		this.seriesLine = seriesLine;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

	public List<BarSerie> getSeriesBar() {
		return seriesBar;
	}

	public void setSeriesBar(List<BarSerie> seriesBar) {
		this.seriesBar = seriesBar;
	}
}
