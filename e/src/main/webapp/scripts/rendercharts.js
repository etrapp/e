function renderChart(args){
	var dataSource = jQuery.parseJSON(args.chartDataSource);
	for(var i=0;i<dataSource.length;i++){
		var tipo = dataSource[i].type;
		var divId = dataSource[i].idDiv;
		if(tipo==4){
			var options = createColumnChartOptions(divId,dataSource[i].title,dataSource[i].subTitle,dataSource[i].categorias,dataSource[i].series);
			new Highcharts.Chart(options);
		}else if(tipo==1){
			var options = createPieChartOptions(divId,dataSource[i].title,dataSource[i].subTitle,dataSource[i].seriesPie);
			new Highcharts.Chart(options);
		}else if(tipo==3){
			var options = createLineChartOptions(divId,dataSource[i].title,dataSource[i].subTitle,dataSource[i].categorias,dataSource[i].yTitle,dataSource[i].seriesLine,dataSource[i].suffix);
			new Highcharts.Chart(options);
		}else if(tipo==2){
			var options = createBarChartOptions(divId,dataSource[i].title,dataSource[i].subTitle,dataSource[i].categorias,dataSource[i].yTitle,dataSource[i].seriesBar,dataSource[i].suffix);
			new Highcharts.Chart(options);
		}else if(tipo==5){
			var options = createColumnSideBySideChartOptions(divId,dataSource[i].title,dataSource[i].subTitle,dataSource[i].categorias,dataSource[i].yTitle,dataSource[i].series);
			new Highcharts.Chart(options);
		}
	}
}

function createColumnChartOptions(divId,title,subTitle,categories,seriesBar) {
	var options = {
	        chart:{
	        	type: 'column',
	        	renderTo: divId
	        },
	        title: {
	            text: title
	        },
	        subtitle: {
	            text: subTitle
	        },
	        credits: {
	            enabled: false
	        },
	        xAxis: {
	            categories: categories
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: 'Total'
	            },
	            stackLabels: {
	                enabled: true,
	                style: {
	                    fontWeight: 'bold',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	                }
	            }
	        },
	        tooltip: {
	            formatter: function () {
	            	
	            	var texto = '<b>' + this.x + '</b><br/>' +
	                    this.series.name + ': ' + this.y;
	            	if(this.point.stackTotal!=undefined){
	            		texto = texto + '<br/>' +
	                    'Total: ' + this.point.stackTotal;
	            	}
	            	return texto;
	            }
	        },
			plotOptions: {
	            series: {
	                lineWidth: 3,
					marker: {
						radius: 5,
						fillColor: null,
						lineWidth: 2,
	                    lineColor: '#fff'
					}
	            },
				column: {
	                stacking: 'normal'
	            }
				
	        },
	        series: seriesBar
	    };
	
	return options; 	
}  

function createPieChartOptions(divId,title,subTitle,series){	
	var options =({
		chart:{
        	type: 'pie',
        	renderTo: divId
        	
        },
        credits: {
            enabled: false
        },
		title: {
			text: title
		},
        subtitle: {
            text: subTitle
        },
		tooltip: {
			pointFormat: 'Valor: <b>{point.y} ({point.percentage:.1f}%)</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: true,
					format: '{point.percentage:.1f}%', // one decimal
					inside: true,
                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || '#555',
                    style: {
                        textShadow: '0 0 3px white'
                    }
				},
				showInLegend: true
			}
		},
		series: series
	});
	return options;	
}

function createLineChartOptions(divId, title, subTitle, categories, yTitle, series, sufix){
	var options =({
		chart:{
        	type: 'line',
        	renderTo: divId
        },
        credits: {
            enabled: false
        },
		title: {
	        text: title,
	        x: -20 //center
	    },
	    credits: {
	        enabled: false
	    },
	    subtitle: {
	        text: subTitle,
	        x: -20
	    },
	    xAxis: {
	        categories: categories
	    },
	    yAxis: {
	        title: {
	            text: yTitle
	        },
	        plotLines: [{
	            value: 0,
	            width: 1,
	            color: '#808080'
	        }]
	    },
	    tooltip: {
	        valueSuffix: ' '+sufix
	    },
	    series: series
	});
	return options;
}

function createBarChartOptions(divId, title, subTitle, categories, yTitle, series, suffix){
		var options = ({
		chart: {
	        type: 'bar',
	        renderTo: divId
	    },
	    title: {
	        text: title
	    },
	    xAxis: {
	        categories: categories,
	        title: {
	            text: null
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: yTitle,
	            align: 'high'
	        },
	        labels: {
	            overflow: 'justify'
	        }
	    },
	    credits: {
	        enabled: false
	    },
	    tooltip: {
			pointFormat: '<strong>{point.y} '+suffix+'</strong> com {point.category}'
	    },
	    plotOptions: {
	        bar: {
	            dataLabels: {
	                enabled: true
	            }
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    credits: {
	        enabled: false
	    },
	    series: series
	});
		return options;
}

function createColumnSideBySideChartOptions(divId,title,subTitle, categorias, yTitle, series){
	var options =({
		chart: {
	        renderTo: divId
	    },
		title: {
            text: title
        },
        xAxis: {
            categories: categorias
        },
        yAxis: {
            title: {
                text: yTitle
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
		
        credits: {
            enabled: false
        },
		plotOptions: {
            series: {
                lineWidth: 3,
				marker: {
					radius: 5,
					fillColor: null,
					lineWidth: 2,
                    lineColor: '#555'
				}
            }
			
        },
        series: series
    });
	return options;
}