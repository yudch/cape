$(function(){
	$('.navbar-static-top #top-nav').hide();
	$('.navbar-static-top #top-login').show();
})
function doLogin(){
	var plainPassword = $("#password").val(); 
	if(plainPassword.indexOf("_encrypted") < 0){
		var key = RSAUtils.getKeyPair(exponent, '', modulus);
		var encryptedPwd = RSAUtils.encryptedString(key, plainPassword);
		$("#password").val(encryptedPwd + "_encrypted"); 
	}
	
    $("#formlogin").submit();
}