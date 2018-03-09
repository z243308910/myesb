<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="java.net.InetAddress"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	  response.setHeader("Pragma","No-cache");    
	  response.setHeader("Cache-Control","no-cache");    
	  response.setDateHeader("Expires",   0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>BOSS管理中心</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<jsp:include page="res_link.jsp"></jsp:include>
		<style type="text/css">
			body {margin-left: 0px;margin-top: 0px;margin-right: 0px;margin-bottom: 0px;background-color: #EEF2FB;}
		</style>
		<script type="text/javascript" src="res/js/Highcharts/highcharts.js"></script>
		<script type="text/javascript" src="res/js/Highcharts/exporting.js"></script>
		<script type="text/javascript">
			Boss.menu.tabChange=function(){$("#secTable > tbody > tr > td").bind("click",function(){var index=$("#secTable > tbody > tr > td").index($(this));$("#secTable > tbody > tr > td").addClass("sec1");$(this).removeClass("sec1");$(this).addClass("sec2");$("#mainTable > tbody").hide();$("#mainTable > tbody").eq(index).show();});};
			$(function()
			{
				Boss.menu.tabChange();
			});
		</script>
	</head>
<body><div id="ss"></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="17" valign="top" background="res/images/mail_leftbg.gif"><img src="res/images/left-top-right.gif" width="17" height="29" /></td>
    <td valign="top" background="res/images/content-bg.gif"><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="left_topbg" id="table2">
      <tr>
        <td height="31"><div class="titlebt">欢迎界面</div></td>
      </tr>
    </table></td>
    <td width="16" valign="top" background="res/images/mail_rightbg.gif"><img src="res/images/nav-right-bg.gif" width="16" height="29" /></td>
  </tr>
  <tr>
    <td valign="middle" background="res/images/mail_leftbg.gif">&nbsp;</td>
    <td valign="top" bgcolor="#F7F8F9">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td colspan="2" valign="top">&nbsp;</td>
        <td>&nbsp;</td>
        <td valign="top">&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" valign="top"><span class="left_bt">感谢您使用 一科互联BOSS 网站管理系统</span><br>
              <br>
            <span class="left_txt">&nbsp;<img src="res/images/ts.gif" width="16" height="16"> 提示：<br>
          	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您现在使用的是一科互联开发的一套用于构建商务信息类门户型网站的专业系统！</span><br>
          	<span class="left_txt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您有任何疑问请与管理员联系:<br>
           	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Email: ${adminuser.email }<br>
           	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;手机: ${adminuser.telephone }<br>
			</span>
		</td>
        <td width="7%">&nbsp;</td>
        <td width="40%" valign="top"></td>
      </tr>
      <tr>
        <td colspan="2">&nbsp;</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td colspan="2" valign="top">
              <TABLE width=72% border=0 cellPadding=0 cellSpacing=0 id="secTable">
                <tbody>
                  <TR align=middle height=20>
                    <TD align="center" class="sec2" >统计信息</TD>
                    <TD align="center" class="sec1" >系统参数</TD>
                  </TR>
                </tbody>
              </TABLE>
          <TABLE class=main_tab id="mainTable" cellSpacing=0 cellPadding=0 width=100% border=0>

                <TBODY>
                  <TR>
                    <TD vAlign=top align=middle><TABLE width=98% border=0 align="center" cellPadding=0 cellSpacing=0>
                        <TBODY>
                          <TR>
                            <TD height="5" colspan="3"></TD>
                          </TR>
                          <TR>
                            <TD bgcolor="#FAFBFC">&nbsp;</TD>
                            <TD width="54%" bgcolor="#FAFBFC"><span class="left_txt">服务器数量： </span><span class="left_txt"><a href="softserver_server_list"><font color="red">${mapData['serverCount']}</font></a> 款</span></TD>                            
                            <TD width="42%" height="25" bgcolor="#FAFBFC"><span class="left_txt">软件提供量： </span><span class="left_txt"><a href="softserver_soft_list"><font color="red">${mapData['softCount']}</font></a> 款</span></TD>
                          </TR>
                          <TR>
                            <TD bgcolor="#FAFBFC">&nbsp;</TD>
                            <TD height="25" bgcolor="#FAFBFC"><span class="left_txt">邮件绑定数： </span><span class="left_txt"><a href="subscribe_res_list"><font color="red">${mapData['emailCount']}</font></a> 个</span></TD>
                            <TD height="25" bgcolor="#FAFBFC"><span class="left_txt">手机绑定数： </span><span class="left_txt"><a href="subscribe_res_list"><font color="red">${mapData['mobileCount']}</font></a> 个</span></TD>
                          </TR>
                          <TR>
                            <TD height="5" colspan="3"></TD>
                          </TR>
                        </TBODY>
                    </TABLE></TD>
                  </TR>
                </TBODY>

                <TBODY style="DISPLAY: none">
                  <TR>
                    <TD vAlign=top align=middle><TABLE width=98% border=0 align="center" cellPadding=0 cellSpacing=0>
                        <TBODY>
                          <TR>
                            <TD colspan="3"></TD>
                          </TR>
                          <TR>
                            <TD height="5" colspan="3"></TD>
                          </TR>
                          <TR>
                            <TD width="4%" height="25" background="res/images/news-title-bg.gif"></TD>
                            <TD height="25" background="res/images/news-title-bg.gif" class="left_txt"><span class="TableRow2">服务器IP:&nbsp;&nbsp;<%=InetAddress.getLocalHost().getHostAddress().toString() %></span></TD>
                            <TD height="25" background="res/images/news-title-bg.gif" class="left_txt"><span class="TableRow2">服务器时间:&nbsp;&nbsp;<fmt:formatDate value="<%=new Date() %>"  pattern="yyyy-MM-dd hh:mm:ss"/> </span></TD>
                          </TR>
                          <TR>
                            <TD height="25" bgcolor="#FAFBFC">&nbsp;</TD>
                            <TD width="42%" height="25" bgcolor="#FAFBFC"><span class="left_txt">操作系统:&nbsp;&nbsp;<%=System.getProperty("os.name")%> (<%=System.getProperty("os.arch")%>)&nbsp;<%=System.getProperty("os.version")%></span></TD>
                            <TD width="54%" height="25" bgcolor="#FAFBFC"><span class="left_txt">环境编码:&nbsp;&nbsp;<%=System.getProperty("file.encoding") %></span></TD>
                          </TR>
                          <TR>
                            <TD height="25" bgcolor="#FAFBFC">&nbsp;</TD>
                            <TD height="25" bgcolor="#FAFBFC"><span class="left_txt">TOMCAT版本:&nbsp;&nbsp;<%=application.getServerInfo() %></span></TD>
                            <TD height="25" bgcolor="#FAFBFC"><span class="left_txt">数据库版本:&nbsp;&nbsp;MySql 5.5</span></TD>
                          </TR>
                         
                          <TR>
                            <TD height="5" colspan="3"></TD>
                          </TR>
                        </TBODY>
                    </TABLE></TD>
                  </TR>
                </TBODY>

            	</TABLE>
            </td>
        <td>&nbsp;</td>
        <td valign="top"></td>
      </tr>
      <tr>
        <td height="40" colspan="4"><table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
          <tr>
            <td><div id="container"></div></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td width="2%">&nbsp;</td>
        <td width="51%" class="left_txt"><img src="res/images/icon-mail2.gif" width="16" height="11"> 管理员邮箱：${adminuser.email }<br>
              <img src="res/images/icon-phone.gif" width="17" height="14"> 管理员电话：${adminuser.telephone }</td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
    </td>
    <td background="res/images/mail_rightbg.gif">&nbsp;</td>
  </tr>
  <tr>
    <td valign="bottom" background="res/images/mail_leftbg.gif"><img src="res/images/buttom_left2.gif" width="17" height="17" /></td>
    <td background="res/images/buttom_bgs.gif"><img src="res/images/buttom_bgs.gif" width="17" height="17"></td>
    <td valign="bottom" background="res/images/mail_rightbg.gif"><img src="res/images/buttom_right2.gif" width="16" height="17" /></td>
  </tr>
</table>
</body>
</html>

