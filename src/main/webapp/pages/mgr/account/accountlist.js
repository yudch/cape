define([ 'jquery', 'knockout', 'text!pages/mgr/account/accountlist.html' ], function($, ko, template) {

	var infoUrl = "/mgr/account/accountinfo/:id";
	
	var addUrl = '/mgr/account/accountinfo/0';
	
	var deleteUrl = '/mgr/account/delete/';
	
	var pageUrl = '/mgr/account/page?page=';

	// 添加详细信息页路由  跳转到相应目录下的js文件，暂时该js所绑定的html 
	/**
	 * 通过directorjs 实现html跳转
	 */
	addRouter(infoUrl);
	var viewModel = {
		data : {
			content : ko.observableArray([]),
			totalPages : ko.observable(0),
			number : ko.observable(0),
			pageSize : ko.observable(0),
			totalCount:ko.observable(0)
		},
		searchText : ko.observable(""),
		setData : function(data) {
			this.data.content(data.content);
			this.data.totalPages(data.totalPages);
			this.data.number(data.number + 1);
			this.data.pageSize(data.size);
			this.data.totalCount(data.totalElements);
		
		},
		infoUrl : infoUrl,
		addUrl : addUrl,
		deleteUrl : deleteUrl,
		pageUrl : pageUrl,
	};

	viewModel.add = function(){
	
		window.router.setRoute(this.addUrl);	

	};
	viewModel.del = function() {
		$.ajax({
			type : 'DELETE',
			dataType : 'json',
			async : false,
			url : $ctx + deleteUrl + this.id,
			success : function(data) {
				if (data){
					alert('删除成功!');
					try{
						var pageNum = viewModel.data.number();
//						alert("开始删除页面数据");
						viewModel.data.content.remove(this);
//						alert("准备进入load方法");
						viewModel.load(pageNum);
//						alert("load方法加载完成");
					
					}catch(e){
						alert(e.message); 
					}
				
					
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("调用删除服务报错!!");
			}
		});

	};
		
	viewModel.load = function(pageIndex){
		 var queryData = {};
         $(".form-search").find(".input_search").each(function(){
             queryData[this.name] = this.value;
         });
		$.ajax({
			type : 'GET',
			url : $ctx + this.pageUrl + pageIndex+"&no="+new Date().getTime(),
			data : queryData,
			dataType : 'json',
			success : function(data) {
				viewModel.setData(data);
				
				$("#pagination").pagination({
					totalPages : viewModel.data.totalPages(),
					currentPage : viewModel.data.number(),
					pageSize : viewModel.data.pageSize(),
					totalCount: viewModel.data.totalCount(),
					page : function(page) {
						viewModel.load(page);
					}
				});							
			}
		});
		
	};
		
	viewModel.searchPage = function() {
		this.load(1);                                                                                          
	};
	
	var init = function() {
		var pageNum = viewModel.data.number();
		if(pageNum > 0){
			viewModel.load(pageNum);
		} else {
			viewModel.load(1);
		}
	};
	
	return {
		'model' : viewModel,
		'template' : template,
		'init' : init
	};

});