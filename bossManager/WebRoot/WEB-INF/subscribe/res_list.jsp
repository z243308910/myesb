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
		<title>系统注册资源列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<style type="text/css">
			.noborderinput{border:1px;border-bottom-style:none;border-top-style:none;border-left-style:none;border-right-style:none;}
		</style>
		<script type="text/javascript" src="res/js/safemonitor.js"></script>
		<script type="text/javascript">
		$(function(){
			$("#regemailtable").datagrid({
				title:"",striped: true,collapsible:false,remoteSort: false,idField:'id',rownumbers:true,
				url:'monit/mail!getRegMail',pagination:true,pageList:[20,30,50],
				toolbar:[{id:'btnsave',text:'刷新',disabled:false,iconCls:'icon-reload',
					handler:function(){
						$("#regemailtable").datagrid({url:"monit/mail!getRegMail"});
					}
				},{
					id:'btndel',text:'删除',iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("regemailtable","monit/mail!delRegMail");
					}
				}]
			});

			$("#regmobiletable").datagrid({
				title:"",striped: true,collapsible:false,remoteSort: false,idField:'id',rownumbers:true,
				url:'monit/msg!getRegMobile',pagination:true,pageList:[20,30,50],
				toolbar:[{
					id:'btnsave',text:'刷新',disabled:false,iconCls:'icon-reload',
					handler:function(){
						$("#regmobiletable").datagrid({url:"monit/msg!getRegMobile"});
					}
				},{
					id:'btndel',text:'删除',iconCls:'icon-remove',
					handler:function(){
							Boss.grid.delData("regmobiletable","monit/msg!delRegMobile");
					}
				}]		
			});			
		});
		</script>
		</head>
			<body>
			<div class="easyui-tabs" data-options="fit:true,plain:true">
				<div title="注册邮件记录" style="padding:10px;">
					<table id="regemailtable" fitColumns="true" fit="true" cellspacing="1">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">编号</th>
									<th field="email" width="80px" align="center">邮箱地址</th>
									<th field="confirmTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">确认时间</th>
								</tr>
							</thead>	
					</table>
				</div>
				<div title="注册短信记录" style="padding:10px;">
					<table id="regmobiletable" fitColumns="true" fit="true">
							<thead>
								<tr>
									<th field="id" width="80px" checkbox=true align="center">编号</th>
									<th field="mobileNumber" width="80px" align="center">手机号码</th>
									<th field="confirmTime" width="80px" align="center" formatter="Boss.util.formatLongToDate">确认时间</th>
								</tr>
							</thead>	
					</table>						
				</div>
			</div>
	</body>
</html>