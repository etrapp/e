package br.com.trapp.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.trapp.ConnectMySql;
import br.com.trapp.util.FacesUtil;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.util.SessionUtils;
import br.com.trapp.vo.CaixaVO;
import br.com.trapp.vo.ContaVO;
import br.com.trapp.vo.FormaPgtoVO;

@SessionScoped
@ManagedBean
public class CaixaBean {

	SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

	private ListagemBean listagemBean = new ListagemBean();
	
	private AuditoriaBean auditoriaBean = new AuditoriaBean();

	private ImportExportBean exportaBean = new ImportExportBean();

	private List<ContaVO> listaTipoConta = new ArrayList<ContaVO>();
	
	private List<FormaPgtoVO> listaFormaPgto = new ArrayList<FormaPgtoVO>();
	
	private Date data;

	private CaixaVO caixaSelecionado;
	
	private Long idCaixa;

	private Long idConta;

	private ContaVO contaSelecionada;

	private Long idFormaPgto;

	private FormaPgtoVO formaPgtoSelecionado;

	private Double valor;
	
	private String responsavel;
	
	private String descricao;
	
	private long sumarioTotal = 0L;
	
	private List<CaixaVO> contas = new ArrayList<CaixaVO>();

	private Double sumarioGrupo;
	
	private Locale locale;
	
	private Long idOrganizacao;

	@PostConstruct
	public void init() {
		this.clearFields();
		locale = (Locale)SessionUtils.getSessionMapValue("locale");
		idOrganizacao = (Long) SessionUtils.getSessionMapValue("idOrganizacao");
		changeLocale(locale);
	}			
	
	public void clearFields() {
		this.initCombos();
		
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
	}
	
	private void initCombos() {
		listaTipoConta = listagemBean.listarContas();
		listaFormaPgto = listagemBean.listarFormasPgto();
	}
	
	public List<CaixaVO> listarD() {
		sumarioTotal = 0L;
		contas.clear();
		
		List<CaixaVO> lista = new ArrayList<CaixaVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		Calendar calExtracaoFim = Calendar.getInstance();
		
		//
		Calendar calExtracaoIni = Calendar.getInstance();
		calExtracaoIni.set(Calendar.DAY_OF_MONTH, 0);
		calExtracaoIni.set(Calendar.HOUR_OF_DAY, 0);
		calExtracaoIni.set(Calendar.MINUTE, 0);
		calExtracaoIni.set(Calendar.SECOND, 0);
		calExtracaoIni.set(Calendar.MILLISECOND, 0);
		
		
		try {
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto order by cx.data desc limit 25");
			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto and cx.id_organizacao="+idOrganizacao+" and cx.dc='d' order by cx.data desc");
			while (rs.next()) {
				calExtracaoFim.setTime(rs.getDate(2));
				CaixaVO cx = new CaixaVO();
				cx.setId(rs.getLong(1));
				cx.setData(rs.getDate(2));
				cx.setConta(rs.getString(3));
				cx.setValor(rs.getDouble(4));
				if(calExtracaoFim.after(calExtracaoIni)) {		
					sumarioTotal += rs.getDouble(4);
				}
				cx.setResponsavel(rs.getString(5));
				cx.setDescricao(rs.getString(6));
				cx.setFormaPgto(rs.getString(7));
				cx.setIdConta(rs.getLong(8));
				cx.setIdFormaPgto(rs.getLong(9));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contas.addAll(lista);
		return lista;
	}
	
	public List<CaixaVO> listarC() {
		sumarioTotal = 0L;
		contas.clear();
		
		List<CaixaVO> lista = new ArrayList<CaixaVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		Calendar calExtracaoFim = Calendar.getInstance();
		
		//
		Calendar calExtracaoIni = Calendar.getInstance();
		calExtracaoIni.set(Calendar.DAY_OF_MONTH, 0);
		calExtracaoIni.set(Calendar.HOUR_OF_DAY, 0);
		calExtracaoIni.set(Calendar.MINUTE, 0);
		calExtracaoIni.set(Calendar.SECOND, 0);
		calExtracaoIni.set(Calendar.MILLISECOND, 0);
		
		
		try {
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto order by cx.data desc limit 25");
			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto and cx.id_organizacao="+idOrganizacao+" and cx.dc='c' order by cx.data desc");
			while (rs.next()) {
				calExtracaoFim.setTime(rs.getDate(2));
				CaixaVO cx = new CaixaVO();
				cx.setId(rs.getLong(1));
				cx.setData(rs.getDate(2));
				cx.setConta(rs.getString(3));
				cx.setValor(rs.getDouble(4));
				if(calExtracaoFim.after(calExtracaoIni)) {		
					sumarioTotal += rs.getDouble(4);
				}
				cx.setResponsavel(rs.getString(5));
				cx.setDescricao(rs.getString(6));
				cx.setFormaPgto(rs.getString(7));
				cx.setIdConta(rs.getLong(8));
				cx.setIdFormaPgto(rs.getLong(9));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contas.addAll(lista);
		return lista;
	}
	
	public int lancarConta(Long idConta, Date data, Long idFormaPgto, Double valor, String responsavel, String descricao) throws SQLException {
		
		String dataFormatada = "now()";
		if(data != null) {
			dataFormatada = "'"+formatData.format(data)+"'";
		}

		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "insert into caixa (data, id_conta, id_forma_pgto, valor, responsavel, descricao, id_organizacao) values("+dataFormatada+", "+idConta+"," + idFormaPgto + "," +valor+", '"+responsavel+"', '"+descricao+"',"+idOrganizacao+")";
		return stmt.executeUpdate(sql);

	}

	public int lancarRecebimento(Date data, Double valor, String responsavel, String descricao) throws SQLException {
		
		String dataFormatada = "now()";
		if(data != null) {
			dataFormatada = "'"+formatData.format(data)+"'";
		}

		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "insert into caixa (data, id_conta, id_forma_pgto, valor, responsavel, descricao, id_organizacao, dc) values("+dataFormatada+", 0, 0," +valor+", '"+responsavel+"', '"+descricao+"',"+idOrganizacao+",'c')";
		return stmt.executeUpdate(sql);

	}	
	public int atualizarConta(Long idCaixa, Date data, Long idConta, Long idFormaPgto, Double valor, String responsavel, String descricao) throws SQLException {
		
		String dataFormatada = formatData.format(data);

		Connection con = ConnectMySql.createConnection();

		Statement stmt = con.createStatement();
		String sql = "update caixa set data='"+dataFormatada+"', id_conta="+idConta+", id_forma_pgto="+idFormaPgto+", valor="+valor+", responsavel='"+responsavel+"', descricao='"+descricao+"' where id_caixa="+ idCaixa;
		return stmt.executeUpdate(sql);

	}

	public int deletarConta(Long idCaixa) throws SQLException {
		System.out.println("DELETAR id:" +idCaixa);
		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "delete from caixa where id_caixa="+ idCaixa;
		return stmt.executeUpdate(sql);
		
	}	

	public void modalOBS(){
		
		editarLinha();
		
		RequestContext context = RequestContext.getCurrentInstance(); 
		context.update("formModal:dlgOBS");
		context.execute("PF('dlgOBS').show();");
	}

	
	public void salvarOBS() {
		System.out.println("SELECIONADO:"+ caixaSelecionado.getDescricao());
		System.out.println("NOVA OBS:" + this.descricao);
	}
	
	
	public void salvar() {
		System.out.println(this.idCaixa + "," + data + ","+ this.idConta + "," + this.idFormaPgto + "," + this.valor + "," + this.responsavel + "," + this.descricao);

		//Validacoes
		if(contaSelecionada == null) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.conta.invalido", getLocale()));
			return;
		}
		if(this.valor == null) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.valor.invalido", getLocale()));
			return;
		}
		if(formaPgtoSelecionado == null) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.formaPgto.invalido", getLocale()));
			return;
		}
		if(this.responsavel == null || this.responsavel.isEmpty()) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.responsavel.invalido", getLocale()));
			return;
		}
		
		if(contaSelecionada != null){
			this.idConta=contaSelecionada.getIdConta();
		}
		if(formaPgtoSelecionado != null){
			this.idFormaPgto=formaPgtoSelecionado.getIdFormaPgto();
		}
		String dataFormatada = "";
		if(data != null) {
			dataFormatada = formatData.format(data);
		} else {
			dataFormatada = formatData.format(new Date());
		}
		try {
			if(this.idCaixa == null) {
				lancarConta(idConta, data, idFormaPgto, valor, responsavel, descricao);
				auditoriaBean.registrar("Insert", "Conta:" + contaSelecionada.getConta() + ", Valor:"+this.valor + ", Forma:"+ formaPgtoSelecionado.getFormaPgto() + ", Resp:" + this.responsavel + ", Data:" + dataFormatada);
			} else {
				atualizarConta(idCaixa, data, idConta, idFormaPgto, valor, responsavel, descricao);
				auditoriaBean.registrar("Update", "Conta:" + contaSelecionada.getConta() + ", Valor:"+this.valor + ", Forma:"+ formaPgtoSelecionado.getFormaPgto()  + ", Resp:" + this.responsavel + ", Data:" + dataFormatada);
			}
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.lancamento.ok", getLocale()));
		} catch (Exception e) {
			e.printStackTrace();
			changeLocale();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.lancamento.nok", getLocale()) + e.getLocalizedMessage(), e.getMessage());
	        return;
		}
		
		limparFormulario();
	}

	//credito
	public void salvarc() {
		System.out.println(this.idCaixa + "," + data + ","+ this.valor + "," + this.responsavel + "," + this.descricao);

		//Validacoes
//		if(contaSelecionada == null) {
//			changeLocale();
//			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.conta.invalido", getLocale()));
//			return;
//		}
		if(this.valor == null) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.valor.invalido", getLocale()));
			return;
		}
//		if(formaPgtoSelecionado == null) {
//			changeLocale();
//			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.formaPgto.invalido", getLocale()));
//			return;
//		}
		if(this.responsavel == null || this.responsavel.isEmpty()) {
			changeLocale();
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.responsavel.invalido", getLocale()));
			return;
		}
		
//		if(contaSelecionada != null){
//			this.idConta=contaSelecionada.getIdConta();
//		}
//		if(formaPgtoSelecionado != null){
//			this.idFormaPgto=formaPgtoSelecionado.getIdFormaPgto();
//		}
		String dataFormatada = "";
		if(data != null) {
			dataFormatada = formatData.format(data);
		} else {
			dataFormatada = formatData.format(new Date());
		}
		try {
			if(this.idCaixa == null) {
				lancarRecebimento(data, valor, responsavel, descricao);
				auditoriaBean.registrar("Insert", "Valor:"+this.valor + ", Descricao:"+ this.descricao + ", Resp:" + this.responsavel + ", Data:" + dataFormatada);
			} else {
				atualizarConta(idCaixa, data, idConta, idFormaPgto, valor, responsavel, descricao);
				auditoriaBean.registrar("Update", "Conta:" + contaSelecionada.getConta() + ", Valor:"+this.valor + ", Forma:"+ formaPgtoSelecionado.getFormaPgto()  + ", Resp:" + this.responsavel + ", Data:" + dataFormatada);
			}
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.lancamento.ok", getLocale()));
		} catch (Exception e) {
			e.printStackTrace();
			changeLocale();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.lancamento.nok", getLocale()) + e.getLocalizedMessage(), e.getMessage());
	        return;
		}
		
		limparFormulario();
	}

	
	private Locale getLocale() {
		if(locale == null) {
			locale = (Locale)SessionUtils.getSessionMapValue("locale");			
//			System.out.println("--> setando locale:" + locale.toString());
		}
		
		return locale;
	}

	public void deletar() {
		System.out.println("DELETAR:" + caixaSelecionado.getId() + ","+ caixaSelecionado.getIdConta()+ "-" + caixaSelecionado.getConta() + ","+ caixaSelecionado.getIdFormaPgto() + "-" + caixaSelecionado.getFormaPgto() + "," + caixaSelecionado.getValor() + "," + caixaSelecionado.getResponsavel() + "," + caixaSelecionado.getDescricao());
		
		if(caixaSelecionado.getId() != null) {
			try {
				deletarConta(caixaSelecionado.getId());
				auditoriaBean.registrar("Delete", "Conta:"+  caixaSelecionado.getConta() + ", Valor:" + caixaSelecionado.getValor() + ", Forma:"+ caixaSelecionado.getFormaPgto() + ", Resp:" + caixaSelecionado.getResponsavel() + ", Data:" + caixaSelecionado.getData());
				FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.exclusao.ok", getLocale()));
			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.exclusao.nok") + e.getLocalizedMessage());
		        return;
			}
		}
		limparFormulario();
	}

	public String moblistagem() {
		limparFormulario();
		changeLocale();
		return "mlistagem";
	}
	
	public String mobcadastro() {
		changeLocale();
		return "mcaixa";
	}

	public String mobdashboard() {
		limparFormulario();
		changeLocale();
		return "mdashboard";
	}	

	public String dashboard() {
		limparFormulario();
		changeLocale();
		return "dashboard";
	}	

	public String cadcredito() {
		limparFormulario();
		changeLocale();
		return "recibo";
	}	


	public String caixa() {
		limparFormulario();
		changeLocale();
		return "caixa";
	}		
	public void limparFormulario() {
		this.idCaixa=null;
//		this.data=null;
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
		this.idConta=null;
		this.idFormaPgto=null;
		this.valor=null;
		this.responsavel=null;
		this.descricao=null;
		caixaSelecionado=null;
		contaSelecionada=null;
		formaPgtoSelecionado=null;
	}
	
	public void editarLinha() {
		System.out.println("Editar " + caixaSelecionado.getId() + ","+ caixaSelecionado.getIdConta()+ "-" + caixaSelecionado.getConta() + ","+ caixaSelecionado.getIdFormaPgto() + "-" + caixaSelecionado.getFormaPgto() + "," + caixaSelecionado.getValor() + "," + caixaSelecionado.getResponsavel() + "," + caixaSelecionado.getDescricao());
		
		this.idCaixa=caixaSelecionado.getId();
		this.data=caixaSelecionado.getData();
		this.idConta=caixaSelecionado.getIdConta();
		this.idFormaPgto=caixaSelecionado.getIdFormaPgto();
		this.valor=caixaSelecionado.getValor();
		this.responsavel=caixaSelecionado.getResponsavel();
		this.descricao=caixaSelecionado.getDescricao();
		
		contaSelecionada = listagemBean.findContaById(idConta);
		formaPgtoSelecionado = listagemBean.findFormaPgtoById(idFormaPgto);
		
	}

	public void exportar() {
		try {
//			if(!existeFiltro() && getTotalRegistros() <= 0) {
//				addInfoMessage("Efetue o filtro antes de solicitar a exportação.");
//				return;
//			}
			
			byte[] arquivo = exportaBean.exportarContas(idOrganizacao);//notaFiscalBean.exportarNotasPendentes(filtro);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=caixa.xls");
			response.setContentLength(arquivo.length);
			response.getOutputStream().write(arquivo);
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().renderResponse();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
//			LOG.warning("Erro ao exportar (tela de pendências)! " + e.getMessage());
			e.printStackTrace();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.exportar.nok") + e.getLocalizedMessage(), e.getMessage());
		}
	}

	public void doUpload(final FileUploadEvent fileUploadEvent) throws Exception {
		
		String nomeArquivo = new String(fileUploadEvent.getFile().getFileName());
		UploadedFile x = fileUploadEvent.getFile();
		
		exportaBean.leituraPlanilha(x.getInputstream(), nomeArquivo,idOrganizacao);
	}

	public Double somarValores(String mes){
	    Double total = new Double(0);

	    for (CaixaVO conta: getContas()) {
	       if(conta.getMes().equals(mes)){
             total += conta.getValor();
	       }
	    }
	    sumarioGrupo=total;
	    return total;
	}

	public void changeLocale() {
		locale = (Locale)SessionUtils.getSessionMapValue("locale");
		changeLocale(locale);
	}

	public void changeLocale(Locale localeObj) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(localeObj);
		SessionUtils.setSessionMapValue("idOrganizacao", idOrganizacao);
	}
	
	public List<ContaVO> getListaTipoConta() {
		return listaTipoConta;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdFormaPgto() {
		return idFormaPgto;
	}

	public void setIdFormaPgto(Long idFormaPgto) {
		this.idFormaPgto = idFormaPgto;
	}

	public List<FormaPgtoVO> getListaFormaPgto() {
		return listaFormaPgto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	public CaixaVO getCaixaSelecionado() {
		return caixaSelecionado;
	}

	public void setCaixaSelecionado(CaixaVO caixaSelecionado) {
		this.caixaSelecionado = caixaSelecionado;
	}

	public long getSumarioTotal() {
		return sumarioTotal;
	}

	public void setSumarioTotal(long sumarioTotal) {
		this.sumarioTotal = sumarioTotal;
	}

	public ContaVO getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(ContaVO contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public FormaPgtoVO getFormaPgtoSelecionado() {
		return formaPgtoSelecionado;
	}

	public void setFormaPgtoSelecionado(FormaPgtoVO formaPgtoSelecionado) {
		this.formaPgtoSelecionado = formaPgtoSelecionado;
	}

	public List<CaixaVO> getContas() {
		return contas;
	}

	public void setContas(List<CaixaVO> contas) {
		this.contas = contas;
	}

	public Double getSumarioGrupo() {
		return sumarioGrupo;
	}

	public void setSumarioGrupo(Double sumarioGrupo) {
		this.sumarioGrupo = sumarioGrupo;
	}
	
}
