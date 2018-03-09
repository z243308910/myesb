<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>服务器基本信息配置</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
		<script type="text/javascript">
			$(function(){
			Boss.form.initDialogForm("servertable","serverForm","soft/server!addServer");
			$("#unbindsoftwaretable").datagrid({title:"部署软件列表",iconCls:"icon-tip",idField:"id",pagination:true,rownumbers:true,toolbar:[
						{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.flush("unbindsoftwaretable","soft/soft!getSoftwareByIds?softIds="+$("#softIds").val());}},
						{id:"btndel",text:"移除",iconCls:"icon-remove",handler:function(){Boss.grid.removeSelected("unbindsoftwaretable");}},
						{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("未绑定软件列表",700,400,"softserver_unbindsoft_list");}}]});
			
			var ips=$("#serverips").val().split(",");	$("#ip").val(ips[0]);
			for(var i = 1 ; i<ips.length;i++){$("#ips").after("<tr id='subips'><td>负载服务器IP:</td><td ><input type='text'  validType='_ip' value='"+ips[i]+"' id='subip'  name='subip'  size=50;  class='easyui-validatebox' required='true'/><a id='del-btn' class='l-btn-text icon-remove' style='padding:8px;'></a></td></tr>");}
			$("#del-btn").live("click",function(){$(this).parent().parent().remove();});
			$("#subip").live("blur",function(){Boss.server.checkIPExist(this);});	});
			
			function addServerIPInput(){$("#ips").after("<tr id='subips'><td>负载服务器IP:</td><td ><input type='text'  validType='_ip' id='subip'  name='subip'  size=50;  class='easyui-validatebox' required='true'/>&nbsp;<a id='del-btn' class='l-btn-text icon-remove' style='padding:8px;'></a></td></tr>");	}
		</script>
</head>

<body>
<div id="serverdiv">
		<div class="panel-header">
			<div class="panel-title panel-with-icon" style="height:20px;line-height:20px;">服务器基本信息</div>
			<div class="panel-icon icon-tip"></div>
			<form method="post" name="serverForm" id="serverForm">
				<input type="hidden"  id="softIds" name="softIds" />
				<input type="hidden" id="serverips" name="server.serverIp">
				<table id="servertable" style="padding:10px;" cellpadding="5">
					<tr>
						<td>服务器名称:</td><td><input type="text"  name="server.serverName"  size=50; class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填</td>
					</tr>
					<tr id="ips">	
						<td>服务器IP:</td><td><input type="text"  validType="ip"  id="ip" size=50;  class="easyui-validatebox" required="true" onblur="Boss.server.checkIPExist(this)"/><a class="easyui-linkbutton"  plain="true" iconCls="icon-add"  onclick="addServerIPInput()"></a>&nbsp;<span class="tip">*</span>必填</td>
					</tr>
					<tr>
						<td colspan="4">
								<br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="emailsysconfigFormsavebtn" iconCls="icon-save" onclick="if($('#serverForm').form('validate')){Boss.software.submitServer();}">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.location.forward('softserver_server_list');">返回</a>
								</div>
						</td>
					</tr>
				</table>
			</form>	
	</div>

<br/>
	<table id="unbindsoftwaretable" fitColumns="true" fit="false">
			<thead>
				<tr>
						<th field="id" width="80px"  checkbox="true" align="center">编号</th>
						<th field="softCode" width="80px" align="center">软件代号</th>
						<th field="softName" width="80px" align="center">软件名称</th>
						<th field="softType" width="80px" align="center">软件类别</th>
						<th field="softPort" width="80px" align="center">访问端口</th>
						<th field="softProtocol" width="80px" align="center" formatter="Boss.software.changeProTypecharacterType">访问协议</th>
						<th field="softUrl" width="80px" align="center">访问地址</th>
						<th field="remark" align="center">备注</th>
						<th field="opp" width="80px" align="center" formatter="Boss.software.opSoftware">操作</th>
				</tr>
			</thead>	
	</table>
	</div>

</body>
</html>

