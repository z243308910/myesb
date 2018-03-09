<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){Boss.form.initDialogForm("rtxtable","testrtxForm","monit/rtx!test");});
</script>
<div id="testbossrtxcontent">
		<form method="post" name="testrtxForm" id="testrtxForm">
				<input type="hidden" name="rtxHistory.btype" value="test"/>
				<input type="hidden" name="rtxHistory.bcode" value="test"/>
				<table style="padding:10px;" cellpadding="1">
					<tr>
						<td>接收人:</td>
						<td><input name="rtxHistory.receiver" type="text" size="50"/></td>
					</tr>
					<tr>
						<td>标题:</td>
						<td><input type="text" name="rtxHistory.title"></td>
					</tr>
					<tr>
						<td>内容:</td>
						<td><textarea rows="5" cols="80" name="rtxHistory.content">这是RTX测试内容</textarea></td>
					</tr>
					<tr>
						<td colspan="2">
								<br/><br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="emailsysconfigFormsavebtn" iconCls="icon-save" onclick="$('#testrtxForm').submit();">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="document.getElementById('testrtxForm').reset();">取消</a>
								</div>
						</td>
					</tr>					
				</table>
		</form>
</div>