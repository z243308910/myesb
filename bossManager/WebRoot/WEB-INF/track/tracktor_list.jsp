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
		<title>系统任务列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#tracktorconfigtable").datagrid({
				url:"monit/track!getTracktor",
				title:"采集器列表",
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
						$("#tracktorconfigtable").datagrid({url:"monit/track!getTracktor"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("tracktorconfigtable","sys/config!delSysConfig");
					}
				},{
					id:'btndel',
					text:'新增',
					iconCls:'icon-remove',
					handler:function(){
						var channelpanel=$('<div id="addtracktconfigwin" iconCls="icon-save" closable="true"  collapsible="false" minimizable="false" maximizable="true"></div>').insertAfter("#tracktorconfigtable");
						$("#addtracktconfigwin").window({title:"添加采集器配置",cache:false,shadow:false,height:300,width:500,modal:true,inline:true,href:"track_tracktor_add"});
					}
				}]
			});
		});
	</script>
	</head>
	<body>
		<table id="tracktorconfigtable" fitColumns="true" fit="true">
				<thead>
					<tr>
						<th field="id" width="80px"  checkbox="true" align="center"></th>
						<th field="configCode" width="80px" align="center">采集器代号</th>
						<th field="configName" width="80px" align="center">采集器名称</th>
						<th field="url" width="80px" align="center">采集器地址</th>
						<th field="configDesc" width="80px" align="center">采集器描述</th>
						<th field="op" width="80px" align="center" formatter="Boss.system.opTracktor">操作</th>						
					</tr>
				</thead>	
		</table>
	</body>
</html>