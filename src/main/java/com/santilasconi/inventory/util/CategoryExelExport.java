package com.santilasconi.inventory.util;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.santilasconi.inventory.models.Category;

public class CategoryExelExport {

	private XSSFWorkbook wordbook;
	private XSSFSheet sheet;
	private List<Category> Listacategory;

	public CategoryExelExport(List<Category> categorias) {
		this.Listacategory = categorias;
		wordbook = new XSSFWorkbook();

	}

	public void WriteHeaderLine() {
		sheet = wordbook.createSheet("resultados");
		Row row = sheet.createRow(0);
		CellStyle stylecell = wordbook.createCellStyle();

		XSSFFont font = wordbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		stylecell.setFont(font); // agregando estilos a la cabecera

		createCell(row, 0, "ID", stylecell);
		createCell(row, 1, "nombre", stylecell);
		createCell(row, 2, "descripcion", stylecell);

	}

	private void createCell(Row row, int ColumnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(ColumnCount);
		Cell cell = row.createCell(ColumnCount);

		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);

	}
	
	private void writeDataLines() {
		int rowCount = 1;
		CellStyle style = wordbook.createCellStyle();
		XSSFFont font = wordbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);
		
		for(Category result: Listacategory) {
			Row row = sheet.createRow(rowCount++);
			int columnsCounst = 0;
			createCell(row, columnsCounst++, String.valueOf(result.getId()), style);
			createCell(row, columnsCounst++, result.getName(), style);
			createCell(row, columnsCounst++, result.getDescripcion(), style);
			
		}
		
	}
	
	public void export(HttpServletResponse response) throws Exception{
		
		WriteHeaderLine();
		writeDataLines();
		ServletOutputStream servletOutput = response.getOutputStream();
		wordbook.write(servletOutput);
		wordbook.close();
		servletOutput.close();
	}
}
