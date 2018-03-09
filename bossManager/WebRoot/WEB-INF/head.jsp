<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>BOSS管理中心</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<body leftmargin="0" topmargin="0">
		<table width="100%" height="64" border="0" cellpadding="0"
			cellspacing="0" class="admin_topbg">
			<tr>
				<td width="61%" height="64">
					<img src="res/images/logo.gif" width="262" height="64">
				</td>
				<td width="39%" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="74%" class="admin_txt" style="padding-top:10px;">
								管理员：<b>${adminuser.name }</b> 您好,感谢登陆使用！
							</td>
							<td width="22%" style="padding-top:10px;padding-right:55px;">
								<a target="_self" onclick="window.parent.location.href='login!logOut'" id="qlogout"><img src="res/images/out.gif" alt="安全退出" width="46" height="20" border="0"></a>
							</td>
							<td width="4%">&nbsp;</td>
						</tr>
						<tr>
							<td height="19" colspan="3">&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>

