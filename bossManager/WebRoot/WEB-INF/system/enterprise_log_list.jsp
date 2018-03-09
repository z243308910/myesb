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
		<title>系统日志列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
	<script>
$(function(){
			$("#enterpriselogtable").datagrid({
				url:"soft/elogs",
				title:"企业活动日志列表",
				iconCls:'icon-tip',
				nowrap: false,
				striped: true,
				collapsible:false,
				remoteSort: false,
				idField:'id',
				pagination:true,
				rownumbers:true,
				pageList:[20,30,50],				
				toolbar:[{
					id:'btnsave',
					text:'刷新',
					disabled:false,
					iconCls:'icon-reload',
					handler:function(){
						$("#enterpriselogtable").datagrid({url:"soft/elogs"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("enterpriselogtable","soft/elogs!delLogs");
					}
				}]
			});
		});
		
		function showOpname(value,rec){if(rec==null){return " ";}else{var name="";try{name=rec.adminUser.userName;}catch(e){}return name;}}
	</script>
	</head>
	<body>
	<table id="enterpriselogtable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox=true width="80px" align="center">编号</th>
					<th field="enterpriseName" width="80px" align="center">企业名称</th>
					<th field="opDateTime" width="80px" align="center" sortable="true" formatter="Boss.util.formatLongToDate">执行时间</th>
					<th field="operator" width="80px" align="center">操作者</th>
					<th field="content" width="80px" align="center">操作内容</th>
				</tr>
			</thead>
	</table>
	</body>
</html>