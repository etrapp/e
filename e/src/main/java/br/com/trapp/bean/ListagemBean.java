package br.com.trapp.bean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.trapp.ConnectMySql;
import br.com.trapp.vo.CaixaFiltro;
import br.com.trapp.vo.ContaVO;
import br.com.trapp.vo.FormaPgtoVO;

@SessionScoped
@ManagedBean
public class ListagemBean {

	public List<ContaVO> listarContas() {
		
		List<ContaVO> lista = new ArrayList<ContaVO>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from conta");
			while (rs.next()) {
				ContaVO vo = new ContaVO();
				vo.setIdConta(rs.getLong(1));
				vo.setConta(rs.getString(2));
				lista.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public List<FormaPgtoVO> listarFormasPgto() {
		
		List<FormaPgtoVO> lista = new ArrayList<FormaPgtoVO>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from forma_pgto");
			while (rs.next()) {
				FormaPgtoVO vo = new FormaPgtoVO();
				vo.setIdFormaPgto(rs.getLong(1));
				vo.setFormaPgto(rs.getString(2));
				lista.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
	public List<String> listarResponsavel() {
		
		List<String> lista = new ArrayList<String>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select nome from usuario");
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public List<String> listarCategoria() {
		
		List<String> lista = new ArrayList<String>();
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select distinct categoria from conta");
			while (rs.next()) {
				lista.add(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}

	public Long consultarConta(String conta) {
		
		Long idConta = 0l; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_conta, conta from conta where conta like '%" +conta+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				idConta = rs.getLong(1);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return idConta;
	}
	
	
	public Long consultarFormaPgto(String query) {
		
		Long id = 0l; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_forma_pgto, forma_pgto from forma_pgto where forma_pgto like '%" +query+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = rs.getLong(1);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return id;
	}
	
	
	public List<String> buscarConta(String conta) {
		
		List<String> contas = new ArrayList<String>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_conta, conta from conta where conta like '%" +conta+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				contas.add(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contas;
	}	

	public List<ContaVO> buscarContaVO(String conta) {
		
		List<ContaVO> contas = new ArrayList<ContaVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_conta, conta from conta where conta like '%" +conta+"%'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				ContaVO vo = new ContaVO();
				vo.setIdConta(rs.getLong(1));
				vo.setConta(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contas;
	}
	
	public ContaVO findContaById(Long id) {
		
		ContaVO contaVO = null; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_conta, conta from conta where id_conta=" +id;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				contaVO = new ContaVO();
				contaVO.setIdConta(rs.getLong(1));
				contaVO.setConta(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contaVO;
	}
	
	public ContaVO findContaByValue(String value) {
		
		ContaVO contaVO = null; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_conta, conta from conta where conta='" +value+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				contaVO = new ContaVO();
				contaVO.setIdConta(rs.getLong(1));
				contaVO.setConta(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contaVO;
	}
	
	public FormaPgtoVO findFormaPgtoById(Long id) {
		
		FormaPgtoVO pgtoVO = null; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_forma_pgto, forma_pgto from forma_pgto where id_forma_pgto=" +id;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pgtoVO = new FormaPgtoVO();
				pgtoVO.setIdFormaPgto(rs.getLong(1));
				pgtoVO.setFormaPgto(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pgtoVO;
	}

	public FormaPgtoVO findFormaPgtoByValue(String value) {
		
		FormaPgtoVO pgtoVO = null; 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			String sql = "select id_forma_pgto, forma_pgto from forma_pgto where forma_pgto='" +value + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				pgtoVO = new FormaPgtoVO();
				pgtoVO.setIdFormaPgto(rs.getLong(1));
				pgtoVO.setFormaPgto(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pgtoVO;
	}

	public HashMap<Integer, Long> pesquisarGraficoQuantidadeErrosQuantidadeNota(CaixaFiltro filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT C.QTD_PROBLEMA, COUNT(RESULTADO.CO_NOTA_FISCAL) AS QTD_NOTAS "); 
		sb.append("FROM ( "); 
		sb.append("  SELECT NOTA.CO_NOTA_FISCAL, COUNT(NTP.CO_PROBLEMA) AS QTD_PROBLEMA "); 
		sb.append("    FROM PAF.TB_NOTA_FISCAL NOTA, PAF.TB_NOTA_FISCAL_PROBLEMA NTP "); 
		sb.append("    WHERE NOTA.CO_NOTA_FISCAL = NTP.CO_NOTA_FISCAL ");  
//		sb.append("  	AND NTP.CO_STATUS_NOTA_PROBLEMA IN (1,3) "); 
		sb.append("  	AND NOTA.CO_STATUS_NOTA_FISCAL = 1 "); 
		sb.append("		AND NTP.CO_PROBLEMA IN (21)	");
//		sb.append(this.montaClausulasComProblema(filtro));
		if(filtro.getDataInicio()!=null){
			sb.append("		AND DATA >=" + filtro.getDataInicio());
		}
		if(filtro.getDataFim()!=null){
			sb.append("		AND DATA >=" + filtro.getDataFim());
		}
		sb.append("  GROUP BY NOTA.CO_NOTA_FISCAL "); 
		sb.append(") RESULTADO "); 
		sb.append("GROUP BY RESULTADO.QTD_PROBLEMA "); 
		sb.append("ORDER BY RESULTADO.QTD_PROBLEMA DESC ");
		HashMap<Integer, Long> retorno = new LinkedHashMap<Integer, Long>();

		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Long quantidadeNotas = ((Integer)rs.getObject(1)).longValue();
				Integer quantidadeErros = (Integer) rs.getObject(0);
				retorno.put(quantidadeErros, quantidadeNotas);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retorno;
	}

	public HashMap<String, Long> pesquisarGraficoNotasPorProblema(CaixaFiltro filtro) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) AS QUANTIDADE, PR.NO_PROBLEMA AS PROBLEMA "); 
		sb.append("FROM "); 
		sb.append("    PAF.TB_NOTA_FISCAL NOTA JOIN DBO.TB_FILIAL FILIAL ON FILIAL.CO_FILIAL = NOTA.CO_FILIAL "); 
		sb.append("    inner join DBO.TB_TIPO_FILIAL TF on tf.CO_TIPO_FILIAL = filial.CO_TIPO_FILIAL "); 
		sb.append("    inner join DBO.TB_REGIONAL REG on reg.CO_REGIONAL = filial.CO_REGIONAL "); 
		sb.append("    inner join PAF.TB_OPERACAO OPE on ope.CO_OPERACAO = nota.CO_OPERACAO "); 
		sb.append("    full outer join paf.TB_HISTORICO_NOTA HN on hn.CO_NOTA_FISCAL = nota.CO_NOTA_FISCAL "); 
		sb.append("    INNER JOIN PAF.TB_NOTA_FISCAL_PROBLEMA NTP ON NTP.CO_NOTA_FISCAL = NOTA.CO_NOTA_FISCAL "); 
		sb.append("    INNER JOIN PAF.TB_PROBLEMA PR ON PR.CO_PROBLEMA = NTP.CO_PROBLEMA "); 
		sb.append("    INNER JOIN PAF.TB_AREA_RESPONSAVEL NPR ON NPR.CO_AREA_RESPONSAVEL = NTP.CO_AREA_RESPONSAVEL "); 
		sb.append("    WHERE "); 
		sb.append("    NTP.CO_STATUS_NOTA_PROBLEMA = 1 ");
		sb.append("		AND  NOTA.DT_HR_CADASTRO_NOTA_FISCAL 	> DATEADD(DAY, -7, GETDATE()) ");
//		sb.append(this.montaClausulasComProblema(filtro));
		sb.append("group by PR.NO_PROBLEMA ");
		sb.append("ORDER BY QUANTIDADE	DESC ");
		
		HashMap<String, Long> retorno = new LinkedHashMap<String, Long>();
		
		Connection con = ConnectMySql.createConnection();

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

	
	
	//	public List<String> consultarConta(String conta) {
//		
//		List<String> lista = new ArrayList<String>(); 
//		
//		Connection con = ConnectMySql.createConnection();
//
//		try {
//			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select * from conta where conta like %" +conta+"%");
//			while (rs.next()) {
//				System.out.println(rs.getInt(1) + "  " + rs.getString(2));
//				lista.add(rs.getString(2));
//			}
//		} catch (Exception e) {
//			System.out.println("Problemas ao efetuar consulta. " + e.getMessage());
//		}
//
//		return lista;
//	}
	
}
