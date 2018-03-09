<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<script type="text/javascript">
$(function(){
			$("#softwaretable").datagrid({url:"soft/soft!getUnBindSoftWareList",iconCls:"icon-tip",nowrap: false,striped: true,collapsible:false,remoteSort: false,idField:"id",pagination:true,rownumbers:true,pageList:[20,30,50],
				toolbar:[
				{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("softwaretable");}},
				{id:"btnsave",text:"新增",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("新增软件基本信息",600,450,"softserver_serversoft_add");}},
				{id:"btnflush",text:"确定",iconCls:"icon-ok",handler:function(){var rows=Boss.grid.getSelctData("softwaretable");	var unbindrows=$("#unbindsoftwaretable").datagrid("getRows");var flag=false;	for(var i=0;i<rows.length;i++){for(var j=0;j<unbindrows.length;j++){	if(rows[i].id == unbindrows[j].id){flag=true;};}if(!flag){$("#unbindsoftwaretable").datagrid("appendRow",rows[i]);};}; Boss.util.closeDialogWin();}}	]});});
</script>
<table id="softwaretable" fitColumns="true" fit="true">
	<thead>
			<tr>
					<th field="id" width="80px"  checkbox="true" align="center">编号</th>
					<th field="softCode" width="80px" align="center">软件代号</th>
					<th field="softName" width="80px" align="center">软件名称</th>
					<th field="softType" width="80px" align="center">软件类别</th>
					<th field="softPort" width="80px" align="center">访问端口</th>
					<th field="softProtocol" width="80px" align="center">访问协议</th>
					<th field="softUrl" width="80px" align="center">访问地址</th>
			</tr>
		</thead>
</table>