<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="res/css/style.css" />
</head>
<body>
    <div class="log_box">
        <div class="logo"></div>${loginerror}
        <form id=myform name=myform  action=login method=post>
	        <ul class="w_box">
	            <li><label for="username">用户名</label><input name="userName" type="text" id="userName" /></li>
	            <li class="tip"><div>用户名错误</div></li>
	            <li><label for="password">密&nbsp;&nbsp;&nbsp;码</label><input name="password" type="password" id="password" /></li>
	            <li class="tip"><div>密码错误</div></li>
	            <li class="md5"><label for="captcha">验证码</label><input name="captcha" type="text" id="captcha" /><img src="captcha.svl" onclick="this.src='captcha.svl?d='+new Date()*1" align="top" width="60px"/></li>
	            <li class="tip"><div>验证码错误</div></li>
	            <li class="btn"><a href="#" onclick="document.getElementById('myform').submit()">登&nbsp;&nbsp;录</a></li>
	        </ul>
        </form>
        <div class="ver"></div>
    </div>
</body>
</html>
