<%@page import="com.echounion.bossmanager.common.enums.ProtocolType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.echounion.bossmanager.entity.EsbSoftWare"/>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.ProtocolType"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<script type="text/javascript">
	function submitSoftWare()
	{
		$.ajax(
		{
			url:"soft/soft!addSoftware",type:"post",data:$("#softwareForm").serialize(),
			success:function(msg){Boss.util.closeDialogWin();Boss.grid.reload("softwaretable");}
		});
	}
	</script>
	<form method="post" name="softwareForm" id="softwareForm" >
		<table style="padding:10px;" cellpadding="3">
			<tr>
				<td>软件代号:</td><td><input type="text"  validType="alphanum[1,50]"   name="software.softCode" size=50;  class="easyui-validatebox" required="true"  onblur="Boss.software.checkSoftwareCode(this)"/>&nbsp;<span class="tip" >*</span>必填</td>
			</tr>		
			<tr>
				<td>软件名称:</td><td><input type="text"  validType="unnormal[1,100]"  name="software.softName" size=50;  class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填</td>
			</tr>
			<tr>
				<td>软件类别:</td>
				<td>
					<select name="software.softType">
						<option value="0">---请选择---</option>
						<c:forEach items="<%=EsbSoftWare.typeList%>" var="item" varStatus="vs">
							<option value="${vs.count}">${item }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>访问端口:</td><td><input type="text" name="software.softPort"  min="0" size=50; maxlength="100"  class="easyui-numberbox"  required="true"/>&nbsp;<span class="tip">*</span>必填(请输入数字)</td>
			</tr>			
			<tr>
				<td>访问协议:</td>
				<td>
					<select name="software.softProtocol">
						<c:forEach items="<%=ProtocolType.values()%>" var="item">
							<option value="${item.id}">${item}</option>
						</c:forEach>
					</select>
				</td>
			</tr>		
			<tr>
				<td>访问地址:</td><td><input type="text" name="software.softUrl" size=50; maxlength="100"  class="easyui-validatebox" />&nbsp;<span class="tip"></td>
			</tr>
			<tr>
				<td>邮件通道:</td>
				<td>
					<select  name="software.mailChannel" >
						<option value="1000">默认通道</option>
						<option value="1001">cargoon</option>
						<option value="1002">sofreight</option>
						<option value="1003">BBS</option>	
						<option value="1004">PO</option>
					</select>
				</td>
			</tr>				
			<tr>
				<td>备注:</td><td><textarea rows="5" cols="50" name="software.remark"></textarea> </td>
			</tr>			
			<tr>
				<td colspan="2">
						<br/>
						<div class="toolbar" align="center">
							<a href="javascript:void(0)" class="easyui-linkbutton" id="softwareFormFormsavebtn" iconCls="icon-save" onclick="if($('#softwareForm').form('validate')){submitSoftWare();}">保存</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.closeDialogWin()">取消</a>
						</div>
				</td>
			</tr>
		</table>
</form>