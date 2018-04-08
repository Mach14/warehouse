

//my account
	var token = window.sessionStorage.getItem("sessionToken");
	$.ajax({
	    		url: "restservices/accounts/token/"+token,
	    		method: "GET",
	    		beforeSend: function (xhr) {
	    			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	    			},
	    		success: function (accountE) {
	    			let account = JSON.parse(accountE);
	    			$("#matsEarned").html(account.MatsEarned);
	    			$("#matsReceived").html(account.MatsReceived);
	    			$("#matsToReceive").html(account.MatsToReceive);
	    			$("#igName").html(account.IGName);
	    			$("#username").html(account.Username);
	    			$("#joined").html(account.Created);
	    			$("#rank").html(account.Rank);
	    			$("#tier").html(account.Tier);


	    		}});


//my runs



//my pending runs