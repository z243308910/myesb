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
				Boss.form.initDialogForm("emailconfigtable","emailsysconfigForm","sys/config!addSysConfig");
			});
		</script>
		
			<form method="post" name="emailsysconfigForm" id="emailsysconfigForm">
						<input type="hidden" value="<%=Constant.SYS_EMAIL%>" name="config.type"/>
						<table id="emailcfgyable" border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
							<tr>
								<td>邮件代码:</td>
								<td><input type="text"  class="easyui-validatebox" required="true" name="config.configCode" onblur="Boss.system.checkConfigCodeExist(this)"/></td>
								<td>(必填*)系统唯一标识</td>
							</tr>
							<tr>
								<td>邮件名称:</td>
								<td><input type="text"  required="true"   class="easyui-validatebox" required="true" name="config.configName" /></td>
								<td></td>
							</tr>
							<tr>
								<td>邮件地址:</td>
								<td><input type="text"  required="true"  class="easyui-validatebox"  name="config.ip" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>SMTP地址:</td>
								<td><input type="text"   required="true"  class="easyui-validatebox"  name="config.url" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>端口:</td>
								<td><input type="text"  min="0" class="easyui-numberbox" required="true" name="config.port"  maxlength="11"/></td>
								<td>*请输入数字</td>
							</tr>
							<tr>
								<td>邮件通道:</td>
								<td>
									<select name="config.channel" >
										<option value="1000">默认通道</option>
										<option value="1001">cargoon</option>
										<option value="1002">sofreight</option>
										<option value="1003">BBS</option>
										<option value="1004">PO</option>
										<option value="1005">BC</option>
										<option value="1006">24Wiz</option>
									</select>
								</td>
								<td></td>
							</tr>
							<tr>
								<td>用户名:</td>
								<td><input type="text"   class="easyui-validatebox" required="true" name="config.userName" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>密&nbsp;&nbsp;码:</td>
								<td><input type="text"  class="easyui-validatebox" required="true" name="config.password" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>配置描述:</td><td colspan="2"><textarea name="config.configDesc" rows="5" cols="50"  class="easyui-validatebox"  validType="length[0,500]" ></textarea> </td>
							</tr>	
							<tr>
								<td colspan="3"><br/>
									<div class="toolbar" align="center">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#emailsysconfigForm').form('validate')){$('#emailsysconfigForm').submit();}">保存</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
									</div>								
								</td>
							</tr>
						</table>
					</form>
