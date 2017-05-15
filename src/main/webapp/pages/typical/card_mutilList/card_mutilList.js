var viewModel = {
	dataTable1: new $.DataTable({
		meta: {
			'billnumber': {},
			'datatime': {},
			'company': {},
			'department': {},
			'person': {},
			'money': {},
			'email': {},
			'phone': {},
			'landline': {},
			'beardeparment': {},
			'project': {}
		}
	}),
	dataTable2: new $.DataTable({
		meta: {
			'address': {},
			'datatime': {},
			'arriveAddress': {},
			'costType': {},
			'reason': {},
			'money': {}
		}
	}),
	dataTable3: new $.DataTable({
		meta: {
			'loanBill': {},
			'loanPerson': {},
			'loanDepartment': {},
			'offMoney': {},
			'costMoney': {},
			'offTime': {},
			'effectTime': {},
			'project': {},
		}
	})
}

viewModel.dataTable1.on("select", function(event) {

	app.serverEvent().addDataTable("dataTable2", "all").fire({
		url: 'dataTable2.json',
		ctrl: '',
		method: '',
		type: 'GET', //注意：正常开发时请去掉此参数，默认为post
		success: function(data) {

		}
	})
	
	
	
	app.serverEvent().addDataTable("dataTable3", "all").fire({
		url: 'dataTable3.json',
		ctrl: '',
		method: '',
		type: 'GET', //注意：正常开发时请去掉此参数，默认为post
		success: function(data) {

		}
	})

})


var app = $.createApp()
app.init(viewModel)

app.serverEvent().addDataTable("dataTable1", "all").fire({
	url: 'dataTable1.json',
	ctrl: '',
	method: '',
	type: 'GET', //注意：正常开发时请去掉此参数，默认为post
	success: function(data) {
		viewModel.dataTable1.setRowSelect(0)
	}
})