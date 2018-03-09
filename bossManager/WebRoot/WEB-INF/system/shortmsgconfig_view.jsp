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
						Boss.form.initDialogForm("shortmsgconfigtable","dialogshortmsgwin","shortmsgconfigForm","sys/config!editSysConfig");
					});
		</script>
					<form method="post" name="shortmsgconfigForm" id="shortmsgconfigForm">
						<input type="hidden"  name="config.type"value="${config.type }" />
						<input type="hidden"  name="config.configCode" value="${config.configCode}" />
						<input type="hidden"  name="config.id" value="${config.id }"/>
						<table id="shortmsgconfigtable" border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
							<tr>
								<td>短信代码:</td>
								<td>${config.configCode}</td>
								<td>系统唯一标识</td>
							</tr>
							<tr>
								<td>网关名称:</td>
								<td><input type="text"  validType="length[1,30]"   class="easyui-validatebox" required="true" name="config.configName" value="${config.configName}" /></td>
								<td></td>
							</tr>
							<tr>
								<td>网关地址:</td>
								<td><input type="text"  data-options="required:true,validType:'url'"    class="easyui-validatebox"  name="config.url" value="${config.url}" maxlength="300"/></td>
								<td></td>
							</tr>							
							<tr>
								<td>用户名:</td>
								<td><input type="text"  validType="length[1,30]"  class="easyui-validatebox" required="true" name="config.userName" value="${config.userName }" maxlength="30"/></td>
								<td></td>
							</tr>							
							<tr>
								<td>密&nbsp;&nbsp;码:</td>
								<td><input type="text"  validType="length[6,30]"   class="easyui-validatebox" required="true" name="config.password" value="${config.password }" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>配置描述:</td><td colspan="2"><textarea name="config.configDesc" rows="5" cols="50">${config.configDesc }</textarea> </td>
							</tr>	
							<tr>
								<td colspan="3"><br/>
									<div class="toolbar" align="center">
										<a href="javascript:void(0)" class="easyui-linkbutton" id="shortmsgsysconfigFormsavebtn" iconCls="icon-save" onclick="if($('#shortmsgconfigForm').form('validate')){$('#shortmsgconfigForm').submit();}">保存</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#dialogshortmsgwin').dialog('close')">取消</a>
									</div>								
								</td>
							</tr>
						</table>
					</form>
