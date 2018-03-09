$.extend($.fn.validatebox.defaults.rules,{
	email:{validator:function(value,param){
				var len=$.trim(value).length;
				var flag=param==null?true:(len>=param[0]&&len<=param[1]);
				return flag&&(/^(\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*)*$/i.test(value));
			},
			message:"请输入有效的邮箱"},
			
	length:{validator:function(value,param ){
				var len=$.trim(value).length;
				return len>=param[0]&&len<=param[1];
			},
			message:"请输入长度为{0}~{1}的字符"},
					
	password:{validator:function(value,param){
				var len=$.trim(value).length;
				var flag=param==null?true:(len>=param[0]&&len<=param[1]);
				return flag&&(/^[\@A-Za-z0-9\!\#\$\%\^\&\*\.\~]*$/.test(value));
			},
			message:"名称包含空格或非法字符"},
			
	//验证名称，可以是中文或英文		
	name:{validator:function(value,param){	
				var len=$.trim(value).length;
				var flag=param==null?true:(len>=param[0]&&len<=param[1]);
				return flag&&(/^([A-Za-z]|[\u4E00-\u9FA5])+$/i.test(value)); 
			},
			message:"只能输入中文或英文！"},
	
	username:{validator:function(value,param){	
				var len=$.trim(value).length;
				var flag=param==null?true:(len>=param[0]&&len<=param[1]);
				return flag&&(/^[\u4e00-\u9FA5]*[\w|0-9]*[@|\.|\-|_]*[\u4e00-\u9FA5|\w|0-9|@|\.|\-|_]*$/i.test(value));
			},
			message:"请输入长度为{0}~{1}的字符,以汉字、数字、字母开头,支持(.-_)"},
		
	ip:{validator:function(value){
			return /^((25[0-5]|2[0-4]\d|1?\d?\d)\.){3}(25[0-5]|2[0-4]\d|1?\d?\d)$/i.test(value);
		},
		message:"请输入有效的IP地址"},
		
	alphanum:{validator:function(value,param){
				var len=$.trim(value).length;
				var flag=param==null?true:(len>=param[0]&&len<=param[1]);
				return flag&&(/^[\w|0-9]*$/i.test(value));
			},
			message:"只能输入字母或数字"},
			
	// 验证是否包含空格和非法字符		
	unnormal:{validator:function(value) { 
            return /^(\w|[\u4E00-\u9FA5])*$/g.test(value); 
        }, 
        message : "输入值不能为空和包含其他非法字符"},
        
    unblank:{validator:function(value,param){	
		var len=$.trim(value).length;
		var flag=param==null?true:(len>=param[0]&&len<=param[1]);
		return flag&&(/^[\u4e00-\u9FA5|\w|0-9|@|\.|\-|_|\/]*$/i.test(value));
	},
	message:"不能含空格,允许输入@、-、_、/"},
}); 