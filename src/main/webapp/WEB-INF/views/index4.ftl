<!DOCTYPE html>
<html>
  <head>
	    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <title>管理系统</title>
	    <!-- Tell the browser to be responsive to screen width -->
	    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	    <!-- Ionicons -->
	    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
		
		<link href="${ctx}/trd/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="${ctx}/css/main.css" rel="stylesheet">
	    <!-- Tell the browser to be responsive to screen width -->
	    <!-- Bootstrap 3.3.5 -->
	    <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
	    <link rel="stylesheet" href="${ctx}/css/AdminLTE.min.css">
	    <link rel="stylesheet" href="${ctx}/css/skins/_all-skins.min.css">
		<script>
			window.$ctx = '${ctx}';

		</script>
	  </head>
  <body  class="hold-transition skin-blue fixed sidebar-mini">
  
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="${ctx}" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>中科普开</b></span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>www.zkpk.org</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
  		 <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a> 				
      
            <div class="navbar-custom-menu">
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <div class="pull-left">
                  <img src="${ctx}/images/favicon.ico" class="user-image" alt="User Image">
                 </div>
                </a>
                <ul class="dropdown-menu">
                	 <ul class="menu">
                	  
                	 <div class="pull-left">
	               		<li><a href="#">系统配置</a></li>
						<li><a href="#">个性化</a></li>
						<li><a href="${ctx}/logout">注销</a></li>
					</div>
					
					</ul>
                </ul>
              </li>
            </div>
        </nav>
        
      </header>
      <!-- Left side column -->
      <div class="main-sidebar">
        <div class="sidebar">
          <!-- 登录用户头像位置-->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="${ctx}/images/user.png" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">
              <p>${cusername}</p>
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>
	          <!-- 菜单位置 -->
	          <ul class="left-menu">		
	          
	          </ul>
        </div>
      </div>
      <div class="content-wrapper">
    		<div class="content">
	    		
	    		  <section class="content-header">
		          <h1>
		           	 系统消息
		            <small> boxed layout</small>
		          </h1>
		        
		        </section>
	    		
				  <!-- MainPage content -->
					 <div class="callout callout-info">
			            <h4>Tip!</h4>
			            <p>Add the layout-boxed class to the body tag to get this layout. The boxed layout is helpful when working on large screens because it prevents the site from stretching very wide.</p>
			          </div>
			          <!-- Default box -->
			          <div class="box">
			            <div class="box-header with-border">
			              <h3 class="box-title">Title</h3>
			              <div class="box-tools pull-right">
			                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
			                <button class="btn btn-box-tool" data-widget="remove" data-toggle="tooltip" title="Remove"><i class="fa fa-times"></i></button>
			              </div>
			            </div>
			            <div class="box-body">
			              Start creating your amazing application!
			            </div><!-- /.box-body -->
			            <div class="box-footer">
			              Footer
			            </div><!-- /.box-footer-->
			          </div><!-- /.box -->
				<!--end MainPage conten-->  
			<div/>
			<!--end conten-->
		 </div>
	    </div>
	  </div>

	  </div>
		  <footer class="main-footer">
	        <div class="pull-right hidden-xs">
	          <b>Version</b> 2.3.0
	        </div>
	        <strong>Copyright &copy; 2015-2016 <a href="#">xxxx</a>.</strong> All rights reserved.
	      </footer>   
       </div>

    	<script src="${ctx}/trd/requirejs/require.js"></script>
		<script src="${ctx}/js/require.config.js"></script>
		<script src="${ctx}/js/index.js"></script>
		<script src="${ctx}/js/app.js"></script>
		<script src="${ctx}/js/demo.js"></script>
  </body>
</html>
