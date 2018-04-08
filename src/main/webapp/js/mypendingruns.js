$.ajax({
		url: "restservices/orderlines/pending",
		method: "GET",
		beforeSend: function (xhr) {
	   		var token = window.sessionStorage.getItem("sessionToken");
	   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	   		},
		success: function (orderline) {
			$.each(orderline, function(i, orderline){ 

			
			$.ajax({
				url: "restservices/orders/"+orderline.Order_ID,
				method: "GET",
				beforeSend: function (xhr) {
			   		var token = window.sessionStorage.getItem("sessionToken");
			   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
			   		},
				success: function (order) {
					var status = order.Status;
					var statustext;
				
					 if(status=="Pending"){
		                 statustext =  '<td><span class="badge badge-warning">Pending</span></td>'
					}
				
					else{
		                 statustext =  '<td><span class="badge badge-dark">Error. Contact Ace.</span></td>'
					}
					
					
					$.ajax({
						url: "restservices/accounts/"+order.Account_ID,
						method: "GET",
						beforeSend: function (xhr) {
					   		var token = window.sessionStorage.getItem("sessionToken");
					   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
					   		},
						success: function (acc) {

					$('tbody').append('<tr>'
							+'<th scope="row">'+order.ID+'</th>'
		                    +'<td>'+acc.IGName+'</td>'

		                    +'<td>'+order.TotalLead+'</td>'
		                    +'<td>'+order.TotalMetal+'</td>'
		                    +'<td>'+orderline.Lead+'</td>'
		                    +'<td>'+orderline.Metal+'</td>'
		                    +'<td>'+order.Created+'</td>'

		                    +'<td>'+order.DateOfRun+'</td>'
		                         
		                   +statustext
		                         
		                        +'<td><button type="button" class="btn btn-info m-b-10 m-l-5">View Run</button></td>'
								+'</tr>'

							)	
							
							
						}})
							
					
				}});
			})
			
			
		}});