<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){Boss.form.initDialogForm("emailhistorytable","testmailForm","monit/mail!test");});
</script>
<div id="testbossmailcontent">
		<form method="post" name="testmailForm" id="testmailForm">
				<table style="padding:10px;" cellpadding="1">
					<tr>
						<td>邮件主题:</td>
						<td><input name="subject" type="text" size="50"/></td>
					</tr>
					<tr>
						<td>邮件地址:</td>
						<td><input type="text" name="receiver">(多个用逗号","隔开)</td>
					</tr>
					<tr>
						<td>邮件内容:</td>
						<td><textarea rows="5" cols="80" name="content">这是测试内容</textarea></td>
					</tr>
					<tr>
						<td colspan="2">
								<br/><br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="emailsysconfigFormsavebtn" iconCls="icon-save" onclick="$('#testmailForm').submit();">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
								</div>
						</td>
					</tr>					
				</table>
		</form>
</div>