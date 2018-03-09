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
		<title>JVM性能数据列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#jvmargstable").datagrid({
				url:"monit/jvm",
				title:"JVM性能数据列表",
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
						$("#jvmargstable").datagrid({url:"monit/jvm"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("jvmargstable","monit/jvm!delJvmArgs");
					}
				}],
				onDblClickRow:function(index,row)
				{
					self.location.href="monitor_jvm_view?jvmId="+row.id;
				}
			});
		});
	</script>
	</head>
	<body>
	<table id="jvmargstable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox="true" align="center">编号</th>
					<th field="appName"  align="center">监控应用</th>					
					<th field="heapMUse" width="80px" align="center">堆内存已使用大小(字节)</th>
					<th field="noHeapMUse" width="80px" align="center">非堆内存已使用大小(字节)</th>																				
					<th field="loadClassCount" width="80px" align="center">加载到JVM中类总数</th>
					<th field="totalLoadClassCount"  align="center">总共加载类总数</th>
					<th field="unLoadClassCount"  align="center">已经卸载的类的总数</th>
					<th field="threadCount"  align="center">当前活Thread的数目</th>
					<th field="upTime"  align="center">JVM正常运行时间(毫秒)</th>
					<th field="monitDate"  align="center" formatter="Boss.util.formatLongToDate">监控时间</th>
				</tr>
			</thead>
	</table>
	</body>
</html>