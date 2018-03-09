package com.echounion.bossmanager.core.report;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;

/**
 * 柱状图
 * @author 胡礼波
 * 2012-11-30 下午05:08:36
 */
public class BarChart {


	public BarChart() {}

	
	/**
	 * 创建一个柱状图
	 * @author 胡礼波
	 * 2012-11-29 下午06:32:14
	 * @param dataset
	 * @return
	 */
	public static JFreeChart createChart(String title,String xTitle,String yTitle,CategoryDataset dataSet) {

		JFreeChart chart = ChartFactory.createBarChart(title,xTitle,yTitle,dataSet,
				PlotOrientation.VERTICAL, // orientation
				true, // include legend
				true, // tooltips?
				false // URLs?
				);
		chart.setBackgroundPaint(Color.white);
		
		Font font=new Font("宋体",Font.BOLD,20);
		chart.getTitle().setFont(font);
		chart.getLegend().setItemFont(font);
		
		CategoryPlot plot = (CategoryPlot) chart.getPlot();

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setLabelFont(font);

		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		renderer.setBaseLegendTextFont(font);
		
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());	//每条柱子上显示具体的数据
		renderer.setBaseItemLabelsVisible(true);
		
		GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,0.0f, new Color(0, 0, 64));
		GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,0.0f, new Color(0, 64, 0));
		GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,0.0f, new Color(64, 0, 0));
		
		renderer.setSeriesPaint(0, gp0);
		renderer.setSeriesPaint(1, gp1);
		renderer.setSeriesPaint(2, gp2);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(font);
		domainAxis.setTickLabelFont(font);
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
		
		return chart;
	}
	
	/**
	 * 生成柱状图---图片
	 * @author 胡礼波
	 * 2012-11-29 下午06:36:55
	 * @return
	 * @throws IOException 
	 */
	public static String createFile(JFreeChart chart, int width,int height,HttpSession session) throws IOException
	{
		String url=ServletUtilities.saveChartAsPNG(chart, width, height,null, session);
		url=session.getServletContext().getContextPath()+"/report.do?filename="+url;
		return url;
	}
}
