$("#submitForm2").click(function(event){
	console.log('submit button clicked!');
	$.ajax({
				url: "restservices/orders",
				success: function(response){
					var data = $("#submitRunForm").serialize();
			    	$.post("restservices/orders", data, function(response){
	   			//	$("#submitRunForm")[0].reset(); 
	   				console.log("success order created");
			    	})
			    	},
	   				error: function(response){
	   				console.log(response);
	   			}
	    	});	
	
});