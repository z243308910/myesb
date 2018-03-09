<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.echounion.bossmanager.entity.EsbSoftWare"/>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.HttpReqeuestType"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>添加系统配置</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript" src="res/js/softserver.js"></script>
	<script>
	$(function(){
			Boss.form.initDialogForm("servicesdirtable","servicedirForm","soft/dir!editServiceDir");
			$("#serviceparamstable").datagrid({
				url:"soft/param!getDirParamsByServiceDirId?serviceDirId=${serviceDir.id}",
				toolbar:[
					{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("serviceparamstable");}},
					{id:"btnremove",text:"删除",iconCls:"icon-remove",handler:function(){Boss.grid.delData("serviceparamstable","soft/param!delDirParams");}},
					{id:"btnsave",text:"添加",iconCls:"icon-add",handler:function(){Boss.util.createDialogWin("添加服务接口信息",500,350,"softserver_dirparams_add");}}
				]
			});			
		});
	</script>
		</head>
	<body>
<div id="softwareviewcontent">
		<div class="panel-header">
			<div class="panel-title panel-with-icon" style="height:20px;line-height:20px;">服务基本信息</div>
			<div class="panel-icon icon-tip"></div>
		<form method="post" name="servicedirForm" id="servicedirForm">
				<input type="hidden" name="serviceDir.id" value="${serviceDir.id}">
				<input type="hidden" name="serviceDir.softId" value="${serviceDir.softId}">
				<table style="padding:10px;" cellpadding="3">
					<tr>
						<td>服务代号:</td>
						<td colspan="3">
							<input type="text"  validType="alphanum[1,50]"   name="serviceDir.serviceCode" value="${serviceDir.serviceCode}" class="easyui-validatebox" required="true" onblur="Boss.software.checkSoftwareCode(this)"/>&nbsp;<span class="tip">*</span>必填
						</td>
					</tr>				
					<tr>
						<td>服务全称:</td>
						<td>
							<input type="text"  validType="unnormal[1,100]"   name="serviceDir.serviceName" value="${serviceDir.serviceName}"  class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填
						</td>
					</tr>
					<tr>
						<td>访问地址:</td>
						<td>
							<input type="text"  validType="unblank[1,300]"  name="serviceDir.serviceUrl" value="${serviceDir.serviceUrl}" size=50; maxlength="100"  class="easyui-validatebox" />&nbsp;<span class="tip">
						</td>
					</tr>
						<tr>
							<td>请求方式:</td>
							<td colspan="3">
								<select id="methodSelect" name="serviceDir.methodId">
											<c:forEach items="<%=HttpReqeuestType.values()%>" var="item">
												<option <c:if test="${item.id eq serviceDir.methodId }">selected="selected"</c:if>  value="${item.id}" >${item}</option>
											</c:forEach>
								</select>
							</td>
						</tr>
					<tr>
						<td>备注:</td>
						<td>
							<textarea rows="5" cols="100" name="serviceDir.remark">${serviceDir.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
								<br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="softwareFormFormsavebtn" iconCls="icon-save" onclick="if($('#servicedirForm').form('validate')){$('#servicedirForm').submit();}">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.historyWin();">返回</a>
								</div>
						</td>
					</tr>
			</table>
		</form>		
</div>
		<br/>
		<table id="serviceparamstable" fitColumns="true" fit="false">
			<input type="hidden" id="serviceDirId" name="serviceDir.id" value="${serviceDir.id}">
			<thead>
				<tr>
					<th field="id" width="80px"  checkbox="true" align="center">编号</th>
					<th field="name" width="80px" align="center" >参数名称</th>					
					<th field="code" width="80px" align="center">参数代码</th>
					<th field="require" width="80px" align="center">是否必填</th>
					<th field="typeId" width="80px" align="center" formatter="Boss.dir.changeParamTypeCharacterType">参数类型</th>
					<th field="remark" align="center" align="center">备注</th>
					<th field="opp" width="80px" align="center" formatter="Boss.dir.opServicedirParam">操作</th>
				</tr>
			</thead>
	</table>		
</div>
	</body>
</html>