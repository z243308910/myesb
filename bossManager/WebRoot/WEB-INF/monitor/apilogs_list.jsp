<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>BOSS系统日志列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#apilogstable").datagrid({
				url:"monit/apiLogs",
				title:"BOSS系统日志列表",
				iconCls:'icon-tip',
				nowrap: false,
				striped: true,
				collapsible:false,
				remoteSort: false,
				idField:'id',
				pagination:true,
				rownumbers:true,
				pageList:[20,30,50],				
				toolbar:"#toolbar"
			});
		});
		
	function bindServerDir(newValue, oldValue)
	{
		$("#serverDir").combogrid({url:"soft/dir!getServiceBySoftId",queryParams:{"softId":newValue}});
	}
	
	function searchLogs()
	{
		var params = {"softId":$("#software").combogrid("getValue"),"serviceId":$("#serverDir").combogrid("getValue"),"effectDate":$("#effectDate").datetimebox("getValue"),"expireDate":$("#expireDate").datetimebox("getValue")};
		$("#apilogstable").datagrid({url:"monit/apiLogs!searchApiLogList",queryParams:params});
	}
	</script>
	</head>
	<body>
	<table id="apilogstable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" width="100px" checkbox="true" align="center">编号</th>
					<th field="softName" width="100px" align="center">软件名称</th>
					<th field="serviceCode" width="100px" align="center">服务接口名称</th>
					<th field="invokeIp" width="100px" align="center">调用方的IP</th>
					<th field="invokeTime" width="100px" align="center" sortable="true" formatter="Boss.util.formatLongToDate">调用时间</th>
					<th field="resCode" width="100px" align="center">接口响应状态码</th>
					<th field="op" width="100px" align="center" formatter="Boss.system.opViewApiLog">操作</th>
				</tr>
			</thead>
	</table>
	<div id="toolbar" style="padding:5px;height:auto">
		软件：<input id="software" class="easyui-combogrid" style="width:150px;" data-options="panelWidth:200,idField:'id',textField:'softName',url:'soft/soft',columns:[[{field:'id',title:'编号',width:80},{field:'softName',title:'软件名称',width:100}]],onChange:bindServerDir">	
		服务：<input id="serverDir" class="easyui-combogrid" style="width:150px;" data-options="panelWidth:300,idField:'id',textField:'serviceCode',url:'soft/dir!getServiceBySoftId',columns:[[{field:'id',title:'编号',width:60},{field:'serviceCode',title:'服务代号',width:100},{field:'serviceUrl',title:'服务地址',width:100}]]">	
		<label>日期：</label>
		<input id="effectDate" class="easyui-datebox" style="width:150px;" data-options="editable:false" />到
		<input id="expireDate" class="easyui-datebox" style="width:150px;" data-options="editable:false" />
		<a href="javascript:void(0)" onclick="searchLogs()" class="easyui-linkbutton" iconCls="icon-search">检索</a>
		<a href="javascript:void(0)" onclick="Boss.grid.delData('apilogstable','monit/apiLogs!delLogs')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
	</body>
</html>