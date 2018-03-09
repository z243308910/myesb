<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<title>系统消息列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<style type="text/css">
			.noborderinput{border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;}
		</style>
		<script type="text/javascript" src="res/js/safemonitor.js"></script>
		<script type="text/javascript">
		$(function(){
			Boss.email.initEmailHistory();
			Boss.msg.initMsgHistory();
			Boss.rtx.initRtxHistory();
		});			
		</script>
		</head>
			<body>
			<div class="easyui-tabs" data-options="fit:true,plain:true">
				<div title="邮件记录" style="padding:10px;">
					<form id="searchEmail" method="post" style="display:none">
							<div style="display:inline;" id="divtime"></div>
							邮件地址:<input type="text" name="mailHistory.emailAddress"/>
							业务类型:<input type="text" name="mailHistory.btype"/>
							业务代号:<input type="text" name="mailHistory.bcode"/>
							<div style="margin-right:60px;" align="right">
								 <a href="javascript:void(0)" onclick="Boss.email.initEmailHistory();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜&nbsp;索</a>
								 <a href="javascript:void(0)" onclick="Boss.form.reset('searchEmail')" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重&nbsp;置</a>
							</div>
					</form>
					<table id="emailhistorytable" fitColumns="true" fit="true" cellspacing="1">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">编号</th>
									<th field="softName" width="80px" align="center">软件名称</th>
									<th field="emailAddress" width="80px" align="center">邮件地址</th>
									<th field="tplName" width="80px" align="center">模版名称</th>
									<th field="sendTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">发送时间</th>
									<th field="errosNum" width="80px" align="center">错误次数</th>
									<th field="lastTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">最后一次发送时间</th>
									<th field="status" width="80px" align="center" formatter="Boss.email.opStatus">状态</th>
									<th field="btype" width="80px" align="center">业务类型</th>
									<th field="bcode" width="80px" align="center">业务代号</th>									
									<th field="op" width="80px" align="center" formatter="Boss.email.opMail">操作</th>
								</tr>
							</thead>
					</table>
				</div>
				<div title="短信记录" style="padding:10px;">
					<form id="searchMsg" method="post" style="display:none">
							<div style="display:inline;" id="divtime"></div>
							手机号:<input type="text" name="mobileHistory.phoneNo"/>
							业务类型:<input type="text" name="mobileHistory.btype"/>
							业务代号:<input type="text" name="mobileHistory.bcode"/>
							<div style="margin-right:60px;" align="right">
								 <a href="javascript:void(0)" onclick="Boss.msg.initMsgHistory();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜&nbsp;索</a>
								 <a href="javascript:void(0)" onclick="Boss.form.reset('searchMsg')" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重&nbsp;置</a>
							</div>
					</form>				
					<table id="shortmsgtable" fitColumns="true" fit="true">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">编号</th>
									<th field="softName" width="80px" align="center">软件ID</th>
									<th field="phoneNo" width="80px" align="center">手机号码</th>
									<th field="activeTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">发送/接收时间</th>
									<th field="type" width="80px" align="center" formatter="Boss.msg.opType">短信类别</th>
									<th field="errorNum" width="80px" align="center">错误次数</th>
									<th field="lastSendTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">上一次发送时间</th>
									<th field="status" width="80px" align="center" formatter="Boss.msg.opStatus">状态</th>									
									<th field="recStatus" width="80px" align="center">收阅状态</th>
									<th field="btype" width="80px" align="center">业务类型</th>
									<th field="bcode" width="80px" align="center">业务代号</th>									
									<th field="op" width="80px" align="center" formatter="Boss.msg.opMsg">操作</th>									
								</tr>
							</thead>	
					</table>						
				</div>
				<div title="RTX记录" style="padding:10px;">
					<form id="searchRtx" method="post" style="display:none">
							<div style="display:inline;" id="divtime"></div>
							RTX号:<input type="text" name="rtxHistory.receiver"/>
							业务类型:<input type="text" name="rtxHistory.btype"/>
							业务代号:<input type="text" name="rtxHistory.bcode"/>
							<div style="margin-right:60px;" align="right">
								 <a href="javascript:void(0)" onclick="Boss.rtx.initRtxHistory();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜&nbsp;索</a>
								 <a href="javascript:void(0)" onclick="Boss.form.reset('searchRtx')" class="easyui-linkbutton" data-options="iconCls:'icon-undo'">重&nbsp;置</a>
							</div>
					</form>					
					<table id="rtxtable" fitColumns="true" fit="true">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">编号</th>
									<th field="receiver" width="80px" align="center">接收者</th>
									<th field="title" width="80px" align="center">消息标题</th>
									<th field="content" width="80px" align="center">消息内容</th>
									<th field="type" width="80px" align="center" formatter="Boss.rtx.opType">消息类型</th>
									<th field="delayTime" width="80px" align="center">停留时间</th>
									<th field="phone" width="80px" align="center">手机号码</th>
									<th field="sendTime" width="80px" align="center"  formatter="Boss.util.formatLongToDate">发送时间</th>
									<th field="errosNum" width="80px" align="center">错误次数</th>									
									<th field="lastTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">最后更新时间</th>
									<th field="status" width="80px" align="center" formatter="Boss.rtx.opStatus">状态</th>
									<th field="btype" width="80px" align="center">业务类型</th>
									<th field="bcode" width="80px" align="center">业务代号</th>
								</tr>
							</thead>	
					</table>						
				</div>
			</div>
	</body>
</html>