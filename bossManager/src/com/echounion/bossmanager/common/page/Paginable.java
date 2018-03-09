package com.echounion.bossmanager.common.page;

/**
 * 分页接口
 * @author 胡礼波
 * 10:13:15 AM Oct 17, 2012
 */
public interface Paginable {

	/**
	 * 总记录数
	 * @author 胡礼波
	 * 10:13:23 AM Oct 17, 2012 
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 总页数
	 * @author 胡礼波
	 * 10:13:27 AM Oct 17, 2012 
	 * @return
	 */
	public int getTotalPage();

	/**
	 * 每页记录数
	 * @author 胡礼波
	 * 10:13:40 AM Oct 17, 2012 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 当前页号
	 * @author 胡礼波
	 * 10:13:44 AM Oct 17, 2012 
	 * @return
	 */
	public int getPageNo();

	/**
	 *  是否第一页
	 * @author 胡礼波
	 * 10:13:49 AM Oct 17, 2012 
	 * @return
	 */
	public boolean isFirstPage();

	/**
	 * 是否最后一页
	 * @author 胡礼波
	 * 10:13:56 AM Oct 17, 2012 
	 * @return
	 */
	public boolean isLastPage();

	/**
	 * 返回下页的页号
	 * @author 胡礼波
	 * 10:14:00 AM Oct 17, 2012 
	 * @return
	 */
	public int getNextPage();

	/**
	 * 返回上页的页号
	 * @author 胡礼波
	 * 10:14:05 AM Oct 17, 2012 
	 * @return
	 */
	public int getPrePage();
}
