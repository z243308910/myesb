<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>服务接入方服务器列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
	<script>
	$(function(){
			$("#servertable").datagrid({
				url:"soft/server",title:"服务接入方服务器列表",
				iconCls:"icon-tip",nowrap: false,striped: true,collapsible:false,remoteSort: false,idField:"id",
				pagination:true,rownumbers:true,pageList:[20,30,50],				
				toolbar:[
					{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("servertable");}},
					{id:"btndel",text:"移除",iconCls:"icon-remove",handler:function(){Boss.grid.delData("servertable","soft/server!delServer");}},
					{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.location.forward("softserver_server_add");}}
				]
			});
		});
	</script>
	</head>
	<body>
		<table id="servertable" fitColumns="true" fit="true">
				<thead>
					<tr>
						<th field="id" width="80px"  checkbox="true" align="center">编号</th>
						<th field="serverName" width="80px" align="center">服务器名称</th>
						<th field="serverIp" width="80px" align="center">服务器IP</th>
						<th field="creator" width="80px" align="center">创建者</th>
						<th field="createDate" width="80px" align="center" formatter="Boss.util.formatLongToDate">创建时间</th>
						<th field="opp" width="80px" align="center" formatter="Boss.server.opViewServer">操作</th>						
					</tr>
				</thead>	
		</table>
	</body>
</html>