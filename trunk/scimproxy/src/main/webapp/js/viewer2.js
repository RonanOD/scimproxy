

$(document).ready(function () {
	var handleResult = function (data) {
			$("#result").hide("slow", function() {
				document.getElementById("result").innerHTML = "";
				
				$('<pre>', {
					class: 'prettyprint, full',
					text: data
				}).appendTo('#result');
				
				prettyPrint();
				$("#result").show("slow");				
			});
		},
		handleError = function () {
			document.location = "/authenticate.html";
		},
		handleList = function (data) {
			var i, current;
			$("#result").hide("slow");
			document.getElementById("result").innerHTML = "";
			
			data = JSON.parse(data);
			
			var $tbl = $('<table>');
			$('#result').append(
					$("<p>",{text: "Got " + data.totalResults + " " + $("#listType").val() + "(s)."}));
			
			if(data.Resources && data.Resources.length != 0) {
				$tbl.append(
						$('<tr>').append(
								$('<th>').text("Id"),
								$('<th>').text("Resource"),
								$('<th>')));
				
	            for (i = 0; i < data.Resources.length; i++) {
	            	current = data.Resources[i];
	                $tbl.append(
	                		$('<tr>', {class: 'list'}).append(
	                				$('<td>', {class: 'alignTop'}).text(current.id),
	                				$('<td>', {class: 'middle'}).append(
	                						$('<pre>', {class: 'prettyprint, table',text: JSON.stringify(current, null,"  "), id: current.id+"show"}),
            								$("<textarea>", {class: "table", text: JSON.stringify(current, null,"  "), id : current.id + "edit"}).hide(),
            								$("<div>", {id: current.id + "editPassword"}).append(
            									$("<label>", {text: "New password:"}),
            									$("<input>", {type: "password", id: current.id + "password"})).hide()),
	                				$('<td>', {class: 'buttonColumn'}).append(
	                						$("<button>", {class: "list", text:"Edit", id: current.id + "editButton"}).click( function (editId) {
	                							return function () {
	                								// other
	                								$("#" + editId + "show").hide();
	                								$("#" + editId + "edit").show();
	                								$("#" + editId + "editPassword").hide();
	                								// Buttons
	                								$("#" + editId + "editButton").hide();
	                								$("#" + editId + "showPasswordGuiButton").hide();
	                								$("#" + editId + "cancelButton").show();
	                								$("#" + editId + "setPasswordButton").hide();
	                								$("#" + editId + "updateButton").show();
	                								$("#" + editId + "deleteButton").hide();
	                							};
	                						}(current.id)),
	                						$("<button>", {class: "list", text:"Set password", id: current.id + "showPasswordGuiButton"}).click( function (editId) {
	                							return function () {
	                								// other
	                								$("#" + editId + "show").hide();
	                								$("#" + editId + "edit").hide();
	                								$("#" + editId + "editPassword").show();
	                								// Buttons
	                								$("#" + editId + "editButton").hide();
	                								$("#" + editId + "showPasswordGuiButton").hide();
	                								$("#" + editId + "cancelButton").show();
	                								$("#" + editId + "setPasswordButton").show();
	                								$("#" + editId + "updateButton").hide();
	                								$("#" + editId + "deleteButton").hide();
	                							};
	                						}(current.id)),
	                						$("<button>", {class: "list", text: "Cancle", id: current.id + "cancelButton"}).click(function (editId) {
            									return function () {
	                								// other
	                								$("#" + editId + "show").show();
	                								$("#" + editId + "edit").hide();
	                								$("#" + editId + "editPassword").hide();
	                								// Buttons
	                								$("#" + editId + "editButton").show();
	                								$("#" + editId + "showPasswordGuiButton").show();
	                								$("#" + editId + "cancelButton").hide();
	                								$("#" + editId + "setPasswordButton").hide();
	                								$("#" + editId + "updateButton").hide();
	                								$("#" + editId + "deleteButton").show();
            									};
            								}(current.id)).hide(),
            								$("<button>", {class: "list", text: "delete", id: current.id + "deleteButton"}).click(function (deleteType, deleteId, deleteEtag) {
	                							return function() {
	                								$.post("/Viewer2/Delete", 
	                										{ type: deleteType, id: deleteId, etag: deleteEtag }, 
	                										handleResult)
	                										.error(handleError);				
	                							}
	                						}($("#listType").val(), current.id, current.meta.version)),
            								$("<button>", {class: "list", text: "Save password", id: current.id + "setPasswordButton"}).click(function (editId) {
        										return function () {
            										$.post("/Viewer2/SetPassword", 
            												{ password: $("#" + editData.id + "editPassword").val(), id: editData.id }, 
            												handleResult)
            												.error(handleError);	            										
            									};
            								} (current.id)).hide(),
            								$("<button>", {class: "list", text: "Save resource", id: current.id + "updateButton"}).click(function (editType, editData) {
            									return function () {
            										$.post("/Viewer2/Edit", 
            												{ type: editType, operation: "PUT", data: $("#" + editData.id + "edit").val(), id: editData.id, etag: editData.meta.version }, 
            												handleResult)
            												.error(handleError);	            										
            									};
            								}($("#listType").val(), current)).hide())));
	            }
	            
	            $('#result').append($tbl);
	            $('#result').show("slow");
	             
			} else {
				$('<pre>', {
					class: 'prettyprint, full',
				    text: "No resources found."
				}).appendTo('#result');
	            $('#result').show("slow");
            }
			prettyPrint();
		};
	var section = $.url(document.location).attr('anchor');
	section = (section === "" ? "list" : section);
	$("#" + section + "Section").show("slow");
	
	if (section === "configuration") {
		$.post("/Viewer2/Configuration", handleResult).error(handleError);
	}

	$("a").click(function () {
		var section = $.url(document.location).attr('anchor');
		section = (section === "" ? "list" : section);
		$("#" + section + "Section").hide("slow");
		$(event.target.hash + "Section").show("slow");

        $('#result').hide("slow");
		document.getElementById("result").innerHTML = "";
        $('#result').show();
		if (event.target.hash === "#configuration") {
			$.post("/Viewer2/Configuration", handleResult).error(handleError);
		}
	});

	$("#listForm").submit(function () {
		var listType = $("#listType").val(),
			listFilter = $("#listFilter").val(),
			listSort = $("#listSort").val(),
			listSortOrder = $("#listSortOrder").val(),
			listEncoding = $("#listEncoding").val(),
			listAttributes = $("#listAttributes").val();
		
		$.post("/Viewer2/List", 
				{ type: listType, filter: listFilter, sort: listSort, attributes: listAttributes, encoding: listEncoding, sortOrder: listSortOrder }, 
				handleList)
			.error(handleError);
		
		return false;
	});
	
	$("#addForm").submit(function () {
		var addType = $("#addType").val(),
			addEncoding = $("#addEncoding").val(),
			addData = $("#addData").val();
		
		$.post("/Viewer2/Add", 
				{ type: addType, data: addData, encoding: addEncoding }, 
				handleResult)
			.error(handleError);
		
		return false;
	});

	$("#editForm").submit(function () {
		var editType = $("#editType").val(),
			editOperation = $("#editOperation").val(),
			editId = $("#editId").val(),
			editEtag = $("#editEtag").val(),
			editData = $("#editData").val();
		
		$.post("/Viewer2/Edit", 
				{ type: editType, operation: editOperation, data: editData, id: editId, etag: editEtag }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
	
	$("#deleteForm").submit(function () {
		var deleteType = $("#deleteType").val(),
			deleteEtag = $("#deleteEtag").val(),
			deleteId = $("#deleteId").val();
		
		$.post("/Viewer2/Delete", 
				{ type: deleteType, id: deleteId, etag: deleteEtag }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
	
	$("#bulkForm").submit(function () {
		var bulkData = $("#bulkData").val();
		
		$.post("/Viewer2/Bulk", 
				{ data: bulkData }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
});
