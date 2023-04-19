package com.santilasconi.inventory.util;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.santilasconi.inventory.models.Category;
import com.santilasconi.inventory.models.Product;

public class productExcelExport {
	private XSSFWorkbook wordbook;
	private XSSFSheet sheet;
	private List<Product> Listaproductos;

	public productExcelExport(List<Product> productos) {
		this.Listaproductos = productos;
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
		createCell(row, 1, "name", stylecell);
		createCell(row, 2, "price", stylecell);
		createCell(row, 3, "account", stylecell);
		createCell(row, 4, "category", stylecell);
		createCell(row, 5, "picture", stylecell);

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
		
		for(Product result: Listaproductos) {
			Row row = sheet.createRow(rowCount++);
			int columnsCounst = 0;
			createCell(row, columnsCounst++, String.valueOf(result.getId()), style);
			createCell(row, columnsCounst++, result.getName(), style);
			createCell(row, columnsCounst++, result.getPrice(), style);
			createCell(row, columnsCounst++, result.getAccount(), style);
			createCell(row, columnsCounst++, result.getCategory().getName(), style);
			
			
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
