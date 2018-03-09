<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<jsp:include page="../res_link.jsp"></jsp:include>
<title>API系统日志</title>
<style type="text/css">
.vzebra-odd {
	background-color: #E8EDFF;
	border: 1 #EDEAFF solid;
}
</style>
</head>
<body>
	<div width="100%" height="100%" style="padding:10px;">
		<a href="javascript:void(0)" onclick="history.go(-1)" class="easyui-linkbutton" iconCls="icon-undo">返回</a>
		<table border="0" cellpadding="4" cellspacing="" width="100%">
			<colgroup>
				<col class="vzebra-even">
			</colgroup>
			<tbody>
				<tr>
					<td width="100">软件名称</td>
					<td>${apilog.softName}</td>
				</tr>
				<tr>
					<td>服务名称:</td>
					<td>${apilog.serviceCode}</td>
				</tr>
				<tr>
					<td>调用方的IP:</td>
					<td>${apilog.invokeIp}</td>
				</tr>
				<tr>
					<td>调用时间:</td>
					<td>${apilog.invokeTime}</td>
				</tr>
				<tr>
					<td>接口响应状态码:</td>
					<td>${apilog.resCode}</td>
				</tr>
				<tr>
					<td>响应结果:</td>
					<td>${apilog.resMsg}</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>