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
		<title>数据采集日志列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#trackinghistorytable").datagrid({
				url:"monit/track",
				title:"数据采集日志列表",
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
						$("#trackinghistorytable").datagrid({url:"monit/track"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("trackinghistorytable","monit/track!delTracking");
					}
				}]
			});
		});
	</script>
	</head>
	<body>
	<table id="trackinghistorytable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox="true" align="center">编号</th>
					<th field="sourceCode" width="80px" align="center">数据来源</th>
					<th field="softName" width="80px" align="center">软件编号</th>
					<th field="enterpriseName" width="80px" align="center">企业编号</th>
					<th field="statusName" width="80px" align="center">采集状态</th>
					<th field="typeName" width="80px" align="center">结果类型</th>																				
					<th field="time" width="80px" align="center" formatter="Boss.util.formatLongToDate">采集时间</th>
					<th field="url"  align="center">请求地址</th>					
				</tr>
			</thead>	
	</table>
	</body>
</html>