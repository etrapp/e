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
import br.com.trapp.vo.PontoVO;

@ViewScoped
@ManagedBean
public class RegistroPontoBean {

	SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

	private String itemSelecionado;
	
	private Date data;

	@PostConstruct
	public void init() {
		this.clearFields();
	}			
	
	public void clearFields() {
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
	}
	
	public List<PontoVO> listar() {
		List<PontoVO> ponto =  new ArrayList<PontoVO>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(batida,'%d-%m-%Y') data, DATE_FORMAT(batida,'%k:%i') hora from e.ponto order by batida asc");
//			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(batida,'%d-%m-%Y') data, DATE_FORMAT(batida,'%k:%i') hora, batida from e.ponto where batida > DATE_SUB(NOW(), INTERVAL 35 DAY) order by batida asc");
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(batida,'%d-%m-%Y') data, DATE_FORMAT(batida,'%k:%i') hora, batida from e.ponto where batida > DATE_SUB(CURDATE(), INTERVAL 35 DAY) order by batida asc");
			String dataGroup = "";
			PontoVO vo = null;
			List<String> listaHora = null;
			
			while (rs.next()) {
				
				if(!dataGroup.equalsIgnoreCase(rs.getString(1)) ){
					if(dataGroup.length()>1){
						vo.setDataf(dataGroup);
						vo.setHora(listaHora);
						ponto.add(vo);
					}
					vo = new PontoVO();
					listaHora = new ArrayList<String>();
					dataGroup = rs.getString(1);
				}
				
				listaHora.add(rs.getString(2));
				vo.setData(rs.getDate(3));
			}
			if(dataGroup.length()>1){
				vo.setDataf(dataGroup);
				vo.setHora(listaHora);
				ponto.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ponto;
	}

	public List<PontoVO> listarMobile() {
		List<PontoVO> ponto =  new ArrayList<PontoVO>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(batida,'%d-%m-%Y') data, DATE_FORMAT(batida,'%k:%i') hora from e.ponto where batida BETWEEN DATE_SUB(NOW(), INTERVAL 10 DAY) AND NOW() order by 1");
			ResultSet rs = stmt.executeQuery("select DATE_FORMAT(batida,'%d-%m-%Y') data, DATE_FORMAT(batida,'%k:%i') hora, batida from e.ponto where batida > DATE_SUB(CURDATE(), INTERVAL 10 DAY) order by batida asc");
			String dataGroup = "";
			PontoVO vo = null;
			List<String> listaHora = null;
			
			while (rs.next()) {
				
				if(!dataGroup.equalsIgnoreCase(rs.getString(1)) ){
					if(dataGroup.length()>1){
						vo.setDataf(dataGroup);
						vo.setHora(listaHora);
						ponto.add(vo);
					}
					vo = new PontoVO();
					listaHora = new ArrayList<String>();
					dataGroup = rs.getString(1);
				}
				
				listaHora.add(rs.getString(2));
				vo.setData(rs.getDate(3));
			}
			if(dataGroup.length()>1){
				vo.setDataf(dataGroup);
				vo.setHora(listaHora);
				ponto.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ponto;
	}

	
	public int registrarPonto(Date data) throws SQLException {
		
		String dataFormatada = "now()";
		if(data != null) {
			dataFormatada = "'"+formatData.format(data)+"'";
		}

		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = "insert into e.ponto values("+dataFormatada+")";
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

	public int deletarLembrete(String dtSelecionada) throws SQLException {
		
		Connection con = ConnectMySql.createConnection();
		Statement stmt = con.createStatement();
		String sql = " delete from e.ponto " +
					" where DATE_FORMAT(batida,'%d-%m-%Y') = '"+dtSelecionada+"' " +
					" order by batida desc " +
					" limit 1 ";
		return stmt.executeUpdate(sql);
		
	}	
	
	public void salvar() {
		try {
			registrarPonto(data);
			FacesUtil.exibirMensagemInfo(MessagesUtil.retornaMsg("msg.lancamento.ok"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.exibeMensagemErro(MessagesUtil.retornaMsg("msg.lancamento.nok") + e.getLocalizedMessage(), e.getMessage());
	        return;
		}
		
		limparFormulario();
	}

	public void deletar() {
		if(itemSelecionado != null) {
			try {
				deletarLembrete(itemSelecionado);
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
		Calendar aCalendar = Calendar.getInstance();
		this.data = aCalendar.getTime();
	}
	

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getItemSelecionado() {
		return itemSelecionado;
	}

	public void setItemSelecionado(String itemSelecionado) {
		this.itemSelecionado = itemSelecionado;
	}


}
