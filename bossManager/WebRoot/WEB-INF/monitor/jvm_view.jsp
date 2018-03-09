<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<base href="<%=basePath%>">
		<title>JVM性能数据列表</title>
		<jsp:include page="../res_link.jsp"></jsp:include>
		<script type="text/javascript">
		$(function()
		{
			$.ajax(
			{
				url:"monit/jvm!viewJvmArgs",
				data:"data=${param.jvmId}",
				type:"POST",
				cache:true,
				success:function(msg)
				{
					$(eval(msg)).each(function(index,item)
					{
						var img=$("<img src='"+item+"' /><hr/>")
						img.appendTo("#showChart");
					});
				}
			});
		});
		</script>
	</head>
	<body>
		<div id="showChart" align="center">
		</div>
	</body>
</html>
