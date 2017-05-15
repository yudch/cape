<html xmlns="http://www.w3.org/1999/xhtml"><head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <title>登录</title>
    <link rel="stylesheet" href="${ctx}/css/dl.css" />
   <link href="${ctx}/js/sys/uui/css/u.css" rel="stylesheet">
</head>
<body>
	<script>
		window.$ctx = '${ctx}';
		var exponent = '${exponent}';
		var modulus = '${modulus}';
	</script>
	<form  method="post" id="formlogin" action="${ctx}/login/formLogin">
		<div id="entry" class=" w1">
			<div id="bgDiv" class="mc ">
				<div clstag="pageclick|keycount|20150112abd|48" id="entry-bg" style="width: 511px; height: 455px; background: url(&quot;${ctx}/images/dl1.png&quot;) no-repeat scroll 0px 0px transparent; position: absolute; left: -44px; top: -44px;">

				</div>
				
				<div class="form " >
					<div class="item fore1">
						<span>邮箱/用户名/已验证手机</span>
						<div class="item-ifo">
							<input type="text" autocomplete="off" tabindex="1" value="" class="text" name="username" id="username">				
						</div>
					</div>
					<div class="item fore2">
						<span>密码</span>
						<div class="item-ifo">
							<input type="password" autocomplete="off" tabindex="2" class="text" name="password" id="password">
						</div>
					</div>
					<div class="item login-btn2013">
						<button type="button" class="btn btn-danger btn-entry" onclick="doLogin()">登     录</button>
					</div>
					<#if (accounterror?exists  )>
							<div class="form-group">
							      <div > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							        <font color="red"> ${accounterror}   </font>
							      </div>
						    </div>
					</#if>
				</div>
			</div>
			<div class="free-regist">
				<span><a  href="#">免费注册&gt;&gt;</a></span>
			</div>
		</div>
	</form>
	<script src="${ctx}/trd/jquery/jquery-1.11.2.js" type="text/javascript"></script>
	<script src="${ctx}/trd/jQuery-cookie/jquery.cookie.js"></script>
	<script src="${ctx}/js/security.js"></script>
	<script src="${ctx}/js/login.js"></script>
</body>
</html>