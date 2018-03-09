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
		<title>租用企业列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#softwaretable").datagrid(
				{url:"soft/soft",title:"接入方软件列表",iconCls:"icon-tip",nowrap: false,striped: true,collapsible:false,remoteSort: false,idField:"id",pagination:true,rownumbers:true,pageList:[20,30,50],
				toolbar:[
				{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("softwaretable");}},
				{id:"btnremove",text:"删除",iconCls:"icon-remove",handler:function(){
					Boss.grid.delData("softwaretable","soft/soft!delSoftware");
					}},
				{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.location.forward("softserver_soft_add");}}]
			});});
	</script>
	</head>
	<body>
		<table id="softwaretable" fitColumns="true" fit="true">
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
	</body>
</html>