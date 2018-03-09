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
			$("#csfiletable").datagrid({
				url:"monit/csFileLogs",
				title:"Cargosmart文件操作日志",
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
		
	function searchLogs()
	{
		var params = {"fileName":$("#fileName").val(),"effectDate":$("#effectDate").datetimebox("getValue"),"expireDate":$("#expireDate").datetimebox("getValue")};
		$("#csfiletable").datagrid({url:"monit/csFileLogs!searchCsFileLogList",queryParams:params});
	}
	</script>
	</head>
	<body>
	<table id="csfiletable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" width="100px" checkbox="true" align="center">编号</th>
					<th field="fileName" width="100px" align="center">文件名称</th>
					<th field="opTime" width="100px" align="center" sortable="true" formatter="Boss.util.formatLongToDate">操作时间</th>
					<th field="remark" width="100px" align="center">备注</th>
				</tr>
			</thead>
	</table>
	<div id="toolbar" style="padding:5px;height:auto">
		文件名：<input id="fileName" tyle="width:150px;" class="easyui-validatebox" maxlength="50">	
		<label>日期：</label>
		<input id="effectDate" class="easyui-datebox" style="width:150px;" data-options="editable:false" />到
		<input id="expireDate" class="easyui-datebox" style="width:150px;" data-options="editable:false" />
		<a href="javascript:void(0)" onclick="searchLogs()" class="easyui-linkbutton" iconCls="icon-search">检索</a>
		<a href="javascript:void(0)" onclick="Boss.grid.delData('csfiletable','monit/csFileLogs!delLogs')" class="easyui-linkbutton" iconCls="icon-remove">删除</a>
	</div>
	</body>
</html>