package com.kevin.commons;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GeneratedExcel {
	public static void generatedExcel(List<Object[]> dataList, OutputStream os, String shellName) throws IOException {
		HSSFWorkbook wb = new HSSFWorkbook();
		wb.createSheet();
		wb.setSheetName(0, shellName, HSSFCell.ENCODING_UTF_16);
		HSSFSheet s = wb.getSheetAt(0);
		s.setDefaultColumnWidth((short) (20));// 设置列的默认宽度
		s.setDefaultRowHeight((short) (30 * 20));
		for (int j = 0; j < dataList.size(); j++) {
			HSSFRow row = s.createRow(j);
			Object objects[] = (Object[]) dataList.get(j);
			for (short i = 0; i < objects.length; i++) {
				HSSFCell cell = row.createCell(i);
				s.getRow(j).getCell(i).setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(String.valueOf(objects[i]));
			}
			// s.setColumnWidth((short) 7, (short) (20 * 256));
		}
		wb.write(os);
		os.flush();
	}

	public static void writeSheet(List<Object[]> dataList,String shellName, int sheetIndex,HSSFWorkbook wb) throws IOException {
		wb.createSheet();
		wb.setSheetName(sheetIndex, shellName, HSSFCell.ENCODING_UTF_16);
		HSSFSheet s = wb.getSheetAt(sheetIndex);
		s.setDefaultColumnWidth((short) (20));
		s.setDefaultRowHeight((short) (30 * 20));
		int lineNum = 0;
		for (int j = 0; j < dataList.size(); j++) {
			HSSFRow row = s.createRow(lineNum);
			Object objects[] = (Object[]) dataList.get(j);
			for (short i = 0; i < objects.length; i++) {
				HSSFCell cell = row.createCell(i);
				s.getRow(lineNum).getCell(i).setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(String.valueOf(objects[i]));
			}
			lineNum++;
		}
	}

	public static final int SHEET_MAX_NUM = 60000;

	public static int calShellTotal(int total) {
		int shellNum = 1;
		if (total > SHEET_MAX_NUM) {
			if (total % SHEET_MAX_NUM == 0) {
				shellNum = total / SHEET_MAX_NUM;
			} else {
				shellNum = total / SHEET_MAX_NUM + 1;
			}
		}
		return shellNum;
	}

	public static String getShellName(String name, int totalSheet, int currentSheet) {
		return name + "_" + totalSheet + "-" + currentSheet;
	}
}
