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
					$("#servicesdirtable").treegrid({
						url:"soft/dir!getServiceDirList",title:"软件服务目录",
						iconCls:"icon-tip",nowrap: false,striped:true,collapsible:false,remoteSort:false,
						pagination:true,rownumbers:true,pageList:[20,30,50],
						idField: "id",treeField:"serviceName",
						toolbar:[
							{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("servicesdirtable");}},
							{id:"btnremove",text:"移除",iconCls:"icon-remove",handler:function(){var row = $("#servicesdirtable").treegrid("getSelected");if(row != null) {var level = $("#servicesdirtable").treegrid("getLevel",row.id);var data=[];if(level == 1){data[0] = row.id-100;Boss.message.delDataMessageShow("servicesdirtable","soft/soft!delSoftware",data);}else{data[0] = row.id;Boss.message.delDataMessageShow1("servicesdirtable","soft/dir!delServiceDir",data);};}else{Boss.grid.isSelected("servicesdirtable");};}},
							{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("添加服务接口信息",550,500,"softserver_servicedir_add");}},
							{text:"(*注意：第一级目录为软件信息，第二级目录为服务信息)"}
						]
					});
				});
		</script>
	</head>
	<body>
		<table id="servicesdirtable" fitColumns="true" fit="true">
			<thead>
					<tr>
						<th field="id" checkbox="true">编号</th>
						<th field="serviceName" width="100">服务名称</th>
						<th field="serviceCode" width="100">服务代号</th>
						<th field="serviceUrl" width="100">服务URL</th>
						<th field="methodId" width="100" formatter="Boss.dir.changeMethodCharacterType">请求方式</th>
						<th field="remark" width="200" >备注</th>
						<th field="opp" width="80px" align="center" formatter="Boss.dir.opSoftAndServicedir">操作</th>
					</tr>
				</thead>
		</table>
	</body>
</html>