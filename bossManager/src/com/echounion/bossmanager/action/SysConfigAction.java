package com.echounion.bossmanager.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.echounion.bossmanager.common.constant.Constant;
import com.echounion.bossmanager.common.enums.LoginFlag;
import com.echounion.bossmanager.common.json.JsonUtil;
import com.echounion.bossmanager.common.security.Boss;
import com.echounion.bossmanager.common.security.annotation.ActionRightCtl;
import com.echounion.bossmanager.common.util.DateUtil;
import com.echounion.bossmanager.common.util.FileUtil;
import com.echounion.bossmanager.common.util.Zipper;
import com.echounion.bossmanager.common.util.Zipper.FileEntry;
import com.echounion.bossmanager.entity.SysConfig;
import com.echounion.bossmanager.entity.dto.BossProperty;
import com.echounion.bossmanager.entity.dto.DBTable;
import com.echounion.bossmanager.entity.dto.Record;
import com.echounion.bossmanager.service.ISysConfigService;

/**
 * 系统配置Action
 * @author 胡礼波
 * 2012-10-31 下午02:50:06
 */
@Controller("sysConfigAction")
@Scope("prototype")
public class SysConfigAction extends BaseAction {

	/**
	 * @author 胡礼波
	 * 2012-10-31 下午02:50:13
	 */
	private static final long serialVersionUID = -1012944963908667160L;
	private Logger logger=Logger.getLogger(SysConfigAction.class);
	private SysConfig config;

	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 默认转到系统配置列表 
	 * @author 胡礼波
	 * 2012-10-31 下午03:39:37
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String execute()
	{
//		List<SysConfig> list=sysConfigService.getSysConfig();
//		JSONObject jsonObject=JsonUtil.toJsonStringFilterPropter(list,super.getRows());
//		setJsonData(jsonObject);
		return getAllEmail();
	}
	
	/**
	 * 添加系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午04:29:34
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String addSysConfig()
	{
		int count=0;
		try {
			count = sysConfigService.addSysConfig(config);
		} catch (RuntimeException e) {
			logger.error("保存系统配置出错"+e);
		}
		writeData(count>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 删除系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:40:03
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delSysConfig()
	{
		String dataStr=getServletReqeust().getParameter("data");
		String[] datas=StringUtils.split(dataStr, ",");
		Integer ids[]=(Integer[])ConvertUtils.convert(datas, Integer.class);
		int count=sysConfigService.delSysConfig(ids);
		writeData(count);
		return null;
	}
	
	/**
	 * 编辑系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:40:22
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String editSysConfig()
	{
		int count=sysConfigService.editSysConfig(config);
		writeData(count>0?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 查看系统配置
	 * @author 胡礼波
	 * 2012-10-31 下午03:40:41
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getSysConfig()
	{
		int sysConfigId=(Integer)ConvertUtils.convert(getServletReqeust().getParameter("configId"),Integer.class);
		SysConfig config=sysConfigService.getSysConfigById(sysConfigId);
		getRequest().put("config",config);
		int type = config.getType();
		String str = null;
		if(type == 2){
			str = "view_config_shortmsg";
		}else if(type == 3){
			str = "view_config_email";
		}else if(type == 4){
			str = "view_config_ftp";
		}
		return str;
	}

	/**
	 * 查看所有邮件配置
	 *@author 张霖
	 * 2013-7-3 下午3:04:51
	 * @return 
	 */
	public String getAllEmail()
	{
		List<SysConfig> emailList=sysConfigService.getSysConfigByType(Constant.SYS_EMAIL);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(emailList,0);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 查看所有短信配置
	 *@author  张霖
	 * 2013-7-4 上午9:52:16
	 * @return
	 */
	public String getAllShortMsg()
	{
		List<SysConfig> shortmsgList=sysConfigService.getSysConfigByType(Constant.SYS_SHORTMSG);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(shortmsgList,0);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 查看所有ftp配置
	 *@author  张霖
	 * 2013-7-4 上午9:52:16
	 * @return
	 */
	public String getAllFTP()
	{
		List<SysConfig> ftpList=sysConfigService.getSysConfigByType(Constant.SYS_FTP);
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(ftpList,0);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 查看配置代码是否存在
	 *@author 张霖
	 * 2013-7-5 上午11:30:09
	 * @return
	 */
	public String checkConfigCodeExist()
	{
		boolean isExist = false;
		String configCode=getServletReqeust().getParameter("code");
		try
		{
			isExist=(sysConfigService.getSysConfig(configCode)) == null? false : true;
		}
		catch (Exception e) {
			logger.error("检查配置代码失败，"+e);
		}
		writeData(isExist);
		return null;
	}
	
	/**
	 * 系统初始化---数据库初始化
	 * @author 胡礼波
	 * 2012-11-15 上午11:09:02
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String initDataSet()
	{
		String tableName=getServletReqeust().getParameter("tbname");		//表名
		boolean flag=sysConfigService.initTableData(tableName);
		writeData(flag ? Constant.OP_SUCCESS : Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 获取当前数据库的所有表信息
	 * @author 胡礼波
	 * 2012-11-15 上午11:43:47
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String getDBTables()
	{
		List<DBTable> list=sysConfigService.getDSTables();
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(list,0);
		writeData(jsonData);
		return JSON;
	}
	
	/**
	 * 项目备份
	 * @author 胡礼波
	 * 2012-12-17 下午05:03:13
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String backProj()
	{
		List<FileEntry> list=new ArrayList<FileEntry>();
		String path=FileUtil.isWindows()?getServletReqeust().getSession().getServletContext().getRealPath("/"):"/home/web/wwwroot/";
		FileEntry fileEntry=new FileEntry(null,null,new File(path));
		list.add(fileEntry);
		path=getBackPath();
		FileUtil.createFile(path);
		File file=new File(path+"BOSSMgr_"+DateUtil.getCurrentDate()+".tar.gz");
		FileOutputStream out=null;
		boolean flag=false;
		try {
			out = new FileOutputStream(file);
			Zipper.zip(out,list);
			flag=true;
		} catch (FileNotFoundException e) {
			logger.error("压缩文件出错"+e);
			flag=false;
		}
		writeData(flag?Constant.OP_SUCCESS:Constant.OP_FAIL);
		return null;
	}
	
	/**
	 * 数据备份路径
	 * @author 胡礼波
	 * 2012-12-17 下午05:54:59
	 * @return
	 */
	private String getBackPath()
	{
		String path=FileUtil.isWindows()?System.getProperty("catalina.base")+File.separator+"back"+File.separator:"/mnt/back/";
		return path;
	}
	
	/**
	 * 获得备份的数据列表
	 * @author 胡礼波
	 * 2012-12-17 下午05:54:14
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String backList()
	{
		String path=getBackPath();
		File file=new File(path);
		JSONArray array=new JSONArray();
		if(file.isDirectory())
		{
			JSONObject jsonObj=null;
			int index=1;
			for (File subFile:file.listFiles()) {
				jsonObj=new JSONObject();
				jsonObj.put("id",index);
				jsonObj.put("name",subFile.getName());		//文件名称
				jsonObj.put("lastTime",subFile.lastModified());//最后修改时间
				jsonObj.put("size",subFile.length()/1024/1024+"/M");//文件大小
				jsonObj.put("filePath",subFile.getPath());
				array.add(jsonObj);
				index++;
			}
		}
		try
		{
			String url=BossProperty.getInstance().getBackUpList();			//获取BOSS的数据备份
			Record record=new Boss().requestBoss(url,null);
			if(record.getStatus()!=Record.SUCCESS)
			{
				logger.info("获取BOSS备份数据失败!");
			}else
			{
				JSONArray jsonBossArray=com.alibaba.fastjson.JSON.parseArray(record.getMessage());
				array.addAll(jsonBossArray);
			}
		}catch (Exception e) {
			logger.error("获取BOSS备份数据异常"+e);
		}
		JSONObject jsonData=JsonUtil.toJsonStringFilterPropter(array,200);
		setJsonData(jsonData);
		return JSON;
	}
	
	/**
	 * 删除备份数据
	 * @author 胡礼波
	 * 2012-12-17 下午06:16:06
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.YES)
	public String delBackData()
	{
		String path=getServletReqeust().getParameter("data");
		boolean flag=false;
		int count=0;
		if(!StringUtils.isEmpty(path))
		{
			String paths[]=path.split(",");
			List<String> list=new ArrayList<String>();
			for (String pa:paths) {
				if(pa.contains("BOSS_"))
				{
					list.add(pa);
					continue;
				}
				flag=FileUtil.delFile(pa);
				if(flag)
				{
					count++;
				}
			}
			count+=delBossBackData(list);
		}
		writeData(flag?count+"条数据成功删除!":Constant.OP_FAIL);
		return null;
	}

	/**
	 * 删除BOSS备份数据
	 * @author 胡礼波
	 * 2012-12-18 下午04:41:06
	 * @return
	 */
	public int delBossBackData(List<String> path)
	{
		int count=0;
		try
		{
			String url=BossProperty.getInstance().getDelBackUp();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("data",StringUtils.join(path,","));
			Record record=new Boss().requestBoss(url,map);
			if(record.getStatus()!=Record.SUCCESS)
			{
				logger.info("删除BOSS备份数据失败!");
				count=0;
			}else
			{
				count=(Integer)ConvertUtils.convert(record.getMessage(),Integer.class);
			}
		}catch (Exception e) {
			logger.error("删除BOSS备份数据异常"+e);
			count=0;
		}
		return count;
	}

	public void setConfig(SysConfig sysConfig) {
		this.config = sysConfig;
	}

	public SysConfig getConfig() {
		return config;
	}
}
