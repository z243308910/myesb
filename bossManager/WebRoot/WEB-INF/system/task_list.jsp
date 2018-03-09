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
		<title>系统任务列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#systasktable").datagrid({
				url:"sys/task",
				title:"系统任务列表",
				iconCls:'icon-tip',
				nowrap: false,
				striped: true,
				collapsible:false,
				remoteSort: false,
				idField:'triggerName',
				pagination:true,
				singleSelect:true,
				rownumbers:true,
				pageList:[20,30,50],				
				toolbar:[{
					id:'btnsave',
					text:'刷新',
					disabled:false,
					iconCls:'icon-reload',
					handler:function(){
						$("#systasktable").datagrid({url:"sys/task"});
					}
				},{
					id:'btnsave',
					text:'暂停',
					disabled:false,
					iconCls:'icon-reload',
					handler:function(){
						Boss.system.opTrigger("sys/task!editTaskPaused");
					}
				},{
					id:'btnsave',
					text:'恢复',
					disabled:false,
					iconCls:'icon-reload',
					handler:function(){
						Boss.system.opTrigger("sys/task!editTaskResume");
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.system.opTrigger("sys/task!delTask");
					}
				},{
					id:'btndel',
					text:'新增',
					iconCls:'icon-remove',
					handler:function(){
						self.location.href="system_task_add";
					}
				}]
			});
		});
	</script>
	</head>
	<body>
	<table id="systasktable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox=true width="80px" align="center">编号</th>
					<th field="triggerName" width="80px" align="center">Trigger 名称</th>
					<th field="triggerGroup" width="80px" align="center">Trigger 分组</th>
					<th field="prevFireTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">上次执行时间</th>					
					<th field="nextFireTime" width="80px" align="center" sortable="true" formatter="Boss.util.formatLongToDate">下次执行时间	</th>
					<th field="priority" width="80px" align="center">优先级</th>
					<th field="triggerState" width="80px" align="center">Trigger 状态</th>
					<th field="triggerType" width="80px" align="center">Trigger 类型</th>					
					<th field="startTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">开始时间	</th>
					<th field="endTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">结束时间</th>
				</tr>
			</thead>	
	</table>
	</body>
</html>