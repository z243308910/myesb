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
		<title>系统序列列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/system.js"></script>
	<script>
$(function(){
			$("#syssquencetable").datagrid({
				url:"sys/sequence",
				title:"系统序列列表",
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
						$("#syssquencetable").datagrid({url:"sys/sequence"});
					}
				},{
					id:'btndel',
					text:'删除',
					iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("syssquencetable","sys/sequence!delSequence");
					}
				},{
					id:'btndel',
					text:'新增',
					iconCls:'icon-remove',
					handler:function(){
						var channelpanel=$('<div id="addsyssequencewin" iconCls="icon-save" closable="true"  collapsible="false" minimizable="false" maximizable="true"></div>').insertAfter("#syssquencetable");
						$("#addsyssequencewin").window({title:"添加系统配置",cache:false,shadow:false,height:400,width:500,modal:true,inline:true,href:"system_sequence_add"});						
					}
				}]
			});
		});
	</script>
	</head>
	<body>
	<table id="syssquencetable" fitColumns="true" fit="true">
			<thead>
				<tr>
					<th field="id" checkbox=true width="80px" align="center">编号</th>				
					<th field="seqCode" width="80px" align="center">代号</th>
					<th field="tableName" width="80px" align="center">表名</th>
					<th field="fieldName" width="80px" align="center">列名</th>
					<th field="minValue" width="80px" align="center">最小值</th>
					<th field="maxValue" width="80px" align="center">最大值</th>
					<th field="nextValue" width="80px" align="center">下一个值</th>
					<th field="createTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">创建时间</th>					
					<th field="op" width="80px" align="center" formatter="Boss.system.opSysSeq">描述</th>
				</tr>
			</thead>	
	</table>
	</body>
</html>