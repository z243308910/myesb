<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<base href="<%=basePath%>">
		<style type="text/css">
			body {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;font-size:12px; font-family:"宋体",Verdana, Arial;}
			a:link {color: #222; text-decoration:none;}
			a:visited {color: #222;text-decoration:none;}
			a:hover {color: #ff0000; text-decoration:none;}
			a:active {color: #222; text-decoration:none;}
		</style>
		<jsp:include page="res_link.jsp"></jsp:include>
	</head>
	<body>
		<div style="clear:both;height:3px;line-height:3px;"></div>
		<table width="1003" height="3" border="0" align="center"
			cellpadding="0" cellspacing="0" bgcolor="#196CC8">
			<tr>
				<td></td>
			</tr>
		</table>
		<table width="1003" height="33" border="0" align="center"
			cellpadding="0" cellspacing="0" style="font-size:12">
			<tr>
				<td>
					<div align="center">
						<a href='' target='_blank'>网站地图</a> |
						<a href="mailto:csjkq@163.com">联系我们</a>
					</div>
				</td>
			</tr>
		</table>
		<table width="1003" height="22" border="0" align="center"
			cellpadding="0" cellspacing="0" style="font-size:12">
			<tr>
				<td>
					<div align="center">
						客户服务热线： 0755-XXXXXXX 传 真：0755-123469846 电子邮件：xxxx@xx.com
						<br />
					</div>
				</td>
			</tr>
		</table>
		<table width="1003" height="22" border="0" align="center"
			cellpadding="0" cellspacing="0" style="font-size:12">
			<tr>
				<td>
					<div align="center">
						地 址：深圳市南山区(哈哈哈哈哈啊哈哈哈) 邮政编码:410100
						<br />
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>

