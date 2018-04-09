//manage members
$.ajax({
		url: "restservices/accounts/",
		method: "GET",
		beforeSend: function (xhr) {
	   		var token = window.sessionStorage.getItem("sessionToken");
	   		xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
	   		},
		success: function (acc) {
			$.each(acc, function(i, acc){ 
				$('#formtbodymembers').append('<tr><form id="updateMemberForm'+acc.ID+'"><td scope="row">'+acc.Username+'</td>'
						+'<td hidden><input name="ID" value="'+acc.ID+'" id="accID" readonly /></td>'
                       +'<td>'+acc.IGName+'</td>'
                       + '<td><select class="form-control custom-select" tabindex="1" id="rank'+acc.ID+'" name="Rank">'
                       +' <option value="'+acc.Rank+'">'
                       +acc.Rank
                       +'</option>'
                             +'<option value="Don">Don</option>'
                             +'<option value="Boss">Boss</option>'
                             +'<option value="Underboss">Underboss</option>'
                             +'<option value="Consigliere">Consigliere</option>'
                             +'<option value="Lieutenant">Lieutenant</option>'
                             +'<option value="Caporegime">Caporegime</option>'
                             +'<option value="Soldier">Soldier</option>'
                             +'<option value="Associate">Associate</option>'
                             +'<option value="Enforcer">Enforcer</option>'
                             +'<option value="Mercenary">Mercenary</option>'
                             +'</select></td>'
                             +'<td><select class="form-control custom-select"  tabindex="1" id="tier'+acc.ID+'" name="Tier">'
                             +' <option value="'+acc.Tier+'"">'
                             +acc.Tier
                             +'</option>'
                             +'<option value="0">0</option>'
                             +'<option value="1">1</option>'
                             +'<option value="2">2</option>'
                             +'<option value="3">3</option>'
                             +'</select></td>'
                             +'<td>'+acc.TotalLead+'</td>'
                             +'<td>'+acc.TotalMetal+'</td>'
                             +'<td>'+acc.Created+'</td>'
                             +'<td><select class="form-control custom-select" tabindex="1" id="status'+acc.ID+'" name="Status">'
                            +' <option value="'+acc.Status+'">'
                             +acc.Status
                             +'</option>'
                             +'<option value="Member">Member</option>'
                             +'<option value="Non Active">Non Active</option>'
                             +'<option value="Faction Killed">Faction Killed</option>'
                             +'</select>'
                             +'</td>'
                             +'<td>'
                             +'<button type="button" class="btn btn-warning m-b-10 m-l-5" id="updateMember'+acc.ID+'">Update Member</button>'
                             +'<button type="button" class="btn btn-info m-b-10 m-l-5" id="viewMember'+acc.ID+'">View Member</button>'
                             +'</td>'
                             +'</form></tr>' 
					)		   				
				
					 $("#updateMember"+acc.ID).click(function() {
					//	 var uri = "/restservices/accounts/" + acc.ID;
						 			var ID = $("#accID").val();
									var tier = $("#tier"+acc.ID+" option:selected").val();
									var rank1 = document.getElementById("rank"+acc.ID);

									var rank  = rank1.options[rank1.selectedIndex].value;
									var status = $("#status"+acc.ID+" option:selected").val();
									
						 $.ajax({
								url: "restservices/accounts/"+acc.ID,
								beforeSend: function (xhr) {
					    			xhr.setRequestHeader( 'Authorization', 'Bearer ' + token);
					    			},
								success: function(response){
								//	var data = $("#updateMemberForm"+acc.ID).serialize();

									var data = "ID=" + ID + "&Tier=" + tier+"&Rank="+rank+"&Status="+status;

							    	$.post("restservices/accounts/"+acc.ID, data, function(response){
					   			//	$("#submitRunForm")[0].reset(); 
					   				console.log("success acc updated created");
							    	})
							    	},
					   				error: function(response){
					   				console.log(response);
					   			}
					    	});	
						 
						 
						 
						 
						 
					
						 });
			});
		}
	});
 

//manage runs

//pending runs


//manage materials
 

 
 

 