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
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#syslogstable").datagrid({
				url:"sys/logs",
				title:"系统日志列表",
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
						$("#syslogstable").datagrid({url:"sys/logs"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("syslogstable","sys/logs!delLogs");
					}
				}]
			});
		});
		
	</script>
	</head>
	<body>
	<table id="syslogstable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="triggerName" checkbox=true width="80px" align="center">编号</th>
					<th field="modelName" width="80px" align="center">模块名称</th>
					<th field="methodName" width="80px" align="center">执行操作</th>
					<th field="opDateTime" width="80px" align="center" sortable="true" formatter="Boss.util.formatLongToDate">执行时间</th>
					<th field="remark" width="80px" align="center">备注</th>
					<th field="userName" width="80px" align="center">操作者</th>
				</tr>
			</thead>	
	</table>
	</body>
</html>