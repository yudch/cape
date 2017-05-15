<!DOCTYPE html>
<html lang="en">
   <link rel="stylesheet" href="${ctx}/css/dl.css" />
   <link href="${ctx}/js/sys/uui/css/u.css" rel="stylesheet">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Admin Theme</title>

    <!-- Bootstrap Core CSS -->
    <link href="${ctx}/trd/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/js/login/metisMenu.min.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="${ctx}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    	<script>
		$(function() {
		    $('#side-menu').metisMenu();
		
		});
		$(function() {
		    $(window).bind("load resize", function() {
		        topOffset = 50;
		        width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
		        if (width < 768) {
		            $('div.navbar-collapse').addClass('collapse');
		            topOffset = 100; // 2-row-menu
		        } else {
		            $('div.navbar-collapse').removeClass('collapse');
		        }
		
		        height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
		        height = height - topOffset;
		        if (height < 1) height = 1;
		        if (height > topOffset) {
		            $("#page-wrapper").css("min-height", (height) + "px");
		        }
		    });
		
		    var url = window.location;
		    var element = $('ul.nav a').filter(function() {
		        return this.href == url || url.href.indexOf(this.href) == 0;
		    }).addClass('active').parent().parent().addClass('in').parent();
		    if (element.is('li')) {
		        element.addClass('active');
		    }
		});
	</script>
</head>

<body>
	<script>
		window.$ctx = '${ctx}';
		var exponent = '${exponent}';
		var modulus = '${modulus}';
	</script>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form  method="post" id="formlogin" action="${ctx}/login/formLogin">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="username"  name="username" id="username" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value=""  id="password">
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input name="remember" type="checkbox" value="Remember Me">Remember Me
                                    </label>
                                </div>
                                <#if (accounterror?exists  )>
									<div class="form-group">
									      <div > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									        <font color="red"> ${accounterror}   </font>
									      </div>
								    </div>
								</#if>
                                <!-- Change this to a button or input when using this as a form -->
                                <button type="button" class="btn btn-lg btn-success btn-block" onclick="doLogin()">登     录</button>
                            </fieldset>
                          
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- jQuery -->
    <script src="${ctx}/trd/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctx}/trd/bootstrap/bootstrap.min.js"></script>
	 <script src="${ctx}/js/login/metisMenu.min.js"></script>
	<script src="${ctx}/trd/jQuery-cookie/jquery.cookie.js"></script>
	<script src="${ctx}/js/security.js"></script>
	<script src="${ctx}/js/login.js"></script>
</body>

</html>
