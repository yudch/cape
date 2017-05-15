define([ 'jquery', 'knockout', 'text!pages/mgr/function/functionlist.html' ], function($, ko, template) {

	var infoUrl = "/mgr/function/functioninfo/:id";
	
	var addUrl = '/mgr/function/functioninfo/0';
	
	var deleteUrl = '/mgr/function/delete/';
	
	var pageUrl = '/mgr/function/page?page=';

	// 添加详细信息页路由
	addRouter(infoUrl);
	
	var viewModel = {
		data : {
			content : ko.observableArray([]),
			
			totalPages : ko.observable(0),
			number : ko.observable(0)
		},
		searchText : ko.observable(""),
		setData : function(data) {
			this.data.content(data.content);
			this.data.totalPages(data.totalPages);
			this.data.number(data.number + 1);
		},
		infoUrl : infoUrl,
		addUrl : addUrl,
		deleteUrl : deleteUrl,
		pageUrl : pageUrl
	};

	viewModel.add = function(){
		window.router.setRoute(this.addUrl);	
	}
		
	viewModel.del = function() {
		$.ajax({
			type : 'DELETE',
			dataType : 'json',
			async : false,
			url : $ctx + deleteUrl + this.id,
			success : function(data) {
				if (data){
					jAlert('删除成功!')
					var pageNum = viewModel.data.number();
					viewModel.data.content.remove(this);
					viewModel.load(pageNum);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				jAlert("调用删除服务报错!!");
			}
		});
		
	}
		
	viewModel.load = function(pageIndex){
		
		 var queryData = {};
         $(".form-search").find(".input_search").each(function(){
             queryData[this.name] = this.value;
         });
       
		$.ajax({
			type : 'GET',
			url : $ctx + this.pageUrl + pageIndex,
			data : queryData,
			dataType : 'json',
			success : function(data) {
				viewModel.setData(data);
				$("#pagination").pagination({
					totalPages : viewModel.data.totalPages(),
					currentPage : viewModel.data.number(),
					page : function(page) {
						viewModel.load(page);
					}
				})							
			}
		});
		
	}
		
	viewModel.searchPage = function() {
		this.load(1);                                                                                          
	}
		
	var init = function() {
		var pageNum = viewModel.data.number();
		if(pageNum > 0){
			viewModel.load(pageNum);
		} else {
			viewModel.load(1);
		}
	}
	
	return {
		'model' : viewModel,
		'template' : template,
		'init' : init
	};

});