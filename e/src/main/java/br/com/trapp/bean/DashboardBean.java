package br.com.trapp.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import br.com.trapp.ConnectMySql;
import br.com.trapp.charts.BarSerie;
import br.com.trapp.charts.ChartDataSource;
import br.com.trapp.charts.ColumnSerie;
import br.com.trapp.charts.LineSerie;
import br.com.trapp.charts.PieSerie;
import br.com.trapp.charts.PieSerie.PieData;
import br.com.trapp.charts.Serie;
import br.com.trapp.charts.Type;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.util.SessionUtils;
import br.com.trapp.vo.CaixaFiltro;
import br.com.trapp.vo.ContaVO;
import br.com.trapp.vo.FormaPgtoVO;

//@ViewScoped
@SessionScoped
@ManagedBean
public class DashboardBean {

	SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

	private ListagemBean listagemBean = new ListagemBean();
	
	private List<ChartDataSource> datasourceVO;
	
	private CaixaFiltro filtro;
	
	private List<ContaVO> listaContas;
	
	private List<FormaPgtoVO> listaFormaPgtos;
	
	private List<String> listaResponsaveis;
	
	private List<String> listaCategorias;
	
	private boolean filtroInicialCarregado = false;
	
	private Locale locale;
	
	private Long idOrganizacao;

	@PostConstruct
	public void init(){
		System.out.println("init dashboard");
//		listagemBean = new ListagemBean();
		idOrganizacao = (Long) SessionUtils.getSessionMapValue("idOrganizacao");
		clearFields();
	}

	public void filtroInicial(){
		System.out.println("dashboard:: filtroInicial acionado");
		if(!filtroInicialCarregado){
			System.out.println("dashboard:: filtro inicial working......");
			filtro = new CaixaFiltro();

			datasourceVO = new ArrayList<ChartDataSource>();
			datasourceVO.add(criarGraficoPizzaPagamentoCategoriaDataSource());//Movimentacao por Categoria
			
			Gson g = new Gson();
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("chartDataSource", g.toJson(datasourceVO));
			filtroInicialCarregado=true;
		}
	}
	
	public void clearFields() {
		this.initCombos();
		filtro = new CaixaFiltro();
		filtro.initData();
	}

	private void initCombos() {
		listaContas = listagemBean.listarContas();
		listaFormaPgtos = listagemBean.listarFormasPgto();
		listaResponsaveis = listagemBean.listarResponsavel();
		listaCategorias = listagemBean.listarCategoria();
	}

	public void changeLocale() {
		locale = (Locale)SessionUtils.getSessionMapValue("locale");
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		SessionUtils.setSessionMapValue("idOrganizacao", idOrganizacao);
	}

	public String caixa() {
		changeLocale();
		return "caixa";
	}	
	
	public String mobcadastro() {
		changeLocale();
		return "mcaixa";
	}

	public void filtrar(){
		System.out.println("Filtrar acionado....");
		datasourceVO = new ArrayList<ChartDataSource>();
//		datasourceVO.add(criarGraficoNotasPorOperacaoDataSource());			//BARRA VERTICAL - conta x total
		datasourceVO.add(criarGraficoBarraTotalPorContaNotaDataSource());	//BARRA HORIZONTAL - conta x total
		datasourceVO.add(criarGraficoPizzaCaixaPorContaDataSource());		//PIZZA - por conta
//		datasourceVO.add(criarGraficoLinhaPagamentoDiarioDataSource());		//LINHA - movimentacao diaria (mes atual/anterior)
		datasourceVO.add(criarGraficoLinhaPagamentoCategoriaDataSource());	//LINHA - movimentacao categoria (mes atual/anterior)
		datasourceVO.add(criarGraficoPizzaPagamentoCategoriaDataSource());	//PIZZA - por categoria
		
		Gson g = new Gson();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("chartDataSource", g.toJson(datasourceVO));
	}

	
	public void filtrarMobile(){
		System.out.println("Filtrar mobile acionado....");
		datasourceVO = new ArrayList<ChartDataSource>();
//		datasourceVO.add(criarGraficoNotasPorOperacaoDataSource());			//BARRA VERTICAL - conta x total
		datasourceVO.add(criarGraficoBarraTotalPorContaNotaDataSource());	//BARRA HORIZONTAL - conta x total
//		datasourceVO.add(criarGraficoPizzaCaixaPorContaDataSource());		//PIZZA - por conta
//		datasourceVO.add(criarGraficoLinhaPagamentoDiarioDataSource());		//LINHA - movimentacao diaria (mes atual/anterior)
		datasourceVO.add(criarGraficoLinhaPagamentoCategoriaDataSource());	//LINHA - movimentacao categoria (mes atual/anterior)
		datasourceVO.add(criarGraficoPizzaPagamentoCategoriaDataSource());	//PIZZA - por categoria
		
		Gson g = new Gson();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("chartDataSource", g.toJson(datasourceVO));
	}
	

	private ChartDataSource criarGraficoBarraTotalPorContaNotaDataSource() {
//		HashMap<Integer, Long> resultado = listagemBean.pesquisarGraficoQuantidadeErrosQuantidadeNota(filtro);
		HashMap<String, Long> resultado = pesquisarGraficoCaixaPorConta(filtro);
		
		List<String> categoria = new ArrayList<String>();
		List<Double> dataSerie = new ArrayList<Double>();
		Object[] keys = (Object[]) resultado.keySet().toArray();
		Arrays.sort(keys, Collections.reverseOrder());
		for(Object key : keys){
			categoria.add(key.toString());  //conta
			dataSerie.add(new Double(resultado.get(key))); //valor
		}
		List<BarSerie> serie = new ArrayList<BarSerie>();
		serie.add(new BarSerie("Valor de Notas",null,Serie.TYPE_BAR,false,dataSerie));
//		ChartDataSource ds = new ChartDataSource("#4 Nota X Volume de Erros",null,categoria,Type.BAR,"containerNotasQuantidadeErros","Quantidade de Notas","Notas");
		ChartDataSource ds = new ChartDataSource(MessagesUtil.retornaMsg("msg.grafico.barra.conta", getLocale()),null,categoria,Type.BAR,"containerBarraTotalPorConta","Total","R$");
		
		ds.setSeriesBar(serie);
		return ds;
	}

	
	private ChartDataSource criarGraficoNotasPorOperacaoDataSource() {
		HashMap<String, Long> resultado = pesquisarGraficoVolumeCaixaMensal(filtro);
		
		List<String> categoria = new ArrayList<String>();
		List<Double> dataSerie = new ArrayList<Double>();
		Object[] keys = (Object[]) resultado.keySet().toArray();
		Arrays.sort(keys, Collections.reverseOrder());
		for(Object key : keys){
			categoria.add(key.toString());  //conta
			dataSerie.add(new Double(resultado.get(key))); //valor
		}
		
		List<ColumnSerie> series = new ArrayList<ColumnSerie>();
		
		series.add(new ColumnSerie("Total",null,Serie.TYPE_COLUMN,false, dataSerie));
//		series.add(new ColumnSerie("Entrada",null,Serie.TYPE_COLUMN,false, entrada));
//		series.add(new ColumnSerie("Total",null,Serie.TYPE_SPLINE,false, acumuladoDiario));
//		return new ChartDataSource("#8 Volume de recebimento de notas no diário",null,series,categorias,Type.COLUMN,"containerNotasOperacao","Volume");//Type.COLUMN(STACKEDBAR) / Type.COLUMNSIDE(BAR)
		return new ChartDataSource("Volume de despesas mensal",null,series,categoria,Type.COLUMN,"containerColunaTotalPorConta","Volume");//Type.COLUMN(STACKEDBAR) / Type.COLUMNSIDE(BAR)
	}
	
	private ChartDataSource criarGraficoPizzaCaixaPorContaDataSource() {
		HashMap<String, Long> resultado = pesquisarGraficoCaixaPorConta(this.filtro);
		List<PieSerie> series = new ArrayList<PieSerie>();
		List<PieData> dados = new ArrayList<PieSerie.PieData>();
		for(String set : resultado.keySet()){
			dados.add(new PieData(set, new Double(resultado.get(set))));
		}
		series.add(new PieSerie(dados));

		return new ChartDataSource (MessagesUtil.retornaMsg("msg.grafico.pizza.conta", getLocale()),null,series,Type.PIE,"containerPizzaPercentualPorConta");
	}

	public CaixaFiltro getFiltro() {
		return filtro;
	}

	public void setFiltro(CaixaFiltro filtro) {
		this.filtro = filtro;
	}	
	

	public HashMap<String, Long> pesquisarGraficoVolumeCaixaMensal(CaixaFiltro filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select c.conta, sum(cx.valor)  "); 
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
			sb.append(" and cx.data between "+formataMySqlData(filtro.getDataInicio())+" and " + formataMySqlData(filtro.getDataFim()));  
		}
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));
		sb.append(" group by c.conta; "); 		
		HashMap<String, Long> retorno = new LinkedHashMap<String, Long>();
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				String problema = rs.getString(1);
				Long quantidade = rs.getLong(2);
				retorno.put(problema, quantidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}
	
	public HashMap<String, Long> pesquisarGraficoCaixaPorConta(CaixaFiltro filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select c.conta, sum(cx.valor)  "); 
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		if (filtro.getDataInicio() != null && filtro.getDataFim() != null) {
			sb.append(" and cx.data between "+formataMySqlData(filtro.getDataInicio())+" and " + formataMySqlData(filtro.getDataFim()));  
		}
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));
		sb.append(" group by c.conta; "); 		
		HashMap<String, Long> retorno = new LinkedHashMap<String, Long>();
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				String problema = rs.getString(1);
				Long quantidade = rs.getLong(2);
				retorno.put(problema, quantidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}	

	public String montaClausulas(CaixaFiltro filtro) {
		StringBuilder sb = new StringBuilder();

		if (filtro.getContas() != null) {
			String param = "";
			for (ContaVO vo : filtro.getContas()) {
				if(param.length()>0) {
					param += ",";
				}
				param += vo.getIdConta().toString();				
			}
			if(param.length()>0){
				sb.append(" and cx.id_conta in ("+ param +")");				
			}
		}
		
		if (filtro.getFormaPgtos() != null) {
			String param = "";
			for (FormaPgtoVO vo : filtro.getFormaPgtos()) {
				if(param.length()>0) {
					param += ",";
				}
				param += vo.getIdFormaPgto().toString();				
			}
			if(param.length()>1){
				sb.append(" and cx.id_forma_pgto in ("+ param +")");				
			}
		}
		if (filtro.getCategorias() != null) {
			String param = "";
			for (String vo : filtro.getCategorias()) {
				if(param.length()>0) {
					param += ",";
				}
				param += "'"+vo+"'";
			}
			if(param.length()>1){
				sb.append(" and c.categoria in ("+ param +")");				
			}			
		}

		if (filtro.getResponsaveis() != null) {
			String param = "";
			for (String vo : filtro.getResponsaveis()) {
				if(param.length()>0) {
					param += ",";
				}
				param += "'"+vo+"'";
			}
			if(param.length()>1){
				sb.append(" and cx.responsavel in ("+ param +")");				
			}			
		}
		return sb.toString();
	}

	private String formataMySqlData(Date data) {
		String dataFormatada = "now()";
		if(data != null) {
			dataFormatada = "'"+formatData.format(data)+"'";
		}
		return dataFormatada;
	}

	public List<ContaVO> getListaContas() {
		return listaContas;
	}

	public void setListaContas(List<ContaVO> listaContas) {
		this.listaContas = listaContas;
	}

	public List<FormaPgtoVO> getListaFormaPgtos() {
		return listaFormaPgtos;
	}

	public void setListaFormaPgtos(List<FormaPgtoVO> listaFormaPgtos) {
		this.listaFormaPgtos = listaFormaPgtos;
	}

	public List<String> getListaResponsaveis() {
		return listaResponsaveis;
	}

	public void setListaResponsaveis(List<String> listaResponsaveis) {
		this.listaResponsaveis = listaResponsaveis;
	}

	public List<String> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<String> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	
	/*
	select c.conta, sum(cx.valor) 
	from e.caixa cx, e.conta c, e.forma_pgto f 
	where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto 
	group by c.conta;
	
	select c.categoria, sum(cx.valor) 
	from e.caixa cx, e.conta c, e.forma_pgto f 
	where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto 
	group by c.categoria;
	
	select f.forma_pgto, sum(cx.valor) 
	from e.caixa cx, e.conta c, e.forma_pgto f 
	where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto 
	group by f.forma_pgto;
	
	select cx.responsavel, sum(cx.valor) 
	from e.caixa cx
	group by cx.responsavel;	 

	 */

	//---------------------------------------------------------------------------------------------------------------------------------
	// GRAFICO GraficoPagamentoDiario MesAtual X Anterior - INICIO
	//---------------------------------------------------------------------------------------------------------------------------------
	private ChartDataSource criarGraficoLinhaPagamentoDiarioDataSource() {
		HashMap<Integer, Integer> resultadoAtual = pesquisarGraficoPagamentoDiarioMesCorrente(filtro);
		HashMap<Integer, Integer> resultadoAnterior = pesquisarGraficoPagamentoDiarioMesAnterior(filtro);
		List<String> categorias = this.criarCategoriasPagamentoDiario(resultadoAtual, resultadoAnterior);
		return new ChartDataSource("#4 Movimentação Diária",null,this.criarSeriesPagamentoDiario(resultadoAtual, resultadoAnterior, categorias),categorias,Type.LINE,"containerMovimentacaoDiariaMesAtualAnterior","Valor","R$");

	}
		
	private List<String> criarCategoriasPagamentoDiario(
			HashMap<Integer, Integer> resultadoAtual, HashMap<Integer, Integer> resultadoAnterior) {
		List<String> categorias = new ArrayList<String>();
		for(Integer dia : resultadoAtual.keySet()){
			categorias.add(dia.toString());
		}
		for(Integer dia : resultadoAnterior.keySet()){
			if(!categorias.contains(dia.toString())){
				categorias.add(dia.toString());
			}
		}
		return categorias;
	}

	private List<LineSerie> criarSeriesPagamentoDiario(
			HashMap<Integer, Integer> resultadoAtual, HashMap<Integer, Integer> resultadoAnterior, List<String> categorias) {
		List<LineSerie> series = new ArrayList<LineSerie>();
		List<Double> atual = new ArrayList<Double>();
		List<Double> anterior = new ArrayList<Double>();
		for(String categoria : categorias){
			if(resultadoAtual.get(Integer.parseInt(categoria))==null){
				atual.add(0D);
			}else{
				atual.add(new Double(resultadoAtual.get(Integer.parseInt(categoria))));
			}
			if(resultadoAnterior.get(Integer.parseInt(categoria))==null){
				anterior.add(0D);
			}else{
				anterior.add(new Double(resultadoAnterior.get(Integer.parseInt(categoria))));
			}
		}
		series.add(new LineSerie("Mês Corrente", null, Serie.TYPE_SPLINE, false, atual));
		series.add(new LineSerie("Mês Anterior", null, Serie.TYPE_SPLINE, false, anterior));
		return series;
	}
	
	
	/**************************************************************************
	 * Grafico Movimentacao Diaria - Comparando Mes Atual com Anterior
	 **************************************************************************/
	public HashMap<Integer, Integer> pesquisarGraficoPagamentoDiarioMesCorrente(CaixaFiltro filtro) {
		HashMap<Integer, Integer> retorno = new HashMap<Integer, Integer>();
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date primeiroDiaMes = aCalendar.getTime();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT DAY(cx.data) AS DIA, sum(cx.valor) AS TOTAL ");
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		sb.append(" and cx.data >= "+formataMySqlData(primeiroDiaMes));  
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));

		sb.append(" group by DAY(cx.data) ");
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Integer dia = rs.getInt(1);
				Integer total = rs.getInt(2);
				retorno.put(dia, total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	/**************************************************************************
	 * Grafico Movimentacao Diaria - Comparando Mes Atual com Anterior
	 **************************************************************************/
	public HashMap<Integer, Integer> pesquisarGraficoPagamentoDiarioMesAnterior(CaixaFiltro filtro) {
		HashMap<Integer, Integer> retorno = new HashMap<Integer, Integer>();
		
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.MONTH, -1);
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date primeiroDiaMesAnterior = aCalendar.getTime();
		Calendar bCalendar = Calendar.getInstance();
		bCalendar.add(Calendar.MONTH, -1);
		bCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date ultimoDiaMesAnterior = bCalendar.getTime();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT DAY(cx.data) AS DIA, sum(cx.valor) AS TOTAL ");
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		sb.append(" and cx.data between "+formataMySqlData(primeiroDiaMesAnterior)+" and " + formataMySqlData(ultimoDiaMesAnterior));  
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));
		sb.append(" group by DAY(cx.data) ");
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Integer dia = rs.getInt(1);
				Integer total = rs.getInt(2);
				retorno.put(dia, total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	// GRAFICO GraficoPagamentoDiario MesAtual X Anterior - FIM
	//---------------------------------------------------------------------------------------------------------------------------------
	
	
	private ChartDataSource criarGraficoPizzaPagamentoCategoriaDataSource() {
		HashMap<String, Double> resultado = pesquisarGraficoPagamentoCategoriaMesCorrente(filtro);
		List<PieSerie> series = new ArrayList<PieSerie>();
		List<PieData> dados = new ArrayList<PieSerie.PieData>();
		for(String set : resultado.keySet()){
			dados.add(new PieData(set, resultado.get(set)));
		}
		series.add(new PieSerie(dados));
//		return new ChartDataSource("Movimentacao",null,series,Type.PIE,"containerPizzaMovimentacaoPorCategoria");
		return new ChartDataSource(MessagesUtil.retornaMsg("msg.grafico.pizza.categoria", getLocale()),null,series,Type.PIE,"containerPizzaMovimentacaoPorCategoria");
		
		
	}	
	
	
	//---------------------------------------------------------------------------------------------------------------------------------
	// GRAFICO GraficoLinhaPagamentoCategoria MesAtual X Anterior  - INICIO
	//---------------------------------------------------------------------------------------------------------------------------------
	
	private ChartDataSource criarGraficoLinhaPagamentoCategoriaDataSource() {
		HashMap<String, Double> resultadoAtual = pesquisarGraficoPagamentoCategoriaMesCorrente(filtro);
		HashMap<String, Double> resultadoAnterior = pesquisarGraficoPagamentoCategoriaMesAnterior(filtro);
		List<String> categorias = this.criarCategoriasPagamentoCategoria(resultadoAtual, resultadoAnterior);
		return new ChartDataSource(MessagesUtil.retornaMsg("msg.grafico.linha.categoria", getLocale()),null,this.criarSeriesPagamentoCategoria(resultadoAtual, resultadoAnterior, categorias),categorias,Type.LINE,"containerLinhaPgtoContaMesAtualAnterior","Valor","R$");
	}	
	
	private List<String> criarCategoriasPagamentoCategoria(
			HashMap<String, Double> resultadoAtual, HashMap<String, Double> resultadoAnterior) {
		List<String> categorias = new ArrayList<String>();
		for(String categ : resultadoAtual.keySet()){
			categorias.add(categ);
		}
		for(String categ : resultadoAnterior.keySet()){
			if(!categorias.contains(categ)){
				categorias.add(categ);
			}
		}
		return categorias;
	}

	private List<LineSerie> criarSeriesPagamentoCategoria(
			HashMap<String, Double> resultadoAtual, HashMap<String, Double> resultadoAnterior, List<String> categorias) {
		List<LineSerie> series = new ArrayList<LineSerie>();
		List<Double> atual = new ArrayList<Double>();
		List<Double> anterior = new ArrayList<Double>();
		for(String categoria : categorias){
			if(resultadoAtual.get(categoria)==null){
				atual.add(0D);
			}else{
				atual.add(new Double(resultadoAtual.get(categoria)));
			}
			if(resultadoAnterior.get(categoria)==null){
				anterior.add(0D);
			}else{
				anterior.add(new Double(resultadoAnterior.get(categoria)));
			}
		}
		series.add(new LineSerie(MessagesUtil.retornaMsg("msg.grafico.mes_atual", getLocale()), null, Serie.TYPE_SPLINE, false, atual));
		series.add(new LineSerie(MessagesUtil.retornaMsg("msg.grafico.mes_anterior", getLocale()), null, Serie.TYPE_SPLINE, false, anterior));
		return series;
	}	
	
	/**************************************************************************
	 * Grafico Movimentacao por Conta - Comparando Mes Atual com Anterior
	 **************************************************************************/
	public HashMap<String, Double> pesquisarGraficoPagamentoCategoriaMesCorrente(CaixaFiltro filtro) {
		HashMap<String, Double> retorno = new HashMap<String, Double>();
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date primeiroDiaMes = aCalendar.getTime();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT c.categoria, sum(cx.valor) AS TOTAL ");
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		sb.append(" and cx.data >= "+formataMySqlData(primeiroDiaMes));  
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));
		sb.append(" group by c.categoria ");
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				String categoria = rs.getString(1);
				Double total = rs.getDouble(2);
				retorno.put(categoria, total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	
	/**************************************************************************
	 * Grafico Movimentacao por Conta - Comparando Mes Atual com Anterior
	 **************************************************************************/
	public HashMap<String, Double> pesquisarGraficoPagamentoCategoriaMesAnterior(CaixaFiltro filtro) {
		HashMap<String, Double> retorno = new HashMap<String, Double>();
		
		Calendar aCalendar = Calendar.getInstance();
		aCalendar.add(Calendar.MONTH, -1);
		aCalendar.set(Calendar.DAY_OF_MONTH, 1);
		Date primeiroDiaMesAnterior = aCalendar.getTime();
		Calendar bCalendar = Calendar.getInstance();
		bCalendar.add(Calendar.MONTH, -1);
		bCalendar.add(Calendar.DAY_OF_MONTH, -1);
		Date ultimoDiaMesAnterior = bCalendar.getTime();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT c.categoria, sum(cx.valor) AS TOTAL ");
		sb.append(" from e.caixa cx, e.conta c, e.forma_pgto f  "); 
		sb.append(" where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto  ");
		sb.append(" and cx.data between "+formataMySqlData(primeiroDiaMesAnterior)+" and " + formataMySqlData(ultimoDiaMesAnterior));  
		if (idOrganizacao != null) {
			sb.append(" and cx.id_organizacao = " + idOrganizacao);  
		}
		sb.append(montaClausulas(filtro));
		sb.append(" group by c.categoria ");
		
		Connection con = ConnectMySql.createConnection();
		System.out.println(sb.toString());
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				String categoria = rs.getString(1);
				Double total = rs.getDouble(2);
				retorno.put(categoria, total);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	//---------------------------------------------------------------------------------------------------------------------------------
	// GRAFICO GraficoLinhaPagamentoCategoria MesAtual X Anterior - FIM
	//---------------------------------------------------------------------------------------------------------------------------------
	


	public boolean isFiltroInicialCarregado() {
		return filtroInicialCarregado;
	}

	public void setFiltroInicialCarregado(boolean filtroInicialCarregado) {
		this.filtroInicialCarregado = filtroInicialCarregado;
	}

	public Locale getLocale() {
		if(locale == null) {
			locale = (Locale)SessionUtils.getSessionMapValue("locale");			
//			System.out.println("--> setando locale:" + locale.toString());
		}
		
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
