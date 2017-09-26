package br.com.trapp.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Classe utilitaria para trabalhar com todo tipo de tabela excel retornando
 **Linhas e Colunas Podendo ser usada futuramente para
 * outras necessidades de trabalho de leitura de planilhas do Excel
 * 
 * @author cleiton.anjos
 * @version 1.1
 */

public  class ExcelUtil{
//	final Logger LOG = Logger.getLogger(this.getClass());

	/**Metodo para ler um arquivo de excel e retornar com as linhas de HashMap
	 * @param fileName
	 * @param inputStream
	 * @return HashMap<Integer, HashMap<Integer, String>>
	 * @throws IOException 
	 */
	public HashMap<Integer, HashMap<Integer, String>> lerPlanilha(String fileName,  InputStream inputStream) throws IOException {
		XSSFWorkbook xssfWorkbook =null;
		HSSFWorkbook hssfWorkbook = null;
		HashMap<Integer, HashMap<Integer, String>> dadosPlanilhaExcel = new HashMap<Integer, HashMap<Integer,String>>();
		try {
			
			if (fileName.contains(".xlsx")) {

				xssfWorkbook = new XSSFWorkbook(inputStream);

				for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
					HashMap<Integer, String> mapValuesColun =null;
					int linha = 0;
					XSSFRow xssFRow = null;
					for (int x = 0; x<xssfWorkbook.getSheetAt(i).getPhysicalNumberOfRows(); x++) {
						xssFRow = xssfWorkbook.getSheetAt(i).getRow(x);
						mapValuesColun  = new HashMap<Integer, String>();
						if(xssFRow !=null)
						for(int c=0; c<(xssFRow.getPhysicalNumberOfCells()+10 ) ;c++){
								if(xssFRow.getCell(c)!=null){
										mapValuesColun.put(c, returnValue(xssFRow.getCell(c)));
								}else{
									mapValuesColun.put(c,null);
								}
								dadosPlanilhaExcel.put(linha, mapValuesColun);
						}
						linha++;
					}
				}
				xssfWorkbook.close();
			}else{
				hssfWorkbook = new HSSFWorkbook(inputStream);
				for (int i = 0; i < hssfWorkbook.getNumberOfSheets(); i++) {
					HashMap<Integer, String> mapValuesColun =null;
					int linha = 0; // Linha 0
					HSSFRow hssFRow = null;
					for (int x = 0; x< hssfWorkbook.getSheetAt(i).getPhysicalNumberOfRows(); x++) {
						hssFRow = hssfWorkbook.getSheetAt(i).getRow(x);	
						mapValuesColun  = new HashMap<Integer, String>();
						if(hssFRow !=null)
					
						for(int c=0; c<( hssFRow.getPhysicalNumberOfCells() + 10) ;c++){
				
							if(hssFRow.getCell(c)!=null){
									mapValuesColun.put(c, returnValue(hssFRow.getCell(c)));
							}else{
								mapValuesColun.put(c,null);
							}
							dadosPlanilhaExcel.put(linha, mapValuesColun);
						}
							
						linha++;
					}
				}
				hssfWorkbook.close();
			}
			return dadosPlanilhaExcel;
		} catch (IOException e) {
			e.printStackTrace();
//			LOG.error("Erro aoprocessar excel: " + e.getMessage());
			throw new IOException("Erro aoprocessar excel:", e);
		}
	}

	// Metodo para trabalhar com os tipos de informacoes da planilha
	private String returnValue(XSSFCell cell){  // Para trabalhar com Excell .xlsx
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
				Date data = new Date();
				data = cell.getDateCellValue();
//            	String format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
				String format = new SimpleDateFormat("yyyy-MM-dd").format(data);
            	return String.valueOf(format);
            } else {
            	return String.valueOf(cell.getRawValue());
            }
		case  XSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case  XSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case  XSSFCell.CELL_TYPE_BLANK:
			return "" ;
		case  XSSFCell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case  XSSFCell.CELL_TYPE_ERROR:
			return String.valueOf(cell.getErrorCellString());
		}
		return null;
	}

	private String returnValue(HSSFCell cell){ // Para trabalhar com Excel .xls
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if(HSSFDateUtil.isCellDateFormatted(cell)){
				Date data = new Date();
				data = cell.getDateCellValue();
//				String format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(data);
				String format = new SimpleDateFormat("yyyy-MM-dd").format(data);
				return String.valueOf(format);
			}else{
				return String.valueOf(cell.getNumericCellValue());
			}
		case  HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case  HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case  HSSFCell.CELL_TYPE_BLANK:
			return "" ;
		case  HSSFCell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case  HSSFCell.CELL_TYPE_ERROR:
			return String.valueOf(cell.getErrorCellValue());
		}
		return null;
	}
}
