define([ 'jquery', 'knockout', 'text!pages/example/datatabledemo/dataTable.html', 'uui', 'biz'], function($,ko,template) {
	
	var urlDispatch = $ctx+'/evt/dispatch' ;
	
	var ctrlId = 'iweb.DemoController' ;
	
	var init = function(id, viewSpace) {
		
		var viewModel = {
			dataTable1 : new $.DataTable({
				params:{
					"cls" : "uap.web.example.entity.DataTableDemo"
				},
				meta : {
					'theid' : {},
					'code' : {},
					'name' : {},
					'memo' : {},
					'isdefault' : {}
				},
				pageSize: 20,
				pageCache:true
			}),
			
			addRow: function() {
				viewModel.dataTable1.createEmptyRow();
			},
			
			delRow: function(model, event) {
				if(viewModel.dataTable1.getSelectedIndexs().length < 1 ){
					$.showMessageDialog({type:"info",title:"提示",msg:"请选择要删除的行!",backdrop:true});
					return ;
				}
				
				if(confirm("确定要删除吗?")){
					app.serverEvent().addDataTable("dataTable1", "current").fire({
						url : urlDispatch,
						ctrl : ctrlId,
						method : 'del'
					})
				}
			
			},
			saveInfo: function() {
				app.serverEvent().addDataTable("dataTable1", "change").fire({
					url : urlDispatch,
					ctrl : ctrlId,
					method : 'update',
					success : function(data) {
						data = JSON.parse(data); 
						if(data && "success"==data.flag){
							$.showMessageDialog({type:"info",title:"提示信息",msg:data.msg,backdrop:true});
						} 
					}
				})
			},
			
			pageChange: function (pageIndex,pageSize){
				var dt = app.getDataTable("dataTable1")
				if(dt.hasPage(pageIndex)){
					dt.setCurrentPage(pageIndex)
				} else {
					viewModel.dataTable1.pageSize(pageSize) ;
					app.serverEvent().addDataTable("dataTable1","all").addParameter('pageIndex',pageIndex).fire({
						url : urlDispatch,
						ctrl : ctrlId,
						method : 'loadData'
					})
				}
			},
			
			upClick:function(){
				var index = viewModel.dataTable1.currSelectedIndex;
				if(index == -1 || index == 0) return;
				var selectRow = this.dataTable1.getRow(index)
				this.dataTable1.removeRow(index)
				this.dataTable1.insertRow(index-1,selectRow)
			},
			
			downClick:function(){
				var index = viewModel.dataTable1.currSelectedIndex;
				var length = viewModel.dataTable1.getAllRows().length;
				if(index == -1 || index == length-1) return;
				var selectRow = this.dataTable1.getRow(index)
				this.dataTable1.removeRow(index)
				this.dataTable1.insertRow(index+1,selectRow)
			},
			
			searchPage:function(){
				 var queryData = {};
		         $(".form-search").find(".input_search").each(function(){
		             queryData[this.name] = this.value;
		         });
		         viewModel.dataTable1.addParams( queryData) ;
		         app.serverEvent().addDataTable("dataTable1", "all").fire({
		 			url : urlDispatch,
		 			ctrl : ctrlId,
		 			method : 'loadData'
		 		})
			},
			
			combodata: [{
				pk: 'Y',
				name: '是'
			}, {
				pk: 'N',
				name: '否'
			}],
			
			comboSelect:function(item){
				window.item = item
			}
		}

		var app = $.createApp()
		app.init(viewModel, viewSpace)
        
		app.serverEvent().addDataTable("dataTable1", "all").fire({
			url : urlDispatch,
			ctrl : ctrlId,
			method : 'loadData'		 
			/*
			error: function(req, textStatus, errorThrown){
 				alert("查询报错!");
			}
			*/
		})
		
		window.viewModel = viewModel
	}

	return {
		'template' : template,
		'init' : init
	};
})