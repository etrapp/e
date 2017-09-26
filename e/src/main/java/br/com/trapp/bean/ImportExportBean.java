package br.com.trapp.bean;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import br.com.trapp.ConnectMySql;
import br.com.trapp.util.ExcelUtil;
import br.com.trapp.util.MessagesUtil;
import br.com.trapp.util.SessionUtils;
import br.com.trapp.vo.CaixaVO;

@ApplicationScoped
@ManagedBean
public class ImportExportBean {

	private ListagemBean listagemBean = new ListagemBean();

	SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<CaixaVO> listar(Long idOrganizacao) {
		
		List<CaixaVO> lista = new ArrayList<CaixaVO>(); 
		
		Connection con = ConnectMySql.createConnection();

		try {
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto order by cx.data desc limit 25");
			ResultSet rs = stmt.executeQuery("select cx.id_caixa, cx.data, c.conta, cx.valor, cx.responsavel, cx.descricao, f.forma_pgto, cx.id_conta, cx.id_forma_pgto, cx.dc from caixa cx, conta c, forma_pgto f where c.id_conta = cx.id_conta and cx.id_forma_pgto = f.id_forma_pgto and cx.id_organizacao="+idOrganizacao+" order by cx.data desc");
			while (rs.next()) {
				CaixaVO cx = new CaixaVO();
				cx.setId(rs.getLong(1));
				cx.setData(rs.getDate(2));
				cx.setConta(rs.getString(3));
				cx.setValor(rs.getDouble(4));
				cx.setResponsavel(rs.getString(5));
				cx.setDescricao(rs.getString(6));
				cx.setFormaPgto(rs.getString(7));
				cx.setIdConta(rs.getLong(8));
				cx.setIdFormaPgto(rs.getLong(9));
				cx.setDc(rs.getString(10));
				lista.add(cx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	public byte[] exportarContas(Long idOrganizacao) {

		Workbook wb = null;
		List<CaixaVO> resultado = listar(idOrganizacao);

		if (!resultado.isEmpty()) {
			wb = new HSSFWorkbook();

			Font font = wb.createFont();

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			CreationHelper createHelper = wb.getCreationHelper();

			CellStyle cellStyleTitulo = wb.createCellStyle();
			cellStyleTitulo.setFont(font);
			cellStyleTitulo.setAlignment(CellStyle.ALIGN_CENTER);

			CellStyle cellStyleData = wb.createCellStyle();
			cellStyleData.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			cellStyleData.setAlignment(CellStyle.ALIGN_CENTER);

			CellStyle cellStyleTexto = wb.createCellStyle();
			cellStyleTexto.setAlignment(CellStyle.ALIGN_CENTER);
			Sheet sheet = wb.createSheet(MessagesUtil.retornaMsg("msg.cadastro.formulario"));

			int linha = 0;
			Row rowTitulos = sheet.createRow(linha);

			criarCabecalho(createHelper, cellStyleTitulo, rowTitulos);
			linha++;

			for (CaixaVO caixa : resultado) {
				criarLinha(cellStyleData, cellStyleTexto, sheet, linha, caixa);
				linha++;
			}

		}

		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
		try {
			wb.write(outByteStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outByteStream.toByteArray();
	}

	private void criarLinha(CellStyle cellStyleData, CellStyle cellStyleTexto, Sheet sheet, int linha, CaixaVO caixa) {

		int celula = 0;

		Row rowDados = sheet.createRow(linha);

		Cell celldocData = rowDados.createCell(celula++); // 1
		celldocData.setCellStyle(cellStyleTexto);

		if (caixa.getData() != null) {
			celldocData.setCellValue(formatData.format(caixa.getData()));
		} else {
			celldocData.setCellValue("");
		}

		Cell cellConta = rowDados.createCell(celula++); // 2
		cellConta.setCellStyle(cellStyleTexto);

		cellConta.setCellValue(caixa.getConta());

		Cell cellValor = rowDados.createCell(celula++);	//3
		cellValor.setCellStyle(cellStyleTexto);

		cellValor.setCellValue(caixa.getValor());

		Cell cellFormaPgto = rowDados.createCell(celula++);	//4
		cellFormaPgto.setCellStyle(cellStyleTexto);
		cellFormaPgto.setCellValue(caixa.getFormaPgto());

		Cell cellResponsavel = rowDados.createCell(celula++);	//5
		cellResponsavel.setCellStyle(cellStyleTexto);
		if (caixa.getResponsavel() != null) {
			cellResponsavel.setCellValue(caixa.getResponsavel());
		} else {
			cellResponsavel.setCellValue("");
		}

		Cell cellDescricao = rowDados.createCell(celula++);	//6
		cellDescricao.setCellStyle(cellStyleTexto);
		if (caixa.getDescricao()!=null) {
			cellDescricao.setCellValue(caixa.getDescricao());
		} else {
			cellDescricao.setCellValue("");
		}

		Cell cellDebCred = rowDados.createCell(celula++);	//7
		cellDebCred.setCellStyle(cellStyleTexto);
		cellDebCred.setCellValue(caixa.getDc());
		
	}

	private void criarCabecalho(CreationHelper createHelper, CellStyle cellStyleTitulo, Row rowTitulos) {

		int celula = 0;

		Cell debitoData = rowTitulos.createCell(celula++); // 1
		debitoData.setCellValue(createHelper.createRichTextString("Data"));
		debitoData.setCellStyle(cellStyleTitulo);

		Cell debitoConta = rowTitulos.createCell(celula++); // 2
		debitoConta.setCellValue(createHelper.createRichTextString("Conta"));
		debitoConta.setCellStyle(cellStyleTitulo);

		Cell debitoValor = rowTitulos.createCell(celula++); // 3
		debitoValor.setCellValue(createHelper.createRichTextString("Valor"));
		debitoValor.setCellStyle(cellStyleTitulo);

		Cell debitoFormaPgto = rowTitulos.createCell(celula++); // 4
		debitoFormaPgto.setCellValue(createHelper.createRichTextString("Tipo Pgto"));
		debitoFormaPgto.setCellStyle(cellStyleTitulo);

		Cell debitoResponsavel = rowTitulos.createCell(celula++); // 5
		debitoResponsavel.setCellValue(createHelper.createRichTextString("Responsável"));
		debitoResponsavel.setCellStyle(cellStyleTitulo);

		Cell debitoDescricao = rowTitulos.createCell(celula++); // 6
		debitoDescricao.setCellValue(createHelper.createRichTextString("Detalhes"));
		debitoDescricao.setCellStyle(cellStyleTitulo);

		Cell debitoDebCred = rowTitulos.createCell(celula++); // 6
		debitoDebCred.setCellValue(createHelper.createRichTextString("DC"));
		debitoDebCred.setCellStyle(cellStyleTitulo);

	}

	void leituraPlanilha(InputStream arquivo, String nomeArquivo,Long idOrganizacao) throws FileNotFoundException, IOException {
		Connection conn = ConnectMySql.createConnection(); 
		HashMap<Integer, HashMap<Integer, String>> linhasPlanilhaExcelMap = new ExcelUtil().lerPlanilha(nomeArquivo, arquivo);

		Collection<HashMap<Integer, String>> values = linhasPlanilhaExcelMap.values();
		int linha=0;
		String parametros = "";
		for (HashMap<Integer, String> hashMap : values) {
			linha++;

			parametros += "'" + checkStr(hashMap.get(0)) + "'";
//			parametros += ",'" + checkStr(hashMap.get(1)) + "'";
			Long idConta = listagemBean.consultarConta(hashMap.get(1));
			parametros += "," + idConta;
			parametros += ",'" + checkStr(hashMap.get(2)) + "'";
//			parametros += ",'" + checkStr(hashMap.get(3)) + "'";
			Long idFormaPgto = listagemBean.consultarFormaPgto(hashMap.get(3));
			parametros += "," + idFormaPgto;
			parametros += ",'" + checkStr(hashMap.get(4)) + "'";
			parametros += ",'" + checkStr(hashMap.get(5)) + "'";
			parametros += ","+idOrganizacao;
			parametros += ",'" + checkStr(hashMap.get(6)) + "'";
			
			//Os dados das planilhas comecam a partir da linha 2
			if (linha > 1) {
				String sql_insert = "";
				int resultado = 0;
				try {
					Statement stmt = conn.createStatement();
					String sql = "INSERT INTO e.caixa (data, id_conta, valor, id_forma_pgto, responsavel, descricao, id_organizacao, dc) VALUES ";
					 
					sql_insert = sql + " (" + parametros + ")";
					resultado = stmt.executeUpdate(sql_insert);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					parametros = "";
				}
			} else {
				parametros = "";
			}
		}
		
	}

	/*
	 * Substituicao de aspas
	 */
	private String checkStr(String s) {
		if(s!=null){
			s = s.replaceAll("'", "''");
		}
		return s;
	}
	
	
}
