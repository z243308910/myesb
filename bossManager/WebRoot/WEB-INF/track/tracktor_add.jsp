<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.echounion.bossmanager.common.constant.Constant"/>
<br/><br/>
<form id="trackConfirForm" method="post">
<input type="hidden" name="config.type" value="<%=Constant.SYS_TRACKING%>"/>
<table id="addTrackConfigTable" border="0" cellpadding="0" cellspacing="0" width="100%" style="padding-left:10px;">
	<tr>
		<td>采集器代号:</td>
		<td><input type="text" name="config.configCode" class="easyui-validatebox" required="true" onblur="Boss.system.checkTracktorExist(this)"/></td>
		<td>&nbsp;<span class="tip">*</span>系统唯一标识</td>	
	</tr>
	<tr>
		<td>采集器名称:</td>
		<td><input type="text" name="config.configName" class="easyui-validatebox" required="true"/></td>
		<td></td>
	</tr>	
	<tr>
		<td>采集器地址:</td>
		<td><input type="text" name="config.url" class="easyui-validatebox" required="true"/></td>
		<td></td>
	</tr>
	<tr>
		<td>采集器备注:</td>
		<td colspan="2">
			<textarea name="config.configDesc" rows="5" cols="50"></textarea>
		</td>
	</tr>
</table>
<div class="toolbar" align="center">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#trackConfirForm').form('validate')){$('#trackConfirForm').submit();}">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#addtracktconfigwin').dialog('close')">取消</a>
</div>
</form>
<script type="text/javascript">
	$(function()
		{
			Boss.form.initDialogForm("tracktorconfigtable","addtracktconfigwin","trackConfirForm","sys/config!addSysConfig");
		});
</script>