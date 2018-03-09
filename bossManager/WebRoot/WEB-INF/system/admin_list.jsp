<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#sysadminstable").datagrid({
				url:"login!getAllAdmin",
				title:"系统管理员列表",
				iconCls:'icon-tip',
				nowrap: false,
				striped: true,
				collapsible:false,
				remoteSort: false,
				idField:'id',
				rownumbers:true,
				pageList:[20,30,50],				
				toolbar:[{
					id:'btnsave',
					text:'刷新',
					disabled:false,
					iconCls:'icon-reload',
					handler:function(){
						$("#syslogstable").datagrid({url:"login!getAllAdmin"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("sysadminstable","login!delAdmins");
					}
				},{
					id:'btndel',
					text:'新增',
					iconCls:'icon-remove',
					handler:function(){
						Boss.util.createDialogWin("添加系统管理员",500,400,"system_admin_add");
					}
				}]
			});
		});
		
	</script>
	</head>
	<body>
	<table id="sysadminstable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox=true width="80px" align="center">编号</th>				
					<th field="userName" width="80px" align="center">用户名</th>
					<th field="name" width="80px" align="center">姓名</th>
					<th field="email" width="80px" align="center">邮箱</th>
					<th field="telephone" width="80px" align="center">联系电话</th>
					<th field="op" width="80px" align="center" formatter="Boss.system.opAdmin">操作</th>
				</tr>
			</thead>
	</table>
	</body>
</html>