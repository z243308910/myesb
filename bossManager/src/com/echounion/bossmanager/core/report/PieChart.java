package com.echounion.bossmanager.core.report;

import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.general.PieDataset;

/**
 * 饼图
 * @author 胡礼波
 * 2012-11-30 下午05:08:47
 */
public class PieChart {

    public PieChart() {}

    
    public static JFreeChart createChart(String title,String xTitle,String yTitle,PieDataset dataSet) {
        JFreeChart chart = ChartFactory.createPieChart3D(title,dataSet,true,true,false);
        PiePlot plot = (PiePlot) chart.getPlot();
		
        Font font=new Font("宋体",Font.BOLD,20);
		chart.getTitle().setFont(font);
		chart.getLegend().setItemFont(font);
		
        plot.setSectionOutlinesVisible(false);
        plot.setNoDataMessage("No data available");
        plot.setLabelFont(font);

        DecimalFormat df=new DecimalFormat("0.00%");
        NumberFormat nf=NumberFormat.getInstance();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} {2}",nf,df));
        
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
