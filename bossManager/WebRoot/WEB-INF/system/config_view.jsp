<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page import="com.echounion.bossmanager.common.constant.Constant"/>
		<script type="text/javascript">
				$(function()
					{
						Boss.form.initDialogForm("emailconfigtable","emailsysconfigForm","sys/config!editSysConfig");
					});
		</script>
					<form method="post" name="emailsysconfigForm" id="emailsysconfigForm">
						<input type="hidden"  name="config.type"value="${config.type }" />
						<input type="hidden"  name="config.configCode" value="${config.configCode}" />
						<input type="hidden"  name="config.id" value="${config.id }"/>
						<table id="addemailconfigtable" border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
							<tr>
								<td>邮件代码:</td>
								<td>${config.configCode}</td>
								<td>(必填*)系统唯一标识</td>
							</tr>
							<tr>
								<td>邮件名称:</td>
								<td><input type="text"  required="true"  class="easyui-validatebox" required="true" name="config.configName" value="${config.configName}" /></td>
								<td></td>
							</tr>
							<tr>
								<td>邮件地址:</td>
								<td><input type="text"  required="true"   class="easyui-validatebox"  name="config.ip" value="${config.ip}" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>SMTP地址:</td>
								<td><input type="text"    required="true"  class="easyui-validatebox"  name="config.url" value="${config.url}" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>端口:</td>
								<td><input type="text"  min="0" class="easyui-numberbox" required="true" name="config.port" value="${config.port }" maxlength="11"/></td>
								<td>*请输入数字</td>
							</tr>
							<tr>
								<td>邮件通道:</td>
								<td>
									<select  name="config.channel" >
										<option value="1000">默认通道</option>
										<option value="1001" <c:if test="${config.channel eq 1001}">selected="selected"</c:if> >cargoon</option>
										<option value="1002" <c:if test="${config.channel eq 1002}">selected="selected"</c:if> >sofreight</option>
										<option value="1003" <c:if test="${config.channel eq 1003}">selected="selected"</c:if> >BBS</option>	
										<option value="1004" <c:if test="${config.channel eq 1004}">selected="selected"</c:if> >PO</option>
										<option value="1005" <c:if test="${config.channel eq 1005}">selected="selected"</c:if> >BC</option>
										<option value="1006" <c:if test="${config.channel eq 1006}">selected="selected"</c:if> >24Wiz</option>
									</select>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td>用户名:</td>
								<td><input type="text"   class="easyui-validatebox" required="true" name="config.userName" value="${config.userName }" /></td>
								<td></td>
							</tr>							
							<tr>
								<td>密&nbsp;&nbsp;码:</td>
								<td><input type="text"  class="easyui-validatebox" required="true" name="config.password" value="${config.password }" /></td>
								<td></td>
							</tr>							
							<tr>
							</tr>	
							<tr>
								<td colspan="3"><br/>
									<div class="toolbar" align="center">
										<a href="javascript:void(0)" class="easyui-linkbutton" id="emailsysconfigFormsavebtn" iconCls="icon-save" onclick="if($('#emailsysconfigForm').form('validate')){$('#emailsysconfigForm').submit();}">保存</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
									</div>								
								</td>
							</tr>
						</table>
					</form>
