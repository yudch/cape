define([ 'jquery', 'knockout','text!pages/mgr/account/accountinfo.html','uui'],	function($, ko, template) {
	
		var dlgURL = 'pages/mgr/account/accountApprover';
		addRouter(dlgURL);
		
		var viewModel = {
			data : ko.observable({})
		}
	
		viewModel.submitForm = function(){
		    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
		    var nodes = treeObj.getCheckedNodes(true);
		    var idList = "";
		    $.each(nodes, function (index, info) {
		        if (idList != "") {
		            idList += ",";
		        }

		        idList += info.id;
		    });
		    viewModel.data().roles=idList;
		    
			var saveUrl = $ctx + '/mgr/account/create';
			if(viewModel.data().id != 0){
				saveUrl = $ctx + '/mgr/account/update';
			} 
//			alert(JSON.stringify(viewModel.data()));
			$.ajax({
				type: 'POST',
				contentType: 'application/json', 
				url: saveUrl,
				data: JSON.stringify(viewModel.data()),
				dataType: 'json',
				success: function(data) {
					if(data!=null){
						alert("保存成功！");
					} 
				},
				error: function(req, textStatus, errorThrown){
					if(req.responseText){
						alert(JSON.stringify(req.responseText));
					} 
				}
			});
		}

		var loadData = function(id) {
			var asyd = "?"+new Date().getTime();
			var infoUrl = $ctx + '/mgr/account/create';
			
			var roleUrl = $ctx + '/mgr/account/rolelist/0'+asyd;
			if(id && id!=0){
				infoUrl = $ctx + '/mgr/account/update/' + id +asyd;
				
				roleUrl = $ctx + '/mgr/account/rolelist/' + id +asyd;
			}
			$.ajax({
				type: 'GET',
				url: infoUrl,
				dataType: 'json',
				success: function(data) {
					viewModel.data(data);
				},
				error: function() {
					alert("获取详细信息失败!","错误");
				}
			});
			
			// 角色列表
			var setting = {
					check: {
						enable: true
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
			$.ajax({
				type: 'GET',
				url: roleUrl,
				dataType: 'json',
				success: function(data) {
					$.fn.zTree.init($("#roleTree"), setting, data);
				},
				error: function() {
					alert("获取详细信息失败 !","错误");
				}
			});
			
		}
	
		
		viewModel.openDlg = function(){
			 $.refer({
				  contentId: "refcontent",//内容区id，如果不提供，创建弹出框口div，以弹出方式打开参照
				  pageUrl:dlgURL, //自定义参照需要设置此属性
				 // dataUrl:pageUrl, //标准参照需要设置此属性
				  isPOPMode: true,
				  width:600,
				  height:600,
				  params:{id:"1000"},
				  onOk: function(data){
					  alert(JSON.stringify(data));
				  },
				  onCancel: function(){
				 
				  }
			 });
		}
		var init = function(params) {
			//表单校验
			$("#userForm").validate();

			loadData(params[0]);
		}
		
		return {
			'model' : viewModel,
			'template' : template,
			'init' : init
		};
	}
);