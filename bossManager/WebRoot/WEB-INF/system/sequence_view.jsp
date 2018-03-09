<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
	$(function()
	{
		Boss.form.initDialogForm("syssquencetable","dialogsysseqwin","sysSequenceForm","sys/sequence!editSequence");
	});
</script>
<div id="sysseqcontent" align="center">
		<form id="sysSequenceForm" method="post">
		<input type="hidden" name="sequence.id" value="${sequence.id}"/>
		<input type="hidden" name="sequence.seqDesc" value="${sequence.seqDesc}"/>
		<input type="hidden" name="sequence.version" value="${sequence.version}"/>
		<input type="hidden" name="sequence.createTime" value="${sequence.createTime}"/>
		<input type="hidden" name="sequence.seqCode" value="${sequence.seqCode}"/>
<table id="viewSysSequenceTable" border="0" cellpadding="0" cellspacing="5" width="100%" style="padding-left:10px;">
	<tr>
		<td>序列代号:</td><td>${sequence.seqCode}</td><td></td>
	</tr>
	<tr>
		<td>表名:</td><td><input type="text" name="sequence.tableName" value="${sequence.tableName}" class="easyui-validatebox" required="true" maxlength="20"/></td><td>系统唯一标识</td>
	</tr>
	<tr>
		<td>作用字段:</td><td colspan="5"><input type="text" name="sequence.fieldName" value="${sequence.fieldName}" class="easyui-validatebox" required="true" maxlength="20"/></td>
	</tr>
	<tr>
		<td>最小值:</td><td><input type="text" value="${sequence.minValue}" name="sequence.minValue" class="easyui-numberbox" data-options="min:1,max:999999999,required:true" maxlength="12"/></td><td></td>
	</tr>
	<tr>
		<td>最大值:</td><td><input type="text" value="${sequence.maxValue}" name="sequence.maxValue" class="easyui-numberbox" data-options="min:1,max:999999999,required:true" maxlength="12"/></td><td></td>
	</tr>	
	<tr>
		<td>描述:</td><td colspan="5"><textarea name="sequence.seqDesc" rows="5" cols="50">${sequence.seqDesc}</textarea> </td>
	</tr>
</table>
<div class="toolbar" align="center">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#sysSequenceForm').form('validate')){$('#sysSequenceForm').submit();}">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#dialogsysseqwin').dialog('close')">取消</a>
</div>
</form>
</div>