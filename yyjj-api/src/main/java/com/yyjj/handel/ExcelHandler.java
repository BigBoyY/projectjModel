package com.yyjj.handel;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler<T> {
	DBHandler dbh =new DBHandler();
	
	/**
	 * 导出excel 
	 * @param workbook 
	 * @param sheetNum 
	 * @param fileName
	 * @param headers
	 * @param dataset
	 * @param out
	 */
	public void exportExcel(HSSFWorkbook workbook,int sheetNum,String fileName,String [] headers,Collection<T> dataset ,OutputStream out) {
		exportExcel(workbook,sheetNum,fileName,headers,dataset,out,"yyyy-MM-dd");
		
	}
	
	/**
	 * excel 07 导出excel调用方法
	 * @param workbook
	 * @param sheetNum
	 * @param fileName
	 * @param headers
	 * @param dataset
	 * @param out
	 */
	public void exportExcel07(XSSFWorkbook workbook, int sheetNum,
            String fileName, String[] headers, Collection<T> dataset,
            OutputStream out) {
		exportExcel07(workbook,sheetNum,fileName,headers,dataset,out,"yyyy-MM-dd");
	}
	
	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param out
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	private void exportExcel07(XSSFWorkbook workbook, int sheetNum,
            String title, String[] headers, Collection<T> dataset,
            OutputStream out, String pattern) {
		try {
			//生成一个表格
			XSSFSheet sheet = workbook.createSheet();
			
			workbook.setSheetName(sheetNum, title);
			//设置表格默认列宽度为15个字符
			sheet.setDefaultColumnWidth((short)25);
			
			XSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
           // style.setFillForegroundColor(XSSFColor.);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setAlignment(HorizontalAlignment.CENTER);
            
            //生成一个字体
            XSSFFont font = workbook.createFont();
           // font.setColor(HSSFColor.HSSFColorPredefined.VIOLET.getIndex());
            font.setFontHeightInPoints((short) 12);
            
            style.setFont(font);
            
            //产生标题行
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
				String string = headers[i];
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				XSSFRichTextString text = new XSSFRichTextString(string);
				cell.setCellValue(text);
			}
            
         // 遍历集合数据，产生数据行
            Iterator<T> it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    XSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (null == value) {
                        textValue = null;
                    } else {
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        } else {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        cell.setCellValue(textValue);
                    }
                }
                
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }	
	}
	
	/**
	 * 
	 * @param workbook
	 * @param sheetNum
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param out
	 * @param pattern 如果有时间数据，设定输出格式。默认为"yyyy-MM-dd"
	 */
	public void exportExcel(HSSFWorkbook workbook, int sheetNum, String title,
            String[] headers, Collection<T> dataset, OutputStream out,
            String pattern) {
		try {
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(sheetNum, title);
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth((short) 25);
            // 生成一个样式
            HSSFCellStyle style = workbook.createCellStyle();
            // 设置这些样式
//            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.SKY_BLUE.getIndex());
//          
//            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            style.setBorderBottom(BorderStyle.THIN);
//            style.setBorderLeft(BorderStyle.THIN);
//            style.setBorderRight(BorderStyle.THIN);
//            style.setBorderTop(BorderStyle.THIN);
//            style.setAlignment(HorizontalAlignment.CENTER);
            // 生成一个字体
            HSSFFont font = workbook.createFont();
            //font.setColor(HSSFColor.HSSFColorPredefined.VIOLET.getIndex());
            font.setFontHeightInPoints((short) 12);
            // 把字体应用到当前的样式
            style.setFont(font);
 
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            
            // 遍历集合数据，产生数据行
            Iterator<T> it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    Class<? extends Object> tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (null == value) {
                        textValue = null;
                    } else {
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        } else {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        cell.setCellValue(textValue);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
  
