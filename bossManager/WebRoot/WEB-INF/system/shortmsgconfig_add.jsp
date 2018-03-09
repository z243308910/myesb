<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="com.echounion.bossmanager.common.constant.Constant"/>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
		<script type="text/javascript">
			$(function()
			{
				Boss.form.initDialogForm("shortmsgconfigtable","shortmsgsysconfigForm","sys/config!addSysConfig");
			});
		</script>
		
			<form method="post" name="shortmsgsysconfigForm" id="shortmsgsysconfigForm">
						<input type="hidden" value="<%=Constant.SYS_SHORTMSG%>" name="config.type"/>
						<table id="shortmsgcfgTable" border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
							<tr>
								<td>短信代码:</td>
									<td><input type="text"  validType="length[1,30]"  class="easyui-validatebox" required="true" name="config.configCode"  onblur="Boss.system.checkConfigCodeExist(this)"/></td>
								<td>(必填*)系统唯一标识</td>
							</tr>
							<tr>
								<td>网关名称:</td>
								<td><input type="text"  validType="length[1,30]"   class="easyui-validatebox" required="true" name="config.configName" /></td>
								<td></td>
							</tr>
							<tr>
								<td>网关地址:</td>
								<td><input type="text"  data-options="required:true,validType:'url'"   class="easyui-validatebox" name="config.url" maxlength="300"/></td>
								<td></td>
							</tr>							
							<tr>
								<td>用户名:</td>
								<td><input type="text"  validType="length[1,30]"  class="easyui-validatebox" required="true" name="config.userName" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>密&nbsp;&nbsp;码:</td>
								<td><input type="text"  validType="length[6,30]"  class="easyui-validatebox" required="true" name="config.password"/></td>
								<td></td>
							</tr>
							<tr>
								<td>配置描述:</td><td colspan="2"><textarea name="config.configDesc" rows="5" cols="50"></textarea> </td>
							</tr>
							<tr>
								<td colspan="3"><br/>
									<div class="toolbar" align="center">
										<a href="javascript:void(0)" class="easyui-linkbutton" id="shortmsgsysconfigFormsavebtn" iconCls="icon-save" onclick="if($('#shortmsgsysconfigForm').form('validate')){$('#shortmsgsysconfigForm').submit();}">保存</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
									</div>
								</td>
							</tr>								
						</table>
					</form>
