	
var token = sessionStorage.getItem('sessionToken');

$.ajax({
		url: "restservices/accounts/token/"+token,
		method: "GET",
		beforeSend: function (xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			},
		success: function (acc) {
			acc = JSON.parse(acc);
			console.log(acc);
			console.log(acc.Role);
			if(acc.Role == 'admin'){
				$.get("adminmenu.html", function(data) {
					$("#sidebar_placeholder").replaceWith(data);
				});
			}
			else{
				$.get("menu.html", function(data) {
					$("#sidebar_placeholder").replaceWith(data);
				});
			}			
		}});	
	

