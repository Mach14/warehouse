$.ajax({
		url: "restservices/orders/pending",
		method: "GET",
		beforeSend: function (xhr) {
	   		var token = window.sessionStorage.getItem("sessionToken");
	   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	   		},
		success: function (order) {
			$.each(order, function(i, o){ 
				
				$.ajax({
					url: "restservices/accounts/"+o.Account_ID,
					method: "GET",
					beforeSend: function (xhr) {
				   		var token = window.sessionStorage.getItem("sessionToken");
				   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
				   		},
					success: function (acc) {
						
						var status = o.Status;
						var statustext;
						if(status=="Pending"){
			                 statustext =  '<td><span class="badge badge-warning">Pending</span></td>'
						}
						else{
			                 statustext =  '<td><span class="badge badge-dark">Error. Contact Ace.</span></td>'
						}
						$('tbody').append('<tr>'
								+'<th scope="row">'+o.ID+'</th>'
			                    +'<td>'+acc.IGName+'</td>'
			                    +'<td>'+o.TotalLead+'</td>'
			                    +'<td>'+o.TotalMetal+'</td>'
			                    +'<td>'+o.Created+'</td>'
			                    +'<td>'+o.DateOfRun+'</td>'
			                         
			                   +statustext
			                         
			                        +'<td><button type="button" class="btn btn-info m-b-10 m-l-5">View Run</button></td>'
									+'</tr>'

								)					
				}
				});
	
			});
		}
	});
