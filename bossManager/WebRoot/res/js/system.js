Boss.system=new Object();

/****************系统配置信息***************************/
Boss.system.addSysConfig=function(formName){$("#"+formName).submit(function(){Boss.system.submitSysConfig(formName,"sys/config!addSysConfig");return false;});};
Boss.system.editSysConfig=function(formName){$("#"+formName).submit(function(){Boss.system.submitSysConfig(formName,"sys/config!editSysConfig");return false;});};
Boss.system.submitSysConfig=function(formName,url){$.ajax({cache:false,url:url,data:$("#"+formName).serialize(),type:"post",beforeSend:function(){var flag=$("#"+formName).form("validate");if(flag){Boss.message.progress();}else{return flag;}},success:function(msg){Boss.message.closeProgress();Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.closeProgress();Boss.message.errorMessageShow(msg);},complete:function(){}});};
Boss.system.changeChannelCharacterType=function(obj){var types=["默认通道","cargoon","sofreight","BBS","PO","BC"];var type=types[obj-1000];return type;};

Boss.system.checkConfigCodeExist=function(obj){Boss.form.checkDataExist("sys/config!checkConfigCodeExist","存在相同的配置代码，请重新输入!",obj);};
Boss.system.opEmail=function(value,rec){return "<a href=javaScript:Boss.system.dialogViewEmail("+rec.id+") > 查看 </a>";};
Boss.system.dialogViewEmail=function(id){Boss.util.createDialogWin("查看邮件配置信息",500,350,"sys/config!getSysConfig?configId="+id);};

Boss.system.opShortmsg=function(value,rec){return "<a href=javaScript:Boss.system.dialogViewShortmsg("+rec.id+") > 查看 </a>";};
Boss.system.dialogViewShortmsg=function(id){var channelpanel=$('<div id="dialogshortmsgwin" iconCls="icon-save" closable="true"  collapsible="false" minimizable="false" maximizable="false"></div>').insertAfter("#shortmsgconfigtable");$("#dialogshortmsgwin").window({title:"查看短信配置信息",cache:false,shadow:false,height:350,width:500,modal:true,inline:true,href:"sys/config!getSysConfig?configId="+id});};

Boss.system.checkAdminExist=function(obj){Boss.form.checkDataExist("login!checkExist","存在相同的用户名，请重新输入!",obj);};
Boss.system.opAdmin=function(value,rec){return "<a href=javaScript:Boss.system.dialogViewAdmin('"+rec.userName+"') > 查看 </a>";};
Boss.system.dialogViewAdmin=function(userName){Boss.util.createDialogWin("查看系统管理员",500,400,"sys/admin!viewAdmin?userName="+userName);};

Boss.system.canEditInputTextarea=function(formname){$("#"+formname+" input,#"+formname+" textarea").removeClass("noborderinput");$("#"+formname+" input,#"+formname+" textarea").attr("readonly",false);$("#"+formname+"savebtn").show();$("#"+formname+"editbtn").hide();};
Boss.system.readonlyInputTextarea=function(formname){$("#"+formname+"savebtn").hide();$("#"+formname+"editbtn").show();$("#"+formname+" input,#"+formname+" textarea").addClass("noborderinput");$("#"+formname+" input,#"+formname+" textarea").attr("readonly",true);};

/***************采集器操作*********************/
Boss.system.opTracktor=function(value,rec){return "<a href='javascript:Boss.system.dialogViewTracktor("+rec.id+")' > 查看 </a>";};
Boss.system.dialogViewTracktor=function(id){var channelpanel=$('<div id="dialogtracktorwin" iconCls="icon-save" closable="true"  collapsible="false" minimizable="false" maximizable="false"></div>').insertAfter("#tracktorconfigtable");$("#dialogtracktorwin").window({title:"查看采集器信息",cache:false,shadow:false,height:300,width:550,modal:true,inline:true,href:"monit/track!viewTracktor?id="+id});};
Boss.system.checkTracktorExist=function(obj){Boss.form.checkDataExist("monit/track!checkExist","存在相同的采集器代号，请重新输入!",obj);};

Boss.system.opViewApiLog=function(value,rec){return "<a href='monit/apiLogs!viewSoftware?apiId="+rec.id+"'>查看</a>";}
