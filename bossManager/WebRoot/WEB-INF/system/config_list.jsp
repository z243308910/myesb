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
			$("#emailconfigtable").datagrid({
				url:"sys/config",
				title:"",nowrap: false,striped: true,collapsible:false,remoteSort: false,
				idField:"id",pagination:true,rownumbers:true,pageList:[20,30,50],				
				toolbar:[
				   {id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){$("#emailconfigtable").datagrid({url:"sys/config"});}},
				   {id:"btndel",text:"删除",iconCls:"icon-remove",handler:function(){Boss.grid.delData("emailconfigtable","sys/config!delSysConfig");}},
				   {id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("添加邮件配置",450,400,"system_config_add");}}
				]
			});
		});
		
	</script>
	</head>
	<body>
		<div class="easyui-tabs" data-options="fit:true,plain:true">
				<div title="邮件配置" style="padding:10px;">
					<table id="emailconfigtable" fitColumns="true" fit="true">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">id</th>
									<th field="configCode" width="80px"  align="center">配置代码</th>
									<th field="configName" width="80px" align="center">配置名称</th>
									<th field="ip" width="80px" align="center">IP</th>
									<th field="url" width="80px" align="center">访问地址</th>
									<th field="port" width="80px" align="center">端口</th>
									<th field="channel" width="80px" align="center" formatter="Boss.system.changeChannelCharacterType">通道</th>
									<th field="userName" width="80px" align="center">用户名</th>					
									<th field="password" width="80px" align="center">密码</th>
									<th field="op" width="80px" align="center" formatter="Boss.system.opEmail">操作</th>
								</tr>
							</thead>	
					</table>
			</div>
		</div>
	</body>
</html>