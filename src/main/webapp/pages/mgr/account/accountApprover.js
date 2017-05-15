define([ 'jquery', 'knockout','text!pages/mgr/account/accountApprover.html'],function($, ko, template){
		var pageUrl = '/mgr/account/page?page=';
		var viewModel = {
			data : {
				content : ko.observableArray([]),
				totalPages : ko.observable(0),
				number : ko.observable(0),
				pageSize : ko.observable(0),
				totalCount:ko.observable(0)
			},
			setData : function(data) {
				alert(JSON.stringify(data.content));
				this.data.content(data.content);
				this.data.totalPages(data.totalPages);
				this.data.number(data.number + 1);
				this.data.pageSize(data.size);
				this.data.totalCount(data.totalElements);
			
			},
			pageUrl : pageUrl
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
					//alert(JSON.stringify(data.content[0].loginName));
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
	
		var init = function(params) {
			//JSON.stringify(params.params) 传入的参数 json格式
//			alert(JSON.stringify(params.params.id));
			
			var pageNum = viewModel.data.number();
			alert(pageNum);
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