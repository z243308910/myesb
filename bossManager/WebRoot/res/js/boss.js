var Boss=new Object();
Boss.menu=new Object();
Boss.location=new Object();
Boss.message=new Object();
Boss.grid=new Object();
Boss.util=new Object();
Boss.form=new Object();
Boss.json=new Object();

/*****************************把Form中的元素序列化为Json对象**********************************/
Boss.json.createJsonObjFromSeriArray=function(Objects){var json={};for(var i=0;i<Objects.length;i++){json[Objects[i].name]=Objects[i].value;}return json;}

/****************************公共的弹出层的Form异步提交 并刷新grid*********************************************/
Boss.form.initDialogForm=function(gridName,formName,url){$("#"+formName).submit(function(){Boss.form.saveDialogFormRefreshGrid(gridName,formName,url);return false;});}
Boss.form.saveDialogFormRefreshGrid=function(gridName,formName,url){$.ajax({cache:false,url:url,data:$("#"+formName).serialize(),type:"post",beforeSend:function(){Boss.message.progress();},success:function(msg){Boss.message.closeProgress();Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.closeProgress();Boss.message.errorMessageShow(msg);},complete:function(){Boss.util.closeDialogWin();if(gridName!=null&&gridName!=""){$("#"+gridName).datagrid("reload");}}});}
Boss.form.checkDataExist=function(url,message,obj){$.ajax({url:url,data:"code="+obj.value,type:"POST",success:function(msg){if(msg=="true"){Boss.message.errorMessageShow(message);obj.value="";}}});}
Boss.form.submit=function(formName){$("#"+formName).submit();}
Boss.form.reset=function(formName){document.getElementById(formName).reset();}
/**********************判断Grid是否选中了数据*********************************/
Boss.grid.isSelected=function(gridName){var rows =Boss.grid.getSelctData(gridName) ; if(rows.length<1){$.messager.alert("操作提示","请选择要操作的数据","info");return false;}else{return true;}}

/**********************返回Grid选中的数据*********************************/
Boss.grid.getSelctData=function(gridName){var rows = $("#"+gridName).datagrid("getSelections");return rows;};
Boss.grid.getData=function(gridName){var data=$("#"+gridName).datagrid("getRows");return data;}
/***************DataGrid删除数据时调用********************/
Boss.grid.delData=function(tableName,url){if(Boss.grid.isSelected(tableName)){var rows=Boss.grid.getSelctData(tableName);var ids = [];for(var i=0;i<rows.length;i++){ids.push(rows[i].id);}Boss.message.delDataMessageShow(tableName,url,ids);}}
Boss.grid.reload=function(gridName){$("#"+gridName).datagrid("reload")}
Boss.grid.flush=function(gridName,url){$("#"+gridName).datagrid({url:url});	}
Boss.message.delDataMessageShow=function(cotanerId,url,params){$.messager.confirm("删除提示", "您确定删除吗?",function(r){if(r){var pross=$.messager.progress({title:'请稍候',msg:'正在执行操作......'});$.ajax({url:url,data:"data="+params,success:function(msg){$.messager.show({title:'操作提示',msg:msg+"条数据成功删除!",showType:"slide"},$("#"+cotanerId).datagrid("clearSelections"));},complete:function(){try{$.messager.progress('close');$("#"+cotanerId).datagrid("reload");$("#"+cotanerId).treegrid("reload");}catch(e){}},error:function(){Boss.message.errorMessageShow();}});}});}
Boss.grid.removeSelected=function(gridName){var selectedLength = $("#"+gridName).datagrid("getSelections").length;for(var i=0;i<selectedLength;i++){var rows = $("#"+gridName).datagrid("getSelections");i=0;var index = $("#"+gridName).datagrid("getRowIndex", rows[i]);$("#"+gridName).datagrid("deleteRow", index);}}

/***************操作成功时调用********************/
Boss.message.successMessageShow=function(){$.messager.show({title:'操作提示',msg:"操作成功!",showType:"slide"});}
Boss.message.successMessageShow=function(msg){$.messager.show({title:'操作提示',msg:msg,showType:"slide"});}

/***************操作成功时调用********************/
Boss.message.errorMessageShow=function(){$.messager.alert("操作提示","操作失败，服务器异常!","error");}
Boss.message.errorMessageShow=function(msg){$.messager.alert("操作提示",msg,"error");}

/****************进度条********************************/
Boss.message.progress=function(){var pross=$.messager.progress({title:'请稍候',msg:'正在执行操作......'});}
Boss.message.closeProgress=function(){$.messager.progress('close');}

/****防止脱离框架访问单独一个页面****/
Boss.location.checkLocation=function(){if(window.location.href==window.parent.location.href){window.parent.location.href="";}}
Boss.location.forward=function(url){window.location.href=url;}
Boss.location.reload=function(){window.location.reload();}

/************long型的转化为时间类型********************/
Boss.util.formatLongToDate=function(longtime){if(longtime==null){return null;}var dd=new Date(longtime);return dd.getFullYear()+"-"+(dd.getMonth()+1)+"-"+dd.getDate()+" "+dd.getHours()+":"+dd.getMinutes()+":"+dd.getSeconds();}
Boss.util.serilazibleId=function(){return (new Date()).getTime()+1;}
Boss.util.textEmpty=function(data){return (data==null||data=="")?"''":data;}
Boss.util.commonOPURL=function(url){$.ajax({url:url,cache:false,type:"post",beforeSend:function(){Boss.message.progress();},success:function(msg){Boss.message.closeProgress();Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.closeProgress();Boss.message.errorMessageShow(msg);}});}
Boss.util.smallLoader=function(){return "<img src='res/images/loader.gif'/>";}
Boss.util.dispaly=function(elementId){if($("#"+elementId).is(":hidden")){$("#"+elementId).show();}else{$("#"+elementId).hide();}}
Boss.util.createDialogWin=function(title,width,height,url){var window=$("<div id='Bosswindow' iconCls='icon-save' closable='true'  collapsible='false' minimizable='false' maximizable='true'></div>").insertAfter("body");$("#Bosswindow").window({title:title,cache:false,shadow:false,height:height,width:width,modal:true,inline:true,href:url});}
Boss.util.closeDialogWin=function(){$("#Bosswindow").window("destroy");}
Boss.util.historyWin=function(){window.history.go(-1)};

Boss.util.getWeekDateTime=function(date){var array=new Array();array[0]="星期天";array[1]="星期一";array[2]="星期二";array[3]="星期三";array[4]="星期四";array[5]="星期五";array[6]="星期六";return date.getFullYear()+"年"+(date.getMonth()+1)+"月"+date.getDate()+"日 "+array[date.getDay()];}
var timerflag=true;
Boss.util.getNowDate=function(){var date=new Date();var minute=date.getMinutes();if(parseInt(minute)<10){minute="0"+minute;}if(timerflag){var hm= date.getHours()+":"+minute;timerflag=false;$("#timebar").html(hm);}else{timerflag=true;var hm= date.getHours()+" "+minute;$("#timebar").html(hm);}$("#timebar").attr("title",Boss.util.getWeekDateTime(date));}

$(function()
{
	Boss.location.checkLocation();
});