<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>网站管理员登陆</title>
	<style type=text/css>
	td {font-size: 12px; color: #ffffff; font-family: 宋体}
	body {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color:#DAE0E9;font-size: 12px; color: #ffffff; font-family: 宋体}	
	</style>	
	<link href="res/css/skin.css" rel="stylesheet" type="text/css">	
  </head>
  <body>
		<form id=myform name=myform  action=login method=post>
			<div id=updatepanel1>
				<div id=div1
					style="left: 0px; position: absolute; top: 0px; background-color: #0066ff"></div>
				<div id=div2
					style="left: 0px; position: absolute; top: 0px; background-color: #0066ff"></div>
				

				<div>&nbsp;&nbsp;</div>
				<div>
					<table cellspacing=0 cellpadding=0 width=900 align=center border=0 style="margin-top:110px;">
						<tbody>
							<tr>
								<td style="height: 105px" align="center">
									<img src="res/images/logo.png" border=0>
								</td>
							</tr>
							<tr>
								<td background=res/images/login_2.jpg height=300>
									<table height=300 cellpadding=0 width=900 border=0>
										<tbody>
											<tr>
												<td colspan=2 height=35></td>
											</tr>
											<tr>
												<td width=360></td>
												<td>
													<table cellspacing=0 cellpadding=2 border=0>
														<tbody>
															<tr>
																<td style="height: 28px" width=80>
																	登 录 名：
																</td>
																<td style="height: 28px" width=150>
																	<input id=userName style="width: 130px" name=userName>
																</td>
																<td style="height: 28px" width=370></td>
															</tr>
															<tr>
																<td style="height: 28px">
																	密&nbsp;&nbsp;&nbsp;&nbsp;码：
																</td>
																<td style="height: 28px">
																	<input id=txtpwd style="width: 130px" type=password name=password>
																</td>
																<td style="height: 28px"></td>
															</tr>
															<tr>
																<td style="height: 28px">
																	验&nbsp;证&nbsp;码：
																</td>
																<td style="height: 28px">
																	<input id=captcha style="width: 130px" name=captcha>
																</td>
																<td style="height: 28px">
																	<img src="captcha.svl" onclick="this.src='captcha.svl?d='+new Date()*1" align="top" width="40px"/>
																</td>
															</tr>
															<tr>
																<td style="height: 18px"></td>
																<td style="height: 18px"><label>${loginerror}</label></td>
																<td style="height: 18px"></td>
															</tr>
															<tr>
																<td></td>
																<td>
																	<input id=btn style="border-top-width: 0px; border-left-width: 0px; border-bottom-width: 0px; border-right-width: 0px" type=image src="res/images/login_button.gif">
																</td>
															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
							<tr>
								<td>
									<img src="res/images/login_3.jpg" border=0>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div></div>
			<script type="text/javascript">
				if(window.location.href!=window.parent.location.href)
				{
					window.parent.location.reload();
				}
			</script>	
		</form>
	</body>
</html>
