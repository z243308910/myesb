<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.echounion.bossmanager.common.enums.SystemStatus"%>
<%@page import="com.echounion.bossmanager.common.enums.EmailStatus"%>
<%@page import="com.echounion.bossmanager.common.enums.ShortMsgStatus"%>
<%@page import="com.echounion.bossmanager.common.enums.RtxStatus"%>
<%@page import="com.echounion.bossmanager.common.enums.SoftServerStatus"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>BOSS 开放API</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	.con_box {min-height: 195px;width: 950px;border:1px solid #666;margin: 0 auto;}
	.wiki_body {background: #FFF;background: -moz-linear-gradient(top, rgba(0, 42, 87, 0.3) 0,rgba(211, 215, 217, 0.3) 60%, rgba(211, 215, 217, 0.3) 100% );background: -webkit-linear-gradient(top, rgba(0, 42, 87, 0.3) 0,rgba(211, 215, 217, 0.3) 60%, rgba(211, 215, 217, 0.3) 100% );border-radius: 8px;box-shadow: 0 0 7px rgba(218, 218, 218, 0.5);overflow: hidden;padding: 1px;position: relative;}
	.wiki_inner {background: #FFF;border-radius: 8px;overflow: hidden;}
	.main_content,.wiki_wrap .main_content_nar {min-height: 700px;}
	.wiki_side {float: left;width: 159px;background-color:#999;height:100%;height: 770px;}
	.wiki_bodyContent {float: left;width: 709px;padding: 20px 20px 30px;overflow: hidden;}
	.wiki_nav .nav_list {padding: 0 0 1px 9px;margin-bottom: 20px;font-size: 0;}
	.wiki_nav .nav_list li {font-size: 12px;text-indent: 10px;padding-bottom:20px;}
	.wiki_nav .child_list li a {display: block;height: 25px;padding-left: 25px;line-height: 25px;}
	li {list-style: none;}
	a {text-decoration: none;color: #3C7CB3;}
	.nav_list{margin:0px;margin-top:40px;}
	.wiki_nav .nav_list .n_top h3 {font-family: "Microsoft Yahei";font-weight: 300;padding-bottom: 18px;}
	.wiki_nav .nav_list .n_top h3 a {color: #000;text-decoration: none;font-size: 14px;display: block;height: 18px;}
	.wiki_nav .wiki_icon {height: 18px;width: 20px;display: inline-block;vertical-align: middle;margin-right: 4px;}
	.wiki_title {line-height: 37px;background: #EDF3F9;border: none;margin: 16px 0 8px 0;padding: 0 0 0 15px;font-size: 14px;font-weight: bold;color: #333;}
	.wiki_table {border: 1px solid #C6CCD2;}
	.wiki_table th, .wiki_table td {border: 1px solid #E8EAEC;vertical-align: middle;padding: 0 10px;}
	.wiki_table th {background: #F3F4F5;height: 29px;font-weight: 700;font-size: 14px;text-align: left;color: #000;}
	.wiki_table td {padding: 7px 10px;line-height: 20px;}
	.wiki_table td {border: 1px solid #E8EAEC;vertical-align: middle;padding: 0 10px;}	
</style>
</head>

<body>
	<br/>
	<center><h1 style="color:blue;">BOSS 开放API</h1></center>
	<center>
		<div class="con_box">
			<div class="wiki_body">
				<div class="wiki_inner">
					<div class="main_content">
						<div class="wiki_side">
							<!--左侧导航-->
							<div class="wiki_nav">
								<ul class="nav_list">
									<li>
										<h3>
											<a href="#" onclick="return false;">系统状态说明</a>
										</h3>
										<ul class="child_list">
											<li id="" class="n_sub sub_cur active"><a href="index.jsp">系统状态</a></li>
										</ul>
									</li>							
									<li>
										<h3>
											<a href="#" onclick="return false;">技术文档</a>
										</h3>
										<ul class="child_list" style="">
											<li id="" class="n_sub"><a href="interface.jsp" items="Array">接口API</a></li>
										</ul>
									</li>
								</ul>
							</div>
							<!--/左侧导航结束-->
						</div>
						<div class="wiki_bodyContent" id="bodyContent">
							<h2>系统级别状态</h2>
							<table class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
							
								<tr>
									<th class="wiki_table_thfirst" style="width:180px">状态代号</th><th class="wiki_table_thfirst" style="width:180px">状态名称</th>
								</tr>
								<c:forEach items="<%=SystemStatus.values()%>" var="item" >
								<tr>
								<td class="wiki_table_tdfirst">${item.code}</td><td class="wiki_table_tdfirst">${item.name}</td>
								</tr>
								</c:forEach>
							</table>

							<h2>邮件级别状态</h2>
							<table class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
								<tr>
									<th class="wiki_table_thfirst" style="width:180px">状态代号</th><th class="wiki_table_thfirst" style="width:180px">状态名称</th>
								</tr>
								<c:forEach items="<%=EmailStatus.values()%>" var="emalItem" >
								<tr>
								<td class="wiki_table_tdfirst">${emalItem.code}</td><td class="wiki_table_tdfirst">${emalItem.name}</td>
								</tr>
								</c:forEach>
							</table>
							
							<h2>短信级别状态</h2>
							<table class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
								<tr>
									<th class="wiki_table_thfirst" style="width:180px">状态代号</th><th class="wiki_table_thfirst" style="width:180px">状态名称</th>
								</tr>
								<c:forEach items="<%=ShortMsgStatus.values()%>" var="shortMsgItem" >
								<tr>
								<td class="wiki_table_tdfirst">${shortMsgItem.code}</td><td class="wiki_table_tdfirst">${shortMsgItem.name}</td>
								</tr>
								</c:forEach>
							</table>
							
								<h2>RTX级别状态</h2>
							<table class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
								<tr>
									<th class="wiki_table_thfirst" style="width:180px">状态代号</th><th class="wiki_table_thfirst" style="width:180px">状态名称</th>
								</tr>
								<c:forEach items="<%=RtxStatus.values()%>" var="rtxItem" >
								<tr>
								<td class="wiki_table_tdfirst">${rtxItem.code}</td><td class="wiki_table_tdfirst">${rtxItem.name}</td>
								</tr>
								</c:forEach>
							</table>
							
							<h2>接口级别状态</h2>
							<table class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
								<tr>
									<th class="wiki_table_thfirst" style="width:180px">状态代号</th><th class="wiki_table_thfirst" style="width:180px">状态名称</th>
								</tr>
								<c:forEach items="<%=SoftServerStatus.values()%>" var="serverItem" >
								<tr>
								<td class="wiki_table_tdfirst">${serverItem.code}</td><td class="wiki_table_tdfirst">${serverItem.name}</td>
								</tr>
								</c:forEach>
							</table>													
						</div>
					</div>
				</div>
			</div>
		</div>
	</center>
</body>
</html>
