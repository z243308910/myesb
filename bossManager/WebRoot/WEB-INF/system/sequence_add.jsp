<br/><br/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="sysSequenceForm" method="post">
<table id="addSysSequenceTable" border="0" cellpadding="0" cellspacing="5" width="100%" style="padding-left:10px;">
	<tr>
		<td>序列代号:</td><td><input type="text" name="sequence.seqCode" class="easyui-validatebox" required="true" maxlength="20" onblur="Boss.system.checkSeqExist(this)" /></td><td>(必填<span class="tip">*</span>)系统唯一标识</td>
	</tr>
	<tr>
		<td>表名:</td><td><input type="text" name="sequence.tableName" class="easyui-validatebox" required="true" maxlength="20"/></td><td>系统唯一标识</td>
	</tr>
	<tr>
		<td>作用字段:</td><td colspan="5"><input type="text" name="sequence.fieldName" class="easyui-validatebox" required="true" maxlength="20"/></td>
	</tr>
	<tr>
		<td>最小值:</td><td><input type="text" value="1" name="sequence.minValue" class="easyui-numberbox" data-options="min:1,max:999999999,required:true" maxlength="12"/></td><td></td>
	</tr>
	<tr>
		<td>最大值:</td><td><input type="text" value="999999999" name="sequence.maxValue" class="easyui-numberbox" data-options="min:1,max:999999999,required:true" maxlength="12"/></td><td></td>
	</tr>	
	<tr>
		<td>描述:</td><td colspan="5"><textarea name="sequence.seqDesc" rows="5" cols="50"></textarea> </td>
	</tr>
</table>
<div class="toolbar" align="center">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#sysSequenceForm').form('validate')){$('#sysSequenceForm').submit();}">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="$('#addsyssequencewin').dialog('close')">取消</a>
</div>
</form>
<script type="text/javascript">
	$(function()
	{
		Boss.form.initDialogForm("syssquencetable","addsyssequencewin","sysSequenceForm","sys/sequence!addSequence");
	});
</script>