var getUrlParameter = function getUrlParameter(sParam) {
    	var sPageURL = decodeURIComponent(window.location.search.substring(1)),
    	sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    	for (i = 0; i < sURLVariables.length; i++) {
        	sParameterName = sURLVariables[i].split('=');

        	if (sParameterName[0] === sParam) {
            	return sParameterName[1] === undefined ? true : sParameterName[1];
        	}
    	}
	};
var id = getUrlParameter('id');
//$.noConflict();
	
	$.ajax({
		url : "restservices/orders/" + id,
		method : "GET",
		beforeSend: function (xhr) {
			var token = window.sessionStorage.getItem("sessionToken");
			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			},
		success : function (data) {

			$("#DateOfRun").val(data.DateOfRun);
			$("#TotalLead").val(data.TotalLead);
			$("#TotalMetal").val(data.TotalMetal);
			$("#Comment").val(data.Comment);
			
			$.ajax({
				url : "restservices/accounts/" + data.Account_ID,
				method : "GET",
				success : function (acc) {
					console.log(acc.Username);
					//<img id="ss1" src="images/uploaded/Ace/Order-8/1.png"></img>

				//	$("#ss1").html('<img src="images/uploaded/'+acc.Username+'/Order-'+data.ID+'/1.png' +'" />');
				//	$("#ss1").attr("src", 'images/uploaded/'+acc.Username+'/Order-'+data.ID+'/1.png');
				//	$("#ss2").attr("src", 'images/uploaded/'+acc.Username+'/Order-'+data.ID+'/2.png');
				//	$("#ss2").attr("onclick", 'opentab(images/uploaded/'+acc.Username+'/Order-'+data.ID+'/2.png)');

					// var win = window.open('images/uploaded/'+acc.Username+'/Order-'+data.ID+'/2.png', '_blank');
					 // win.focus();
					  $("#ss1").click(function(){
						  var win = window.open('images/uploaded/'+acc.Username+'/Order-'+data.ID+'/1.png', '_blank');
						  win.focus();
					  });
					  
					  $("#ss2").click(function(){
						  var win = window.open('images/uploaded/'+acc.Username+'/Order-'+data.ID+'/2.png', '_blank');
						  win.focus();
					  });
					  
					  
				}
			});

		}
	});
	
	
	