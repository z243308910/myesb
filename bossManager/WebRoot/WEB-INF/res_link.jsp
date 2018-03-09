<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/icon.css">
<link href="<%=basePath%>res/css/skin.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="<%=basePath%>res/css/boss.css">
<script src="<%=basePath%>res/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/jquery.extendvalidate.js"></script>
<script src="<%=basePath%>res/js/boss.js"></script>
