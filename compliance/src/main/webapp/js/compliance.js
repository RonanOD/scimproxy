$(document).ready(
	function() {

		var handleError = function(event) {
			$("#compliance-error").text("An error occurred. " + event.statusText);
			$("#compliance-error-container").show();
		};

		var handleComplianceResult = function(data){

	        $.each(data.results, function(i) {
	        	var wire = data.results[i].wire;
	        	wire = $.base64.decode(wire);
	        	wire = "TODO: do modal!";
	        	
	        	var icon = "<span class=\"label label-success\">Success</span>";
	        	if(data.results[i].status != 4) {
	        		icon = "<span class=\"label label-important\" rel=\"tooltip\" title=\"" + data.results[i].message + "\">Failed</span>";	
	        	}
	        	
	        	$("#compliance-result").append("<tr><td>" + data.results[i].name + "</td><td>" + icon + "&nbsp;<span class=\"label label-info\" rel=\"tooltip\" title=\"" + wire + "\">Wire</span></td></tr>")
	        });
	        
	        var options = {
	        		legend: 'none',
	        		colors:['darkgrey','lightgrey']
	              };

	        // Create and populate the data table.
	        var chartData = google.visualization.arrayToDataTable([
	                                                          ['Result', 'Number'],
	                                                          ['Failed', data.stats.failed],
	                                                          ['Success', data.stats.success]
	                                                          ]);
	        
	        var totalTests = data.stats.failed + data.stats.success;

        	$("#compliance-statistics").append("<p>Tests: " + totalTests + "</p>");
        	$("#compliance-statistics").append("<p>Success: "+ data.stats.failed + "</p>");
        	$("#compliance-statistics").append("<p>Failed: " + data.stats.success + "</p>");
	        
	        // Create and draw the visualization.
	        new google.visualization.PieChart(document.getElementById('compliance-chart')).draw(chartData, options);
	        			
			$("#compliance-result-container").show();
		    $("[rel=tooltip]").tooltip();

		};
		
		$("#sendCompliance").click(function () {
			$("#compliance-result").empty();
			$("#compliance-result-container").hide();
			$("#compliance-error-container").hide();
        
			var complianceUrl = $("#complianceUrl").val();
			$.post("/compliance", { url: complianceUrl }, handleComplianceResult).error(handleError);
			
			return false;
		});

	});

