package com.echounion.bossmanager.entity.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 服务模块DTO
 * @author 胡礼波
 * 2013-3-4 下午01:43:05
 */
public class ServiceModuleDTO implements Serializable {

	/**
	 * @author 胡礼波
	 * 2013-2-20 下午01:52:06
	 */
	private static final long serialVersionUID = -252899955538692330L;
	
	private String name;		//模块名称
	private String url;			//模块地址
	private String remark;		//模块备注
	private String parentCode;	//父节点代号
	private String code;		//节点代号
	
	private List<ServiceModuleDTO> subModuleList;	//子模块
	private List<ParamsDTO> listParams;//参数
	
	public List<ParamsDTO> getListParams() {
		return listParams;
	}
	public void setListParams(List<ParamsDTO> listParams) {
		this.listParams = listParams;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<ServiceModuleDTO> getSubModuleList() {
		return subModuleList;
	}
	public void setSubModuleList(List<ServiceModuleDTO> subModuleList) {
		this.subModuleList = subModuleList;
	}
}
