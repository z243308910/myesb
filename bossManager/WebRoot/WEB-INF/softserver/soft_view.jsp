<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.echounion.bossmanager.entity.EsbSoftWare"/>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.ProtocolType"/>
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
			Boss.form.initDialogForm("softwaretable","softwareForm","soft/soft!editSoftware");
			$("#unbindServicedirtable").datagrid({
				url:"soft/dir!getServiceBySoftId?softId=${soft.id}",
				toolbar:[
					{id:"btnflush",text:"刷新",iconCls:"icon-reload",handler:function(){Boss.grid.reload("unbindServicedirtable");}},
					{id:"btnremove",text:"删除",iconCls:"icon-remove",handler:function(){Boss.grid.delData("unbindServicedirtable","soft/dir!delServiceDir");}},
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
		<form method="post" name="softwareForm" id="softwareForm">
				<input type="hidden" name="serviceDirIds" id="serviceDirIds"/>
				<input type="hidden" value="${soft.id}"  id="softId" name="software.id"/>
				<input type="hidden" value="${soft.creator}" name="software.creator"/>
				<input type="hidden" value="${soft.createDate}" name="software.createDate"/>
				<input type="hidden" value="${soft.serverId}" name="software.serverId"/>
				<input type="hidden" value="${soft.authKey}" name="software.authKey"/>
				<table style="padding:10px;" cellpadding="3">
					<tr>
						<td>软件代号:</td>
						<td colspan="3">
							<input type="text"  validType="alphanum[1,50]"   name="software.softCode" value="${soft.softCode}" class="easyui-validatebox" required="true" />&nbsp;<span class="tip">*</span>必填
						</td>
					</tr>				
					<tr>
						<td>软件全称:</td>
						<td>
							<input type="text"  validType="unnormal[1,100]"   name="software.softName" value="${soft.softName}"  class="easyui-validatebox" required="true"/>&nbsp;<span class="tip">*</span>必填
						</td>
					</tr>
					<tr>
						<td>软件类别:</td>
						<td>
							<select name="software.softType">
								<option value="0" <c:if test="${soft.softType eq 0}">selected="selected"</c:if> >---请选择---</option>
								<c:forEach items="<%=EsbSoftWare.typeList %>" var="item" varStatus="vs">
									<option value="${vs.count}" <c:if test="${soft.softType eq vs.count}">selected="selected"</c:if> >${item }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>访问端口:</td>
						<td>
							<input type="text"  name="software.softPort" value="${soft.softPort}"  min="0" validType="length[0,10]"   class="easyui-numberbox"  required="true" />&nbsp;<span class="tip">*</span>必填(请输入数字)
						</td>
					</tr>			
					<tr>
						<td>访问协议:</td>
						<td>
							<select name="software.softProtocol">
								<c:forEach items="<%=ProtocolType.values()%>" var="item">
									<option value="${item.id}" <c:if test="${item.id eq soft.softProtocol}">selected="selected"</c:if> >${item}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td>访问地址:</td>
						<td>
							<input type="text" required="true"  name="software.softUrl" value="${soft.softUrl}" size=50; maxlength="100"  class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>邮件通道:</td>
						<td>
							<select name="software.mailChannel" >
								<option value="1000" <c:if test="${soft.mailChannel eq 1000 }">selected="selected"</c:if> >默认通道</option>
								<option value="1001" <c:if test="${soft.mailChannel eq 1001 }">selected="selected"</c:if> >cargoon</option>
								<option value="1002" <c:if test="${soft.mailChannel eq 1002 }">selected="selected"</c:if> >sofreight</option>
								<option value="1003" <c:if test="${soft.mailChannel eq 1003 }">selected="selected"</c:if> >BBS</option>	
								<option value="1004" <c:if test="${soft.mailChannel eq 1004 }">selected="selected"</c:if> >PO</option>
								<option value="1005" <c:if test="${soft.mailChannel eq 1005 }" >selected="selected"</c:if> >BC</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>短信通道:</td>
						<td>
							<select  name="software.shortMsgChannel" >
								<option value="default" <c:if test="${soft.shortMsgChannel eq 'default' }">selected="selected"</c:if> >默认通道</option>
								<option value="other" <c:if test="${soft.shortMsgChannel eq 'other' }">selected="selected"</c:if> >其他</option>
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
						<td>BOSS授权码:</td>
						<td>${soft.authKey}</td>
					</tr>
					<tr>
						<td>所属服务器:</td>
						<td>
							${server.serverName}(${server.serverIp})
						</td>
					</tr>					
					<tr>
						<td>备注:</td>
						<td>
							<textarea rows="5" cols="100" name="software.remark">${soft.remark }</textarea>
						</td>
					</tr>
					<tr>
						<td>创建人:</td><td>${soft.creator} </td>
						<td>创建时间:</td><td><fmt:formatDate value="${soft.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
					</tr>					
					<tr>
						<td colspan="2">
								<br/>
								<div class="toolbar" align="center">
									<a href="javascript:void(0)" class="easyui-linkbutton" id="softwareFormFormsavebtn" iconCls="icon-save" onclick="if($('#softwareForm').form('validate')){Boss.software.submitSoft();}">保存</a>
									<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" onclick="Boss.util.historyWin();">返回</a>
								</div>
						</td>
					</tr>
			</table>
		</form>		
</div>
	
		<br/>
		<table id="unbindServicedirtable" fitColumns="true" fit="false" title="服务接口列表" idField="id" remoteSort=false collapsible=false rownumbers=true pagination=true iconCls='icon-tip' nowrap=false striped=true>
				<thead>
					<tr>
						<th field="id" checkbox="true">编号</th>
						<th field="serviceCode" width="100">服务代码</th>
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