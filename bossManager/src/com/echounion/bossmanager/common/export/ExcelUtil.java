package com.echounion.bossmanager.common.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.common.json.JsonUtil;

/**
 * Excel工具类
 * @author 胡礼波
 * 2013-2-4 下午02:22:47
 */
public class ExcelUtil {

	private static int sheetSize=200;		//工作簿每页显示数据条数
	
	private static Logger logger=Logger.getLogger(ExcelUtil.class);
	
	public static void setSheetSize(int sheetSize) {
		ExcelUtil.sheetSize = sheetSize;
	}

	/**
	 * 创建Excel工作簿
	 * @author 胡礼波
	 * 2013-2-4 下午02:31:48
	 * @return
	 */
	public static Workbook createWorkBook()
	{
		Workbook wb=new  HSSFWorkbook();
		return wb;
	}
	
	/**
	 * 创建Excel Sheet
	 * @author 胡礼波
	 * 2013-2-4 下午02:32:41
	 * @return
	 */
	public static Sheet createSheet(Workbook workbook)
	{
		Assert.notNull(workbook,"工作簿为空!");
		Sheet sheet=workbook.createSheet();
		return sheet;
	}
	
	/**
	 * 创建Excel Sheet
	 * @author 胡礼波
	 * 2013-2-4 下午02:32:41
	 * @return
	 */
	public static Sheet createSheet(Workbook workbook ,String name)
	{
		Assert.notNull(workbook,"工作簿为空!");
		Assert.notNull(name,"工作簿名称不能为空!");
		Sheet sheet=workbook.createSheet(name);
		return sheet;
	}
	
	/**
	 * 导出数据
	 * @author 胡礼波
	 * 2013-2-4 下午02:54:48
	 * @param titleMap  Excel表头
	 * @param data	 Excel行数据
	 * @param path	 Excel导出路径
	 * @return 返回导出的数据的条数
	 */
	public static int exportData(Map<String,String> titleMap,List<?> data,String path)
	{
		Workbook wb=createWorkBook();
		
		CellStyle cellStyle = wb.createCellStyle();            //设置单元格式样
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		int sheetCount=(data.size()+sheetSize-1)/sheetSize;		//总页码
		Sheet sheet=null;
		Cell cell=null;
		Row row=null;
		int currentRowIndex=0;									//当前行索引
		int currentCellIndex=0;									//当前单元格索引
		int exportCount=0;										//导出数据总量
		JSONObject jsonObj=null;
		for (int i=0;i<sheetCount;i++) {						//实现翻页
			sheet=wb.createSheet();
			currentRowIndex=0;
			currentCellIndex=0;
			logger.info("新建Excel Sheet:"+sheet.getSheetName());
			row=sheet.createRow(currentRowIndex);								//Excel表头
			row.setHeight((short)500);
			for (String key:titleMap.keySet()) {
				cell=row.createCell(currentCellIndex);
				cell.setCellValue(titleMap.get(key));
				cell.setCellStyle(cellStyle);
				currentCellIndex++;
			}
			currentCellIndex=0;
			
			Iterator<?> it=data.iterator();
			while(it.hasNext()) {					//填充数据
				currentRowIndex++;
				row=sheet.createRow(currentRowIndex);
				row.setHeight((short)400);
				jsonObj=JsonUtil.toJsonStringFilterPropter(it.next());
				for (String key:titleMap.keySet()) {
					cell=row.createCell(currentCellIndex);
					cell.setCellValue(String.valueOf(jsonObj.get(key)));
					cell.setCellStyle(cellStyle);
					currentCellIndex++;
				}
				currentCellIndex=0;								//重置cell索引
				exportCount++;
				it.remove();
				if(currentRowIndex%sheetSize==0)
				{
					break;
				}
			}
			logger.info("成功导出"+exportCount+"条数据");
		}
		writeData(wb,path);
		return exportCount;
	}

	/**
	 * 把Excel输出到文件中
	 * @author 胡礼波
	 * 2013-2-4 下午03:33:06
	 * @param workbook
	 * @param path
	 * @throws Exception
	 */
	public static void writeData(Workbook workbook,String path)
	{
		FileOutputStream output=null;
		try {
			output = new FileOutputStream(path);
			workbook.write(output);
			logger.info("Excel文件成功导出！");
		} catch (Exception e) {
			logger.error("导出Excel文件出错!");
		}
		finally
		{
			try {
				if(output!=null)
				{
					output.flush();
					output.close();					
				}
			} catch (IOException e) {
				logger.error("关闭文件输出资源出错!");
			}
		}
	}
	
}
