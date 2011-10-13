

$(document).ready(function () {
	var handleResult = function (data) {
		document.getElementById("result").innerHTML = "";
		$("#result").append("<pre class=\"prettyprint\">" + data + "</pre>");
		prettyPrint();
		},
		handleError = function () {
			document.location = "/authenticate.html";
		};
	var section = $.url(document.location).attr('anchor');
	section = (section === "" ? "list" : section);
	$("#" + section).show();

	$("a").click(function () {
		var section = $.url(document.location).attr('anchor');
		section = (section === "" ? "list" : section);
		$("#" + section).hide();
		$(event.target.hash).show();
		document.getElementById("result").innerHTML = "";
	});

	$("#listForm").submit(function () {
		var listType = $("#listType").val(),
			listFilter = $("#listFilter").val(),
			listSort = $("#listSort").val(),
			listSortOrder = $("#listSortOrder").val(),
			listEncoding = $("#listEncoding").val(),
			listAttributes = $("#listAttributes").val();
		
		$.post("/Viewer/List", 
				{ type: listType, filter: listFilter, sort: listSort, attributes: listAttributes, encoding: listEncoding, sortOrder: listSortOrder }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
	
	$("#addForm").submit(function () {
		var addType = $("#addType").val(),
			addEncoding = $("#addEncoding").val(),
			addData = $("#addData").val();
		
		$.post("/Viewer/Add", 
				{ type: addType, data: addData, encoding: addEncoding }, 
				handleResult)
			.error(handleError);
		
		return false;
	});

	$("#editForm").submit(function () {
		var editType = $("#editType").val(),
			editOperation = $("#editOperation").val(),
			editId = $("#editId").val(),
			editEncoding = $("#listEncoding").val(),
			editData = $("#editData").val();
		
		$.post("/Viewer/Edit", 
				{ type: editType, operation: editOperation, data: editData, id: editId, encoding: editEncoding }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
	
	$("#deleteForm").submit(function () {
		var deleteType = $("#deleteType").val(),
			deleteEncoding = $("#listEncoding").val(),
			deleteId = $("#deleteId").val();
		
		$.post("/Viewer/Delete", 
				{ type: deleteType, id: deleteId, encoding: deleteEncoding }, 
				handleResult)
			.error(handleError);
		
		return false;
	});
});
