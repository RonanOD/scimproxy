
var ScimClient = {
	SORT_ASCENDING : "ascending",
	SORT_DESCENDING : "descending",
	baseUrl : "",
	
	init : function(baseUrl) {
		this.baseUrl = baseUrl;
	}
		
	put : function(user, callback) {
		alert("put");
	},

	patch : function(patch, callback) {
		alert("patch");
	},
	
	get : function(parameters, callback) {
		var url = baseUrl + "/Users",
			query = "?";
			xmlhttp = new XMLHttpRequest(),
			i;
		
		if (parameters.userId) {
			url += "/" + parameters.userId
		} else {
			
		}
		
		if(parameters.attributes) {
			query += "attributes=";
			query += parameters.attributes.join(",");
			for(i=0; i < parameters.attributes.length; i++) {
				query += parameters.attributes[i] + ",";
				query +=
			}
		}
				
		xmlhttp.onreadystatechange = function(event) {
			if (event.target.readyState == XMLHttpRequest.DONE) {
				alert("Get Done");
			}
		}
		xmlhttp.open("GET", url,true);
		xmlhttp.send();
	},
	
	post : function(user, callback) {
		alert("post");
	}
	
}



ScimClient.get({filter : {by : "email", op : "equals", value : "bjensen@example.com"}, 
	            attributes : ["userName", "displayName"],
	            sort : {by : "userName", order : "ascending"}, 
	            pagination : {startIndex : 0, count : 10}}, 
	            function(result) {
	            	if(result.code == 200) {
	            		// success handling 
	            	} else {
	            		// error handling
	            	}
	            });

ScimClient.get({userId : "3f62ce30-dcd6-4dd7-abfe-2352a76f9978",
	            attributes : ["userName", "displayName"]}, 
	           function(result) {
	            	if(result.code == 200) {
	            		// success handling 
	            	} else {
	            		// error handling
	            	}
	            });

