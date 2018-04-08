    


$("#login").click(function(event) {
    	var data = $("#loginform").serialize();
    	$.post("restservices/authentication", data, function(response) {
    		//geef gebruiker een token voor rechten
    	window.sessionStorage.setItem("sessionToken", response); 
    	window.location.replace('index.html');

 //   	console.log(window.sessionStorage.getItem("sessionToken"));//   //	window.location.href="hoofdpagina.html";
    	}).fail(function(jqXHR, textStatus, errorThrown) {
    	console.log(textStatus);
    	console.log(errorThrown);
    //	$(".expendable").replaceWith("<div class='alert alert-danger' id='response'>Gebruikersnaam of wachtwoord is onjuist.</div>");
    	});
    	
    	});