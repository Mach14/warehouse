$.ajax({
		url: "restservices/orderlines/",
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
					if(status=="Approved"){
		                 statustext =  '<td><span class="badge badge-primary">Approved</span></td>'
					}
					else if(status=="Pending"){
		                 statustext =  '<td><span class="badge badge-warning">Pending</span></td>'
					}
					else if(status=="Denied"){
		                 statustext =  '<td><span class="badge badge-danger">Denied</span></td>'
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
							$("#totalLead").html(acc.TotalLead);
			    			$("#totalMetal").html(acc.TotalMetal);
			    			$("#totalMats").html(acc.MatsEarned);
			    			
			    			
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
		                         
		                        +'<td><button type="button" class="btn btn-info m-b-10 m-l-5" onclick="goToRunPage('+order.ID+')" >View Run</button></td>'
								+'</tr>'

							)	
							
							
						}})
							
					
				}});
			})
			
			
		}});

function goToRunPage(ID) {
	window.location="run.html?id=" + ID;
}