Boss.software=new Object();
Boss.server=new Object();
Boss.dir=new Object();

/**********************服务器操作*****************************/
Boss.server.opViewServer=function(value,rec){return "<a href='soft/server!viewServer?svId="+rec.id+"'>查看</a>";}
Boss.server.checkIPExist=function(obj){Boss.form.checkDataExist("soft/server!checkIPExist","存在相同的服务器IP，请重新输入!",obj);};

/**********************软件操作**********************************/
Boss.software.editStatus=function(sid,opName,value){$.ajax({cache:false,url:"soft/soft!editSoftwareStatus",data:"sid="+sid+"&op="+opName+"&value="+value,type:"post",beforeSend:function(){Boss.message.progress();},success:function(msg){Boss.message.closeProgress();Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.closeProgress();Boss.message.errorMessageShow(msg);},complete:function(){$("#softwaretable").datagrid("reload");}});}
Boss.software.opSoftware=function(value,rec){return "<a href='soft/soft!viewSoftware?softId="+rec.id+"' > 查看 </a>";}
Boss.software.submitServer=function(){var rows=Boss.grid.getData("unbindsoftwaretable");var ids = [];for(var i=0;i<rows.length;i++){ids.push(rows[i].id);};$("#softIds").val(ids);	var ips = $("#ip").val();	$("input[name=subip]").each(function(){ips +=","+$(this).attr("value")});	$("#serverips").val(ips);$("#serverForm").submit();};
Boss.software.checkSoftwareCode=	function(obj){Boss.form.checkDataExist("soft/soft!checkSoftCodeExist","存在相同的软件代号，请重新输入!",obj);};
Boss.software.changeProTypecharacterType=function(obj){var types=["HTTP","SOAP","JMS","FTP","SOCKET"];var type=types[obj-1];return type;}

/**********************软件部署操作******************************/
Boss.dir.checkServicedirCode=function(obj){Boss.form.checkDataExist("soft/dir!checkServiceCodeExist","存在相同的服务代号，请重新输入!",obj);};
Boss.dir.opServicedir=function(value,rec){return "<a href='soft/dir!viewServiceDir?serviceId="+rec.id+"' > 查看 </a>";};
Boss.dir.opSoftAndServicedir=function(value,rec){return "<a href='javascript:Boss.dir.checkIsSoftOrService("+rec.id+")' > 查看 </a>";};
Boss.dir.checkIsSoftOrService=function checkIsSoftOrService(id){var level = $("#servicesdirtable").treegrid("getLevel" ,id);if(level== 1){id=id-100;Boss.location.forward("soft/soft!viewSoftware?softId="+id);}if(level == 2){Boss.location.forward("soft/dir!viewServiceDir?serviceId="+id);};}
Boss.dir.changeMethodCharacterType=function(obj){var types=["NULL","POST","GET"];var type=types[obj] ;return type;};
Boss.dir.opServicedirParam=function(value,rec){return "<a href=javaScript:Boss.util.createDialogWin('参数信息',500,350,'soft/param!viewServicedirParam?paramId="+rec.id+"') > 查看 </a>"}
Boss.dir.changeParamTypeCharacterType=function(obj){var types=["整形","浮点型","布尔型","字符串","时间型"];var type=types[obj-1];return type;};

/***********************软件服务操作****************************/
Boss.dir.AjaxLoadService=function(softId){$.ajax({cache:false,url:"soft/dir!getServiceBySoftId",data:"softId="+softId,type:"post",success:function(msg){var node=$("#softservicestable").treegrid("getSelected");$("#softservicestable").treegrid("append",{parent:node.id,data:eval(msg)});},error:function(msg){Boss.message.errorMessageShow("加载数据出错!"+msg);}});};

Boss.software.submitSoft=function()
{
	var softServerID =$("#softServerID").val();
	if(softServerID == "" ){
		$("#softServerID").val(0);
	};
	var rows=Boss.grid.getData("unbindServicedirtable");
	var ids = [];for(var i=0;i<rows.length;i++){ids.push(rows[i].id);}
	$("#serviceDirIds").val(ids);
	$("#softwareForm").submit();
}
