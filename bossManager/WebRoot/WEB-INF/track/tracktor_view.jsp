<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	Boss.form.initDialogForm("tracktorconfigtable","dialogtracktorwin","tracktorForm","monit/track!editTracktor");
</script>		
		
<div id="tracktorcontent" align="center">
		<form method="post" name="tracktorForm" id="tracktorForm">
				<input type="hidden" name="config.id"         value="${config.id}">
				<input type="hidden" name="config.type"       value="${config.type}">
				<input type="hidden" name="config.ip"         value="${config.ip}">
				<input type="hidden" name="config.userName"   value="${config.userName}">
				<input type="hidden" name="config.password"   value="${config.password}">
				<input type="hidden" name="config.creator"    value="${config.creator}">
				<input type="hidden" name="config.createDate" value="${config.createDate}">
				
				<table style="padding:10px;" cellpadding="3" border="0">
					<tr>
						<td>采集器代号:</td>
						<td><input type="text" name="config.configCode" value="${config.configCode }" class="easyui-validatebox" required="true"/></td>
						<td>&nbsp;<span class="tip">*</span>系统唯一标识</td>	
					</tr>
					<tr>
						<td>采集器名称:</td>
						<td><input type="text" name="config.configName" value="${config.configName}" class="easyui-validatebox" required="true"/></td>
						<td></td>
					</tr>	
					<tr>
						<td>采集器地址:</td>
						<td><input type="text" name="config.url" value="${config.url}" class="easyui-validatebox" required="true"/></td>
						<td></td>
					</tr>
					<tr>
						<td>采集器备注:</td>
						<td colspan="2">
							<textarea name="config.configDesc" rows="5" cols="50">${config.configDesc}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4">
								<br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="tracktorFormsavebtn" iconCls="icon-save" onclick="if($('#tracktorForm').form('validate')){$('#tracktorForm').submit();}">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#dialogtracktorwin').dialog('close')">取消</a>
								</div>
						</td>
					</tr>
			</table>
		</form>		
</div>