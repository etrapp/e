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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.trapp.ConnectMySql;
import br.com.trapp.util.FacesUtil;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.vo.CrudVO;
import br.com.trapp.vo.TabViewVO;

@ViewScoped
@ManagedBean
public class LembreteBean {

	SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");

	private AuditoriaBean auditoriaBean = new AuditoriaBean();

	private Date data;

	private CrudVO itemSelecionado;
	
	private Long id;

	private String lembrete;
	
	private String categoria;
	
	private List<CrudVO> contas = new ArrayList<CrudVO>();
	
	private boolean concluido;

	@PostConstruct
	public void init() {
		this.clearFields();
	}			
	
	public void clearFields() {
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
	}
	
	public List<CrudVO> listar() {
//		contas.clear();
		
		List<CrudVO> lista = new ArrayList<CrudVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, data, nome, concluido, categoria from lembrete order by data desc");
			while (rs.next()) {
				CrudVO cx = new CrudVO();
				cx.setId(rs.getLong(1));
				cx.setData(rs.getDate(2));
				cx.setNome(rs.getString(3));
				cx.setConcluido(rs.getBoolean(4));
				cx.setCategoria(rs.getString(5));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contas.addAll(lista);
		return lista;
	}

	public List<CrudVO> listar(String categoria) {
//		contas.clear();
		
		List<CrudVO> lista = new ArrayList<CrudVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		String sql = "select id, data, nome, concluido, categoria from lembrete where categoria ='"+categoria+"' order by data desc";
		
		try {
			if(categoria == null) {
				sql = "select id, data, nome, concluido, categoria from lembrete where categoria is null order by data desc";
			}
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				CrudVO cx = new CrudVO();
				cx.setId(rs.getLong(1));
				cx.setData(rs.getDate(2));
				cx.setNome(rs.getString(3));
				cx.setConcluido(rs.getBoolean(4));
				cx.setCategoria(rs.getString(5));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		contas.addAll(lista);
		return lista;
	}
	
	public List<TabViewVO> listarTabs() {
//		contas.clear();
		
		List<TabViewVO> lista = new ArrayList<TabViewVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct categoria from lembrete");
			while (rs.next()) {
				TabViewVO cx = new TabViewVO();
				cx.setCategoria(rs.getString(1));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (TabViewVO tabViewVO : lista) {
			tabViewVO.setCategorias(listar(tabViewVO.getCategoria()));
			if(tabViewVO.getCategoria() == null) {
				tabViewVO.setCategoria("Outros");				
			}
		}
		
//		tabs.addAll(lista);
		return lista;
	}
	
	public int adicionarLembrete(Date data, String lembrete, String categoria) throws SQLException {
		
		String dataFormatada = "now()";
		if(data != null) {
			dataFormatada = "'"+formatData.format(data)+"'";
		}

		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "insert into lembrete (data, nome, categoria) values("+dataFormatada+", '"+lembrete+"'" +", '"+categoria+"')";
		if(categoria == null) {
			sql = "insert into lembrete (data, nome) values("+dataFormatada+", '"+lembrete+"')";
		}
		return stmt.executeUpdate(sql);

	}

	public int atualizarLembrete(Long id, Date data, String responsavel, boolean concluido, String categoria) throws SQLException {
		
		String dataFormatada = formatData.format(data);

		Connection con = ConnectMySql.createConnection();

		Statement stmt = con.createStatement();
		String sql = "update lembrete set data='"+dataFormatada+"', nome='"+responsavel+"', concluido="+ concluido +", categoria='"+ categoria +"' where id="+ id;
		if(categoria == null) {
			sql = "update lembrete set data='"+dataFormatada+"', nome='"+responsavel+"', concluido="+ concluido +", categoria=null where id="+ id;
		}
		return stmt.executeUpdate(sql);

	}

	public int atualizarLembrete(Long id, boolean concluido) throws SQLException {
		
		Connection con = ConnectMySql.createConnection();

		Statement stmt = con.createStatement();
		String sql = "update lembrete set concluido="+ concluido +" where id="+ id;
		return stmt.executeUpdate(sql);

	}

	public int deletarLembrete(Long idLembrete) throws SQLException {
		
		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "delete from lembrete where id="+ idLembrete;
		return stmt.executeUpdate(sql);
		
	}	
	
	public void salvar() {
		System.out.println(this.id + "," + data + "," + this.lembrete);

		//Validacoes
		if(this.lembrete == null || this.lembrete.isEmpty()) {
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.responsavel.invalido"));
			return;
		}
		
		String dataFormatada = "";
		if(data != null) {
			dataFormatada = formatData.format(data);
		} else {
			dataFormatada = formatData.format(new Date());
		}
		try {
			if(this.id == null) {
				adicionarLembrete(data, lembrete, categoria);
				auditoriaBean.registrar("Insert", "Lembrete:" + this.lembrete + ", Data:" + dataFormatada);
			} else {
				atualizarLembrete(id, data, lembrete, concluido, categoria);
				auditoriaBean.registrar("Update", "Resp:" + this.lembrete + ", Data:" + dataFormatada);
			}
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.lancamento.ok"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.lancamento.nok") + e.getLocalizedMessage(), e.getMessage());
	        return;
		}
		
		limparFormulario();
	}

	public void deletar() {
		System.out.println("DELETAR:" + itemSelecionado.getId() + ","+ itemSelecionado.getNome());
		
		if(itemSelecionado.getId() != null) {
			try {
				deletarLembrete(itemSelecionado.getId());
				auditoriaBean.registrar("Delete", "Conta:"+  itemSelecionado.getNome() + ", Data:" + itemSelecionado.getData());
				FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.exclusao.ok"));
			} catch (SQLException e) {
				e.printStackTrace();
				FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.exclusao.nok") + e.getLocalizedMessage());
		        return;
			}
		}
		limparFormulario();
	}
		
	public void limparFormulario() {
		this.id=null;
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
		this.lembrete=null;
		this.categoria=null;
		itemSelecionado=null;
	}
	
	public void editarLinha() {
		System.out.println("Editar " + itemSelecionado.getId() + ","+ itemSelecionado.getNome());
		
		this.id=itemSelecionado.getId();
		this.data=itemSelecionado.getData();
		this.lembrete=itemSelecionado.getNome();
		this.categoria=itemSelecionado.getCategoria();
	}

	public void concluirLinha(Long idParam, boolean concl) {
		System.out.println("Concluir " + idParam);
		System.out.println("Concluir " + concl);
		
//		this.id=itemSelecionado.getId();
//		this.data=itemSelecionado.getData();
//		this.lembrete=itemSelecionado.getNome();

		try {
			if(idParam != null) {
//				atualizarLembrete(itemSelecionado.getId(), itemSelecionado.isConcluido());
				atualizarLembrete(idParam, concl);
//				auditoriaBean.registrar("Update", "Resp:" + this.lembrete + ", Data:" + dataFormatada);
				FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.lancamento.ok"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.lancamento.nok") + e.getLocalizedMessage(), e.getMessage());
	        return;
		}

	}

	public List<String> completeLembrete(String query){
		List<String> queries = buscarLembrete(query);
		return queries;
		
//        List<String> filteredResponsavel = new ArrayList<String>();
//        
//        for (int i = 0; i < listaResponsavel.size(); i++) {
//            String vo = listaResponsavel.get(i);
//            if(vo.toLowerCase().contains(query.toLowerCase())) {
//                filteredResponsavel.add(vo);
//            }
//        }
//         
//        return filteredResponsavel;		
	}

    
	public List<String> buscarLembrete(String lembrete) {
		
		List<String> contas = new ArrayList<String>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select distinct nome from lembrete where nome like '" +lembrete+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				contas.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contas;
	}	
	

	public List<String> completeCategoria(String query){
		List<String> contas = new ArrayList<String>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select distinct categoria from lembrete where categoria like '" +query+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				contas.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contas;
	}
	
	
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Long getIdCaixa() {
		return id;
	}

	public void setIdCaixa(Long idCaixa) {
		this.id = idCaixa;
	}

	public List<CrudVO> getContas() {
		return contas;
	}

	public void setContas(List<CrudVO> contas) {
		this.contas = contas;
	}

	public CrudVO getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(CrudVO itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}

	public String getLembrete() {
		return lembrete;
	}

	public void setLembrete(String lembrete) {
		this.lembrete = lembrete;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}


}
