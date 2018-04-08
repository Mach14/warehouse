	$.ajax({
	    		url: "restservices/accounts/totalaccounts",
	    		method: "GET",
	    		beforeSend: function (xhr) {
	    			var token = window.sessionStorage.getItem("sessionToken");
	    			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	    			},
	    		success: function (accounts) {
	    			$("#totalMembers").html(accounts.TotalAccounts);
	    		}});
	
	$.ajax({
		url: "restservices/orders/totallead",
		method: "GET",
		beforeSend: function (xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			},
		success: function (lead) {
			$("#totalLead").html(lead.TotalLead);
		}});	
	
	$.ajax({
    		url: "restservices/orders/totalmetal",
    		method: "GET",
    		beforeSend: function (xhr) {
    			var token = window.sessionStorage.getItem("sessionToken");
    			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
    			},
    		success: function (metal) {
    			$("#totalMetal").html(metal.TotalMetal);
    		}});	
	
	$.ajax({
	    		url: "restservices/orders/totalmaterials",
	    		method: "GET",
	    		beforeSend: function (xhr) {
	    			var token = window.sessionStorage.getItem("sessionToken");
	    			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	    			},
	    		success: function (mats) {
	    			$("#totalMats").html(mats.TotalMaterials);
	    		}});
	
	
	
	
	