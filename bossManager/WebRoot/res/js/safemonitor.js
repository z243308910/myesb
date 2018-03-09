Boss.email=new Object();
Boss.msg=new Object();
Boss.rtx=new Object();

Boss.email.opStatus=function(value,rec){if(rec.status==1){return "发送成功";}else if(rec.status==0){return "等待发送";}else if(rec.status==2){return "发送失败";}}
Boss.email.opMail=function(value,rec){if(rec.status==0 || rec.status==2){return "<input type='button' value='再次发送' style='border:1px solid;cursor:pointer' onclick=Boss.email.sendMail(this,"+rec.id+") />";}}
Boss.email.sendMail=function(obj,ids){var parent=$(obj).parent();$.ajax({url:"monit/mail!reSend",data:"data="+ids,type:"post",beforeSend:function(){parent.html(Boss.util.smallLoader())},success:function(msg){parent.html("");Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.errorMessageShow(msg);}});}
Boss.email.delMailTpl=function(){var rows=Boss.grid.getSelctData("mailtpltable");var paths=[];for(var i=0;i<rows.length;i++){paths.push(rows[i].filePath);}Boss.util.commonOPURL("monit/mail!delMailTpl?data="+paths);}
Boss.email.initEmailHistory=function()
{
	$("#emailhistorytable").datagrid({
		title:"",striped: true,collapsible:false,remoteSort: false,idField:'id',rownumbers:true,
		url:"monit/mail?"+$("#searchEmail").serialize(),pagination:true,pageList:[20,30,50],
		toolbar:[
		{id:'btnsave',text:'刷新',disabled:false,iconCls:'icon-reload',handler:function(){Boss.grid.reload("emailhistorytable");}},
		{id:'btndel',text:'删除',iconCls:'icon-remove',handler:function(){Boss.grid.delData("emailhistorytable","monit/mail!delHistory");}
		},
		{id:'btndel',text:'测试邮件服务',iconCls:'icon-sum',handler:function()
			{
				Boss.util.createDialogWin("测试BOSS邮件服务",650,400,"subscribe_test_mail");
			}
		},{
			id:'btndel',text:'搜索',iconCls:'icon-search',handler:function(){Boss.util.dispaly("searchEmail");}
		}],onDblClickRow:function(index,row)
		{
			window.location.href="monit/mail!getHistory?data="+row.id;
		},rowStyler:function(index,row){if (row.status==2){return 'background-color:#7F0055;';}else if(row.status==0){return 'background-color:#FFCC33;';}}
	});
}


Boss.msg.opStatus=function(value,rec){if(rec.status==1){return "发送成功";}else if(rec.status==0){return "等待发送";}else if(rec.status==2){return "发送失败";}}
Boss.msg.opType=function(value,rec){if(rec.type==2){return "接收短信";}else if(rec.type==1){return "发送短信";}}
Boss.msg.opMsg=function(value,rec){if(rec.status==0 || rec.status==2){return "<input type='button' value='再次发送' style='border:1px solid;cursor:pointer' onclick=Boss.msg.sendMsg(this,"+rec.id+") />";}}
Boss.msg.sendMsg=function(obj,ids){var parent=$(obj).parent();$.ajax({url:"monit/msg!reSend",data:"data="+ids,type:"post",beforeSend:function(){parent.html(Boss.util.smallLoader())},success:function(msg){parent.html("");Boss.message.successMessageShow(msg);},error:function(msg){Boss.message.errorMessageShow(msg);}});}
Boss.msg.initMsgHistory=function()
{
	$("#shortmsgtable").datagrid({
		title:"",striped: true,collapsible:false,remoteSort: false,idField:'id',rownumbers:true,
		url:"monit/msg?"+$("#searchMsg").serialize(),pagination:true,pageList:[20,30,50],
		toolbar:[
		{
			id:'btnsave',text:'刷新',disabled:false,iconCls:'icon-reload',handler:function(){Boss.grid.reload("shortmsgtable");}
		},{
			id:'btndel',text:'删除',iconCls:'icon-remove',handler:function(){Boss.grid.delData("shortmsgtable","monit/msg!delHistory");}
		},{
			id:'btndel',text:'测试短信服务',iconCls:'icon-sum',handler:function()
			{
				Boss.util.createDialogWin("测试BOSS短信服务",650,400,"subscribe_test_shortmsg");
			}
		},{
			id:'btndel',text:'搜索',iconCls:'icon-search',handler:function(){Boss.util.dispaly("searchMsg");}
		}],rowStyler:function(index,row){if (row.status==2){return 'background-color:#7F0055;';}else if(row.status==0){return 'background-color:#FFCC33;';}
		}		
	});		
}

Boss.rtx.opStatus=function(value,rec){if(rec.status==1){return "发送成功";}else if(rec.status==0){return "等待发送";}else if(rec.status==2){return "发送失败";}}
Boss.rtx.opType=function(value,rec){if(rec.type==0){return "普通消息";}else if(rec.type==1){return "紧急消息";}}
Boss.rtx.initRtxHistory=function()
{
	$("#rtxtable").datagrid({
		title:"",striped: true,collapsible:false,remoteSort: false,idField:'id',rownumbers:true,
		url:"monit/rtx?"+$("#searchRtx").serialize(),queryParams:{},pagination:true,pageList:[20,30,50],
		toolbar:[
		{
			id:'btnsave',text:'刷新',disabled:false,iconCls:'icon-reload',handler:function(){Boss.grid.reload("rtxtable");}
		},{
			id:'btndel',text:'删除',iconCls:'icon-remove',handler:function(){Boss.grid.delData("rtxtable","monit/rtx!delHistory");}
		},{
			id:'btndel',text:'测试RTX服务',iconCls:'icon-sum',handler:function()
			{
				Boss.util.createDialogWin("测试BOSSRTX服务",650,400,"subscribe_test_rtx");
			}
		},{
			id:'btndel',text:'搜索',iconCls:'icon-search',handler:function(){Boss.util.dispaly("searchRtx");}
		}],rowStyler:function(index,row){if (row.status==2){return 'background-color:#7F0055;';}else if(row.status==0){return 'background-color:#FFCC33;';}
		}		
	});
}