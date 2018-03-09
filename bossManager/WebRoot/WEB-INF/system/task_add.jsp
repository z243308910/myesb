<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.TaskGroup"/>
<jsp:directive.page import="com.echounion.bossmanager.common.enums.TaskName"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <base href="<%=basePath%>">
    <title>添加系统任务</title>
	<jsp:include page="../res_link.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>res/js/system.js"></script>
	<style type="text/css">
		BODY{font-size:12px;}
	</style>
	<script type="text/javascript">
		$(function()
		{
			Boss.system.addTrigger("simpTriggerForm");
			Boss.system.addTrigger("trigger1");
			Boss.system.addTrigger("trigger2");
			Boss.system.addTrigger("trigger3");
			Boss.system.addTrigger("trigger4");
			Boss.system.addTrigger("trigger5");			
			Boss.system.checkTaskInstall("BOSS_<%=TaskName.SYSCPUMEMTASK.getCode()%>");
			Boss.system.checkTaskInstall("BOSS_<%=TaskName.SYSBACKUP.getCode()%>");			
			Boss.system.checkTaskInstall("<%=TaskName.SYSCPUMEMTASK.getCode() %>");
		});
	</script>
  </head>
  
  <body>
        <form id="simpTriggerForm" method="post">
        	<input type="hidden" name="triggerType" value="1"/>
            <table>
                <tr>
                    <th colspan="3" bgcolor="gray"><b>Simple Trigger</b></th>
                </tr>
                <tr>
                    <td>Trigger名称：</td>
                    <td ><input type="text"  class="easyui-validatebox" data-options="required:true" name ="task_triggerName" size="20"></td>
                    <td>（必填） </td>
                </tr>
                <tr>
                    <td> Trigger分组：</td>
                    <td>
                        <select name="task_triggerGroup">
                            <option value="DEFAULF">defaulf</option>
                            <option value="行政组">行政组</option>
                            <option value="财务组">财务组</option>
                            <option value="开发组">开发组</option>
                        </select>
                    </td>
                    <td>（Trigger分组，Quartz默认组为DEFAULF）</td>
                </tr>
                <tr>
                    <td>开始时间： </td>
                    <td><input class="easyui-datetimebox" name="task_startTime" data-options="required:true" style="width:150px"></td>
                    <td>（Trigger执行开始时间，必填）</td>
                </tr>
                <tr>
                    <td> 结束时间：</td>
                    <td><input class="easyui-datetimebox" name="task_endTime" data-options="required:true" style="width:150px"></td>
                    <td>（Trigger执行结束时间，可以不填写 </td>
                </tr>
                <tr>
                    <td> 执行次数：</td>
                    <td> <input " type="text" name="task_repeatCount" size="20"></td>
                    <td> 次（表示Trigger启动后执行多少次结束，不填写执行一次）</td>
                </tr>
                <tr>
                    <td> 执行间隔：</td>
                    <td><input " type="text" name="task_repeatInterval" size="20"></td>
                    <td> 秒（表示Trigger间隔多长时间执行一次，不填写前后两次执行没有时间间隔，直到任务结束） </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" value="添加Trigger"></td>
                </tr>
             </table>
         </form>
				
         <form id="trigger1" action="" method="post">
	         <input type="hidden" name="triggerType" value="2"/>
             <table>
                 <tr>
                     <th colspan="3" bgcolor="gray">
                         <b>Cron Trigger 1</b>
                     </th>
                 </tr>
                 <tr>
                      <td nowrap>Trigger 名称： </td>
                      <td><input type="text" class="easyui-validatebox" data-options="required:true" name ="triggerName" size="20"></td>
                      <td>（必填）</td>
                  </tr>
                  <tr>
                      <td nowrap> Cron表达式：</td>
                      <td><input type="text" class="easyui-validatebox" data-options="required:true" name ="cronExpression" size="20"></td>
                      <td>（必填，Cron表达式(如"0/10 * * ? * * *"，每10秒中执行调试一次)，对使用者要求比较，要会写Cron表达式，实际项目中不适用）</td>
                  </tr>
                  <tr>
                      <td colspan="3"><input type="submit" value="添加Trigger"></td>
                  </tr>
               </table>
         </form>
				
		 <form id="trigger2" action="" method="post">
				<input type="hidden" name="triggerType" value="3"/>
				<input type="hidden" name="group" value="DEFAULT"/>
                <table>
                    <tr>
                        <th colspan="4" bgcolor="gray" width="100%"><b>Cron Trigger 2</b></th>
                    </tr>
                    <tr>
                        <td>Trigger 名称：</td>
                        <td colspan="3"><input type="text" class="easyui-validatebox" data-options="required:true" name ="triggerName" size="20">（必填）</td>
                    </tr>
                    <tr>
                        <td>每</td>
                        <td><input type="text" name ="valType" size="5"></td>
                        <td>
                            <select name="selType">
                            	<option value="second">秒</option>
								<option value="minute">分</option>									
                            </select>
                        </td>
						<td>执行一次（必填，范围 0-59）<td>
                    </tr>
                    <tr>
                        <td colspan="4"><input type="submit" value="添加Trigger"></td>
                    </tr>
                </table>
		</form>
		
                <table border="1px" style="border:1px" cellpadding="5" cellspacing="0">
                    <tr>
                        <th colspan="4" bgcolor="red"><b>系 统 默 认 任 务</b></th>
                    </tr>
                    <tr align="center">
                        <td>任务名称：</td>
                        <td>任务周期</td>
                        <td>说明</td>
                        <td>操作</td>
                    </tr>
                    <tr>
                    	<td colspan="4">
						 <form id="trigger3" action="" method="post">
						 <input type="hidden" name="triggerType" value="3"/>
						 	<table border="0px" style="border:1px" cellpadding="5" cellspacing="0">
				                    <tr align="center">
				                        <td>系统性能监控<input type="hidden" name="group" value="<%=TaskGroup.SYSTEMTASK.getCode() %>"/><input type="hidden" name ="triggerName" value="<%=TaskName.SYSCPUMEMTASK.getCode() %>"></td>
				                        <td>
				                        	<input type="text" name ="valType" size="5" value="30">
				                            <select name="selType">
												<option value="minute">分</option>                            
				                            	<option value="second">秒</option>
				                            </select>执行一次（必填，范围 0-59）
				                        </td>
										<td>监控管理系统CPU、内存使用状况</td>
										<td id="op<%=TaskName.SYSCPUMEMTASK.getCode() %>">&nbsp;</td>
				                    </tr>
						 	</table>
						 </form>						             		
                    	</td>
                    </tr>
                    <tr>
                    	<td colspan="4">
                    		<form id="trigger4" action="" method="post">
                    		<input type="hidden" name="triggerType" value="3"/>
			                  	<table border="0px" style="border:1px" cellpadding="5" cellspacing="0">
			                    	<tr align="center">
			                        	<td>BOSS系统性能监控<input type="hidden" name="group" value="BOSS_<%=TaskGroup.SYSTEMTASK.getCode() %>"/><input type="hidden" name ="triggerName" value="BOSS_<%=TaskName.SYSCPUMEMTASK.getCode() %>"></td>
			                        	<td>
			                        		<input type="text" name ="valType" size="5" value="30">
			                            	<select name="selType">
												<option value="minute">分</option>                            
			                            		<option value="second">秒</option>
			                            	</select>执行一次（必填，范围 0-59）
			                        	</td>
										<td>监控BOSS系统CPU、内存使用状况</td>
										<td id="opBOSS_<%=TaskName.SYSCPUMEMTASK.getCode() %>">&nbsp;</td>
			                    </tr>                    
			                	</table>                    		
                    		</form>
                    	</td>
                    </tr>
					<tr>
						<td colspan="4">
							 <form id="trigger5" action="" method="post">
							 <input type="hidden" name="triggerType" value="2"/>
							 	<table border="0px" style="border:1px" cellpadding="5" cellspacing="0">
					                    <tr align="center">
					                        <td>BOSS数据备份<input type="hidden" name="group" value="BOSS_<%=TaskGroup.SYSTEMTASK.getCode() %>"/><input type="hidden" name ="triggerName" value="BOSS_<%=TaskName.SYSBACKUP.getCode() %>"></td>
					                        <td>
					                        	<input type="text" name ="cronExpression" size="20" value="0 0 23 ? * 6">
					                        </td>
											<td>系统在指定时间内自动进行数据备份(默认每周5晚上24时) Cron表达式(如"0/10 * * ? * * *")</td>
											<td id="opBOSS_<%=TaskName.SYSBACKUP.getCode() %>">&nbsp;</td>
					                    </tr>
							 	</table>
							 </form>						
						</td>
					</tr>                    
  </body>
</html>
