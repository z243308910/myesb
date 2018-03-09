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
		<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/icon.css">
		<link href="<%=basePath%>res/css/skin.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="<%=basePath%>res/css/boss.css">
		<script src="<%=basePath%>res/js/jquery-1.8.2.js"></script>
		<script type="text/javascript" src="<%=basePath%>res/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>res/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false">
			<jsp:include page="head.jsp"/>
		</div>
		<div data-options="region:'west',split:true,title:'菜单栏'" style="width:150px;">
			<jsp:include page="left.jsp"/>
		</div>
		<div data-options="region:'south',border:false">&nbsp;</div>
		<div data-options="region:'center'">
			<iframe  src="<%=basePath%>welcome" name="main" width="100%" height="100%" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" id="main"></iframe>
		</div>
	</body>
</html>

