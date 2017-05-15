define([ 'jquery', 'knockout', 'text!pages/example/demo/testrestful.html' ], function($, ko, template) {

	var viewModel = {
		data : {
			content : ko.observableArray([])
		}
	};

	//本地后端直接签名后发起http请求调用
	viewModel.callRestfulServiceDirect = function(){
		var url = $ctx + "/restful/call";
		var signUrl = "http://localhost:8081/ecmgr/api/demo/restservice";
		$.ajax({
			type : 'POST',
			dataType : 'json',
			async : false,
			url : url,
			data : {'callUrl':signUrl},
			success : function(data) {
				if ("fail" == data.result){
					jAlert('调用服务失败!')
				} else {
					jAlert(data.result)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				jAlert('调用服务出错!')
			}
		});
	};
	
	//本地后端签名后，前端再次以签名后的方式ajax调用
	viewModel.testRestFul = function(){
		var url = $ctx + "/sign/signRequest"
		var signUrl = "http://localhost:8081/ecmgr/api/demo/testrestful";
		$.ajax({
			type : 'POST',
			dataType : 'json',
			async : false,
			url : url,
			data : {'signUrl':signUrl},
			success : function(data) {
				if ("fail" == data.result){
					jAlert('调用签名服务失败!')
				} else {
					callRestFultService(data.result,signUrl);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				jAlert('调用签名服务失败!')
			}
		});
	};
	
	function callRestFultService(sign,callUrl){
		$.ajax({
			type : 'GET',
			dataType : 'jsonp',
			async : true,
			url : callUrl,
			data : {
				"sign" : sign,
				"clientType" : "UAP_MOBILE"
			},
			contentType : 'application/json',
	        jsonp : "jsonpcallback",
			success : function(data) {
				if (data){
					jAlert('调用成功!')
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				jAlert("调用服务报错!!");
			}
		});
		
	}
		
	
	var init = function() {
	}
	
	return {
		'model' : viewModel,
		'template' : template,
		'init' : init
	};

});