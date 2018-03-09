<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(function()
	{
		Boss.form.initDialogForm("sysadminstable","adminForm","sys/admin!editAdmin");
	});
</script>
<div id="adminFormdiv" align="center">
<form method="post" name="adminForm" id="adminForm">
	<input type="hidden" name="adminUser.id" value="${adminUser.id }"/>
	<input type="hidden" name="adminUser.password" value="${adminUser.password }"/>
	<input type="hidden" name="adminUser.userName" value="${adminUser.userName }"/>
	<table id="addadminTable" border="0" cellpadding="5" cellspacing="5" width="100%" style="padding-left:10px;">
		<tr>
			<td>用户名:</td>
			<td>${adminUser.userName }</td>
			<td></td>
		</tr>
		<tr>
			<td>密码:</td>
			<td><input type="text"  validType="password[1,30]"  class="easyui-validatebox"  name="password" /><span>若修改密码,请输入新密码</span></td>
			<td></td>
		</tr>
		<tr>
			<td>姓名:</td>
			<td><input type="text" validType="name[1,30]"  class="easyui-validatebox" required="true" name="adminUser.name" value="${adminUser.name }" /></td>
			<td></td>
		</tr>							
		<tr>
			<td>电子邮件:</td>
			<td><input type="text"  required="true"  validType='email[1,50]'"  class="easyui-validatebox"  name="adminUser.email" value="${adminUser.email }" maxlength="50"/></td>
			<td></td>
		</tr>
		<tr>
			<td>联系电话:</td>
			<td><input type="text"  validType="length[1,20]"  min="0"  class="easyui-numberbox" required="true" name="adminUser.telephone" value="${adminUser.telephone }" /></td>
			<td></td>
		</tr>							
	</table>
		<div class="toolbar" align="center">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#adminForm').form('validate')){$('#adminForm').submit();}">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
		</div>			
</form>
</div>