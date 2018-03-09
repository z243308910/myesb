<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<title>租赁协议详情信息</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		</head>
	<body>
<div id="mailhistoryviewcontent" style="padding:10px;" align="center">
		<table cellpadding="5" style="border:1px solid" cellspacing="5">
			<tr>
				<td>软件名称</td><td>${history.softName}</td><td>邮件地址</td><td>${history.emailAddress}</td><td>邮件模版</td><td>${history.tplName}</td>
			</tr>
			<tr>
				<td>发送时间</td><td><fmt:formatDate value="${history.sendTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td><td>错误次数</td><td>${history.errosNum}</td><td>最后一次发送时间</td><td><fmt:formatDate value="${history.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td><td>状态</td><td>${history.statusName}</td>
			</tr>
			<tr><td colspan="8"><br/><br/></td></tr>
			<tr>
				<td colspan="8">
					${history.content}
				</td>
			</tr>
		</table>
</div>		
	</body>
</html>