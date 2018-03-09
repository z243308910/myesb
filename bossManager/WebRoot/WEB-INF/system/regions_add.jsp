<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="addregionsform"  method="post">
<br/>
	<table  border="0" cellpadding="5" cellspacing="0">
			<tbody>
			<tr>
				<td ><div align="right">父&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：</div></td>
				<td >
					<div>
						<input type="hidden" name="regions.parentCode" value="${param.parentId }" />${param.parentId}
					</div>
				</td>
			</tr>
			<tr>
				<td><div align="right">邮编/区域代码：</div></td>
				<td><div align="left"><input type="text" name="regions.code" maxlength="50" class="easyui-validatebox" required="true"><font color="#FF0000">*</font></div></td>
			</tr>
			<tr>
				<td><div align="right">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称：</div></td>
				<td><div align="left"><input type="text" name="regions.name" maxlength="50" class="easyui-validatebox" required="true"><font color="#FF0000">*</font></div></td>
			</tr>
		</tbody>
	</table>
	<br/>
	<div class="toolbar" align="center">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="if($('#addregionsform').form('validate')){$('#addregionsform').submit();}">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="document.getElementById('addregionsform').reset();">取消</a>
	</div>
</form>
<script type="text/javascript">
$(function()
{
		Boss.form.initDialogForm("regionstable","addregionswin","addregionsform","sys/region!addRegion");
});
</script>