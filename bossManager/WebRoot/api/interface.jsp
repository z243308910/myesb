<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String bossPath="http://127.0.0.1:7777/boss/api";
%>
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
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>res/js/jquery-easyui-1.3.1/themes/icon.css">
<script src="<%=basePath%>res/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=basePath%>res/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>

<script type="text/javascript">

	$(function(){
						$("#servertable").treegrid({
						url:"api/soft!getServerSoftwareList",
						iconCls:"icon-tip",
						nowrap: false,
						striped:true,
						collapsible:false,
						remoteSort:false,
						idField: "id",
						treeField:"softName",
						onClickRow:function(rowData){ showInterfacedesc(rowData.id);}
					});
				});    

				function showInterfacedesc(value){
					$("#interfaceParams  tr:not(:first)").empty();
					$.ajax({
						url:"api/dir!getServiceBySoftId",
						type:"post",
						data:"softId="+value,
						success:function(msg){
							data = msg.rows;
							$("#interfacedesc  tr:not(:first)").empty()
							for(var i = 0 ; i<data.length; i++){$("#interfacedesc").append("<tr  style='cursor:hand;'  onclick='showInterfaceparams("+data[i].id+")'><td class='wiki_table_tdfirst'><%=bossPath%>"+data[i].serviceUrl+"</td><td>"+data[i].serviceName+"</td><td>"+data[i].remark+"</td></tr>");}
						}
					});
				};
				
				function showInterfaceparams(value){
					$.ajax({
						url:"api/param!getDirParamsByServiceDirId",
						type:"post",
						data:"serviceDirId="+value,
						success:function(msg)
						{
							type=["整形","浮点型","布尔型","字符串","时间型"];
							data = eval(msg);
							$("#interfaceParams  tr:not(:first)").empty();
							for(var i = 0 ; i<data.length; i++){
							var id=data[i].typeId;
							$("#interfaceParams").append("<tr><td class='wiki_table_tdfirst'>"+data[i].name+"</td><td>"+data[i].code+"</td><td>"+data[i].require+"</td><td>"+type[id-1]+"</td><td>"+data[i].remark+"</td></tr>");}
						}
					});
				}
</script>

</head>

<body>
	<br/>
	<center><h1 style="color:blue;">BOSS 开放API</h1></center>
	<center>
		<div class="con_box">
			<div class="wiki_body">
				<div class="wiki_inner">
					<div class="main_content">
						<div class="wiki_side" >
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
						<div class="wiki_bodyContent" id="bodyContent" align="left">
							<h2 align="left">开放API</h2>
							
								<table id="servertable" fitColumns="true" fit="true">
										<thead>
												<tr>
													<th field="id"   style="display: none;"></th>
													<th field="softName" width="100"></th>
												</tr>
											</thead>
									</table>
									
							<br/>
							<h2 align="left">接口参数列表及说明</h2>
							 <table id="interfacedesc" fitColumns="true" fit="true" class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
									<tbody>
										<tr>
											<th class="wiki_table_thfirst" style="width:180px">接口URI </th>
											<th>接口名称</th>
											<th>接口描述</th>
										</tr>
									</tbody>
							</table>
							<br/>
							<table id="interfaceParams" class="wiki_table" border="0" cellspacing="0" cellpadding="0" style="width:100%">
									<tbody>
										<tr>
											<th class="wiki_table_thfirst" style="width:180px">参数名称</th>
											<th>参数代码</th>
											<th>是否必填</th>
											<th>参数类型</th>
											<th>备注</th>
										</tr>
									</tbody>
							</table>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</center>
</body>
</html>
