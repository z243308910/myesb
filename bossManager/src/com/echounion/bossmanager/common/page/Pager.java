package com.echounion.bossmanager.common.page;

import java.io.Serializable;

/**
 * 简单分页类
 * @author 胡礼波
 * 10:12:00 AM Oct 17, 2012
 */
public class Pager implements Paginable,Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int DEF_COUNT = 20;
	private static ThreadLocal<Pager> pagerContext=new ThreadLocal<Pager>();
	
	/**
	 * 把分页对象放到threadLocal中
	 * @author 胡礼波
	 * 10:12:07 AM Oct 17, 2012 
	 * @param pager
	 */
	public static void setPager(Pager pager)
	{
		pagerContext.set(pager);
	}
	
	/**
	 * 得到分页对象
	 * @author 胡礼波
	 * 10:12:11 AM Oct 17, 2012 
	 * @return
	 */
	public static Pager getPager()
	{
		return pagerContext.get();
	}
	
	/**
	 * 移除threadLocal中的分页对象
	 * @author 胡礼波
	 * 10:12:16 AM Oct 17, 2012
	 */
	public static void remove()
	{
			pagerContext.remove();
	}
	
	/**
	 * 检查页码 checkPageNo
	 * @author 胡礼波
	 * 10:12:22 AM Oct 17, 2012 
	 * @param pageNo
	 * @return
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public Pager() {}

	/**
	 * 构造器
	 * @author 胡礼波
	 * 10:12:27 AM Oct 17, 2012 
	 * @param pageNo
	 * @param pageSize
	 * @param totalCount
	 */
	public Pager(int pageNo, int pageSize, int totalCount) {
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	/**
	 * 调整页码，使不超过最大页数
	 * @author 胡礼波
	 * 10:12:32 AM Oct 17, 2012
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int tp = getTotalPage();
		if (pageNo > tp) {
			pageNo = tp;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;


	/**
	 * if totalCount<0 then totalCount=0
	 * @author 胡礼波
	 * 10:12:44 AM Oct 17, 2012 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}
	}

	/**
	 * if pageSize< 1 then pageSize=DEF_COUNT
	 * @author 胡礼波
	 * 10:12:51 AM Oct 17, 2012 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
	}

	/**
	 * if pageNo < 1 then pageNo=1
	 * @author 胡礼波
	 * 10:12:56 AM Oct 17, 2012 
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		if (pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
	}
}
