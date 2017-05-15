<!DOCTYPE html>
<html>
  <head>
	    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
	    <meta http-equiv="Pragma" content="no-cache">
	    <meta http-equiv="expires" content="Mon, 23 Jan 1978 12:52:30 GMT">
	    <title>管理系统</title>
	    <!-- Tell the browser to be responsive to screen width -->
	    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    	<link rel="stylesheet" href="${ctx}/css/font-awesome.min.css">
	    <!-- Ionicons -->
	    <link rel="stylesheet" href="${ctx}/css/ionicons.min.css">
		<link href="${ctx}/trd/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="${ctx}/css/main.css" rel="stylesheet">
	    <!-- Tell the browser to be responsive to screen width -->
	    <link rel="stylesheet" href="${ctx}/css/AdminLTE.min.css">
	    <link rel="stylesheet" href="${ctx}/css/skins/_all-skins.min.css">
	     <link rel="stylesheet" href="${ctx}/js/sys/uui/css/common.css">
	  
		<script>
			window.$ctx = '${ctx}';
		</script>
  </head>
  
  <body class="hold-transition skin-blue sidebar-mini">
  
    <div class="wrapper">

      <header class="main-header">
  <!-- Logo -->
        <a href="#" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>普开</b></span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>中科普开</b></span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
             <a href="${ctx}/logout" class="btn btn-default btn-flat">退出</a>
            </ul>
          </div>
        </nav>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <div class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <div class="sidebar">
          <!-- Sidebar user panel -->
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
           <ul class="sidebar-menu">
          	 <li class="treeview">
		       	  <ul class="left-menu">		
		          
		    	  </ul> 
	    	  </li>
           </ul>
        </div>
        <!-- /.sidebar -->
      </div>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Main content -->
        <div class="content">
			
        </div><!-- /.content -->
      </div><!-- /.content-wrapper -->
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 1.0.0
        </div>
        <strong>Copyright &copy; 2014-2015 <a href="#">XXXXX</a>.</strong> All rights reserved.
      </footer>

      <!-- 右上角默认隐藏区域 -->
  
      </div>
    
    </div><!-- ./wrapper -->
    <script src="${ctx}/trd/jquery/jquery-1.9.1.min.js"></script>
    <script src="${ctx}/trd/bootstrap/js/bootstrap.min.js"></script>
    <script src="${ctx}/trd/requirejs/require.js"></script>
	<script src="${ctx}/js/require.config.js"></script>
	<script src="${ctx}/js/index.js"></script>
	<script src="${ctx}/js/app.js"></script>
  </body>
</html>
