<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<script>
	$(function(){
			Boss.form.initDialogForm("unbindsoftwaretable","serverForm","soft/server!editServer");
			$("#unbindsoftwaretable").datagrid({
				url:"soft/soft!getSoftwareByServerId?svId=${server.id}",title:"已部署软件列表",iconCls:"icon-tip",nowrap: false,striped: true,collapsible:false,remoteSort: false,idField:"id",pagination:false,rownumbers:true,
				toolbar:[
				{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("unbindsoftwaretable");}},
				{id:"btnremove",text:"删除",iconCls:"icon-remove",handler:function(){Boss.grid.delData("unbindsoftwaretable","soft/soft!delSoftware");}},
				{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("未绑定软件列表",700,400,"softserver_unbindsoft_list");}}
				]
			});
			var ips=$("#serverips").val().split(",");	$("#ip").val(ips[0]);
			for(var i = 1 ; i<ips.length;i++){$("#ips").after("<tr id='subips'><td>负载服务器IP:</td><td ><input type='text'  validType='length[1,50]' value='"+ips[i]+"' id='subip'  name='subip'  size=50;  class='easyui-validatebox' required='true'/><a id='del-btn' class='l-btn-text icon-remove' style='padding:8px;'></a></td></tr>");	}
			$("#del-btn").live("click",function(){$(this).parent().parent().remove();});
			$("#subip").live("blur",function(){Boss.server.checkIPExist(this);});	
		});
		function addServerIPInput(){$("#ips").after("<tr id='subips'><td>负载服务器IP:</td><td ><input type='text'  validType='length[1,50]' id='subip'  name='subip'  size=50;  class='easyui-validatebox' required='true'/>&nbsp;<a id='del-btn' class='l-btn-text icon-remove' style='padding:8px;'></a></td></tr>");	}
	</script>
		</head>
	<body>
<div id="servercontent">
		<div class="panel-header">
			<div class="panel-title panel-with-icon" style="height:20px;line-height:20px;">服务器基本信息</div>
			<div class="panel-icon icon-tip"></div>
		<form method="post" name="serverForm" id="serverForm">
				<input type="hidden" value="${server.id }" name="server.id"/>
				<input type="hidden" value="${server.serverIp}" name="server.serverIp" id="serverips">
				<input type="hidden" value="${server.creator}" name="server.creator"/>
				<input type="hidden" value="${server.createDate}" name="server.createDate"/>
				<input type="hidden" name="softIds" id="softIds"/>
				<table style="padding:10px;" cellpadding="5">
					<tr>
						<td>服务器名称:</td><td><input type="text"  name="server.serverName"  value="${server.serverName }" size=50; class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填</td>
					</tr>
					<tr id="ips">	
						<td>服务器IP:</td><td><input type="text"  validType="ip"   id="ip"  size=50; class="easyui-validatebox" required="true" /><a class="easyui-linkbutton"  plain="true" iconCls="icon-add"  onclick="addServerIPInput()"></a>&nbsp;<span class="tip">*</span>必填</td>
					</tr>
					<tr>
						<td>创建人:</td><td>${server.creator}</td>
					</tr>
					<tr>
						<td>创建时间:</td><td><fmt:formatDate value="${server.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
					<tr>
						<td colspan="4">
								<br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="emailsysconfigFormsavebtn" iconCls="icon-save" onclick="if($('#serverForm').form('validate')){Boss.software.submitServer();}">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.historyWin()">返回</a>
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
						<th field="softCode" width="50px" align="center">软件代号</th>
						<th field="softName" width="50px" align="center">软件名称</th>
						<th field="softPort" width="50px" align="center">访问端口</th>
						<th field="softProtocol" width="50px" align="center" formatter="Boss.software.changeProTypecharacterType">访问协议</th>
						<th field="softUrl" width="120px" align="center">访问地址</th>
						<th field="authKey" width="120px" align="center">授权码</th>
						<th field="remark" align="center">备注</th>
						<th field="opp" width="80px" align="center" formatter="Boss.software.opSoftware">操作</th>
				</tr>
			</thead>	
	</table>
</div>		
	</body>
</html>