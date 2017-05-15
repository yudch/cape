define([ 'jquery', 'knockout', 'text!pages/example/demo/demolist.html' ], function($, ko, template) {

	var infoUrl = "/example/demo/demoinfo/:id";
	
	var addUrl = '/example/demo/demoinfo/0';
	
	var deleteUrl = '/example/demo/delete/';
	
	var pageUrl = '/example/demo/page?page=';

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
	};
	
		
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
		 $('#markedAll').attr('checked',false) ;
		 var queryData = {};
         $(".form-search").find(".input_search").each(function(){
             queryData[this.name] = this.value;
         });
       
		$.ajax({
			type : 'GET',
			url : $ctx + this.pageUrl + pageIndex + "&page.size=20",
			data : queryData,
			dataType : 'json',
			success : function(data) {
				viewModel.setData(data);
				$("#pagination").pagination({
					jumppage : false,
					pageSize : false,
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

	// 选择列表中的checkbox
	viewModel.selectSingle = function(){
		var nCnt = 0;
		var chks = document.getElementsByName("marked");
			for(var i=0; i<chks.length; i++){
			if(chks[i].checked){
				nCnt++;
			}
		}
		// 是否全部选中
		document.getElementById("markedAll").checked  = (nCnt==chks.length);	
	}
	
	// 选择选中全部或取消选中全部
	viewModel.selectAll = function() {
		var current_checkbox = document.getElementById("markedAll");
	    var chks = document.getElementsByName("marked");
	    if (chks != null){
		    for (var i=0; i<chks.length; i++) {
				chks[i].checked  =  current_checkbox.checked;
		    }		    
	    }
	}
	
	//确定是否有选中信息
	viewModel.haschecked =function(){
		var nCnt = 0;
		var chks = document.getElementsByName("marked");
		if(chks != null){
			for(var i=0; i<chks.length; i++){
				if(chks[i].checked){
					nCnt++;
				}
			}			
		}
		if(nCnt == 0){
			alert("没有选中任何信息!");
			return false;
		}
		return true;
	}
	
	// 选择列表中的checkbox
	viewModel.getmarked  =  function(){
		var str = '';
		var chks = document.getElementsByName("marked");
			for(var i=0; i<chks.length; i++){
			if(chks[i].checked){
				str += chks[i].value+",";
			}
		}
		return str.substring(0, str.length-1);
	}
	
	viewModel.delBatch=  function(){
		var cfm=confirm("确认删除")
		if( !cfm ){
			return ;
		}
		if( !this.haschecked() ){
			return ;
		}
	    var marked = this.getmarked() ;
	    var params = {
	    		"ids": marked
	    }
		$.ajax({
			type : 'GET',
			async : false,
			data: jQuery.param(params),
			url :"example/demo/deleteBatch",
			success : function(data) {
				if (data) {
					 alert("删除成功!");
					 var pageNum = viewModel.data.number();
				     viewModel.load(pageNum);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				jAlert("删除服务报错!!", "错误");
			}
		});
		
	}
	
	return {
		'model' : viewModel,
		'template' : template,
		'init' : init
	};

});