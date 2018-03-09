<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.echounion.bossmanager.common.enums.ProtocolType"%>
<jsp:directive.page import="com.echounion.bossmanager.entity.EsbSoftWare"/>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.ProtocolType"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>添加软件基本信息配置</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
		<script type="text/javascript">
		$(function(){
			Boss.form.initDialogForm("softwaretable","softwareForm","soft/soft!addSoftwareAjax");
			$("#softServerID").combogrid({panelWidth:300,idField:"id",textField:"serverName",url:"soft/server",columns:[[{field:"id",title:"编号",width:60},{field:"serverName",title:"服务器名称",width:100},{field:"serverIp",title:"服务器IP",width:120}]]});	
			$("#unbindServicedirtable").datagrid({
				toolbar:[
					{id:"btnremove",text:"删除",iconCls:"icon-remove",handler:function(){Boss.grid.removeSelected("unbindServicedirtable");}},
					{id:"btnsave",text:"添加",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("添加服务接口信息",550,500,"softserver_softdir_add");}}
				]
			});
		});
		</script>
</head>

<body>
<div id="softwareviewcontent">
		<div class="panel-header">
			<div class="panel-title panel-with-icon" style="height:20px;line-height:20px;">软件基本信息</div>
			<div class="panel-icon icon-tip"></div>
	<form method="post" name="softwareForm" id="softwareForm" >
		<input type="hidden" name="serviceDirIds" id="serviceDirIds"/>
		<input type="hidden" id="softId"  value="0"/>
		<table style="padding:10px;" cellpadding="3">
			<tr>
				<td>软件代号:</td><td><input type="text"  validType="alphanum[1,50]"  name="software.softCode" size=50; class="easyui-validatebox" required="true" onblur="Boss.software.checkSoftwareCode(this)"/>&nbsp;<span class="tip">*</span>必填</td>
			</tr>		
			<tr>
				<td>软件名称:</td><td><input type="text"  validType="unnormal[1,100]"  name="software.softName" size=50; class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填</td>
			</tr>
			<tr>
				<td>软件类别:</td>
				<td>
					<select name="software.softType">
						<option value="0">---请选择---</option>
						<c:forEach items="<%=EsbSoftWare.typeList%>" var="item" varStatus="vs">
							<option value="${vs.count}">${item }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>访问端口:</td><td><input type="text" name="software.softPort"  min="0" size=50; validType="length[0,10]"  class="easyui-numberbox"  required="true"/>&nbsp;<span class="tip">*</span>必填(请输入数字)</td>
			</tr>			
			<tr>
				<td>访问协议:</td>
				<td>
					<select name="software.softProtocol">
						<c:forEach items="<%=ProtocolType.values()%>" var="item">
							<option value="${item.id}">${item}</option>
						</c:forEach>
					</select>
				</td>
			</tr>		
			<tr>
				<td>访问地址:</td><td><input type="text"  required="true"  name="software.softUrl" size=50; maxlength="100"  class="easyui-validatebox" /></td>
			</tr>
			<tr>
				<td>邮件通道:</td>
				<td>
					<select  name="software.mailChannel" >
						<option value="1000">默认通道</option>
						<option value="1001">cargoon</option>
						<option value="1002">sofreight</option>
						<option value="1003">BBS</option>	
						<option value="1004">PO</option>
						<option value="1005">BC</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>短信通道:</td>
				<td>
					<select  name="software.shortMsgChannel" >
						<option value="default">默认通道</option>
						<option value="other">其他</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>服务接入方授权码:</td>
				<td>
					<input type="text"   name="software.softKey" value="${soft.softKey}" size=50; maxlength="100"  class="easyui-validatebox" />
				</td>
			</tr>
			<tr>
				<td>所属服务器:</td>
				<td>
					<input type="text" id="softServerID" name="software.serverId" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>备注:</td><td><textarea rows="5" cols="50" name="software.remark"></textarea> </td>
			</tr>			
			<tr>
				<td colspan="2">
						<br/>
						<div class="toolbar" align="center">
							<a href="javascript:void(0)" class="easyui-linkbutton" id="softwareFormFormsavebtn" iconCls="icon-save" onclick="if($('#softwareForm').form('validate')){Boss.software.submitSoft();}">保存</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.location.forward('softserver_soft_list');">返回</a>
						</div>
				</td>
			</tr>
		</table>
		</form>
	</div>
		<br/>
		<table id="unbindServicedirtable" fitColumns="true" fit="false" title="服务接口列表" idField="serviceId" remoteSort=false collapsible=false rownumbers=true pagination=true iconCls='icon-tip' nowrap=false striped=true>
				<thead>
					<tr>
						<th field="id" checkbox="true">编号</th>
						<th field="serviceCode" width="100">服务名称</th>
						<th field="serviceName" width="100">服务名称</th>
						<th field="serviceUrl" width="100">服务URL</th>
						<th field="methodId" width="100" formatter="Boss.dir.changeMethodCharacterType">请求方式</th>
						<th field="remark" width="200" >备注</th>
						<th field="opp" width="80px" align="center" formatter="Boss.dir.opServicedir">操作</th>
					</tr>
				</thead>	
		</table>		
</div>
</body>
</html>