$("#usernameValidation").hide();
$("#ignameValidation").hide();
$("#passwordValidation").hide();

var token = sessionStorage.getItem('sessionToken');
if(token == null || token.length < 30){
	if(location.pathname == '/Warehouse/page-register.html' || location.pathname == '/Warehouse/page-login.html' || location.pathname == '/page-register.html' || location.pathname == '/page-login.html'){
	}
	else{
	window.location.replace('page-login.html');
}
}

$("#logout").click(function(event) {
	window.sessionStorage.setItem("sessionToken", "guest");
	var token = window.sessionStorage.getItem("sessionToken");
	window.location.href="page-login.html";
});


$("#register").click(function(event){
	var username = $("#username").val();
	var igname = $("#igname").val();
	var password = $("#password").val();
	if(username.length > 30 || username.length < 1 || username == null){
		$("#usernameValidation").show();
	}
	else if(igname.length > 40 || igname.length < 1){
		$("#usernameValidation").hide();
		$("#ignameValidation").show();
	}

	else if(password.length > 24 || password.length < 6){
		$("#usernameValidation").hide();
		$("#ignameValidation").hide();
		$("#passwordValidation").show();
	}
	
	 else {
		 $("#usernameValidation").hide();
		 $("#ignameValidation").hide();
		 $("#passwordValidation").hide();

	    	$.ajax({
				url: "restservices/accounts",
				success: function(response){
					var data = $("#registeraccount").serialize();
			    	$.post("restservices/accounts", data, function(response){
	   				$("#registeraccount")[0].reset(); 
	   			  swal({
	   	            title: "Your account has been created",
	   	            type: "success",
	   	            confirmButtonColor: "#5cb85c",
	   	            confirmButtonText: "Login",
	   	            closeOnConfirm: false
	   	        },
	   	        
	   	        function(isConfirm){
	   	            if (isConfirm) {
	   	                window.location.href="page-login.html";
	   	            }
	   	        }   
	   		     );
			    	})
			    	},
	   				error: function(response){
	   				 sweetAlert("Oops...", "Something went wrong!", "error");
	   			}
	    	});	
	    }
});
